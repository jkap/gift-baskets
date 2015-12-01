(ns gift-baskets.prep
  (:require [clj-http.client :as client])
  (:use [hickory.core])
  (:require [hickory.select :as s])
  (:require [gift-baskets.util :as u])
  (:gen-class))

(def base-url "http://www.winecountrygiftbaskets.com")
(def products-list-url (str base-url "/gift_basket_list_ajax.asp?GIFT-BASKETS=GIFT-BASKETS&RST=1&RSV=171&view=all"))

(defn- get-cached-product-list
    ([] (get-cached-product-list "product-list.edn"))
    ([path] (u/parse-if-exists path)))

(defn- get-remote-product-page [url])

(defn- extract-product-desc [tree]
    (join " " (map #(-> % :content first) (drop-last 2 (s/select (s/child (s/id "up_desc") (s/tag :li)) product-tree)))))

(defn- get-remote-product-list
    ([] (get-remote-product-list products-list-url))
    ([url] (def product-tree (u/get-and-parse url))
            (map #(-> % :attrs :href)
                (s/select (s/child (s/class "prt_list_item_img") (s/tag :a)) product-tree))))

(defn- cache-product-list
    ([product-list] (cache-product-list "product-list.edn" product-list))
    ([path product-list] (spit path (with-out-str (pr product-list)))))

(defn get-product-list []
  (println "getting products")
  (def product-list (get-cached-product-list))
  (if (nil? product-list) (do
      (println "remote shit")
      (def product-list (get-remote-product-list))
      (cache-product-list product-list))))
