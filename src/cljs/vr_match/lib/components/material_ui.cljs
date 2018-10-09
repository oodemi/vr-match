(ns vr-match.lib.components.material-ui
  (:require [reagent.core :as r]
            ;; [reagent.impl.template :as rtpl]
            ;; ["material-ui/styles" :refer [withStyles]]
            ;; material-ui のバージョンアップによって変わったのか、 js/material_ui ではなく js/MaterialUI にモジュールが展開されるようになっていた
            ["material-ui"]
            ["material-ui/styles"]
            ["material-ui/colors"]))

(def app-bar (r/adapt-react-class js/MaterialUI.AppBar))
(def tool-bar (r/adapt-react-class js/MaterialUI.Toolbar))
(def typo-graphy (r/adapt-react-class js/MaterialUI.Toolbar))
(def icon-button (r/adapt-react-class js/MaterialUI.IconButton))

(def create-mui-theme (.-createMuiTheme js/MaterialUIStyles))
(def create-generate-class-name (.-createGenerateClassName js/MaterialUIStyles))

(def mui-color-green (.-Green js/MaterialUIColors))
(def mui-color-red (.-Red js/MaterialUIColors))

(def MuiThemeProvider (-> (.-MuiThemeProvider js/MaterialUIStyles) r/adapt-react-class))

(defn theme []
  (create-mui-theme
    #js {"palette"
         #js {"primary" mui-color-green
              "accent" mui-color-red
              "type" "light"}}))
