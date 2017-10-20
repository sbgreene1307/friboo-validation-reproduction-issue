(ns exception-handling-reproduction.utils
  (:require [clojure.edn :as edn])
  (:import (java.net ServerSocket)))

(defn slurp-if-exists [file]
  (when (.exists (clojure.java.io/as-file file))
    (slurp file)))

(defn load-dev-config
  ([]
   (load-dev-config "./dev-config.edn"))
  ([file]
   (edn/read-string (slurp-if-exists file))))

(defn get-free-port []
  (let [sock (ServerSocket. 0)]
    (try
      (.getLocalPort sock)
      (finally
        (.close sock)))))
