(defn op [p] (fn [& args] (fn [vars] (apply p (mapv #(% vars) args)))))

(def add (op +))
(def subtract (op -))
(def multiply (op *))
(def divide (op #(/ (double %1) %2)))
(def negate subtract)
(def exp (op #(Math/exp %)))
(def ln (op #(Math/log (Math/abs %))))

(defn constant [val] (fn [& args] val))
(defn variable [val] #(get % val))

(def opMap { '+ add,
            '- subtract,
            '* multiply,
            '/ divide,
            'exp exp,
            'ln ln,
            'negate negate})

(defn parse [expr]
  (cond
    (number? expr) (constant expr)
    (symbol? expr) (variable (str expr))
    :else (apply (get opMap (first expr)) (mapv parse (rest expr)))))

(defn parseFunction [expr] (parse (read-string expr)))