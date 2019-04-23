(ns vr-match.wizard.component
  (:require [vr-match.lib.components.material-ui :as mui]
            [vr-match.wizard.components.wizard-nickname-step :refer [wizard-nickname-step]]
            [vr-match.wizard.components.wizard-platform-step :refer [wizard-platform-step]]
            [vr-match.wizard.components.wizard-image-step :refer [wizard-image-step]]))

(defn wizard
  [{:keys [me
           step
           platformChoices
           handleNextNicknameStep
           handleNextPlatformStep
           handleNextImageStep
           handleClickSkip] :as props}]
  (case step
    :nickname [wizard-nickname-step {:me me
                                     :handleClickNext handleNextNicknameStep
                                     :handleClickSkip handleClickSkip}]
    :platform [wizard-platform-step {:me me
                                     :platformChoices platformChoices
                                     :handleClickNext handleNextPlatformStep
                                     :handleClickSkip handleClickSkip}]
    :image [wizard-image-step {:me me
                               :handleClickNext handleNextImageStep
                               :handleClickSkip handleClickSkip}]
    nil))
