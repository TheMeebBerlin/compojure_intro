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

(defn post-handler
  "handling posts"
  [req]
  (pr-str (:uid (:params req))))

(defroutes myapp
  (GET "/hello/:name" [name] (str "Hello" " " name) )
  (GET ["/hi/:name.:ext" :name #".*", :ext #"(html|json)$" ] [name ext] (str "Hello" " " name " in: " ext) )
  (GET "/query" [] (fn [req] (pr-str req) ))
  (POST "/post" [] post-handler)
  (GET "/words" {params :params}
    (->>
      (read-string (:gt params))
      filter-words
      (clojure.string/join "\n")) )
  (route/not-found "Page not found"))

(defn -main [& [port]]
  (print "now we are running")
  (let [port (Integer. (or port (env :port) 5000))]
    (jetty/run-jetty (site #'myapp) {:port port :join? false})))
