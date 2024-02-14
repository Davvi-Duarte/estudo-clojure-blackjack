(ns teste)

(def nome "Davvi")
(def idade "22")

(defn saudacao
  "funcao para realizar uma saudacao utlizando um argumento";documentacao
  [nome]                                                    ;arg
  (str "Bem vindo " nome)                                    ;funcao
  )

(println (saudacao nome))