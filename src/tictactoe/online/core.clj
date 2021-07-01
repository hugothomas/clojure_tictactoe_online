(ns tictactoe.online.core
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defroutes app-routes
   (GET "/" [] ( :h1 "Hello World"))
   (route/not-found (:h1 "Page not found")))
(def app
  (wrap-defaults app-routes site-defaults))