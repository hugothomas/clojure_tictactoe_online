(defproject tictictoe_online "0.1.0-SNAPSHOT"
  :description "A school exercise, tic tac toe webapp."
  :url "https://github.com/hugothomas/clojure_tictactoe_online.git"
  :dependencies [[org.clojure/clojure "1.10.1"],[ring/ring-core "1.9.3"],[hiccup "1.0.5"],[compojure "1.6.2"]]
  :main ^:skip-aot tictictoe-online.core
  :target-path "target/%s"
  :plugins [[lein-tar "3.2.0"]]
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
