(ns jenny.credentials
  (require [clojure.edn :as edn]
           [google-apps-clj.credentials :as gauth]))

(defn configuration
  [file-path]
  (edn/read-string (slurp file-path)))

(defn configuration-map
  [config scopes]
  (gauth/get-auth-map config scopes))
