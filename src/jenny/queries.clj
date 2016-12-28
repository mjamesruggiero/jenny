(ns jenny.queries
  (require [google-apps-clj.google-drive :as gdrive]
           [jenny.credentials :as jennycred]))

(def config-file
  "config/google-creds.edn")

(def g-scopes
  ["https://www.googleapis.com/auth/drive"
   "https://docs.google.com/feeds/"
   "https://spreadsheets.google.com/feeds"
   "https://www.googleapis.com/auth/calendar"])

(def query
  {:model :files :action :list})

(defn file-query
  [config q]
  (gdrive/execute-query! config q))

(defn get-file
  [config file-id]
  (gdrive/execute-query! config {:model :files :action :get :file-id file-id}))

(defn titles
  [results]
  (map #(:title %) results))

(defn mine
  [rec]
  (some? (:owned-by-me rec)))

(defn- prune [row]
  (select-keys row
               [:last-modifying-user-name
                :id
                :export-links
                :title]))

(defn only-one-on-ones [records]
  (filter #(re-matches #"[a-zA-Z]+ 1:1" (:title %)) records))

(def my-files
  (let [scopes g-scopes
        file-path config-file
        conf (jennycred/configuration file-path)
        r (file-query conf query)
        filtered (filter mine r)]
    (map #(prune %) filtered)))

(def one-file
  (let [file-path config-file
                     conf (jennycred/configuration file-path)
                     test-id (first (map #(:id %) (only-one-on-ones my-files)))
                     gfile (get-file conf test-id)
                     r (gdrive/download-file! conf (:id  gfile))]
                 r))
