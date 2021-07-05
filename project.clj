(defproject tictictoe.online "0.1.0-SNAPSHOT"
  :description "A school exercise, tic tac toe webapp."
  :url "https://github.com/hugothomas/clojure_tictactoe_online.git"
  :min-lein-version "2.0.0"
  :dependencies [
                 [org.clojure/clojure "1.10.1"]
                 [ring/ring-core "1.9.3"]
                 [selmer "0.9.2"]
                 [compojure "1.6.2"]
                 [ring/ring-defaults "0.3.2"]
                 [org.clojure/java.jdbc "0.7.12"]
                 [org.xerial/sqlite-jdbc "3.36.0.1"]
                 ]
  :plugins [[lein-tar "3.2.0"] [lein-ring "0.12.5"]]
  :ring {:handler tictactoe.online.http/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.2"]]}})
