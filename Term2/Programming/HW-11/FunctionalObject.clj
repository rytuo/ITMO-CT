(def Op #(fn [& args] {:sign % :args (vec args)}))
(def Add (Op '+))
(def Subtract (Op '-))
(def Divide (Op '/))
(def Multiply (Op '*))
(def Negate (Op 'negate))
(def Exp (Op 'exp))
(def Ln (Op 'ln))

(def Obj #(fn [val] {:sign % :args val}))
(def Variable (Obj 'var))
(def Constant (Obj 'const))

(defn div [f & args]
  (cond (empty? args) f
        (zero? (first args)) (* f ##Inf)
        :else (apply div (/ f (first args)) (rest args))))

(defn evaluate [expr vars]
  (cond
    (= (expr :sign) 'const) (expr :args)
    (= (expr :sign) 'var) (get vars (expr :args))
    (= (expr :sign) '/) (apply div (mapv #(evaluate % vars) (expr :args)))
    (= (expr :sign) 'negate) (- (evaluate (first (expr :args)) vars))
    (= (expr :sign) 'ln) (Math/log (Math/abs (evaluate (first (expr :args)) vars)))
    (= (expr :sign) 'exp) (Math/exp (evaluate (first (expr :args)) vars))
    :else (apply (eval (expr :sign)) (mapv #(evaluate % vars) (expr :args)))))

(defn toString [expr]
  (cond
    (= (expr :sign) 'const) (format "%.1f" (expr :args))
    (= (expr :sign) 'var) (str (expr :args))
    :else (str "("
               (expr :sign)
               (apply str (mapv #(str " " (toString %)) (expr :args)))
               ")")))

(defn diff [expr var]
  (cond
    (= (expr :sign) 'const) (Constant 0.0)
    (= (expr :sign) 'var) (if (= (expr :args) var) (Constant 1.0) (Constant 0.0))
    (or (= (expr :sign) '+)
        (= (expr :sign) '-)
        (= (expr :sign) 'negate)) (apply (Op (expr :sign)) (mapv #(diff % var) (expr :args)))
    (= (expr :sign) '*) (apply Add (mapv #(apply Multiply (update-in (expr :args) [%] (fn [old] (diff old var))))
                                         (range (count (expr :args)))))
    (= (expr :sign) '/) (let [f ((expr :args) 0)
                              s #(if (> (count (expr :args)) 2) (apply Multiply (rest (expr :args)))
                                                                ((expr :args) 1))]
                          (Divide (Subtract (Multiply (diff f var) (s))
                                            (Multiply f (diff (s) var)))
                                  (Multiply (s) (s))))
    (= (expr :sign) 'exp) (Multiply (Exp (first (expr :args))) (diff (first (expr :args)) var))
    (= (expr :sign) 'ln) (Multiply (Divide (Constant 1.0) (first (expr :args))) (diff (first (expr :args)) var))))

(def opMap {'+      Add
            '-      Subtract
            '*      Multiply
            '/      Divide
            'negate Negate
            'exp    Exp
            'ln     Ln
            })

(defn parse [expr]
  (cond
    (number? expr) (Constant expr)
    (symbol? expr) (Variable (str expr))
    :else (apply (get opMap (first expr)) (mapv parse (rest expr)))))

(defn parseObject [expr] (parse (read-string expr)))