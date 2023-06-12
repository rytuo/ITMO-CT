;;;;;;;;;;;;;;;;;;;;; Declares ;;;;;;;;;;;;;;;;;;;;;;;

(declare Zero One)

;;;;;;;;;;;;;;;;; main java classes ;;;;;;;;;;;;;;;;;;

(definterface Expression
  (evaluate [vars])
  (toStringSuffix [])
  (diff [var]))

(deftype Op [sign s args df]
  Object
  (toString [this] (str "("
                        (.-s this)
                        (apply str (mapv #(str " " (.toString %)) (.-args this)))
                        ")"))
  Expression
  (evaluate [this vars] (apply (.-sign this) (mapv #(.evaluate % vars) (.-args this))))
  (toStringSuffix [this] (str "("
                              (apply str (mapv #(str (.toStringSuffix %) " ") (.-args this)))
                              (.-s this)
                              ")"))
  (diff [this var] (apply (.-df this) (apply conj (vec (.-args this)) (mapv #(.diff % var) (.-args this))))))

(deftype Const [val]
  Object
  (toString [this] (format "%.1f" (.-val this)))
  Expression
  (evaluate [this _] (.-val this))
  (toStringSuffix [this] (.toString this))
  (diff [_ _] Zero))

(deftype Var [val]
  Object
  (toString [this] (.-val this))
  Expression
  (toStringSuffix [this] (.toString this))
  (evaluate [this vars] (vars ((comp clojure.string/lower-case first) (.-val this))))
  (diff [this var] (if (= var (.-val this)) One Zero)))

;;;;;;;;;;;; Working division for double ;;;;;;;;;;;;;

(defn div [f & args] (if (empty? args) f (apply div (/ (double f) (double (first args))) (rest args))))

;;;;;;;;;; Constructors for clojure tests ;;;;;;;;;;;;

(defn Constant [val] (Const. val))
(def Zero (Constant 0.0))
(def One (Constant 1.0))

(defn Variable [val] (Var. val))

(defn Add [& args] (Op. + '+ args
                        (fn [f s df ds] (Add df ds))))
(defn Subtract [& args] (Op. - '- args
                             (fn [f s df ds] (Subtract df ds))))
(defn Multiply [& args] (Op. * '* args
                             (fn [f s df ds] (Add (Multiply df s) (Multiply f ds)))))
(defn Divide [& args] (Op. div '/ args
                          (fn [f s df ds] (Divide (Subtract (Multiply df s) (Multiply f ds))
                                              (Multiply s s)))))
(defn Negate [& args] (Op. - 'negate args
                           (fn [f df] (Negate df))))
(defn Exp [& args] (Op. #(Math/exp %) 'exp args
                        (fn [f df] (Multiply (Exp f) df))))
(defn Ln [& args] (Op. #(Math/log (Math/abs %)) 'ln args
                       (fn [f df] (Divide df f))))

(defn evaluate [expr vars] (.evaluate expr vars))
(defn toString [expr] (.toString expr))
(defn diff [expr var] (.diff expr var))
(defn toStringSuffix [expr] (.toStringSuffix expr))

;;;;;;;;;;;;;;;;;;;;;; Parser ;;;;;;;;;;;;;;;;;;;;;;;;

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

;;;;;;;;;;;;;;;;;;;; Parser 12 ;;;;;;;;;;;;;;;;;;;;;;

(defn -return [value tail] {:value value :tail tail})
(def -valid? boolean)
(def -value :value)
(def -tail :tail)
(defn _empty [value] (partial -return value))
(defn _char [p]
  (fn [[c & cs]]
    (if (and c (p c)) (-return c cs))))
(defn _map [f result]
  (if (-valid? result)
    (-return (f (-value result)) (-tail result))))
(defn _combine [f a b]
  (fn [str]
    (let [ar ((force a) str)]
      (if (-valid? ar)
        (_map (partial f (-value ar))
              ((force b) (-tail ar)))))))
(defn _either [a b]
  (fn [str]
    (let [ar ((force a) str)]
      (if (-valid? ar) ar ((force b) str)))))
(defn _parser [p]
  (fn [input]
    (-value ((_combine (fn [v _] v) p (_char #{\u0000})) (str input \u0000)))))

(defn +char [chars] (_char (set chars)))
(defn +char-not [chars] (_char (comp not (set chars))))
(defn +map [f parser] (comp (partial _map f) parser))
(def +ignore (partial +map (constantly 'ignore)))
(defn iconj [coll value]
  (if (= value 'ignore) coll (conj coll value)))
(defn +seq [& ps]
  (reduce (partial _combine iconj) (_empty []) ps))
(defn +seqf [f & ps] (+map (partial apply f) (apply +seq ps)))
(defn +seqn [n & ps] (apply +seqf (fn [& vs] (nth vs n)) ps))
(defn +or [p & ps]
  (reduce _either p ps))
(defn +opt [p]
  (+or p (_empty nil)))
(defn +star [p]
  (letfn [(rec [] (+or (+seqf cons p (delay (rec))) (_empty ())))] (rec)))
(defn +plus [p] (+seqf cons p (+star p)))
(defn +str [p] (+map (partial apply str) p))
(def +parser _parser)

;;;;;;;;;;;;;;;;;; suffix parser ;;;;;;;;;;;;;;;;;;;

; expr = ws '(' ws *args ws *Op ws ')' ws

(def parseSuffix
  (let
    [*all-chars (mapv char (range 0 128))
     *space (+char (apply str (filter #(Character/isWhitespace %) *all-chars)))
     *ws (+ignore (+star *space))
     *digit (+char (apply str (filter #(Character/isDigit %) *all-chars)))
     *number (+map read-string (+str (+seqf cons (+opt (+char "-")) (+plus (+or *digit (+char "."))))))
     *variable (+str (+plus (+char "xyzXYZ")))
     *operation (+map (comp symbol toString) (+or (+char "+-*/")
                                                  (+seqf (constantly 'negate)
                                                         (+char "n") (+char "e") (+char "g") (+char "a") (+char "t") (+char "e"))))]
    (letfn [(*args [] (+map vec (+star (+seqn 0 *ws (delay (*value))))))
            (*seq [] (+seq (*args) *ws *operation))
            (*expr [] (+seqn 1 (+char "(") (*seq) *ws (+char ")")))
            (*value [] (+or *number *variable (*expr)))]
      (+parser (+seqn 0 *ws (*value) *ws)))
    ))

; expr = num || var || [[args] op]

(defn parseObjectSuffix [str]
  (letfn [(convert [input]
            (cond
              (number? input) (Constant input)
              (string? input) (Variable input)
              (vector? input) (apply (get opMap (input 1)) (mapv convert (first input)))))]
    (convert (parseSuffix str))))

;;;;;;;;;;;;;;;;;;;; Debug ;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn _show [result]
  (if (-valid? result) (str "-> " (pr-str (-value result)) " | " (pr-str (apply str (-tail result))))
                       "!"))
(defn tabulate [parser inputs]
  (run! (fn [input] (printf "    %-10s %s\n" (pr-str input) (_show (parser input)))) inputs))

(println (diff (Add (Variable "x") (Constant 2.0)) "x"))