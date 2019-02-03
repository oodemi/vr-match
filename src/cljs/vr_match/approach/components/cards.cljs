(ns vr-match.approach.components.cards
  (:require [cljs.spec.alpha :as s]
            [reagent.core :as r]
            [vr-match.lib.components.material-ui :as mui]
            [vr-match.approach.components.card-item :refer [card-item]]))

(defn cards
  [{:keys [classes
           firstItem
           secondItem
           isSwipe
           isFavorite
           handleOnExit] :as props}]
  (if (or firstItem secondItem)
    [mui/grid {:item true
               :style {"height" "64vh"
                       "width" "86vw"
                       "position" "relative"}}
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
    [mui/grid {:item true
               :style {"height" "64vh"
                       "width" "86vw"
                       "position" "relative"}}
     ^{:key 0} [card-item {:item {:image ""}
                           :isSwipe false
                           :isFavorite false
                           :restCard? false
                           :handleOnExit #()}]
     ^{:key 1} [card-item {:item {:image ""}
                           :isSwipe false
                           :isFavorite false
                           :restCard? true
                           :handleOnExit #()}]]))
