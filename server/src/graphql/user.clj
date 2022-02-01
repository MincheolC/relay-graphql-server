(ns graphql.user
  (:require [database :refer [insert! execute! execute-one!]]
            [next.jdbc :as jdbc]
            [util.base64 :as base64]))

(def ^:private encode-prefix "users.")

(defn- encode-user-id [user-id]
  (base64/encode (str encode-prefix user-id)))

(defn decode-user-id [encoded-user-id]
  (let [decoded (base64/decode encoded-user-id)
        [_ user-id] (re-find (re-pattern (str encode-prefix "(\\d+)"))
                             decoded)]
    user-id))

(defn get-user-by-id [ds id]
  (let [query {:select [:*]
               :from   [:users]
               :where  [:= :id id]}]
    (when-let [result (execute-one! ds query)]
      (update result :id encode-user-id))))

(defn get-users [{ds :db} _ _]
  (let [query {:select [:*]
               :from [:users]}]
    (->> (execute! ds query)
         (map #(update % :id encode-user-id)))))

(defn create-user [{ds :db} {:keys [name phone]} _]
  (let [insert-query {:insert-into :users
                      :values [{:name name :phone phone}]}]
    (jdbc/with-transaction [tx ds]
      (when-let [id (:generated-key (insert! tx insert-query))]
        (get-user-by-id ds id)))))

(defn update-user [{ds :db} {id :id username :name phone :phone} _]
  (if (or username phone)
    (let [update-query (cond-> {:update :users
                                :set {}
                                :where  [:= :id id]}
                               name (assoc-in [:set :name] username)
                               phone (assoc-in [:set :phone] phone))]
      (execute-one! ds update-query)
      (get-user-by-id ds id))
    (get-user-by-id ds id)))

(def resolver-map {:get-users get-users
                   :create-user create-user
                   :update-user update-user})


(comment
  (re-pattern (str encode-prefix "(\\d+)"))
  (re-find (re-pattern (str encode-prefix "(\\d+)")) "users.1"))
