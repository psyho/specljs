(ns specljs.macros)

(defmacro describe [name & body]
  `(js/describe ~name (fn [] ~@body)))

(defmacro it [name & body]
  `(js/it ~name (fn [] ~@body)))

(defmacro should= [expected actual]
  `(.toEqual (js/expect ~actual) ~expected))

(defmacro should-not= [expected actual]
  `(.toEqual (.-not (js/expect ~actual)) ~expected))
