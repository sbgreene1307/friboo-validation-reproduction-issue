(ns exception-handling-reproduction.api
  (:require [org.zalando.stups.friboo.ring :refer :all]
            [org.zalando.stups.friboo.log :as log]
            [com.stuartsierra.component :as component]
            [org.zalando.stups.friboo.config :refer [require-config]]
            [ring.util.response :as r]
            [exception-handling-reproduction.sql :as sql]))


(defrecord Controller [;; Set on creation
                       configuration
                       ;; Injected by the framework
                       db]
  component/Lifecycle
  (start [this]
    (log/info "Starting API controller.")
    this)
  (stop [this]
    (log/info "Stopping API controller.")
    this))


(defn put-memory
  "Creates or updates a memory by ID"
  [{:as this :keys [db]} {:keys [memory_id memory]} request]
  (log/info "Writing a memory with ID %s" memory_id)
  (let [memory-data {:textlist (:text memory)}]
    (sql/cmd-create-memory! memory-data {:connection db})
		(r/response nil)))
