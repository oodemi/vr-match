(ns vr-match.register.container
  (:require [reagent.core :as r]
            [re-frame.core :as re-frame]
            [vr-match.register.component :as component]
            [vr-match.events :as events]
            [vr-match.util :as util]))

(def register-state (r/atom {}))

(defn- handle-click-twitter []
  (re-frame/dispatch [::events/push "/wizard"]))

(defn register
  [params]
  [component/register (merge @register-state
                             {:handleClickTwitter handle-click-twitter})])

(util/universal-set-loaded! :register)
