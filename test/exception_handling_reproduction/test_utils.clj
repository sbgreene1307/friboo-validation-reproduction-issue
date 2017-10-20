(ns exception-handling-reproduction.test-utils
  (:require [clojure.java.jdbc :as jdbc]
            [org.zalando.stups.friboo.system.db :as db]
            [com.stuartsierra.component :as component]
            [exception-handling-reproduction.core :as core]
            [org.zalando.stups.friboo.zalando-internal.config :as config]
            [exception-handling-reproduction.utils :as u]))

(defn wipe-db [db]
  (jdbc/delete! db :memories ["text IS NOT NULL"]))

(defmacro with-db [[db] & body]
  `(let [dev-config# (u/load-dev-config)
         config#     (config/load-config (merge core/default-db-config dev-config#) [:db])
         ~db         (component/start (db/map->DB {:configuration (:db config#)}))]
     (try
       ~@body
       (finally
         (component/stop ~db)))))
