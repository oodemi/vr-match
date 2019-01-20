(ns vr-match.approach.components.cards
  (:require [cljs.spec.alpha :as s]
            [reagent.core :as r]
            [vr-match.lib.components.material-ui :as mui]
            [vr-match.approach.components.card-item :refer [card-item]]))

(def cards-styles
  #js {"loading" #js {"height" "74vh"
                      "width" "86vw"
                      "position" "relative"}})

(defn cards-component
  [{:keys [classes
           firstItem
           secondItem
           isSwipe
           isFavorite
           handleOnExit] :as props}]
  (if (or firstItem secondItem)
    [mui/grid {:item true}
     (if firstItem
       ^{:key 0}
       [card-item {:item firstItem
                   :isSwipe isSwipe
                   :isFavorite isFavorite
                   :restCard? false
                   :handleOnExit handleOnExit}])
     (if secondItem
       ^{:key 1}
       [card-item {:item secondItem
                   :isSwipe isSwipe
                   :isFavorite isFavorite
                   :restCard? true
                   :handleOnExit handleOnExit}])]
    [:div {:class-name (.-loading classes)}]))

(def cards
  (r/adapt-react-class ((mui/with-styles cards-styles) (r/reactify-component cards-component))))
