(ns vr-match.wizard.component
  (:require [vr-match.lib.components.material-ui :as mui]
            [vr-match.wizard.components.wizard-nickname-step :refer [wizard-nickname-step]]))

(defn wizard
  [{:keys [me
           step
           handleNextNicknameStep
           handleClickSkip] :as props}]
  (case step
    :nickname [wizard-nickname-step {:me me
                                     :handleClickNext handleNextNicknameStep
                                     :handleClickSkip handleClickSkip}]
    nil))
