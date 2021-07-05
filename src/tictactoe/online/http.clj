(ns tictactoe.online.http
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [hiccup.core :refer :all]
            [tictactoe.online.boardUtils :refer :all]
            [tictactoe.online.db :refer :all]
            [tictactoe.online.core :refer :all]
            [ring.middleware.resource :refer :all]
            [hiccup.page :refer :all]))

(route/resources "/")

(defroutes app-routes
           (GET "/" [] (prepareBoard))
           (GET "/play" {params :query-params} (playTicTacToe params))

           (route/not-found (html [:h1 "Page not found"])))

(def app
  (wrap-defaults app-routes site-defaults))
