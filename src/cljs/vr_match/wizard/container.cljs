(ns vr-match.wizard.container
  (:require
   [reagent.core :as reagent]
   [re-frame.core :as re-frame]
   [vr-match.util :as util]
   [vr-match.events :as events]
   [vr-match.wizard.component :as component]))

;; TODO: re-frameとつなぎこんで消す
(def mock-wizard-state
  (reagent/atom {:me {:id 1
                      :userName "一箱"
                      :introduction "バーチャル清楚系女子高校生Webアプリケーションエンジニアおじさんです。こっそりプログラミングしてます。"
                      :platForms []
                      :image "https://storage.googleapis.com/boxp-tmp/profile_sample.jpg"}
                 :platformChoices [{:id 1 :name "VRChat"} {:id 2 :name "VRoidHub"} {:id 3 :name "VirtualCast"}]
                 :step :nickname}))

(defn- handle-next-nickname-step
  [nickname]
  (println nickname)
  (swap! mock-wizard-state
         (fn [state]
           (assoc state :step :platform))))

(defn- handle-next-platform-step
  [platforms]
  (println platforms)
  (re-frame/dispatch [::events/push "/approach"]))

(defn- handle-click-skip []
  (println "Skip!")
  (re-frame/dispatch [::events/push "/approach"]))

(defn wizard
  [params]
  [component/wizard (merge @mock-wizard-state
                           {:handleNextNicknameStep handle-next-nickname-step
                            :handleNextPlatformStep handle-next-platform-step
                            :handleClickSkip handle-click-skip})])

(util/universal-set-loaded! :wizard)
