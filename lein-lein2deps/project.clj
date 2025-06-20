(defproject io.github.borkdude/lein-lein2deps "0.1.1"
  :description "A lein plugin to turn project.clj into deps.edn"
  :url "https://github.com/borkdude/lein2deps"
  :license {:name "MIT"
            :url "https://opensource.org/licenses/MIT"}
  :eval-in-leiningen true
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [io.github.borkdude/lein2deps "0.1.1"]]
  :deploy-repositories [["clojars" {:url "https://clojars.org/repo"
                                    :username :env/clojars_user
                                    :password :env/clojars_pass
                                    :sign-releases false}]])
