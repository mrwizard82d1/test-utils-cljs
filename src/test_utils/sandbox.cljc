(ns test-utils.sandbox
  #?(:cljs (:require [test-utils.core :as tuc]
                     [test-utils.word-source :as ws])
     :clj (:require (test-utils [core :as tuc]
                                [word-source :as ws])
                    [clojure.string :as clj-str])))

;; Stuff I'm trying out

(defn random-uuid []
  (java.util.UUID/randomUUID))

(defn hex-uuid 
  ([] (hex-uuid (random-uuid)))
  ([uuid] (clj-str/join (map #(Long/toHexString %) [(.getMostSignificantBits uuid) (.getLeastSignificantBits uuid)]))))

