(ns vr-match.login.component
  (:require [cljs.spec.alpha :as s]
            [reagent.core :as r]
            [clojure.string :as string]
            [vr-match.lib.component :refer [navigation-bar-layout]]
            [vr-match.lib.components.material-ui :as mui]
            [vr-match.lib.components.plat-form-chip :refer [plat-form-chip]]))

(defn login
  [{:keys [handleClickTwitter
           handleClickRegister] :as props}]
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
     "Login with Twitter"]]
   [mui/divider {:style {:margin-top 24
                         :width "100%"}
                 :variant "middle"}]
   [mui/grid {:style {:margin-top 24}
              :container true
              :direction "row"
              :justify "center"
              :align-items "center"}
    [mui/typo-graphy {:component "p1"
                      :variant "caption"}
     "or"]
    [mui/button {:color "primary"
                 :on-click handleClickRegister}
     "register"]]])

