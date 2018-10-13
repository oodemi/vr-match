(ns vr-match.lib.components.material-ui
  (:require [reagent.core :as r]
            ;; [reagent.impl.template :as rtpl]
            ;; ["material-ui/styles" :refer [withStyles]]
            ;; material-ui のバージョンアップによって変わったのか、 js/material_ui ではなく js/MaterialUI にモジュールが展開されるようになっていた
            ["material-ui"]
            ["material-ui/styles"]
            ["material-ui/colors"]
            ["material-ui-icons"]))

;; material-ui
(def app-bar (r/adapt-react-class js/MaterialUI.AppBar))
(def tool-bar (r/adapt-react-class js/MaterialUI.Toolbar))
(def typo-graphy (r/adapt-react-class js/MaterialUI.Typography))
(def icon-button (r/adapt-react-class js/MaterialUI.IconButton))
(def no-ssr (r/adapt-react-class js/MaterialUI.NoSsr))

;; material-ui-icons
(def account-circle (r/adapt-react-class js/MaterialUIIcons.AccountCircle))
(def menu-icon (r/adapt-react-class js/MaterialUIIcons.Menu))

;; material-ui/styles
(def with-styles (.-withStyles js/MaterialUIStyles))
(def create-mui-theme (.-createMuiTheme js/MaterialUIStyles))
(def create-generate-class-name (.-createGenerateClassName js/MaterialUIStyles))
(def MuiThemeProvider (-> (.-MuiThemeProvider js/MaterialUIStyles) r/adapt-react-class))

;; material-ui/colors
(def mui-color-teal (.-teal js/MaterialUIColors))
(def mui-color-pink (.-pink js/MaterialUIColors))

(defn theme []
  (create-mui-theme
   #js {"palette"
        #js {"primary" mui-color-teal
             "accent" mui-color-pink
             "type" "light"}}))
