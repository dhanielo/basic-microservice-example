(ns basic-microservice-example.controller-test
  (:require [midje.sweet :refer :all]
            [basic-microservice-example.db.saving-account :as db.saving-account]
            [basic-microservice-example.controller :as controller])
  (:import [java.util UUID]))

(def customer-id (UUID/randomUUID))

(fact "Sketching account creation"
  (controller/create-account! customer-id ..storage.. ..http..) => (just {:id          uuid?
                                                                          :name        "Tom Zé"
                                                                          :tags        (just ["verified" "savings-beta"]
                                                                                             :in-any-order)
                                                                          :customer-id customer-id})
  (provided
    (controller/get-customer customer-id ..http..) => {:customer-name "Tom Zé"}
    (db.saving-account/add-account! (contains {:name "Tom Zé"}) ..storage..) => irrelevant))
