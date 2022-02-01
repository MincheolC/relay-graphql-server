(ns migration
  (:require [migratus.core :as migratus]
            [hikari-cp.core :as hk]
            [clojure.edn :as edn]))

(defn get-config
  "상황에 따라 프로파일을 바꾸고 migration/config/{profile}.edn을 넣어주세요."
  [profile]
  (let [ds-config-filename (format "migration/config/%s.edn" profile)
        ds-options (edn/read-string (slurp ds-config-filename))]
    {:store                :database
     :migration-dir        "migrations/"
     :migration-table-name "migratus_migrations"
     :db                   {:datasource (hk/make-datasource ds-options)}}))

(comment
  (migratus/create (get-config "local") "create-table-follows")
  (migratus/create (get-config "local") "insert-follows-seed-data")

  (migratus/migrate (get-config "local"))
  (migratus/rollback (get-config "local"))

  ; 여러 개의 마이그레이션 실행
  (migratus/up (get-config "local") 20210812044623 20210813112233)
  )
