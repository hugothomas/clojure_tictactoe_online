(ns tictactoe.online.db
  (:require [clojure.java.jdbc :refer :all])
  (:gen-class))

(def db
  {:classname   "org.sqlite.JDBC"
   :subprotocol "sqlite"
   :subname     "db/database.db"})


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
                                             [:winner :char :default :'.']])

                          (create-table-ddl :boardValues
                                            [
                                             [:boardId :integer]
                                             [:position :tinyint]
                                             [:value :char]
                                             ["FOREIGN KEY(boardId) REFERENCES boards(boardId)"]])])
         (catch Exception e
           (println (.getMessage e))))))



(defn getActiveBoardInfo []

  (do
    (ensure-db)
    (try (first (query db [
                           "SELECT boardId, currentPlayer
                          from boards b
                          where active = true"]
                       ))
         (catch Exception e (println (.getMessage e))))))



(defn hasActiveBoard? []
  (do
    (ensure-db)
    (try (let [result (first (query db ["select boardId from boards where active = true"]))]
           (let [boardId (:boardid result)]
             (if (= boardId nil) false true)))
         (catch Exception e (println (.getMessage e))))))




(defn ensureActiveBoard []
  (do
    (ensure-db)
    (let [result (hasActiveBoard?)]
      (if (= result true)
        ()
        (try (do (execute! db ["insert into boards (active, currentPlayer) values(true, 'x');"])
                 (let [result (getActiveBoardInfo)]
                   (let [boardId (:boardid result)]
                     (insert-multi! db :boardValues
                                    [{:boardId boardId :position 0 :value "."}
                                     {:boardId boardId :position 1 :value "."}
                                     {:boardId boardId :position 2 :value "."}
                                     {:boardId boardId :position 3 :value "."}
                                     {:boardId boardId :position 4 :value "."}
                                     {:boardId boardId :position 5 :value "."}
                                     {:boardId boardId :position 6 :value "."}
                                     {:boardId boardId :position 7 :value "."}
                                     {:boardId boardId :position 8 :value "."}]))))
             (catch Exception e (println (.getMessage e))))))))



(defn getActiveBoardValues []
  (do
    (ensure-db)
    (ensureActiveBoard)
    (try (let [result (query db [
                                 "SELECT value
                                 from boards b
                                 join boardValues bv
                                 on b.boardId = bv.boardId
                                 where active = true
                                 order by bv.position
                                 "])]
           result)
         (catch Exception e (println (.getMessage e))))))





