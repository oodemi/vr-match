(ns vr-match.matching.component
  (:require
   [reagent.core :as r]
   [vr-match.lib.component :refer [navigation-bar-layout]]
   [vr-match.lib.components.material-ui :as mui]
   [vr-match.lib.components.user-list-item :refer [user-list-item]]))

(defn- component-did-mount
  [this]
  (let [props (r/props this)]
    ((:handleDidMount props))))

(def matching-component
  (with-meta
    (fn
      [{:keys [items
               handleClickItem
               handleDidMount]}]
      [navigation-bar-layout {:title "マッチングしたアバター"}
       [:div {:style {:padding "8px"}}
        [mui/list
         (map (fn [{:keys [id userName platForms image introduction]}]
                ^{:key id}
                [:div {:style {:margin-bottom "16px"}}
                 [user-list-item {:id id
                                  :image image
                                  :platForms platForms
                                  :nickname userName
                                  :introduction introduction
                                  :handleClick handleClickItem}]])
              items)]]])
    {:component-did-mount component-did-mount}))
