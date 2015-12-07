(ns gift-baskets.util
  (:require [clojure.java.io :as io])
  (:require [clj-http.client :as client])
  (:require [clojure.string :as str])
  (:use [hickory.core :only (parse as-hickory)])
  (:gen-class))

(defn get-and-parse [url] (-> (client/get url) :body parse as-hickory))

(defn url-to-path [url] (str/replace url #"/" ":"))

(defn parse-if-exists [path]
  (if (.exists (io/as-file path))
    (read-string (slurp path))))

(defn make-get-cached [default-path]
  (fn get-cached
    ([] (get-cached default-path))
    ([path] (parse-if-exists path))))

(defn make-write-cache [default-path]
  (fn write-cache
    ([data] (write-cache default-path data))
    ([path data] (spit path (prn-str data)) data)))

(defn take-chars [n coll]
  (lazy-seq (when (pos? n)
              (when-let [s (seq coll)]
                (if (< (count (first s)) n)
                  (cons (first s) (take-chars (- n (count (first s)) 1)  (rest s))))))))

(defn drop-last-while
  "Repeatedly drop the last element of `coll` as long as `pred` returns true
  when applied to each element."
  [pred coll]
  (cond (empty? coll) nil
        (pred (last coll)) (recur pred (drop-last coll))
        :else coll))
