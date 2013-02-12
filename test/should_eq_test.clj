(ns should-eq-test
  (:use [clojure.test :only [deftest use-fixtures]]
        [specljs-test :only [given-file the-specs-should-pass with-example-dir]]))

(deftest passing-should=
  (given-file 
    "hello.cljs"

    (ns hello)
    (defn greet [name]
      (str "Hello, " name "!")))

  (given-file
    "hello_test.cljs"

    (ns hello-test
      (:require [hello :as h])
      (:require-macros [specljs.macros :as s]))

    (s/describe "hello"
      (s/it "should greet"
               (s/should= "Hello, John!" (h/greet "John")))
      (s/it "should not say bye"
               (s/should-not= "Bye, John!" (h/greet "John"))) ))
      
  (the-specs-should-pass))

(use-fixtures :each with-example-dir)
