(ns vr-match-api.app.my-webapp.handler
  (:require [com.stuartsierra.component :as component]
            [cheshire.core :refer [generate-string]]
            [clojure.edn :as edn]
            [com.walmartlabs.lacinia.util :refer [attach-resolvers]]
            [com.walmartlabs.lacinia.schema :as schema]
            [com.walmartlabs.lacinia :refer [execute]]
            [vr-match-api.domain.usecase.example :as example-usecase]
            [vr-match-api.app.my-webapp.resolvers :as resolvers]))

(defn index
  [{:keys [example-usecase] :as comp}]
  (-> {:message (example-usecase/get-message example-usecase)}

      generate-string))

(defn graphql
  [{:keys [graphql-schema]} req]
  (let [query (-> req :query-params :query)]
    (-> (execute graphql-schema query nil nil)
        generate-string)))

(defn- load-schema []
  (-> "resources/graphql-schema.edn"
      slurp
      edn/read-string
      (attach-resolvers {:resolve-approach-list resolvers/approach-list})
      schema/compile))

(defrecord MyWebappHandlerComponent [example-usecase graphql-schema]
  component/Lifecycle
  (start [this]
    (println ";; Starting MyWebappHandlerComponent")
    (-> this
        (assoc :graphql-schema (load-schema))))
  (stop [this]
    (println ";; Stopping MyWebappHandlerComponent")
    (-> this
        (dissoc :graphql-schema))))

(defn my-webapp-handler-component
  []
  (map->MyWebappHandlerComponent {}))
