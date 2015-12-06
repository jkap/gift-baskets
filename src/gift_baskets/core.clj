(ns gift-baskets.core
  (:require [clojure.tools.cli :refer [parse-opts]]
            [gift-baskets.prep :as prep])
  (:gen-class))

(def cli-options [])

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [opts (parse-opts args cli-options)]
    (case (first (:arguments opts))
    "prep" (do
        (println "prepping")
        (prep/get-product-list))
    (println "nothing"))))
