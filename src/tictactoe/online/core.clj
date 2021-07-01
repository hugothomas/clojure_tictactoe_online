(ns tictactoe.online.core
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))
(use [tictactoe.online.boardUtils])
(defroutes app-routes
   (GET "/" [] ( :h1 (displayBoard [
                      "x" "o" "x"
                      "o" "x" "."
                      "x" "o" "."
                      ] (:div "x") (:div "o") (:div "ntm hugo") "" )))
   (route/not-found (:h1 "Page not found")))
(def app
  (wrap-defaults app-routes site-defaults))