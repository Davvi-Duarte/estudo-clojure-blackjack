(ns estudo-clojure-blackjack.game
  (:require [card-ascii-art.core :as card]))

(defn geracarta
  "funcao para gerar o valor da carta de forma aleatoria de 1 ate 13"
  []
   (inc( rand-int 13)))

;(println (cards)) teste de geracao de cartas

(defn player
  "funcao que define um jogador e utiliza a funcao gera carta para definiar a mao inicial"
  [nomejogador]

  ;(def carta-1 (geracarta))
  ;(def carta-2 (geracarta))

  ;def é algo utilizado pelo namespace de forma global, logo o "carta-1" poderia ser acessado de fora, o que  nao e ideal
  ;utilizamos o let para evitar isso, para criar um simbolo so para aquele escopo.

  (let [carta1 (geracarta)
        carta2 (geracarta)]
    {:player-name nomejogador
     :cards [carta1 carta2] }

     )
  )

;teste geracao jogador
(card/print-player (player "Davvi"))
(card/print-player (player "Vanessa"))