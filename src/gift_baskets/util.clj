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

(defn make-get-cached [default-path]
  (fn get-cached
    ([] (get-cached default-path))
    ([path] (parse-if-exists path))))

(defn make-write-cache [default-path]
  (fn write-cache
    ([data] (write-cache default-path data))
    ([path data] (spit path (with-out-str (pr data))))))
