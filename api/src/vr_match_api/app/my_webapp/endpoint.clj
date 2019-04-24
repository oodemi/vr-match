(ns vr-match-api.app.my-webapp.endpoint
  (:require [com.stuartsierra.component :as component]
            [compojure.core :refer [defroutes context GET POST OPTIONS routes]]
            [compojure.route :as route]
            [ring.adapter.jetty :as server]
            [vr-match-api.app.my-webapp.handler :as handler]))

(defn wrap-header-csp
  [handler]
  (fn [request]
    (assoc-in (handler request)
              [:headers "Content-Security-Policy"]
              "default-src http://localhost:8888")))

(defn wrap-header-cors
  [handler]
  (fn [request]
    (-> (handler request)
        (assoc-in [:headers "Access-Control-Allow-Origin"] "http://localhost:8888")
        (assoc-in [:headers "Access-Control-Allow-Credentials"] "true"))))

(defn main-routes
  [{:keys [my-webapp-handler] :as comp}]
  (routes
   (GET "/" [] (handler/index my-webapp-handler))
   (POST "/graphql" req (handler/graphql my-webapp-handler req))
   (OPTIONS "*" []
     {:status 200
      :headers {"Access-Control-Allow-Methods" "POST, GET, OPTIONS"
                "Access-Control-Allow-Credentials" "true"
                "Access-Control-Allow-Headers" "Content-Type"}})
   (route/not-found {:status 404
                     :headers {}
                     :body "<h1>404 page not found</h1>"})))

(defn app
  [comp]
  (-> (main-routes comp)
      wrap-header-csp
      wrap-header-cors))

(defrecord MyWebappEndpointComponent [port server my-webapp-handler]
  component/Lifecycle
  (start [this]
    (println ";; Starting MyWebappEndpointComponent")
    (-> this
        (assoc :server (server/run-jetty (app this) {:port port :join? false}))))
  (stop [this]
    (println ";; Stopping MyWebappEndpointComponent")
    (.stop (:server this))
    (-> this
        (dissoc :server))))

(defn my-webapp-endpoint-component
  [port]
  (map->MyWebappEndpointComponent {:port port}))
