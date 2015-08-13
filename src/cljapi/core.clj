(ns cljapi.core
  (:require  [compojure.core :refer :all]
              [compojure.route :as route]
              [environ.core :refer [env]]
              [ring.adapter.jetty :as jetty ]
              [compojure.handler :refer [site]]))

(def words (slurp "https://raw.githubusercontent.com/eneko/data-repository/master/data/words.txt"))

(def words-list  (clojure.string/split words #"\n" ))

(defn filter-words [n]
  (filter #(> (count %) n) words-list))


(defroutes myapp
  (GET "/" [] "Hello World")
  (GET "/words" {params :params}
    (->>
      (read-string (:gt params))
      filter-words
      (clojure.string/join "\n")) ))

(defn -main [& [port]]
  (print "now we are running")
  (let [port (Integer. (or port (env :port) 5000))]
    (jetty/run-jetty (site #'myapp) {:port port :join? false})))
