(ns gift-baskets.core
  (:require [clojure.tools.cli :refer [parse-opts]]
            [gift-baskets.prep :as prep])
  (:gen-class))

(def cli-options [])

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (def opts (parse-opts args cli-options))
  (if (= (first (:arguments opts)) "prep") (do
    (println "prepping")
    (prep/get-categories))))
