(ns test
  (:require app
            [specljs :as spec]))

(.log js/console (app/greet "World"))

(throw "cant compile this")

(spec/describe "hello-world")
