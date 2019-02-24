(ns vr-match.login.container
(:require [reagent.core :as r]
          [re-frame.core :as re-frame]
          [vr-match.login.component :as component]
          [vr-match.util :as util]
          [vr-match.events :as events]))

;; TODO: re-frameつなぎ込み
(def login-state (r/atom {}))

(defn handle-click-twitter []
  (re-frame/dispatch [::events/push "/"]))

(defn login
  [params]
  [component/login (merge @login-state
                          {:handleClickTwitter handle-click-twitter})])

(util/universal-set-loaded! :login)

