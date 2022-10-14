(ns lein2deps.api-test
  (:require [clojure.test :refer [deftest is] :as t]
            [lein2deps.api :refer [lein2deps]]))

(deftest eval-test
  (let [deps (:deps (lein2deps {:eval true
                               :project-clj "
(def version \"1.0.0\")

(defproject dude/foo \"0.0.1\"
  :dependencies [[cheshire ~version]])"}))]
    (is (= ["src"] (:paths deps)))
    (is (= "1.0.0" (-> deps :deps (get 'cheshire/cheshire) :mvn/version)))))
