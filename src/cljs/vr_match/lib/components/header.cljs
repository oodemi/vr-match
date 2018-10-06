(ns vr-match.lib.components.header
  (:require [cljs.spec.alpha :as s]
            [reagent.core :as r]
            [vr-match.lib.components.material-ui :as mui]))

(s/def ::title string?)
(s/def ::header-props
  (s/keys :req [::title]))
(s/fdef header
  :args (s/cat :props ::header-props)
  :ret vector?)
(defn header
  [props]
  [mui/app-bar {:position "static"}
   [mui/tool-bar {}
     [mui/icon-button {:color "inherit"
                       :aria-label "Menu"}]]])
