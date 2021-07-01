(ns tictactoe.online.core
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [hiccup.core :refer :all]))

(defroutes app-routes
   (GET "/" []  (html [:h1 "Hello World"]))
   (route/not-found (html [:h1 "Page not found"])))
(def app
  (wrap-defaults app-routes site-defaults))