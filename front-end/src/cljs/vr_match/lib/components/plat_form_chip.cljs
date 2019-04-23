(ns vr-match.lib.components.plat-form-chip
  (:require [cljs.spec.alpha :as s]
            [vr-match.lib.components.material-ui :as mui]))


(s/def ::name string?)
(s/def ::props (s/keys :req [::name]))
(s/fdef ::plat-form-chip
  :args (s/cat :props ::props))
(defn plat-form-chip
  [{:keys [name] :as props}]
  [mui/chip {:label name
             :color "primary"}])
