(ns lein2deps.build
  (:require [clojure.tools.build.api :as b]))

(def basis (b/create-basis {:project "deps.edn"}))

(defn compile-java [_]
  (let [opts (get-in basis [:aliases :lein2deps :lein2deps/compile-java])]
    (b/javac (assoc opts :basis basis))))
