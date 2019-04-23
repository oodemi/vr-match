(ns user
  (:require [com.stuartsierra.component :as component]
            [clojure.tools.namespace.repl :refer (refresh)]
            [vr-match-api.system :refer [vr-match-api-system
                                     load-config]]))

(def system nil)

(defn init []
  (alter-var-root #'system
                  (constantly (vr-match-api-system (load-config)))))

(defn start []
  (alter-var-root #'system component/start))

(defn stop []
  (alter-var-root #'system
                  (fn [s] (when s (component/stop s)))))

(defn go []
  (init)
  (start))

(defn reset []
  (stop)
  (refresh :after 'user/go))
