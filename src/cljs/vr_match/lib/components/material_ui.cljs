(ns vr-match.lib.components.material-ui
  (:require [reagent.core :as r]
            ;; [reagent.impl.template :as rtpl]
            ;; ["material-ui/styles" :refer [withStyles]]
            ;; material-ui のバージョンアップによって変わったのか、 js/material_ui ではなく js/MaterialUI にモジュールが展開されるようになっていた
            ["material-ui"]
            ["material-ui/styles"]
            ["material-ui/colors"]))

;; material-ui
(def app-bar (r/adapt-react-class js/MaterialUI.AppBar))
(def tool-bar (r/adapt-react-class js/MaterialUI.Toolbar))
(def typo-graphy (r/adapt-react-class js/MaterialUI.Typography))
(def icon-button (r/adapt-react-class js/MaterialUI.IconButton))
(def icon (r/adapt-react-class js/MaterialUI.Icon))
(def no-ssr (r/adapt-react-class js/MaterialUI.NoSsr))
(def card (r/adapt-react-class js/MaterialUI.Card))
(def card-action-area (r/adapt-react-class js/MaterialUI.CardActionArea))
(def card-media (r/adapt-react-class js/MaterialUI.CardMedia))
(def card-content (r/adapt-react-class js/MaterialUI.CardContent))

;; material-ui/styles
(def with-styles (.-withStyles js/MaterialUIStyles))
(def create-mui-theme (.-createMuiTheme js/MaterialUIStyles))
(def create-generate-class-name (.-createGenerateClassName js/MaterialUIStyles))
(def MuiThemeProvider (-> (.-MuiThemeProvider js/MaterialUIStyles) r/adapt-react-class))

;; material-ui/colors
(def mui-color-red (.-red js/MaterialUIColors))

(defn theme []
  (create-mui-theme
   #js {"palette"
        #js {"primary" #js {"main" "#ef5350"}
             "secondary" mui-color-red}}))
