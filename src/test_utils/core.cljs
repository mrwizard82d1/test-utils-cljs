(ns test-utils.core)

(enable-console-print!)

(def rand-digit (partial rand-int 10))

(def rand-2 (partial rand-int 1e2))
(def rand-3 (partial rand-int 1e3))
(def rand-4 (partial rand-int 1e4))
(def rand-5 (partial rand-int 1e5))
(def rand-6 (partial rand-int 1e6))
(def rand-7 (partial rand-int 1e7))
(def rand-8 (partial rand-int 1e8))
(def rand-9 (partial rand-int 1e9))
(def rand-10 (partial rand-int 1e10))

(defn rand-range [begin end]
  "Return a random integer between begin and end (excluding end)."
  (+ begin (rand-int (- end begin))))

(defn rand-alpha []
  "Return a random, alphabetic character."
  (let [alpha-chars (map char(concat (range (.charCodeAt "A") (.charCodeAt "Z"))
                               (range (.charCodeAt "a") (.charCodeAt "z"))))]
    (nth alpha-chars (rand-range 0 (count alpha-chars)))))

(defn rand-alphas
  "Return a string a n random, alphabetic characters."
    ([] (repeatedly rand-alpha))
    ([n] (apply str (take n (rand-alphas)))))

(defn rand-timestamp [begin-year end-year]
  "Return a vector with random timestamp elements with years in the range [begin-year, end-year)."
  [(rand-range begin-year end-year) (rand-range 1 13) (rand-int 31) (rand-int 24) (rand-int 60) (rand-int 60)])

