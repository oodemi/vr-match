(ns vr-match.register.component
  (:require [cljs.spec.alpha :as s]
            [reagent.core :as r]
            [clojure.string :as string]
            [vr-match.lib.components.material-ui :as mui]
            [vr-match.lib.components.plat-form-chip :refer [plat-form-chip]]))

(defn register
  [{:keys [handleClickTwitter] :as props}]
  [mui/grid {:style {:height "100%"
                     :width "100%"}
             :container true
             :direction "column"
             :justify "center"
             :alignItems "center"}
   [mui/typo-graphy {:component "h1"
                     :gutterBottom true
                     :variant "title"}
    "Welcome to VR Match"]
   [mui/grid {:style {:margin-top 48}
              :container true
              :direction "column"
              :justify "center"
              :alignItems "center"}
    [mui/button {:variant "contained"
                 :on-click handleClickTwitter}
     "Register with Twitter"]]])
