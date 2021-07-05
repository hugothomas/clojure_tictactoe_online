(ns tictactoe.online.core
  (:require [selmer.parser :as parser]))

(defn prepareBoard []
  (parser/render-file "./templates/board.html" {:c00         0, :c01 0, :c02 0,
                                                :c10         0, :c11 0, :c12 0,
                                                :c20         0, :c21 0, :c22 0,
                                                :player      1, :ended 0
                                                :actualBoard "c00=0&c01=0&c02=0&c10=0&c11=0&c12=0&c20=0&c21=0&c22=0"}))

(defn winning-morpion? [plateau]
  (defn test2 [a b c] (and (not= a 0) (= a b) (= a c)))
  (or (test2 (get plateau "c00") (get plateau "c01") (get plateau "c02"))
      (test2 (get plateau "c10") (get plateau "c11") (get plateau "c12"))
      (test2 (get plateau "c20") (get plateau "c21") (get plateau "c22"))

      (test2 (get plateau "c00") (get plateau "c10") (get plateau "c20"))
      (test2 (get plateau "c01") (get plateau "c11") (get plateau "c21"))
      (test2 (get plateau "c02") (get plateau "c12") (get plateau "c22"))

      (test2 (get plateau "c00") (get plateau "c11") (get plateau "c22"))
      (test2 (get plateau "c20") (get plateau "c11") (get plateau "c02"))
      ))

(defn exchange [player]
  (if (== player 1) 2 1))

(defn buildActualBoardQuery [actualBoard]
  (clojure.string/join [
                        "c00=" (get actualBoard "c00") "&"
                        "c01=" (get actualBoard "c01") "&"
                        "c02=" (get actualBoard "c02") "&"
                        "c10=" (get actualBoard "c10") "&"
                        "c11=" (get actualBoard "c11") "&"
                        "c12=" (get actualBoard "c12") "&"
                        "c20=" (get actualBoard "c20") "&"
                        "c21=" (get actualBoard "c21") "&"
                        "c22=" (get actualBoard "c22")]))

(defn continue? [board]
  (or (zero? (get board "c00"))
      (zero? (get board "c01"))
      (zero? (get board "c02"))
      (zero? (get board "c10"))
      (zero? (get board "c11"))
      (zero? (get board "c12"))
      (zero? (get board "c20"))
      (zero? (get board "c21"))
      (zero? (get board "c22"))))

(defn tictactoeEnded? [board]
  (if (winning-morpion? board)
    1
    (if (continue? board)
      0
      1)))

(defn buildFreshMarkers [board player actualBoardQuery]
  {:c00    (get board "c00"), :c01 (get board "c01"), :c02 (get board "c02"),
   :c10    (get board "c10"), :c11 (get board "c11"), :c12 (get board "c12"),
   :c20    (get board "c20"), :c21 (get board "c21"), :c22 (get board "c22"),
   :player (exchange player), :actualBoard actualBoardQuery,
   :ended  (tictactoeEnded? board), :winner (if (winning-morpion? board) player 0)})


(defn playTicTacToe [queryParams]
  (def selected (get queryParams "selected"))
  (def player (Integer/parseInt (get queryParams "player")))
  (def board {
              "c00" (Integer/parseInt (get queryParams "c00")),
              "c01" (Integer/parseInt (get queryParams "c01")),
              "c02" (Integer/parseInt (get queryParams "c02")),
              "c10" (Integer/parseInt (get queryParams "c10")),
              "c11" (Integer/parseInt (get queryParams "c11")),
              "c12" (Integer/parseInt (get queryParams "c12")),
              "c20" (Integer/parseInt (get queryParams "c20")),
              "c21" (Integer/parseInt (get queryParams "c21")),
              "c22" (Integer/parseInt (get queryParams "c22")),
              })

  (def board (assoc board selected player))
  (parser/render-file "./templates/board.html" (buildFreshMarkers board player (buildActualBoardQuery board))))
