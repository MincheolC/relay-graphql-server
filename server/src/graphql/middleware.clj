(ns graphql.middleware
  (:require [util.graphql :refer [->camel-case ->kebab-case]]
            [medley.core :refer [map-vals]]))

(defn make-resolver [{:keys [middleware resolver]}]
  (reduce (fn [r f]
            (f r)) resolver (reverse middleware)))

(defn wrap-authorization [roles]
  (fn [resolver]
    (fn [ctx variable parent]
      (if (roles (keyword (:role ctx)))
        (resolver ctx variable parent)
        (do
          (prn "invalid role"))))))

(defn wrap-naming-convention []
  (fn [resolver]
    (fn [ctx variable parent]
      (let [resolved (resolver ctx
                               (->kebab-case variable)
                               parent)]
        (->camel-case resolved)))))


(comment
  (defn prn-resolver [ctx variable parent]
    (prn ctx variable parent))

  (let [middleware [(wrap-authorization #{:admin :buyer :farmer})
                    (wrap-naming-convention)]
        resolver' (make-resolver {:resolver prn-resolver
                                  :middleware middleware})]
    (resolver' {:role "buyer"} {"helloWorld" "graphql"} nil)))

(comment
  (map-vals inc {:a 1
                 :b 2})
  (select-keys {:a 1
                :b 2} [:a]))
