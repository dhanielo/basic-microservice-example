(ns basic-microservice-example.matcher-example-test
  (:require [midje.sweet :refer :all]
            [matcher-combinators.matchers :refer :all]
            [matcher-combinators.midje :refer [match]]
            [matcher-combinators.test] ;; extends `is` macro with `match?`
            [clojure.test :refer :all]))

(fact "sequences: every level contains _exactly_ these elements"
  [[[1]]] => (just [(just [(just [odd?])])])

  [[[1]]] => (match [[[odd?]]]))

(fact "sequences: in-any-order"
  [1 2 3] => (just [odd? odd? even?] :in-any-order)

  [1 2 3] => (match (in-any-order [odd? odd? even?])))

(fact "maps: every level contains _at least_ these key/value pairs"
  {:a {:b {:c 1}}} => (contains {:a (contains {:b (contains {:c odd?})})})

  {:a {:b {:c 1}}} => (match {:a {:b {:c odd?}}}))

(fact "maps: every level contains _exactly_ these key/value pairs"
  {:a {:b {:c 1}}} => (just {:a (just {:b (just {:c odd?})})})

  {:a {:b {:c 1}}} => (match (equals {:a (equals {:b (equals {:c odd?})})})))


(deftest works-with-clojure-test
  (is (match? (equals {:a (equals {:b (equals {:c odd?})})})
              {:a {:b {:c 1}}})))
