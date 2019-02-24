(ns vr-match.profile.component
  (:require [cljs.spec.alpha :as s]
            [reagent.core :as r]
            [clojure.string :as string]
            [vr-match.lib.components.material-ui :as mui]))

(defn profile-component
  [{:keys [image] :as props}]
  [mui/grid {:container true
             :direction "column"
             :style {:width "100vw"}}
   [:img {:style {:object-fit "cover"
                  :width "100%"}
          :src image}]
   [mui/grid {:container true
              :spacing 16}
    [mui/grid {:item true
               :xs 12}
     "プロフィール"]]])

(def profile profile-component)
