(ns vr-match.profile.components.platform-link
  (:require [vr-match.lib.components.material-ui :as mui]))

(defn platform-link
  [{:keys [name link] :as platform}]
  [mui/button {:variant "contained"
               :color "primary"
               :size "small"
               :href link}
   [mui/icon {:style {:margin-right 8}} "link"]
   name])
