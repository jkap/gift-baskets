(ns gift-baskets.prep
  (:require [clj-http.client :as client])
  (:gen-class))

(def products-list-url "http://www.winecountrygiftbaskets.com/gift_basket_list_ajax.asp?RST=1&RSV=171&RFV=&prfrom=&prto=&rc=")

(defn get-product-list []
  (println "getting products")
  (client/get ))

(defn- get-cached-product-list [])

(defn- cache-product-list [])
