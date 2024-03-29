(defproject gift-baskets "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/tools.cli "0.3.3"]
                 [clj-http "2.0.0"]
                 [hickory "0.5.4"]
                 [janiczek/markov "0.3.0"]
                 [clojure-opennlp "0.3.3"]
                 [intervox/clj-progress "0.2.1"]]
  :main ^:skip-aot gift-baskets.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}
             :dev {:source-paths ["dev"]}
             :dependencies [[org.clojure/tools.namespace "0.2.3"]]})
