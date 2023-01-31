(ns leiningen.lein2deps
  (:require [lein2deps.api :as lein2deps]))

(defn lein2deps
  [_project & args]
  (apply lein2deps/-main args))
