(ns tictactoe.online.boardUtils)
(use [clojure.string])
(defn displayBoard [board xString oString emptyString separatorString]
  (join separatorString
  (for [cell board]
    (if (= cell "x")
      xString
      (if (= cell "o")
        oString
        (if (= cell ".")
          emptyString
          (throw (IllegalArgumentException "board should only contains \"xo.\" characters"))
          )
        )
      )
    ))
  )