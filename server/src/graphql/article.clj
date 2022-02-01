(ns graphql.article
  (:require [database :refer [execute! execute-one!]]
            [graphql.user :refer [decode-user-id]]
            [next.jdbc :as jdbc]
            [util.base64 :as base64]
            [util.graphql :refer [make-resolver]]))

(def ^:private encode-prefix "articles.")

(defn- encode-article-id [article-id]
  (base64/encode (str encode-prefix article-id)))

(defn decode-article-id [encoded-article-id]
  (let [decoded (base64/decode encoded-article-id)
        [_ article-id] (re-find (re-pattern (str encode-prefix "(\\d+)"))
                                decoded)]
    article-id))

(defn get-article-by-id [ds id]
  (let [query {:select [:*]
               :from   [:articles]
               :where  [:= :id id]}]
    (when-let [result (execute-one! ds query)]
      (update result :id encode-article-id))))

(defn get-articles-by-user-id [{ds :db} _ {user-id :id}]
  (let [query {:select [:*]
               :from   [:articles]
               :where  [:= :author_id (decode-user-id user-id)]}]
    (->> (execute! ds query)
         (map #(update % :id encode-article-id)))))

(defn get-articles [{ds :db} _ _]
  (let [query {:select [:*]
               :from [:articles]}]
    (->> (execute! ds query)
         (map #(update % :id encode-article-id)))))

(defn update-article [{ds :db} {id :id like-count :likeCount} _]
  (if like-count
    (let [update-query {:update :user
                        :set {:like_count like-count}
                        :where  [:= :id id]}]
      (jdbc/with-transaction [tx ds]
        (execute-one! tx update-query)
        (get-article-by-id tx id)))
    (get-article-by-id ds id)))

(def resolver-map {:get-articles            (make-resolver {:resolver get-articles})
                   :get-articles-by-user-id (make-resolver {:resolver get-articles-by-user-id})
                   :update-article          update-article})

