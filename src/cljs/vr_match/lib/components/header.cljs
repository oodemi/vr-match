(ns vr-match.lib.components.header
  (:require [cljs.spec.alpha :as s]
            [reagent.core :as r]
            [vr-match.lib.components.material-ui :as mui]))

(def styles
  #js {"root" #js {"flexGrow" 1}
       "grow" #js {"flexGrow" 1}
       "iconButton" #js {"width" 24
                         "height" 24}
       "menuButton" #js {"marginLeft" -12
                         "marginRight" 20}})

(s/def ::title string?)
(s/def ::classses object?)
(s/def ::header-component-props
  (s/keys :req [::title ::classes]))
(s/fdef header-component
        :args (s/cat :props ::header-component-props)
        :ret vector?)
(defn header-component
  [{:keys [title classes] :as props}]
  [:div {:class-name (.-root classes)}
   [mui/app-bar {:position "static"}
    [mui/tool-bar
     [mui/icon-button {:color "inherit"
                       :aria-label "Menu"
                       :class-name (.-menuButton classes)}
      [mui/icon "menu"]]
     [mui/typo-graphy {:variant "title"
                       :color "inherit"
                       :class-name (.-grow classes)}
      title]]]])

(s/def ::header-props
  (s/keys :req [::title]))
(s/fdef header
        :args (s/cat :props ::header-props)
        :ret vector?)
(def header
  (r/adapt-react-class ((mui/with-styles styles) (r/reactify-component header-component))))
