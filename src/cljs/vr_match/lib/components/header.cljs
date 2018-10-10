(ns vr-match.lib.components.header
  (:require [cljs.spec.alpha :as s]
            [reagent.core :as r]
            [vr-match.lib.components.material-ui :as mui]))

(def styles
  #js {"root" #js {"flexGrow" 1}
       "grow" #js {"flexGrow" 1}
       "menuButton" #js {"marginLeft" -12
                         "marginRight" 20}})

(s/def ::title string?)
(s/def ::header-props
  (s/keys :req [::title]))
(s/fdef header-component
  :args (s/cat :props ::header-props)
  :ret vector?)
(defn header-component
  [props]
  [mui/app-bar {:position "static"}
   [mui/tool-bar {}
     [mui/icon-button {:color "inherit"
                       :aria-label "Menu"}]]])

(defn header []
  [(r/adapt-react-class ((mui/with-styles styles) (r/reactify-component header-component)))])
