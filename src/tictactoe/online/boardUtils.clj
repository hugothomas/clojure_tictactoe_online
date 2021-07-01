(ns tictactoe.online.boardUtils)

(defn displayBoard [board xString oString emptyString]
  (for [cell board]
    (if (= cell "x")
      xString
      (if (= cell "o")
        oString
        (if (= cell ".")
          emptyString
          (throw (IllegalArgumentException. "board should only contains \"xo.\" characters"))
          )
        )
      )
    )
  )
