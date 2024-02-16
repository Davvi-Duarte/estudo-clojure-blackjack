(ns estudo-clojure-blackjack.game
  (:require [card-ascii-art.core :as card]))

;AS VARIAVEIS ENVOLVENDO CARTAS MUDAM DE INGLES PARA PORTUGUES POIS A LIB EXIGE A NOMEACAO EXATA


;valores das cartas: A, 2, 3, 4, 5, 6, 7, 8, 9, 10, J, Q, K - do 1 ao 13 respectivamente.
(defn geracarta
  "funcao para gerar o valor da carta de forma aleatoria de 1 ate 13"
  []
   (inc( rand-int 13)))

;(println (cards)) teste de geracao de cartas

(defn player
  "funcao que define um jogador e utiliza a funcao gera carta para definiar a mao inicial"
  [nomejogador]

  ;----------------------------------------------------------------------------------------------
  ;(def carta-1 (geracarta))
  ;(def carta-2 (geracarta))

  ;def é algo utilizado pelo namespace de forma global, logo o "carta-1" poderia ser acessado de fora, o que  nao e ideal
  ;utilizamos o let para evitar isso, para criar um simbolo so para aquele escopo.
  ;----------------------------------------------------------------------------------------------

  ;REGRAS
  ;1- J, Q e K valem 10
  ;2a - [A 10] = 11 ou 21 = 21
  ;2b -[A 5 7] = 1+5+7=13 ou 11+5+7=23
  ;2c -A = 11 mas se passar de 21 ele vale 1

  (defn jqk-10
    "transforma as cartas j, q e k no valor 10"
    [card]
    (if (> card 10)
      10
      card)
    )


  ;2ª REGRA - IMPLEMENTACAO
  (defn a-11
    "Caso seja melhor usar A como 11"
    [card]
    (if (= card 1)
      11
      card)
    )

  ;1ª REGRA - IMPLEMENTACAO
  ;Para transformar J Q K valendo 10 utilizaremos o map
  ;map trasnforma um conjunto de valores em um outro conjunto de valores

  ;reduce para somar valores e reduzir para apenas um valor
  ;(reduce + [2 3 4] 1)
  ;(+ 1 2) =3
  ;(+ 3 3) =6
  ;(+ 6 4) =10
  (defn pontos-cartas
    "funcao para calcular pontos de acordo com as cartas"
    [cartas]
    (let [cartas-sem-jqk (map jqk-10 cartas)
          cartas-a-11 (map a-11 cartas-sem-jqk)
          pontos-com-a-1 (reduce + cartas-sem-jqk)
          pontos-com-a-11 (reduce + cartas-a-11)]

      (if (> pontos-com-a-11 21)
            pontos-com-a-1
            pontos-com-a-11
            ))
    )

  (let [carta1 (geracarta)
        carta2 (geracarta)
        cards [carta1 carta2]
        points (pontos-cartas cards)]
    {:player-name nomejogador
     :cards [carta1 carta2]
     :points points}
     )
  )

;teste geracao jogador
;(card/print-player (player "Davvi"))
;(card/print-player (player "Vanessa"))




;chama a funcao para gerar nova carta
;atualiza vetor dentro do player com a nova carta
;calcula os pontos da nova mao
;retorna um novo jogador
;(conj) adiciona um novo valor para um vetor
;(assoc) associa um valor á um mapa
;o (update) faz isso de forma mais enxuta
(defn mais-cartas
  "define logica de puxar mais uma carta"
  [player]
  (let [carta (geracarta)
        cartas (conj (:cards player) carta)
        player-novo (update player :cards conj carta)
        points (pontos-cartas cartas)]
    (assoc player-novo :points points)))

;(readline) ler do teclado
(defn decisao-player
  "indica a decisao d player de comprar ou nao uma carta"
  [player]
  (= (read-line) "sim" ) )

(defn decisao-dealer
  "indica a decisao do dealer de comprar ou nao uma carta comparando os pontos entre o dealer e o player"
  [player-points dealer]
  (let [dealer-points (:points dealer)]
    (< dealer-points player-points )))


(defn game
  "funcao game. Serve para ter a logica de rodadas e turnos, basicamente o coracao do jogo :)"
  [player fn-decisao-continuar]
    (println (:player-name player )": Mais cartas?")
    (if (fn-decisao-continuar player)
      (let [player-comprou-carta (mais-cartas player)]
            (card/print-player player-comprou-carta)
            (recur player-comprou-carta fn-decisao-continuar))
      player)
  )



;definicao do Dealer
(def dealer (player "Dealer"))


;definicao do player
(def player (player "Davvi Duarte"))
(card/print-player dealer)
(card/print-player player)

(def player-after-game (game player decisao-player) )

;(partial) chama a funcao decisao dealer parcialmente passando os pontos do player
(game dealer (partial decisao-dealer (:points player-after-game)))

