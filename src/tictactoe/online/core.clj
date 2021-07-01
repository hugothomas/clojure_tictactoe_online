(ns tictactoe.online.core
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [hiccup.core :refer :all]
            [tictactoe.online.boardUtils :refer :all]
            [ring.middleware.resource :refer :all]
            )
  (:use [hiccup.page :only (html5 include-css include-js)])
  )


(route/resources "/")

(defroutes app-routes
           (GET "/" [] (html
                         [:head
                          [:title "Tic Tac Toe"]
                          (include-css "css/styles.css")]
                          [:body

                            [:div {:class "ttt-board"} (displayBoard [
                                                                "x" "o" "x"
                                                                "o" "x" "."
                                                                "x" "o" "."
                                                                ] [:div "x"] [:div "o"] [:div "ntm hugo"])]

                           ]
                          ))
           (route/not-found (html [:h1 "Page not found"])))
(def app
  (wrap-defaults app-routes site-defaults)
  )