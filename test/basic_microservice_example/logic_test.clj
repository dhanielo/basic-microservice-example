(ns basic-microservice-example.logic-test
  (:require [basic-microservice-example.logic :as logic]
            [midje.sweet :refer :all]))

(def customer-id (java.util.UUID/randomUUID))

(fact "New account generation"
  (logic/new-account customer-id "Tim Maia") => (just {:id          uuid?
                                                       :name        "Tim Maia"
                                                       :customer-id customer-id}))



