(ns vr-match.favorite.component
  (:require
   [reagent.core :as r]
   [vr-match.lib.component :refer [navigation-bar-layout]]
   [vr-match.lib.components.material-ui :as mui]
   [vr-match.lib.components.list-item :refer [list-item]]))

(defn- component-did-mount
  [this]
  (let [props (r/props this)]
    ((:handleDidMount props))))

(def favorite-component
  (with-meta
    (fn
      [{:keys [items
               handleDidMount]}]
      [navigation-bar-layout {:title "お気に入りに登録したアバター"}
       [:div {:style {:padding "8px"}}
        [mui/list
         (map (fn [{:keys [id userName platForms image introduction]}]
                ^{:key id}
                [:div {:style {:margin-bottom "16px"}}
                 [list-item {:image image
                             :platForms platForms
                             :nickname userName
                             :introduction introduction}]])
              items)]]])
    {:component-did-mount component-did-mount}))
