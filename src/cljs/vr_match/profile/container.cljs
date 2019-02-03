(ns vr-match.profile.container
  (:require [reagent.core :as r]
            [vr-match.profile.component :as component]
            [vr-match.util :as util]))

(def profile-state (r/atom {}))

(defn profile
  [params]
  [component/profile @profile-state])

(util/universal-set-loaded! :profile)
