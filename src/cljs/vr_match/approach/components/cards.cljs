(ns vr-match.approach.components.cards
  (:require [cljs.spec.alpha :as s]
            [reagent.core :as r]
            [vr-match.lib.components.material-ui :as mui]
            [vr-match.approach.components.card-item :refer [card-item]]))

(def cards-styles
  #js {"root" #js {"height" "74vh"
                   "width" "86vw"
                   "position" "relative"}})

(defn cards-component
  [{:keys [classes
           items
           isSwiped
           isFavorited] :as props}]
  [mui/grid {:item true}
   (map-indexed (fn [idx item]
                  ^{:key (.-id item)}
                  [card-item {:item item
                              :isSwiped isSwiped
                              :isFavorited isFavorited
                              :restCard? (not (= 0 idx))}]) items)])

(def cards
  (r/adapt-react-class ((mui/with-styles cards-styles) (r/reactify-component cards-component))))
