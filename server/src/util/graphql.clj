(ns util.graphql
  (:require [camel-snake-kebab.core :as csk]
            [camel-snake-kebab.extras :as cske]))

(defn ->kebab-case [m]
  (cske/transform-keys csk/->kebab-case-keyword m))

(defn ->camel-case [m]
  (cske/transform-keys csk/->camelCaseKeyword m))

(defn make-resolver [{:keys [resolver]}]
  (fn [context args parent]
    (->camel-case (resolver context
                            (->kebab-case args)
                            parent))))

(comment
  (def m {"userName" "myName"
          "obj" {"userAge" 23}})
  (->kebab-case m)
  (->camel-case (->kebab-case m)))
