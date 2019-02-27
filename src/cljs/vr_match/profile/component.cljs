(ns vr-match.profile.component
  (:require [cljs.spec.alpha :as s]
            [reagent.core :as r]
            [clojure.string :as string]
            [vr-match.lib.components.material-ui :as mui]
            [vr-match.lib.components.plat-form-chip :refer [plat-form-chip]]
            [vr-match.profile.components.platform-link :refer [platform-link]]))

(defn profile-component
  [{:keys [image
           platForms
           userName
           introduction
           isMatched] :as props}]
  [mui/grid {:container true
             :direction "column"
             :style {:width "100vw"}}
   [:img {:style {:object-fit "cover"
                  :width "100vw"
                  :height "100vw"}
          :src image}]
   [:div {:style {:padding 16}}
    [mui/grid {:container true
               :spacing 16}
     [mui/grid {:item true
                :style {"marginBottom" "0.35em"}
                :xs 12}
      (if isMatched
        [mui/grid {:container true
                   :justify "flex-start"
                   :spacing 8
                   :style {"marginBottom" 8}}
         (map (fn [{:keys [id] :as platform}] [mui/grid {:key id
                                                         :item true}
                                               [platform-link platform]])
              platForms)]
        [mui/grid {:container true
                   :justify "flex-start"
                   :spacing 8}
         (map (fn [{:keys [id name]}] [mui/grid {:key id
                                                 :item true}
                                       [plat-form-chip {:name name}]])
              platForms)])
      [mui/typo-graphy {:gutterBottom true
                        :variant "subheading"
                        :component "h2"}
       userName]
      [mui/typo-graphy {:component "p"}
       introduction]]]]])

(def profile profile-component)
