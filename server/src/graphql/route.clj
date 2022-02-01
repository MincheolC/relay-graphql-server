(ns graphql.route
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]
            [com.walmartlabs.lacinia :as lacinia]
            [com.walmartlabs.lacinia.schema :as schema]
            [com.walmartlabs.lacinia.util :as util]
            [graphql.custom-scalar :as gcs]
            [graphql.user :as gu]
            [graphql.article :as ga]
            [medley.core :refer [deep-merge]]
            [ring.util.http-response :as hr]))

(defn- merge-all-schema []
  ;;; deep-merge 시 common 의 key-value 가 다른 schema 에 의해 overwrite 되는 것을 방지 하기 위해 끝에 배치해야함
  (->> ["schema/user.edn"
        "schema/article.edn"
        "schema/scalar.edn"]
       (map #(edn/read-string (slurp (io/resource %))))
       (apply deep-merge)))

(def schema
  (-> (merge-all-schema)
      (util/attach-resolvers (apply merge [gu/resolver-map
                                           ga/resolver-map]))
      (util/attach-scalar-transformers gcs/schema)
      schema/compile))

(defn handler [ctx]
  (fn [request]
    (let [query  (get-in request [:body-params :query])
          variables (get-in request [:body-params :variables])
          identity (:identity request)
          result (lacinia/execute schema query variables (assoc ctx :identity identity))]
      (hr/ok result))))

(comment
  (merge-all-schema))
