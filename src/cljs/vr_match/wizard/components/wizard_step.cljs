(ns vr-match.wizard.components.wizard-step
  (:require [vr-match.lib.components.material-ui :as mui]))

(defn wizard-step
  [{:keys [title
           form
           me
           handleClickNext
           handleClickSkip] :as props}]
  [:div {:style {:padding 16}}
       [mui/grid {:container true
                  :spacing 32
                  :direction "column"
                  :justify "space-between"
                  :align-items "center"
                  :style {:height "100%"
                          :width "100vw"
                          :padding 32}}
        [mui/typo-graphy {:component "h2"
                          :variant "title"
                          :gutterBottom true
                          :style {:text-align "center"}}
         title]
        form
        [mui/grid {:container true
                   :direction "column"}
         [mui/button {:variant "contained"
                      :color "primary"
                      :on-click handleClickNext}
          "次へ"]
         [mui/button {:style {:margin-top 16}
                      :on-click handleClickSkip}
          "スキップ"]]]])
