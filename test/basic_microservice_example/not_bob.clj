(ns basic-microservice-example.not-bob
  (:require [clojure.test :refer :all]
            [midje.sweet :refer :all]
            [matcher-combinators.test] ;; extends `is` macro with `match?`
            [matcher-combinators.matchers :refer [in-any-order
                                                  embeds
                                                  equals]]
            [matcher-combinators.midje :refer [match]]))

(defn- permutations
  "Lazy seq of all permutations of a collection"
  [coll]
  (for [i (range 0 (count coll))]
    (lazy-cat (drop i coll) (take i coll))))

(defn- random-order
  "Returns a permutation of the provided collection"
  [coll]
  (nth (permutations coll) (int (rand (count coll)))))

(defn approved-customers []
  {:status  200
   :headers "Content-Type: application/edn"
   :meta    {:version 0.1}
   :body    (random-order
              [{:name   "Alice"
                :risk   972
                :tags   [:rescored :in-house-authorizer]}
               {:name   "Bob"
                :tags   [:savings-beta :external-authorizer]
                :risk   632}])})

(fact "Check certain customers are present"
  (approved-customers)
  => (contains {:status #(<= 200 % 299)
                :body   (just [(contains {:name "Bob"})
                               (contains {:name "Alice"})]
                              :in-any-order)}))

;; Testing with midje + matcher combinators
(fact "Check certain customers are present w/ matchers"
  (approved-customers)
  => (match {:status #(<= 200 % 299)
             :body   (in-any-order
                       [{:name "Alice"}
                        {:name "Bob"}])}))

(fact "super explicit version"
  (approved-customers)
  => (match (embeds
              {:status #(<= 200 % 299)
               :body   (in-any-order
                         [(embeds {:name (equals "Alice")})
                          (embeds {:name (equals "Bob")})])})))

;; Testing with clojure.test + matcher combinators
(deftest check-approved-customers-with-matchers
  (is (match? {:status #(<= 200 % 299)
               :body (in-any-order
                       [{:name "Bob"}
                        {:name "Alice"}])}
              (approved-customers))))
