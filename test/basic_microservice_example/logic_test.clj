(ns basic-microservice-example.logic-test
  (:require [basic-microservice-example.logic :as logic]
            [midje.sweet :refer :all]))

(def customer-id (java.util.UUID/randomUUID))

(fact "New account generation"
  (logic/new-account customer-id "bob") => (just {:id          uuid?
                                                  :name        "not-bob"
                                                  :tags        (just ["verified" "savings-beta"]
                                                                     :in-any-order)
                                                  :customer-id customer-id}))



