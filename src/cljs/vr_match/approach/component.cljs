(ns vr-match.approach.component
  (:require [cljs.spec.alpha :as s]
            [reagent.core :as r]
            [vr-match.lib.components.material-ui :as mui]))

(def card-item-styles
  #js {"card" #js {"width" "86vw"
                   "height" "74vh"
                   "position" "relative"}
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
           item
           isSwiped
           isFavorited
           restCard?]
    :as props}]
  (let [{:keys [title
                userName
                introduction
                image]}
        (js->clj item :keywordize-keys true)]
    [mui/slide {:direction "right"
                :appear false
                :in (or (not (or isSwiped isFavorited))
                        restCard?)}
     [mui/card {:class-name (.-card classes)
                :style {"marginTop" (when restCard? "-74vh")
                        "zIndex" (if restCard? "1000" "1200")}
                :elevation (if restCard? 1 2)}
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
         introduction]]]]]))

(def card-item
  (r/adapt-react-class ((mui/with-styles card-item-styles) (r/reactify-component card-item-component))))

(def cards-styles
  #js {"root" #js {"height" "74vh"
                   "width" "86vw"
                   "position" "relative"}})

(defn cards-component
  [{:keys [classes
           items
           isSwiped
           isFavorited] :as props}]
  [mui/grid {:item true}
   (map-indexed (fn [idx item]
                  ^{:key (.-id item)}
                  [card-item {:item item
                              :isSwiped isSwiped
                              :isFavorited isFavorited
                              :restCard? (not (= 0 idx))}]) items)])

(def cards
  (r/adapt-react-class ((mui/with-styles cards-styles) (r/reactify-component cards-component))))

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

(def action-buttons
  (r/adapt-react-class ((mui/with-styles action-buttons-styles) (r/reactify-component action-buttons-component))))

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
