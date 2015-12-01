(ns gift-baskets.util
    (:require [clojure.java.io :as io])
    (:require [clj-http.client :as client])
    (:use [hickory.core])
    (:gen-class))

(defn get-and-parse [url] (-> (client/get url) :body parse as-hickory))

(defn parse-if-exists [path]
    (if-not (.exists (io/as-file path))
        nil
        (read-string (slurp path))))
