(ns vr-match.lib.components.list-item
  (:require
   [reagent.core :as r]
   [vr-match.lib.components.material-ui :as mui]
   [vr-match.lib.components.plat-form-chip :refer [plat-form-chip]]))

(defn list-item
  [{:keys [image
           platForms
           nickname
           introduction]}]
  [mui/list-item {:alignitems "flex-start"}
   [mui/list-item-avatar
    [mui/avatar {:src image
                 :style {:width "56px"
                         :height "56px"}}]]
   [mui/list-item-text {:disable-typography true
                        :primary
                        (r/as-element
                         [mui/typo-graphy {:component "span"
                                           :gutterBottom true}

                          nickname])
                        :secondary (r/as-element
                                    [mui/grid {:container true
                                               :justify "flex-start"
                                               :spacing 8}
                                     (->> platForms
                                          (take 3)
                                          (map (fn [{:keys [id name]}]
                                                 [mui/grid {:key id
                                                            :item true}
                                                  [plat-form-chip {:name name}]])))])}]])
