(ns tictactoe.online.core
  (:require [selmer.parser :as parser]))

(defn prepareBoard []
  (parser/render-file "./templates/board.html" {:c00         0, :c01 0, :c02 0,
                                                :c10         0, :c11 0, :c12 0,
                                                :c20         0, :c21 0, :c22 0,
                                                :player      1,
                                                :actualBoard "c00=0&c01=0&c02=0&c10=0&c11=0&c12=0&c20=0&c21=0&c22=0"}))

(defn get-case-morpion [plateau i j]
  (get plateau (+ i (* 3 j))))

(defn exchange [player]
  (if (== player 1) 2 1))

(defn end-morpion? [plateau]
  (empty? (filter (fn [x] (zero? x)) plateau)))

(defn buildActualBoardQuery [actualBoard]
  (clojure.string/join [
                        "c00=" (get board "c00") "&"
                        "c01=" (get board "c01") "&"
                        "c02=" (get board "c02") "&"
                        "c10=" (get board "c10") "&"
                        "c11=" (get board "c11") "&"
                        "c12=" (get board "c12") "&"
                        "c20=" (get board "c20") "&"
                        "c21=" (get board "c21") "&"
                        "c22=" (get board "c22")]))

(defn buildFreshMarkers [board player actualBoard]
  {:c00    (get board "c00"), :c01 (get board "c01"), :c02 (get board "c02"),
   :c10    (get board "c10"), :c11 (get board "c11"), :c12 (get board "c12"),
   :c20    (get board "c20"), :c21 (get board "c21"), :c22 (get board "c22"),
   :player (exchange player), :actualBoard actualBoard})

(defn winning-morpion? [plateau]
  (defn test [a b c] (and (not= a 0) (= a b) (= a c)))
  (or (test (get-case-morpion plateau 0 0) (get-case-morpion plateau 0 1) (get-case-morpion plateau 0 2))
      (test (get-case-morpion plateau 1 0) (get-case-morpion plateau 1 1) (get-case-morpion plateau 1 2))
      (test (get-case-morpion plateau 2 0) (get-case-morpion plateau 2 1) (get-case-morpion plateau 2 2))

      (test (get-case-morpion plateau 0 0) (get-case-morpion plateau 1 0) (get-case-morpion plateau 2 0))
      (test (get-case-morpion plateau 0 1) (get-case-morpion plateau 1 1) (get-case-morpion plateau 2 1))
      (test (get-case-morpion plateau 0 2) (get-case-morpion plateau 1 2) (get-case-morpion plateau 2 2))

      (test (get-case-morpion plateau 0 0) (get-case-morpion plateau 1 1) (get-case-morpion plateau 2 2))
      (test (get-case-morpion plateau 2 0) (get-case-morpion plateau 1 1) (get-case-morpion plateau 0 2))
      ))

(defn playTicTacToe [queryParams]
  (def selected (get queryParams "selected"))
  (def player (Integer/parseInt (get queryParams "player")))
  (def board {
              "c00" (Integer/parseInt (get queryParams "c00")), "c01" (Integer/parseInt (get queryParams "c01")), "c02" (Integer/parseInt (get queryParams "c02")),
              "c10" (Integer/parseInt (get queryParams "c10")), "c11" (Integer/parseInt (get queryParams "c11")), "c12" (Integer/parseInt (get queryParams "c12")),
              "c20" (Integer/parseInt (get queryParams "c20")), "c21" (Integer/parseInt (get queryParams "c21")), "c22" (Integer/parseInt (get queryParams "c22")),
              })

  (def board (assoc board selected player))
  (parser/render-file "./templates/board.html" (buildFreshMarkers board player (buildActualBoardQuery board))))
