(ns vr-match.wizard.components.wizard-nickname-step
  (:require
   [reagent.core :as r]
   [vr-match.lib.components.material-ui :as mui]
   [vr-match.wizard.components.wizard-step :refer [wizard-step]]))

(defn- nickname-form
  [{:keys [isDuplicatedNickname
           nickname
           handleChangeInput] :as props}]
  [mui/form-control {:fullWidth true}
   [mui/text-field {:id "nickname"
                    :label "ニックネーム"
                    :defaultValue nickname
                    :onChange handleChangeInput
                    :margin "normal"}]])

(defn wizard-nickname-step
  [{:keys [me
           handleClickNext
           handleClickSkip]}]
  (let [draft-nickname (r/atom (:userName me))
        handle-change-input (fn [e]
                              (println (.. e -target -value))
                              (reset! draft-nickname (.. e -target -value)))
        handle-click-next (fn [] (handleClickNext @draft-nickname))]
    (fn []
      [wizard-step {:title [:<>
                            "ニックネームを"
                            [:br]
                            "教えてください"]
                    :form [nickname-form {:isDuplicatedNickName false
                                          :nickname @draft-nickname
                                          :handleChangeInput handle-change-input}]
                    :me me
                    :handleClickNext handle-click-next
                    :handleClickSkip handleClickSkip}])))
