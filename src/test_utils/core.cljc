(ns test-utils.core
  (:require [clojure.string :as cljstr]
            [kixi.stats.distribution :as ksd])
  (:import (java.time Duration LocalDateTime LocalTime)))

#?(:cljs (enable-console-print!))

(def rand-digit (partial rand-int 10))

(def rand-2 (partial rand-int 1e2))
(def rand-3 (partial rand-int 1e3))
(def rand-4 (partial rand-int 1e4))
(def rand-5 (partial rand-int 1e5))
(def rand-6 (partial rand-int 1e6))
(def rand-7 (partial rand-int 1e7))
(def rand-8 (partial rand-int 1e8))
(def rand-9 (partial rand-int 1e9))

(defn rand-range
  "Return a random integer between begin and end (excluding end)."
  ([end] (rand-range 0 end))
  ([begin end] (+ begin (rand-int (- end begin)))))

(defn rand-alpha []
  "Return a random, alphabetic character."
  (let [alpha-chars (map char(concat (range #?(:cljs (.charCodeAt "A")
                                               :clj (int \A))
                                            #?(:cljs (.charCodeAt "Z")
                                               :clj (int \Z)))
                               (range #?(:cljs (.charCodeAt "a")
                                         :clj (int \a))
                                      #?(:cljs (.charCodeAt "z")
                                         :clj (int \z)))))]
    (nth alpha-chars (rand-range 0 (count alpha-chars)))))

(defn rand-alphas
  "Return a string a n random, alphabetic characters."
    ([] (repeatedly rand-alpha))
    ([n] (apply str (take n (rand-alphas)))))

(defn rand-timestamp [begin-year end-year]
  "Return a vector with random timestamp elements with years in the range
  [begin-year, end-year)."
  [(rand-range begin-year end-year) (rand-range 1 (inc 12)) (rand-range 1 (inc 31))
   (rand-int 24) (rand-int 60) (rand-int 60)])

(defn draw-normal
  "Draw a single value from the normal distribution with mean, `mu`
  (default 0.0), and standard deviation, `sigma` (default 1.0)."
  ([] (draw-normal 0.0 1.0))
  ([mu sigma] (ksd/draw (ksd/normal {:mu mu :sd sigma}))))

(defn sample-normal
  "Return a sample of n (default 3) values from the normal distribution
  with mean, `mu` (default 0.0), and standard deviation, `sigma`
  (default 1.0)."
  ([] (sample-normal 3 0.0 1.0))
  ([n] (sample-normal n 0.0 1.0))
  ([n mu sigma] (ksd/sample n (ksd/normal {:mu mu :sd sigma}))))

(defn draw-pareto
  "Draw a single value from a pareto distribution with `scale`
   (default log base 4 of 5) and shape (default 1)."
  ([] (draw-pareto (/ (Math/log 5) (Math/log 4)) 1))
  ([shape scale] (ksd/draw (ksd/pareto {:shape shape
                                        :scale scale}))))

(defn sample-pareto
  "Return a sample of n (default 3) values from the pareto distribution
  with shape, `shape` (default log base 4 of 5), and scale, `scale` (default 1.0)."
  ([] (sample-pareto 3))
  ([n] (sample-pareto n (/ (Math/log 5) (Math/log 4)) 1.0))
  ([n shape scale] (ksd/sample n (ksd/pareto {:shape shape
                                              :scale scale}))))

(defn draw-uniform
  "Draw a single value from the uniform distribution with lower bound, `lower`
  (default 0.0), and upper bound, `upper` (default 1.0)."
  ([] (draw-uniform 0.0 1.0))
  ([lower upper] (ksd/draw (ksd/uniform {:a lower :b upper}))))

(defn sample-uniform
  "Return a sample of n (default 3) values from the uniform distribution
  with lower bound, `lower` (default 0.0), and upper bound, `upper`
  (default 1.0)."
  ([] (sample-uniform 3 0.0 1.0))
  ([n] (sample-uniform n 0.0 1.0))
  ([n lower upper] (ksd/sample n (ksd/uniform {:a lower :b upper}))))

(defn two-digit-zero-pad [number]
  (if (>= number 10)
    number
    (str "0" number)))

(defn rand-uuid []
  "Return a random UUID."
  (java.util.UUID/randomUUID))

(defn rand-uuids
  "Return many random UUIDs."
  ([] (rand-uuids 3))
  ([n] (repeatedly n rand-uuid)))

(defn rand-argb
  ([] (rand-argb :bytes))
  ([tag]
   (let [floats->bytes (fn [x] (-> (* 256 x) int Integer/toHexString))]
        (case tag
          :float (vec (repeatedly 4 rand))
          :bytes (vec (map floats->bytes (rand-argb :float)))
          :hex-string (cljstr/join (rand-argb :bytes))))))

(defn total-seconds->hms [total-seconds]
  (let [hours (quot total-seconds 3600)
        minutes (quot (rem total-seconds 3600) 60)
        seconds (rem (rem total-seconds 3600) 60)]
    [hours minutes seconds]))

(defn timestamp->java-time [time-stamp]
  "Converts a timestamp to a `java.time.LocalDateTime`."
  (apply #(LocalDateTime/of %1 %2 %3 %4 %5 %6) time-stamp))

(def java-time->timestamp
  (juxt #(.getYear %) #(.getMonthValue %) #(.getDayOfMonth %)
         #(.getHour %) #(.getMinute %) #(.getSecond %)))
