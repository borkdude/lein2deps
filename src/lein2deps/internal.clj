(ns lein2deps.internal
  (:require
   [clojure.walk :as walk]
   [edamame.core :as e]))

(defmacro defproject [& [_name _version & body]]
  `(apply hash-map
          '~(walk/prewalk (fn [form]
                            (if (and (seq? form)
                                     (= 'clojure.core/unquote (first form)))
                              (eval (second form))
                              form))
                          body)))

(defn safe-parse [input]
  (let [parser (e/reader input)
        cfg {:read-eval identity}
        form (first (take-while #(or
                                  (and (seq? %)
                                       (= 'defproject (first %)))
                                  (= ::e/eof %)) (repeatedly #(e/parse-next parser cfg))))
        project-clj-edn form]
    (apply hash-map (drop 3 project-clj-edn))))

(defn qualify-dep-name [d]
  (if (simple-symbol? d)
    (symbol (str d) (str d))
    d))

(defn convert-dep [[name version & {:keys [classifier exclusions]}]]
  (let [name (qualify-dep-name name)
        name (if classifier
               (symbol (str name "$" classifier))
               name)
        params (cond-> {:mvn/version version}
                 (seq exclusions)
                 (assoc :exclusions (mapv qualify-dep-name exclusions)))]
    [name params]))

(defn add-prep-lib [deps-edn project-edn]
  (let [{:keys [java-source-paths compile-path javac-options]} project-edn]
    (assoc deps-edn
           :aliases
           {:lein2deps
            {:deps
             {'io.github.borkdude/lein2deps {:git/sha "1bcf2fbbcbef611381e5e9ccdc77bec1e62ea5e5"}}
             :ns-default 'lein2deps.build
             :lein2deps/compile-java (cond-> {:src-dirs java-source-paths
                                              :class-dir compile-path}
                                       javac-options
                                       (assoc :javac-opts javac-options))}}
           :deps/prep-lib
           {:ensure compile-path
            :alias :lein2deps
            :fn 'compile-java})))
