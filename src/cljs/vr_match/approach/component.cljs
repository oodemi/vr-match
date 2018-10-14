(ns vr-match.approach.component
  (:require [cljs.spec.alpha :as s]
            [reagent.core :as r]
            [vr-match.lib.components.material-ui :as mui]))

(def card-item-styles
  #js {"card" #js {"width" "100%"
                   "height" "100%"}
       "actionArea" #js {"height" "100%"
                         "position" "relative"
                         "display" "flex"
                         "flexDirection" "column"}
       "media" #js {"objectFit" "cover"
                    "width" "100%"
                    "height" 284
                    "flexGrow" 2}
       "content" #js {"flexGrow" 1}})

(defn card-item-component
  [{:keys [classes] :as props}]
  [mui/card {:class-name (.-card classes)}
   [mui/card-action-area {:class-name (.-actionArea classes)}
     [mui/card-media {:class-name (.-media classes)
                      :component "div"
                      :alt "サンプル画像"
                      :image "https://storage.googleapis.com/boxp-tmp/profile_sample.jpg"
                      :title "サンプル画像"}]
     [mui/card-content {:class-name (.-content classes)}
      [mui/typo-graphy {:gutterBottom true
                        :variant "subheading"
                        :component "h2"}
       "一箱"]
      [mui/typo-graphy {:component "p"}
       "バーチャル清楚系女子高校生Webアプリケーションエンジニアおじさんです。こっそりプログラミングしてます。"]]]])

(defn card-item
  [props]
  [(r/adapt-react-class ((mui/with-styles card-item-styles) (r/reactify-component card-item-component))) props])

(def cards-styles
  #js {"root" #js {"height" "76%"
                   "width" "86%"}})

(defn cards-component
  [{:keys [classes] :as props}]
  [mui/grid {:item true
             :class-name (.-root classes)}
   [card-item {}]])

(defn cards
  [props]
  [(r/adapt-react-class ((mui/with-styles cards-styles) (r/reactify-component cards-component))) props])

(def action-buttons-styles
  #js {"root" #js {}})

(defn action-buttons-component
  [{:keys [classes] :as props}]
  [mui/grid {:container true
             :justify "space-around"}
   [mui/button {:variant "fab"
                :aria-label "スキップ"}
    [mui/icon "archive"]]
   [mui/button {:variant "fab"
                :color "secondary"
                :aria-label "すき"}
    [mui/icon "favorite"]]])

(defn action-buttons
  [props]
  [(r/adapt-react-class ((mui/with-styles action-buttons-styles) (r/reactify-component action-buttons-component))) props])

(def approach-styles
  #js {"root" #js {"height" "100%"}})

(defn approach-component
  [{:keys [classes] :as props}]
  [mui/grid {:container true
             :align-items "center"
             :justify "space-around"
             :direction "column"
             :class-name (.-root classes)}
   [mui/grid {:container true
              :justify "center"}
    [cards {}]]
   [action-buttons {}]])

(defn approach
  [params]
  [(r/adapt-react-class ((mui/with-styles approach-styles) (r/reactify-component approach-component))) params])
