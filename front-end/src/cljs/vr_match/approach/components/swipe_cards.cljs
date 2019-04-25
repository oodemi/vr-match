(ns vr-match.approach.components.swipe-cards
  (:require [reagent.core :as r]
            [vr-match.lib.components.material-ui :as mui]
            [vr-match.approach.components.swipe-card-item :refer [swipe-card-item]]))

(def swipe-cards-styles
  #js {"root" #js {"height" "64vh"
                   "width" "86vw"
                   "position" "relative"}})

(defn swipe-cards-component
  [])

(def swipe-cards
  (r/adapt-react-class ((mui/with-styles swipe-cards-styles) (r/reactify-component swipe-cards-component))))
