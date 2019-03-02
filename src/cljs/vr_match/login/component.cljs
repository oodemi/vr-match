(ns vr-match.login.component
  (:require [cljs.spec.alpha :as s]
            [reagent.core :as r]
            [clojure.string :as string]
            [vr-match.lib.component :refer [navigation-bar-layout]]
            [vr-match.lib.components.material-ui :as mui]
            [vr-match.lib.components.plat-form-chip :refer [plat-form-chip]]))

(defn login
  [{:keys [backgroundImage
           handleClickTwitter
           handleClickRegister] :as props}]
  [mui/grid {:style {:height "100%"
                     :width "100%"}
             :container true
             :direction "column"
             :justify "center"
             :alignItems "center"}
   [:span {:style {:display "block"
                   :height "100%"
                   :width "100%"
                   :background (str "url(" backgroundImage ") no-repeat center center fixed")
                   :background-size "cover"
                   :filter "blur(4px) opacity(64%)"
                   :position "absolute"
                   :top 0
                   :left 0
                   :right 0
                   :bottom 0
                   :z-index -1}}]
   [mui/typo-graphy {:component "h1"
                     :gutterBottom true
                     :variant "display3"}
    "VR Match"]
   [mui/grid {:style {:margin-top 208}
              :container true
              :direction "column"
              :justify "center"
              :alignItems "center"}
    [mui/button {:variant "contained"
                 :color "primary"
                 :on-click handleClickTwitter}
     "Twitterアカウントでログイン"]]
   [mui/grid {:style {:margin-top 24}
              :container true
              :direction "row"
              :justify "center"
              :align-items "center"}
    [mui/typo-graphy {:component "p1"
                      :variant "body2"}
     "はじめての方は"]
    [mui/button {:color "primary"
                 :size "small"
                 :on-click handleClickRegister}
     "こちらから新規登録"]]])

