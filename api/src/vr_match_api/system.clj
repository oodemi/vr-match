(ns vr-match-api.system
  (:require [com.stuartsierra.component :as component]
            [environ.core :refer [env]]
            [vr-match-api.infra.datasource.example :refer [example-datasource-component]]
            [vr-match-api.infra.repository.example :refer [example-repository-component]]
            [vr-match-api.domain.usecase.example :refer [example-usecase-component]]
            [vr-match-api.app.my-webapp.handler :refer [my-webapp-handler-component]]
            [vr-match-api.app.my-webapp.endpoint :refer [my-webapp-endpoint-component]])
  (:gen-class))

(defn vr-match-api-system
  [{:keys [vr-match-api-example-port
           vr-match-api-my-webapp-port] :as conf}]
  (component/system-map
    :example-datasource (example-datasource-component vr-match-api-example-port)
    :example-repository (component/using
                          (example-repository-component)
                          [:example-datasource])
    :example-usecase (component/using
                       (example-usecase-component)
                       [:example-repository])
    :my-webapp-handler (component/using
                         (my-webapp-handler-component)
                         [:example-usecase])
    :my-webapp-endpoint (component/using
                          (my-webapp-endpoint-component vr-match-api-my-webapp-port)
                          [:my-webapp-handler])))

(defn load-config []
  {:vr-match-api-example-port (-> (or (env :vr-match-api-example-port) "8000") Integer/parseInt)
   :vr-match-api-my-webapp-port (-> (or (env :vr-match-api-my-webapp-port) "8080") Integer/parseInt)})

(defn -main []
  (component/start
    (vr-match-api-system (load-config))))
