(ns vr-match.wizard.components.wizard-title
  (:require [vr-match.lib.components.material-ui :as mui]))

(defn wizard-title
  [{:keys [title]}]
  [mui/typo-graphy {:component "h2"
                    :variant "display1"
                    :gutterBottom true
                    :style {:text-align "left"}}
   title])
