(ns specljs-test
  (:require [clojure.pprint :as pp]
            [cljs.closure :as cljsc]
            [clojure.test :as t]
            [cemerick.pomegranate :as pom]
            [clojure.java.shell :as shell])
  (:import org.apache.commons.io.FileUtils
           java.io.File))

(def temp-dir "tmp/example")
(def example-dir "fixtures/example")

(defn path [& parts]
  (str temp-dir "/" (clojure.string/join "/" parts)))

(defn given-file' [name content]
  (spit (path "src" name) 
        (with-out-str (doseq [form content] (pp/pprint form)))))

(defmacro given-file [name & content]
  (let []
    `(given-file' ~name (quote ~content))))

(defn compile-example []
  (pom/add-classpath "src/cljs")
  (pom/add-classpath "src/clj")
  (cljsc/build (path "src")
               {:output-dir (path "target")
                :output-to (path "out" "main.js")
                :optimizations :simple
                :pretty-print true}))

(defn run-tests-status-code []
  (let [status (shell/sh "phantomjs" "runner.js" :dir (path "out"))]
    (println (:out status))
    (:exit status)))

(defn the-specs-should-pass []
  (compile-example)
  (t/is (= 0 (run-tests-status-code))))

(defn rm-rf [dir]
  (let [f (java.io.File. dir)]
    (when (.exists f)
      (FileUtils/deleteDirectory f))))

(defn copy-example []
  (.mkdirs (File. temp-dir))
  (FileUtils/copyDirectory (File. example-dir) (File. temp-dir)))

(defn with-example-dir [f]
  (rm-rf temp-dir)
  (copy-example)
  (f))
