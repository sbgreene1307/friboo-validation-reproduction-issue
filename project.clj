(defproject exception-handling-reproduction "0.0.1-SNAPSHOT"
  :description "Example project based on friboo-ext-zalando"
  :url "http://example.com/FIXME"
  :license {:name "The Apache License, Version 2.0"
            :url  "http://www.apache.org/licenses/LICENSE-2.0"}
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [yesql "0.5.3"]
                 [org.zalando.stups/friboo-ext-zalando "2.0.0-beta1"]]
  :main ^:skip-aot exception-handling-reproduction.core
  :uberjar-name "exception-handling-reproduction.jar"
  :target-path "target/%s"
  :manifest {"Implementation-Version" ~#(:version %)}
  :plugins [[lein-cloverage "1.0.9"]
            [lein-set-version "0.4.1"]]
  :aliases {"cloverage" ["with-profile" "test" "cloverage"]}
  :profiles {:uberjar {:aot :all}
             :test    {:dependencies [[midje "1.8.3"]]}
             :dev     {:repl-options {:init-ns user}
                       :source-paths ["dev"]
                       :dependencies [[org.clojure/tools.namespace "0.2.11"]
                                      [org.clojure/java.classpath "0.2.3"]
                                      [midje "1.8.3"]]}})
