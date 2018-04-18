(ns basic-microservice-example.logic)

(defn new-account [customer-id customer-name]
  {:id          (java.util.UUID/randomUUID)
   :name        customer-name
   :tags        ["savings-beta" "verified"]
   :customer-id customer-id})
