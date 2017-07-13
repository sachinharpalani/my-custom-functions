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
  (if (> val (count in))
    (reverse (into '() in))
    (if (= (count out) val)
      (reverse (into '() out))
      (recur val (rest in) (conj out (first in))))))

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

;; COMPLEMENT FUNCTION


;; SORT-BY FUNCTION
