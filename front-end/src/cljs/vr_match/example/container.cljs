(ns vr-match.example.container
  (:require [reagent.core :as reagent]
            [vr-match.example.component :as component]
            [vr-match.util :as util]
            [re-frame.core :as re-frame]))

(defn box
  [params]
  [component/box params])

(util/universal-set-loaded! :example)
