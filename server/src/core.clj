(ns core
  (:require [integrant.core :as ig]
            [clojure.java.io :as io]
            [hikari-cp.core :refer [make-datasource close-datasource]]
            [ring.adapter.jetty :refer [run-jetty]]
            [route]))

(defmethod ig/init-key :db/mysql
  [_ config]
  (make-datasource config))

(defmethod ig/init-key :app/config
  [_ config]
  config)

(defmethod ig/init-key :handler/app
  [_ config]
  (route/app config))

(defmethod ig/init-key :server/jetty
  [_ {:keys [handler] :as options}]
  (run-jetty handler {:port (:port options) :join? false}))

(defmethod ig/halt-key! :db/mysql
  [_ conn]
  (close-datasource conn))

(defmethod ig/halt-key! :server/jetty
  [_ server]
  (.stop server))

(defmethod ig/suspend-key! :db/mysql
  [_ conn]
  (close-datasource conn))

(defmethod ig/suspend-key! :server/jetty
  [_ server]
  (.stop server))

(defn -main []
  (let [config (-> "config.edn" io/resource slurp ig/read-string)]
    (ig/init (ig/prep config))))

(comment
  (-main))
