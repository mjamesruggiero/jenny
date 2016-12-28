(defproject jenny "0.1.0-SNAPSHOT"
  :description "Accessing Google Drive for Fun"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [google-apps-clj "0.5.3"]]
  :main ^:skip-aot jenny.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
