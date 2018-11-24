(ns vr-match.approach.component
  (:require [cljs.spec.alpha :as s]
            [reagent.core :as r]
            [vr-match.lib.components.material-ui :as mui]))

(def card-item-styles
  #js {"card" #js {"width" "86vw"
                   "height" "74vh"}
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
  [{:keys [classes
           item]
    :as props}]
  (let [{:keys [title
                userName
                introduction
                image]}
        (js->clj item :keywordize-keys true)]
     [mui/card {:class-name (.-card classes)}
      [mui/card-action-area {:class-name (.-actionArea classes)}
       [mui/card-media {:class-name (.-media classes)
                        :component "div"
                        :alt title
                        :image image
                        :title title}]
       [mui/card-content {:class-name (.-content classes)}
        [mui/typo-graphy {:gutterBottom true
                          :variant "subheading"
                          :component "h2"}
         userName]
        [mui/typo-graphy {:component "p"}
         introduction]]]]))

(defn card-item
  [props]
  [(r/adapt-react-class ((mui/with-styles card-item-styles) (r/reactify-component card-item-component))) props])

(def cards-styles
  #js {"root" #js {"height" "74vh"
                   "width" "86vw"
                   "position" "relative"}})

(defn cards-component
  [{:keys [classes
           items] :as props}]
  [mui/grid {:item true}
   (map (fn [item]
          ^{:key (:id item)} [card-item {:item item}]) items)])

(defn cards
  [props]
  [(r/adapt-react-class ((mui/with-styles cards-styles) (r/reactify-component cards-component))) props])

(def action-buttons-styles
  #js {"root" #js {}})

(defn action-buttons-component
  [{:keys [classes
           onClickSkip
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

(defn action-buttons
  [props]
  [(r/adapt-react-class ((mui/with-styles action-buttons-styles) (r/reactify-component action-buttons-component))) props])

(def approach-styles
  #js {"root" #js {"height" "100%"}})

(defn approach-component
  [{:keys [classes
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
    [cards {:items cardItems}]]
   [action-buttons {:onClickSkip handleClickSkip
                    :onClickFavorite handleClickFavorite}]])

(defn approach
  [props]
  [(r/adapt-react-class ((mui/with-styles approach-styles) (r/reactify-component approach-component))) props])
