(ns lein2deps.api
  (:require
   [babashka.cli :as cli]
   [babashka.fs :as fs]
   [flatland.ordered.map :refer [ordered-map]]
   [lein2deps.internal :refer [safe-parse convert-dep
                               add-prep-lib
                               #_:clj-kondo/ignore
                               defproject
                               pprint]]))

(defn ^:private repositories->repos
  [repositories]
  (into {}
    (map (fn [[repo-name repo-spec]]
           (if (string? repo-spec)
             [repo-name {:url repo-spec}]
             [repo-name repo-spec])))
    repositories))

(defn lein2deps
  "Converts project.clj to deps.edn.

  Options:
  * `:project-clj` - defaults to `project.clj` in working directory. In case the specified file does not exist, the input is treated as a string.
  * `:eval` - evaluate code in `project.clj`. Defaults to `false`
  * `:write-file` - write `deps.edn` to specified file. Defaults to not writing.
  * `:print` - print `deps.edn` to stdout. Defaults to `false`."
  [opts]
  (let [project-clj (or (:project-clj opts)
                        "project.clj")
        project-clj-str (if (fs/exists? project-clj)
                          (slurp project-clj)
                          project-clj)
        project-edn (if (:eval opts)
                      (binding [*ns* (find-ns 'lein2deps.api)]
                        (load-string project-clj-str))
                      (safe-parse project-clj-str))
        project-edn (merge {:compile-path "target/classes"
                            :source-paths ["src"]
                            :resource-paths ["resources"]}
                           project-edn)
        {:keys [dependencies source-paths resource-paths compile-path java-source-paths repositories]} project-edn
        deps (map convert-dep dependencies)
        dev-deps (into (ordered-map) (keep #(when (= :dev (:alias (second %)))
                                              [(first %) (dissoc (second %) :alias)])
                                           deps))
        deps (into (ordered-map) (remove (comp :alias second) deps))
        deps-edn {:paths (cond-> (into (vec source-paths) resource-paths)
                           java-source-paths
                           (conj compile-path))
                  :deps deps}
        deps-edn (cond-> deps-edn
                   java-source-paths
                   (add-prep-lib project-edn)
                   (seq repositories) (assoc :mvn/repos (repositories->repos repositories))
                   (seq dev-deps) (assoc-in [:aliases :dev :extra-deps] dev-deps))]
    (when-let [f (:write-file opts)]
      (spit (str f) (with-out-str (pprint deps-edn))))
    (when (:print opts)
      (pprint deps-edn))
    {:deps deps-edn }))

(defn -main
  {:no-doc true}
  [& args]
  (let [opts (cli/parse-opts args)]
    (if (:help opts)
      (println "Usage: lein2deps <opts>

Options:

  --project-clj <file>: defaults to \"project.clj\"
  --eval              : evaluate project.clj. Use at your own risk.")
      (do (lein2deps (merge {:print true} opts))
          nil))))
