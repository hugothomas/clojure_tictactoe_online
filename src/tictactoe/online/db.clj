(ns tictactoe.online.db
  (:require [clojure.java.jdbc :refer :all])
  (:gen-class))

(def db
  {:classname   "org.sqlite.JDBC"
   :subprotocol "sqlite"
   :subname     "db/database.db"
   })

(defn ensure-db
  "create db and table"
  []
  (if (.exists (clojure.java.io/as-file "db/database.db"))
    "ok"
    (try (db-do-commands db
                         [
                          (create-table-ddl :boards
                                            [
                                             [:boardId :integer :primary :key]
                                             [:currentPlayer :char]
                                             [:active :boolean :default :true]
                                             [:winner :char :default :'.']
                                             ])
                          (create-table-ddl :boardValues
                                            [
                                             [:boardId :integer]
                                             [:position :tinyint]
                                             [:value :char]
                                             ["FOREIGN KEY(boardId) REFERENCES boards(boardId)"]
                                             ])
                          ]
                         )
         (catch Exception e
           (println (.getMessage e))))
    )
  )

(defn getActiveBoardInfo []

  (do
    (ensure-db)
    (try (query db [
                    "SELECT boardId, currentPlayer
                    from boards b
                    where active = true"]) (catch Exception e (println (.getMessage e)))
         ))
  )

(defn hasActiveBoard? []
  (do
    (ensure-db)
    (try (query db [
                    "select EXISTS(select boardId from boards where active = true)"
                    ])
         (catch Exception e (println (.getMessage e)))
         )))



(defn ensureActiveBoard []
  (do
    (ensure-db)
    (if (hasActiveBoard?)
      true
      (try (do (execute! db [
                             "insert into boards (active, currentPlayer) values(true, 'x');"
                             ])
          (let [boardId (getActiveBoardInfo)]
            (execute! db [
                          "
                          insert into boardValues ("boardId", position, value) values (boardId, 0, '.');
                          insert into boardValues ("boardId", position, value) values (boardId, 1, '.');
                          insert into boardValues ("boardId", position, value) values (boardId, 2, '.');
                          insert into boardValues ("boardId", position, value) values (boardId, 3, '.');
                          insert into boardValues ("boardId", position, value) values (boardId, 4, '.');
                          insert into boardValues ("boardId", position, value) values (boardId, 5, '.');
                          insert into boardValues ("boardId", position, value) values (boardId, 6, '.');
                          insert into boardValues ("boardId", position, value) values (boardId, 7, '.');
                          insert into boardValues ("boardId", position, value) values (boardId, 8, '.');
                          "
                          ])
            )
          )
           (catch Exception e (println (.getMessage e)))
         )
      )
    )
  )

(defn getActiveBoardValues []

  (do
    (ensureActiveBoard)
    (try (query db [
                    "SELECT value
                    from boards b
                    join boardValues bv
                    on b.boardId = bv.boardId
                    where active = true
                    order by bv.position
                    "]) (catch Exception e (println (.getMessage e)))
         ))
  )



