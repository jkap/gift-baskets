(ns gift-baskets.generate
  (:require [gift-baskets.util :as u])
  (:require [clojure.string :as str])
  (:require [markov.core :as markov])
  (:require [opennlp.nlp :as nlp])
  (:gen-class))

(def get-cached-dictionary (u/make-get-cached "cache/dictionary.edn"))
(def detokenize (nlp/make-detokenizer "nlp-models/english-detokenizer.xml"))
(def pos-tag (nlp/make-pos-tagger "nlp-models/en-pos-maxent.bin"))
(def tokenize (nlp/make-tokenizer "nlp-models/en-token.bin"))

(defn- is-noun? [token] (let [pos (second token)]
                          (some #{pos} '("NN" "NNS" "NNP" "NNPS"))))

(defn generate-title
  ([] (generate-title 20))
  ([chars] (let [dict (first (get-cached-dictionary))]
             (str/join " " (u/take-chars chars (markov/generate-walk dict))))))

(defn generate-desc
  ([] (generate-desc 140))
  ([chars] (let [dict (second (get-cached-dictionary))]
             (str/join " " (u/take-chars chars (markov/generate-walk dict))))))

(defn generate-tweet
  ([] (generate-tweet 140))
  ([chars]
   (let [title (detokenize
                 (map first (u/drop-last-while (complement is-noun?)
                                               (remove nil? (pos-tag (tokenize (generate-title 20)))))))
         desc (detokenize
                (map first (u/drop-last-while (complement is-noun?)
                                              (remove nil? (pos-tag (tokenize (generate-desc (- chars (count title) 2))))))))]
     (str title ": " desc))))
