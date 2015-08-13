(defproject cljapi "0.1.0-SNAPSHOT"
  :description "A project for Meebs"
  :url "?"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                    [compojure "1.4.0"]
                    [environ "0.5.0"]
                    [ring/ring-jetty-adapter "1.2.2"]
                    [ring/ring-json "0.3.1"]]

  :min-lein-version "2.0.0"
  :plugins [[environ/environ.lein "0.2.1"]]
  :hooks [environ.leiningen.hooks]
  :uberjar-name "cljapi.jar"
  :main cljapi.core
  :profiles {:production {:env {:production true}}})
