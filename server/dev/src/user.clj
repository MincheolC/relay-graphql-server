(ns user
  (:require [integrant.core :as ig]
            [integrant.repl :as ig-repl]
            [integrant.repl.state :as state]
            [clojure.tools.namespace.repl :refer [set-refresh-dirs]]
            [core]))

(set-refresh-dirs "dev" "src")

(ig-repl/set-prep!
  (fn [] (-> "dev/resources/config.edn" slurp ig/read-string)))

(def go ig-repl/go)
(def halt ig-repl/halt)
(def reset ig-repl/reset)
(def suspend ig-repl/suspend)

(def app (-> state/system :handler/app))
(def db (-> state/system :db/mysql))
(def config (-> state/system :app/config))

(comment
  (clojure.tools.namespace.repl/set-refresh-dirs "src")
  (go)
  (halt)
  (reset)
  (suspend))
