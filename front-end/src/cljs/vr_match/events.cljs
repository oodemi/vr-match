(ns vr-match.events
  (:require
   [re-frame.core :as re-frame]
   [vr-match.effects :as effects]
   [vr-match.coeffects :as coeffects]
   [vr-match.db :as db]))

(re-frame/reg-event-fx
 ::initialize
 (fn [{:keys []} [_ history preload]]
   (as-> {:db db/default-db} $
     (if preload (update $ :db #(merge % preload)))
     (if history (assoc-in $ [:db :history] history) $))))

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
