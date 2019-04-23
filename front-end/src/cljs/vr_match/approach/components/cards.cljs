(ns vr-match.approach.components.cards
  (:require [cljs.spec.alpha :as s]
            [reagent.core :as r]
            [vr-match.lib.components.material-ui :as mui]
            [vr-match.approach.components.card-item :refer [card-item]]))

(def cards-styles
  #js {"root" #js {"height" "64vh"
                   "width" "86vw"
                   "position" "relative"}})

(defn cards-component
  [{:keys [classes
           firstItem
           secondItem
           isSwipe
           isFavorite
           handleClickCardItem
           handleOnExit] :as props}]
  (if (or firstItem secondItem)
    [mui/grid {:item true
               :class-name (.-root classes)}
     (if firstItem
       ^{:key 0}
       [card-item {:item firstItem
                   :isSwipe isSwipe
                   :isFavorite isFavorite
                   :restCard? false
                   :handleClickCard handleClickCardItem
                   :handleOnExit handleOnExit}])
     (if secondItem
       ^{:key 1}
       [card-item {:item secondItem
                   :isSwipe isSwipe
                   :isFavorite isFavorite
                   :restCard? true
                   :handleClickCard handleClickCardItem
                   :handleOnExit handleOnExit}])]
    [mui/grid {:item true
               :class-name (.-root classes)}
     ^{:key 0} [card-item {:item {:image ""}
                           :isSwipe false
                           :isFavorite false
                           :restCard? false
                           :handleClickCard #()
                           :handleOnExit #()}]
     ^{:key 1} [card-item {:item {:image ""}
                           :isSwipe false
                           :isFavorite false
                           :restCard? true
                           :handleClickCard #()
                           :handleOnExit #()}]]))

(def cards
  (r/adapt-react-class ((mui/with-styles cards-styles) (r/reactify-component cards-component))))
