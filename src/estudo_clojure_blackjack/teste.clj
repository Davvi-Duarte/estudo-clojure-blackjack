(ns teste)

(def nome "Davvi")
(def idade "22")

(def numeros [1 2 3 4 5])                                   ;lista

(defn saudacao
  "funcao para realizar uma saudacao utlizando um argumento";documentacao
  [arg]                                                    ;arg
  (str "Bem vindo " arg)                                    ;funcao
  )

(println (saudacao nome))