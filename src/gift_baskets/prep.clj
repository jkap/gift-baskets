(ns gift-baskets.prep
  (:require [hickory.select :as s])
  (:require [gift-baskets.util :as u])
  (:require [clojure.string :as str])
  (:gen-class))

(def base-url "http://www.winecountrygiftbaskets.com")
(def products-list-url (str base-url "/gift_basket_list_ajax.asp?GIFT-BASKETS=GIFT-BASKETS&RST=1&RSV=171&view=all"))

(def get-cached-product-list (u/make-get-cached "product-list.edn"))
(def cache-product-list (u/make-write-cache "product-list.edn"))

(def get-cached-product-details (u/make-get-cached "product-details.edn"))
(def cache-product-details (u/make-write-cache "product-details.edn"))

(defn- extract-product-desc [tree]
  (str/join " " (map #(-> % :content first) (drop-last 2 (s/select (s/child (s/id "up_desc") (s/tag :li)) tree)))))

(defn- extract-product-title [tree]
  (-> (s/select (s/id "desc") tree) first :content first))

(defn- get-remote-product-details [url]
  (def product-tree (u/get-and-parse (str base-url url)))
  {:title (extract-product-title product-tree) :desc (extract-product-desc product-tree)})

(defn- get-remote-product-list
    ([] (get-remote-product-list products-list-url))
    ([url] (def product-tree (u/get-and-parse url))
            (map #(-> % :attrs :href)
                (s/select (s/child (s/class "prt_list_item_img") (s/tag :a)) product-tree))))

(defn get-product-list []
  (println "getting products")
  (let [product-list (or (get-cached-product-list) (do (println "remote shit")
                                                      (get-remote-product-list)))]
    (cache-product-list product-list)))
