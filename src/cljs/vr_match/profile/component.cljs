(ns vr-match.profile.component
  (:require [cljs.spec.alpha :as s]
            [reagent.core :as r]
            [clojure.string :as string]
            [vr-match.lib.components.material-ui :as mui]))

(defn profile-component
  [{:keys [] :as props}]
  [mui/grid {:container true}
   "プロフィール"])

(def profile profile-component)
