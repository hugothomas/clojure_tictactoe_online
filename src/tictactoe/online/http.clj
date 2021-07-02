(ns tictactoe.online.http
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [hiccup.core :refer :all]
            [tictactoe.online.boardUtils :refer :all]
            [tictactoe.online.db :refer :all]
            [ring.middleware.resource :refer :all]
            [hiccup.page :refer :all]))

(route/resources "/")

(defroutes app-routes
           (GET "/" [] (html
                         [:head
                          [:title "Tic Tac Toe"]
                          (include-css "css/styles.css")]
                         [:body
                          [:div {:class "ttt-board"} (displayBoard (getActiveBoardValues) [:div "x"] [:div "o"] [:div])]
                          ]
                         ))

           (GET "/board" [] (html [:head
                                   [:title "Board"]
                                   (include-css "css/board.css")]
                                  [:body
                                   [:div.container
                                    [:h1 " Tic-Tac-Toe "]
                                    [:div.play-area
                                     [:div#block_0.block {:onclick (str "console.log('dfhgugh')")}]
                                     [:div#block_1.block]
                                     [:div#block_2.block]
                                     [:div#block_3.block]
                                     [:div#block_4.block]
                                     [:div#block_5.block]
                                     [:div#block_6.block]
                                     [:div#block_7.block]
                                     [:div#block_8.block]
                                     ]
                                    ]
                                   ]
                                  ))
           (GET "/createdb" [] (ensure-db))

           (route/not-found (html [:h1 "Page not found"])))

(def app
  (wrap-defaults app-routes site-defaults))
