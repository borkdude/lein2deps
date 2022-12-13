(ns lein2deps.api-test
  (:require [clojure.test :refer [deftest is testing] :as t]
            [lein2deps.api :refer [lein2deps]]))

(deftest eval-test
  (testing "unquote"
    (let [deps (:deps (lein2deps {:eval true
                                  :project-clj "
(def version \"1.0.0\")

(defproject dude/foo \"0.0.1\"
  :dependencies [[cheshire ~version]])"}))]
      (is (= ["src"] (:paths deps)))
      (is (= "1.0.0" (-> deps :deps (get 'cheshire/cheshire) :mvn/version)))))
  (testing "read-eval"
    (let [deps (:deps (lein2deps {:eval true
                                  :project-clj "
(defproject dude/foo \"0.0.1\"
  :dependencies [[cheshire #=(clojure.core/str \"1.0.\" \"0\")]])"}))]
      (is (= ["src"] (:paths deps)))
      (is (= "1.0.0" (-> deps :deps (get 'cheshire/cheshire) :mvn/version))))))

(deftest scope-test
  (testing "provided"
    (let [deps (:deps (lein2deps {:eval true
                                  :project-clj "
(defproject dude/foo \"0.0.1\"
  :dependencies [[cheshire \"1.0.0\"]
                 [org.clojure/test.check \"1.0.13\" :scope \"provided\"]])"}))]
      (is (= "1.0.0" (-> deps :deps (get 'cheshire/cheshire) :mvn/version)))
      (is (= 1 (count (:deps deps))))
      (is (= '{:extra-deps #:org.clojure{test.check #:mvn{:version "1.0.13"}}}
             (-> deps :aliases :dev))))))

(deftest regex-test
  (let [deps (:deps (lein2deps {:project-clj "
(defproject dude/foo \"0.0.1\"
  :dependencies [[cheshire \"1.0.0\"]
                 [org.clojure/test.check \"1.0.13\" :scope \"provided\"]]
  :regex #\"foo\")"}))]
    (is (= "1.0.0" (-> deps :deps (get 'cheshire/cheshire) :mvn/version)))))

(deftest maintain-ordered-test
  (let [orig-deps (mapv (fn [i] (symbol "d" (str "dep" i))) (shuffle (range 10)))
        deps-in-order (pr-str (into []
                                    (concat (mapv (fn [dep] [dep "0.0.1"]) orig-deps)
                                            ;; dev deps
                                            (mapv (fn [dep] [dep "0.0.2" :scope "provided"]) orig-deps))))
        project-clj (str "
(defproject dude/foo \"0.0.1\"
  :dependencies " deps-in-order ")")]
    (let [deps (:deps (lein2deps {:project-clj project-clj}))]
      (is (= orig-deps (-> deps :deps (keys))))
      (is (= orig-deps (-> deps :aliases :dev :extra-deps (keys)))))

    (let [deps (:deps (lein2deps {:eval true
                                  :project-clj project-clj}))]
      (is (= orig-deps (-> deps :deps (keys))))
      (is (= orig-deps (-> deps :aliases :dev :extra-deps (keys)))))))

(deftest prevent-namespacing-test
  (let [output (with-out-str (lein2deps {:print true
                                         :project-clj "
(defproject dude/foo \"0.0.1\"
  :dependencies [[cheshire \"1.0.0\"]])"}))]
    (is (clojure.string/includes? output "cheshire/cheshire {:mvn/version \"1.0.0\"}"))))
