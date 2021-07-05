;; L'objectif du TP est de realiser un Morpion (tic-tac-toe)
;; en clojure a deux joueurs

;; Il est conseille de suivre le schema indique, en implementant les fonctions
;; dans cet ordre et de les tester au fur et a mesure

;; les etudiants rapides (et courageux) pourront s'inspirer
;; de cette implementation pour faire un 'puissance 4' ...
;; -------------------------------------------
;; Constructeur du morpion
;; On pourra utiliser le type vector
;; (a.k.o liste à la python)
;; l'une des stratégies les plus simple consiste à coder les cases :
;; - 0 : case vide
;; - 1 : joueur X
;; - 2 : joueur O


(defn make-morpion []
  (vec (take 9 (repeat 0))))

; test de la fonction :
(make-morpion)
; -------------------------------------------

; -------------------------------------------
; Renvoie le symbole present dans
; la case (x,y) du plateau
; x et y sont donnes en coordonnees de 0 a 2
(defn get-case-morpion [plateau i j]
  (get plateau (+ i (* 3 j))))


; test de la fonction :
(get-case-morpion (make-morpion) 1 1)
; -------------------------------------------

; -------------------------------------------
; Affiche le plateau

(defn print-morpion [plateau]
  (defn pcase [c]
    (case c
      0 "."
      1 "X"
      2 "O"))
  (newline)
  (dotimes [i 3]
    (dotimes [j 3]
      (print (pcase (get-case-morpion plateau i j))))
    (newline)))


; test de la fonction :
(print-morpion (make-morpion))
; -------------------------------------------

; -------------------------------------------
;; Marque le coup du joueur player (represente par son numéro
;; a la position (i,j)

(defn set-case-morpion [plateau player i j]
  (assoc plateau (+ i (* 3 j)) player))


; test de la fonction :
(print-morpion (set-case-morpion (make-morpion) 1 1 1))
(print-morpion (set-case-morpion (make-morpion) 2 2 2))
(print-morpion (set-case-morpion (make-morpion) 2 1 2))
; -------------------------------------------

; -------------------------------------------
; renvoie true si le coup est legal (case (i,j) valide et vide
(defn legal-move-morpion? [plateau i j]
  (and (>= i 0)
       (<= i 2)
       (>= j 0)
       (<= j 2)
       (= (get-case-morpion plateau i j) 0)))


; test de la fonction :
(legal-move-morpion? (make-morpion) 1 1)
(legal-move-morpion? (make-morpion) 3 3)
(legal-move-morpion? (set-case-morpion (make-morpion) 1 1 1) 1 1)
; -------------------------------------------

; -------------------------------------------
; renvoie true si une position gagnante est reconnue
; alignement de 3 cases identiques et differentes de 0
(defn winning-morpion? [plateau]
  (defn test [a b c] (and (not= a 0) (= a b) (= a c)))
  (or (test (get-case-morpion plateau 0 0)  (get-case-morpion plateau 0 1)  (get-case-morpion plateau 0 2))
      (test (get-case-morpion plateau 1 0)  (get-case-morpion plateau 1 1)  (get-case-morpion plateau 1 2))
      (test (get-case-morpion plateau 2 0)  (get-case-morpion plateau 2 1)  (get-case-morpion plateau 2 2))

      (test (get-case-morpion plateau 0 0)  (get-case-morpion plateau 1 0)  (get-case-morpion plateau 2 0))
      (test (get-case-morpion plateau 0 1)  (get-case-morpion plateau 1 1)  (get-case-morpion plateau 2 1))
      (test (get-case-morpion plateau 0 2)  (get-case-morpion plateau 1 2)  (get-case-morpion plateau 2 2))

      (test (get-case-morpion plateau 0 0)  (get-case-morpion plateau 1 1)  (get-case-morpion plateau 2 2))
      (test (get-case-morpion plateau 2 0)  (get-case-morpion plateau 1 1)  (get-case-morpion plateau 0 2))
      ))

; test de la fonction :
;(winning-morpion?   (make-morpion))
(-> (make-morpion) (set-case-morpion 1 0 0) (set-case-morpion 1 0 1) (set-case-morpion 1 0 2) (winning-morpion?))
(-> (make-morpion) (set-case-morpion 1 0 0) (set-case-morpion 1 0 1) (winning-morpion?))
(-> (make-morpion) (winning-morpion?))



; notez l'utilisation de -> pour simuler plusieurs coups ....
; -------------------------------------------

; -------------------------------------------
; lit un coup au clavier pour le joueur player (X ou O)
; recommence si le coup n'est pas legal



(defn move-morpion [plateau player]
  (defn getpos [] (let [x (read-line) n (read-string x)]
                    (cond (not (integer? n)) (do (println "not a number") (getpos))
                          (> n 3) (do (println "trop grand") (getpos))
                          (< n 1) (do (println "trop petit") (getpos))
                          :else (dec n))))
  (println "Player " player "joue !")
  (let [i (do (println "ligne [1-3] :") (getpos))
        j (do (println "colonne [1-3] :") (getpos))]
    (cond (not (legal-move-morpion? plateau i j)) (do (println "illegal move") (move-morpion plateau player))
          :else  (set-case-morpion plateau player i j))))

; test de la fonction :
(print-morpion (move-morpion (make-morpion) 1))
(print-morpion (move-morpion (set-case-morpion (make-morpion) 2 0 2) 1))
; -------------------------------------------

; -------------------------------------------
; renvoie 1 si player = 2 et 2 si player = 1
(defn exchange [player]
  (- 3 player))
; -------------------------------------------

; -------------------------------------------
; renvoie #t si toutes les cases sont occupees
; #f sinon
(defn end-morpion? [plateau]
  (empty? (filter (fn [x] (zero? x)) plateau)))
; -------------------------------------------

; -------------------------------------------
; fonction principale
(defn play-morpion
  ([] (play-morpion (make-morpion) 1))
  ([plateau player]
   (print-morpion plateau)
   (let [p (move-morpion plateau player)]
     (cond (winning-morpion? p) (do (print-morpion plateau) (println "Player " player " wins !"))
           (end-morpion? p)  (do (print-morpion plateau)(println "draw"))
           :else (play-morpion p (exchange player))))))
