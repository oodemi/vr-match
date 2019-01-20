(ns vr-match.approach.components.card-item
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
           isSwipe
           isFavorite
           restCard?
           handleOnExit]
    :as props}]
  (let [{:keys [title
                userName
                introduction
                image]}
        (js->clj item :keywordize-keys true)]
    [mui/slide {:direction (cond isSwipe "right" isFavorite "left" :default "right")
                :appear false
                :timeout #js {"enter" 0 "exit" 300}
                :in (or (not (or isSwipe isFavorite))
                        restCard?)
                :onExited handleOnExit}
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

