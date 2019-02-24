(ns vr-match.register.container
  (:require [reagent.core :as r]
            [vr-match.register.component :as component]
            [vr-match.util :as util]))

(def register-state (r/atom {}))

(defn register
  [params]
  [component/register (merge @register-state {})])

(util/universal-set-loaded! :register)
