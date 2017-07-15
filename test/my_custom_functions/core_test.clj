(ns my-custom-functions.core-test
  (:require #_[clojure.test :refer :all]
            [my-custom-functions.core :refer :all]
            [expectations :as expect]))

#_(deftest a-test
  (testing "FIXME, I fail."
    (is (= 0 1))))

;; MAP TEST CASES

(expect/expect [true false] (my-map even? [2 3]))
(expect/expect [2 3 4] (my-map inc [1 2 3]))
(expect/expect [5 5] (my-map count ["hello" "world"]))
#_(expect/expect [2 3 4] (my-map + [1 2 3] [1 1 1]))

;; REDUCE TEST CASES

(expect/expect 3 (my-reduce + [1 2]))
(expect/expect 15 (my-reduce + [1 2 3 4 5]))
(expect/expect [1 2 3 4 5] (my-reduce conj [1 2 3] [4 5]))

;;TAKE TEST CASES

(expect/expect [1 2 3] (my-take 3 [1 2 3 4 5 6]))
(expect/expect [1 2] (my-take 3 [1 2 ]))
(expect/expect [] (my-take 1 []))

;;DROP TEST CASES

(expect/expect '(4 5 6) (my-drop 3 [1 2 3 4 5 6]))
(expect/expect '() (my-drop 4 [1 2 3]))
(expect/expect '() (my-drop 1 []))
#_(expect/expect '(1 2 3 4) (my
                             -drop -1 [1 2 3 4]))

;; TAKE-WHILE TEST CASES

(expect/expect '(-2 -1) (my-take-while neg? [-2 -1 0 1 2 3]))

(expect/expect '(-2 -1) (my-take-while neg? [-2 -1 0 -1 -2 3]))

(expect/expect '() (my-take-while neg? []))
(expect/expect '() (my-take-while neg? nil))

;;DROP-WHILE TEST CASES

(expect/expect '() (my-drop-while neg? []))
(expect/expect '(0 1 2) (my-drop-while neg? [-2 -1 0 1 2]))
(expect/expect '(0 1 2 -3 -4) (my-drop-while neg? [-2 -1 0 1 2 -3 -4]))
(expect/expect '() (my-drop-while neg? nil))

;;FILTER TEST CASES

(expect/expect '(0 2 4 6 8) (my-filter even? (range 10)))
(expect/expect '([:y 2] [:z 3]) (my-filter (comp #{2 3} last) {:x 1 :y 2 :z 3}))
(expect/expect '(1 [] :a) (my-filter some? '(1 nil [] :a nil)))
(expect/expect '() (my-filter odd? []))

;;SOME FUNCTION

(expect/expect true (my-some even? '(2 2 1)))
(expect/expect nil (my-some even? '(1 1 1)))
(expect/expect true (my-some even? '(2 1 1 1 1)))

;;SORT TEST CASES

(expect/expect '() (my-sort []))
(expect/expect '(-1 0 1 2 3) (my-sort [-1 0 1 2 3]))
(expect/expect '(1 1) (my-sort [1 1]))

;;CONCAT TEST CASES

(expect/expect '(1 2 3 4) (my-concat [1 2] [3 4]))
(expect/expect '(1 2) (my-concat [1 2] []))
(expect/expect '(1 2 3 4 6 5) (my-concat '(1 2) [3 4] #{5 6}))
(expect/expect '(\s \a \c \h \i \n) (my-concat "sac" "hin"))

(expect/expect :a (expect/in [:a :b]))

;;COMP FUNCTION

(expect/expect "6" ((my-comp str +) 1 2 3))
(expect/expect "7" ((my-comp str inc +) 1 2 3))
