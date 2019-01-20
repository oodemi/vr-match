(ns vr-match.approach.component
  (:require [cljs.spec.alpha :as s]
            [reagent.core :as r]
            [vr-match.lib.components.material-ui :as mui]
            [vr-match.approach.components.cards :refer [cards]]
            [vr-match.approach.components.action-buttons :refer [action-buttons]]))

(def approach-styles
  #js {"root" #js {"height" "100%"}})

(defn approach-component
  [{:keys [classes
           isSwiped
           isFavorited
           cardItems
           handleClickSkip
           handleClickFavorite] :as props}]
  [mui/grid {:container true
             :align-items "center"
             :justify "space-around"
             :direction "column"
             :class-name (.-root classes)}
   [mui/grid {:container true
              :justify "center"}
    [cards {:items cardItems
            :isFavorited isFavorited
            :isSwiped isSwiped}]]
   [action-buttons {:onClickSkip handleClickSkip
                    :onClickFavorite handleClickFavorite}]])

(def approach
  (r/adapt-react-class ((mui/with-styles approach-styles) (r/reactify-component approach-component))))
