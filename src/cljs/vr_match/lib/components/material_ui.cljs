(ns vr-match.lib.components.material-ui
  (:require [reagent.core :as r]
            ;; [reagent.impl.template :as rtpl]
            ;; ["material-ui/styles" :refer [withStyles]]
            ;; material-ui のバージョンアップによって変わったのか、 js/material_ui ではなく js/MaterialUI にモジュールが展開されるようになっていた
            ["material-ui"]
            ))

(def app-bar (r/adapt-react-class js/MaterialUI.AppBar))
(def tool-bar (r/adapt-react-class js/MaterialUI.Toolbar))
(def typo-graphy (r/adapt-react-class js/MaterialUI.Toolbar))
(def icon-button (r/adapt-react-class js/MaterialUI.IconButton))
