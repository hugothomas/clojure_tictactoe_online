(defproject tictictoe_online "0.1.0-SNAPSHOT"
  :description "A school exercise, tic tac toe webapp."
  :url "https://github.com/hugothomas/clojure_tictactoe_online.git"
  :dependencies [[org.clojure/clojure "1.10.1"]]
  :main ^:skip-aot tictictoe-online.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
