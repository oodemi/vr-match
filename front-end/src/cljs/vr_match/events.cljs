(ns vr-match.events
  (:require
   [re-frame.core :as re-frame]
   [re-graph.core :as re-graph]
   [vr-match.effects :as effects]
   [vr-match.coeffects :as coeffects]
   [vr-match.db :as db]))

(re-frame/reg-event-fx
 ::initialize
 (fn [{:keys []} [_ {:keys [history
                            preload
                            api-endpoint]}]]
   (as-> {:db db/default-db} $
     (if preload (update $ :db #(merge % preload)))
     (if history (assoc-in $ [:db :history] history) $)
     (if api-endpoint (assoc-in $ [:db :api-endpoint] api-endpoint) $)
     (assoc $ :dispatch
            [::re-graph/init
             {:ws-url nil
              :http-url (str (-> $ :db :api-endpoint) "/graphql")}]))))

(re-frame/reg-event-db
 ::universal-push
 (fn [db [_ key params]]
   (-> db
       (assoc-in [:router :key] key)
       (assoc-in [:router :params] params))))

(re-frame/reg-event-fx
 ::push
 (fn [_ [_ path]]
   {::effects/route [path]}))

(re-frame/reg-event-fx
 ::api-error
 (fn [{:keys [db]} [_ error]]
   {:db (assoc db :api-error error)}))
