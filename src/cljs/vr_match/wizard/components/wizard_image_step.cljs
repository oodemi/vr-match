(ns vr-match.wizard.components.wizard-image-step
  (:require
   [reagent.core :as r]
   [vr-match.lib.components.material-ui :as mui]
   [vr-match.wizard.components.wizard-step :refer [wizard-step]]))

(defn- image-form
  [{:keys [image
           handleChange]}]
  [mui/grid {:container true}
   [:a {:role "button"
        :style {:position "relative"}}
    [mui/avatar {:src image}]]])

(defn wizard-image-step
  [{:keys [me
           handleClickNext
           handleClickSkip]}]
  (let [draft-image (r/atom (:image me))
        handle-change-input (fn [e])
        handle-click-next (fn [] (handleClickNext @draft-image))]
    (fn []
      [wizard-step {:title [:<>
                            "プロフィール画像を"
                            [:br]
                            "設定しましょう"]
                    :form [image-form {:image @draft-image
                                       :handleChangeInput handle-change-input}]
                    :me me
                    :handleClickNext handle-click-next
                    :handleClickSkip handleClickSkip}])))
