(ns vr-match.approach.events
  (:require
   [re-frame.core :as re-frame]
   [re-graph.core :as re-graph]
   [venia.core :as v]))

(re-frame/reg-event-db
 ::on-success-fetch-approach-list
 (fn [db [_ {:keys [data errors] :as payload}]]
   (assoc-in db [:approach :list] (-> payload
                                      js/JSON.parse
                                      (js->clj :keywordize-keys true)
                                      :data
                                      :approachList))))

(re-frame/reg-event-fx
 ::fetch-approach-list
 (fn [{:keys [db] :as cofx} [_ {:keys [limit]}]]
   {:dispatch [::re-graph/query
               (v/graphql-query
                {:venia/queries [[:approachList {:limit limit
                                                 :offset 0}
                                  [:id
                                   :userName
                                   :introduction
                                   :image
                                   [:platForms [:id :name]]]]]})
               {}
               [::on-success-fetch-approach-list]]}))
