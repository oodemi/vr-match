(ns vr-match.approach.components.action-buttons
  (:require [cljs.spec.alpha :as s]
            [reagent.core :as r]
            [vr-match.lib.components.material-ui :as mui]))

(defn action-buttons
  [{:keys [onClickSkip
           onClickFavorite] :as props}]
  [mui/grid {:container true
             :justify "space-around"}
   [mui/button {:variant "fab"
                :aria-label "スキップ"
                :on-click onClickSkip}
    [mui/icon "reply"]]
   [mui/button {:variant "fab"
                :color "secondary"
                :aria-label "すき"
                :on-click onClickFavorite}
    [mui/icon "favorite"]]])
