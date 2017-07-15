(ns my-custom-functions.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

;; MAP FUNCTION

(defn helper-map [f in out]
  (if (empty? in)
    out
    (recur  f (rest in) (conj out (f (first in))))))
(defn my-map [f arr]
  (helper-map f arr []))

;; REDUCE FUNCTION

(defn helper-reduce [f in out]
  (if (empty? in)
    out
    (recur f (rest in) (f out (first in)))))
(defn my-reduce
  ([f in]
   (helper-reduce f (rest in) (first in)))
  ([f val in]
   (helper-reduce f in val)))

;; TAKE FUNCTION

(defn helper-take [val in out]
  (if (< val 0)
    '()
    (if (> val (count in))
      (reverse (into '() in))
      (if (= (count out) val)
        (reverse (into '() out))
        (recur val (rest in) (conj out (first in)))))))

(defn my-take [val in]
  (helper-take val in []))

;; DROP FUNCTION

(defn helper-drop[val in out]
  (if(= val (count out))
    in
    (recur val (rest in) (conj out (first in)))))

(defn my-drop [val in]
  (helper-drop val in []))

;; TAKE-WHILE FUNCTION

(defn helper-my-take-while [f in out]
  (if (empty? in)
    '()
    (if (f (first in))
      (recur f (rest in) (conj out (first in)))
      (reverse (into '() out)))))

(defn my-take-while [f in]
  (helper-my-take-while f in []))

;; DROP-WHILE FUNCTION

(defn helper-my-drop-while [f in out]
  (if (empty? in)
    '()
    (if (f (first in))
      (recur f (rest in) (conj out (first in)))
      in)))

(defn my-drop-while [f in]
  (helper-my-drop-while f in []))

;; FILTER FUNCTION
(defn helper-filter [f in out]
  (if (empty? in)
    (reverse (into '() out))
    (if (f (first in))
      (recur f (rest in) (conj out (first in)))
      (recur f (rest in) out))))

(defn my-filter [f in]
  (helper-filter f in []))

;; SOME FUNCTION
(defn my-some [f in]
  (if (empty? in)
    nil
    (if (f (first in))
      true
      (recur f (rest in)))))

;; SORT FUNCTION
;;remove-from-1 is the OOP WAY AND HAS TO BE AVOIDED
(defn remove-from-1 [a in out cnt]
  (if (empty? in)
    out
    (if (and (= cnt 1) (= a (first in)))
      (recur a (rest in) out (inc cnt))
      (recur a (rest in) (conj out (first in)) cnt ))))
;; remove-from-2 is the functional way
(defn remove-from-2 [a in]
  (if (empty? in)
    a
    (if (= a (first in))
      (rest in)
      (cons (first in) (remove-from-2 a (rest in))))))
(defn helper-sort [in out]
  (if (empty? in)
    out
    (recur (remove-from-2 (apply min in) in)
           (conj out (apply min in)))))


(defn my-sort [in]
  (helper-sort in []))

;;CONCAT FUNCTION

(defn helper-concat [a out]
  (if (empty? (first a))
    out
    (recur (rest a) (into out (first a)))))

(defn my-concat [& a]
  (helper-concat a []))

;; PARTIAL FUNCTION

(defn my-partial [f & in]
  (fn [& b]
    (apply f (into in b))))

;; COMPLEMENT FUNCTION


(defn my-complement [f]
  (fn [& b]
    (not (apply f b))))
;; SORT-BY FUNCTION

(defn convert-input [f in out]
  (if (empty? in)
    out
    (recur f
           (rest in)
           (conj out (vector (first in) (f (first in)))))))

(defn my-remove-by [a in]
  (if (empty? in)
    a
    (if (= a (first in))
      (rest in)
      (cons (first in) (my-remove-by a (rest in))))))
;; min using reduce doesnt return alphabet result if count is same
(defn my-min-by [in]
  (if (empty? in)
    '()
    (reduce (fn [a b]
              (if (<=  (second a) (second b))
                a
                b))
            in)))

(defn helper-sort-by [in out]
  (if (empty? in)
    out
    (recur (my-remove-by (my-min-by in) in)
           (conj out (first (my-min-by in))))))

(defn my-sort-by [f in]
  (if (empty? in)
    '()
    (helper-sort-by (convert-input f in []) [])))


;; QUESTION 1
;; i/p: (fn 2 [1 2 3])
;; o/p: [1 1 2 2 3 3]

(defn helper-f1 [val in out cnt]
  (if (empty? in)
    out
    (if (= 0 val)
      (recur cnt (rest in) out val)
      (recur (dec val) in (conj out (first in)) (inc cnt)))))

(defn f1 [val in]
  (helper-f1 val in [] 0))


;;COMP FUNCTION

(defn helper-my-comp [func inp]
  (if (empty? func)
    inp
    (recur (rest func) ((first func) inp))))

(defn my-comp [& f]
  (let [revf (reverse f)]
    (fn [& in]
      (helper-my-comp (rest revf)
                      (apply (first revf) in)))))
