(defn v [f] (fn [a b] (mapv f a b)))

(def s+ +)
(def s- -)
(def s* *)
(def sd /)

(def v+ (v s+))
(def v- (v s-))
(def v* (v s*))
(def vd (v sd))

(def m+ (v v+))
(def m- (v v-))
(def m* (v v*))
(def md (v vd))

(def c+ (v m+))
(def c- (v m-))
(def c* (v m*))
(def cd (v md))

(defn scalar [a b] (apply + (mapv * a b)))
(defn vect [v1 v2] (vector
                     (- (* (v1 1) (v2 2)) (* (v1 2) (v2 1)))
                     (- (* (v1 2) (v2 0)) (* (v1 0) (v2 2)))
                     (- (* (v1 0) (v2 1)) (* (v1 1) (v2 0)))))

(defn *s [s] (fn [v1] (mapv (partial * s) v1)))
(defn v*s [v1 s] ((*s s) v1))
(defn m*s [m s] (mapv (*s s) m))

(defn transpose [m] (apply mapv vector m))
(defn m*v [m1 v1] (mapv (fn [v2] (scalar v1 v2)) m1))
(defn m*m [m1 m2] (mapv (fn [v1] (m*v (transpose m2) v1)) m1))