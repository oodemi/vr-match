(ns vr-match.client
  (:require
   [cljs.loader :as loader]
   [cljs.reader :as reader]
   [pushy.core :as pushy]
   [reagent.core :as reagent]
   [re-frame.core :as re-frame]
   [vr-match.events :as events]
   [vr-match.lib.component :as component]
   [vr-match.route :as route]
   [vr-match.config :as config]
   [vr-match.util :as util]
   [secretary.core :as secretary :refer-macros [defroute]]))

(defn dev-setup []
  (when config/debug?
    (enable-console-print!)
    (println "dev mode")))

(def history
  (pushy/pushy secretary/dispatch!
               (fn [x] (when (secretary/locate-route x) x))))

;; https://github.com/kibu-australia/pushy#routing-libraries から輸入
(defn hook-history []
  (pushy/start! history))

(defn ^:export mount-root []
  (re-frame/clear-subscription-cache!)
  (reagent/render [component/app]
                  (.getElementById js/document "app")))

(defn- preload-state []
  (some->
    js/window
    (aget "preload")
    reader/read-string))

(defn ^:export init []
  (let [preload (preload-state)]
    (util/universal-load (-> preload :router :key route/route-table :module-name)
      (fn []
        (re-frame/dispatch-sync [::events/initialize history preload])
        (dev-setup)
        (hook-history)
        (mount-root)))))

(set! (. js/window -onload) init)

(loader/set-loaded! :client)
