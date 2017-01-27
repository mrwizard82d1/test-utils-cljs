(ns test-utils.address
  (:require [test-utils.core :as tuc]
            [test-utils.word-source :as ws]
            [goog.string :as gstring]
            [goog.string.format]))

(defn house-number []
  (tuc/rand-4))

(defn street-name []
  (repeatedly 2 ws/rand-word))

(defn zip-code []
  (tuc/rand-5))

(defn phone-number []
  (str "1" (gstring/format "%02d" (tuc/rand-2))  "-" "555" "-01" (gstring/format "%02d" (tuc/rand-2))))

(defn state []
  (let [abbreviations ["AL" "AK" "AZ" "AR" 
                       "CA" "CO" "CT"
                       "DE" "FL" "GA" "HI"
                       "ID" "IL" "IN" "IA"
                       "KS" "KY" 
                       "LA"
                       "ME" "MD" "MA" "MI" "MN" "MS" "MO" "MT"
                       "NE" "NV" "NH" "NJ" "NM" "NY" "NC" "ND"
                       "OH" "OK" "OR"
                       "PA" "RI"
                       "SC" "SD"
                       "TN" "TX"
                       "UT"
                       "VT" "VA"
                       "WA" "WV" "WI" "WY"]]
    (rand-nth abbreviations)))
