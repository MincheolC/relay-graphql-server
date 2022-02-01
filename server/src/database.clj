(ns database
  (:require [honey.sql :as sql]
            [next.jdbc :as jdbc]
            [next.jdbc.result-set :as rs]))

(defn ->qs [query]
  (sql/format query {:dialect :mysql}))

(defn execute!
  ([ds query] (execute! ds query {:builder-fn rs/as-unqualified-kebab-maps}))
  ([ds query opt]
   (jdbc/execute! ds (->qs query) opt)))

(defn execute-one!
  ([ds query] (execute-one! ds query {:builder-fn rs/as-unqualified-kebab-maps}))
  ([ds query opt]
   (jdbc/execute-one! ds (->qs query) opt)))

(defn insert! [ds query]
  (execute-one! ds query {:builder-fn rs/as-unqualified-kebab-maps
                          :return-keys true}))
