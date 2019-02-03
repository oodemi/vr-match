(ns vr-match.approach.components.card-item
  (:require [cljs.spec.alpha :as s]
            [reagent.core :as r]
            [vr-match.lib.components.material-ui :as mui]
            [vr-match.lib.components.plat-form-chip :refer [plat-form-chip]]))

(defn card-item
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
                platForms
                image]}
        (js->clj item :keywordize-keys true)]
    [mui/slide {:direction (cond isSwipe "right" isFavorite "left" :default "right")
                :appear false
                :timeout #js {"enter" 0 "exit" 300}
                :in (or (not (or isSwipe isFavorite))
                        restCard?)
                :onExited handleOnExit}
     [mui/card {:style {"marginTop" (when restCard? "-64vh")
                        "zIndex" (if restCard? "1000" "1200")
                        "width" "86vw"
                        "height" "64vh"
                        "position" "relative"}
                :elevation (if restCard? 1 2)}
      [mui/card-action-area {:style {"width" "86vw"
                                     "height" "100%"
                                     "position" "relative"
                                     "display" "flex"
                                     "flexDirection" "column"}}
       [mui/card-media {:style {"objectFit" "cover"
                                "width" "86vw"
                                "height" 284
                                "flexGrow" 2}
                        :component "div"
                        :alt title
                        :image image
                        :title title}]
       [mui/card-content {:style {"width" "100%"
                                  "boxSizing" "border-box"
                                  "flexGrow" 1}}
        [mui/grid {:container true
                   :justify "flex-start"
                   :style {"marginBottom" "0.35em"}
                   :spacing 8}
         (map (fn [{:keys [id name]}] [mui/grid {:key id
                                                 :item true}
                                       [plat-form-chip {:name name}]])
              platForms)]
        [mui/typo-graphy {:gutterBottom true
                          :variant "subheading"
                          :component "h2"}
         userName]
        [mui/typo-graphy {:component "p"}
         introduction]]]]]))
