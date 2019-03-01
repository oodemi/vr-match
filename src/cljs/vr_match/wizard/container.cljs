(ns vr-match.wizard.container
  (:require
   [reagent.core :as reagent]
   [vr-match.util :as util]
   [vr-match.wizard.component :as component]))

;; TODO: re-frameとつなぎこんで消す
(def mock-wizard-state
  (reagent/atom {:me {:id 1
                      :userName "一箱"
                      :introduction "バーチャル清楚系女子高校生Webアプリケーションエンジニアおじさんです。こっそりプログラミングしてます。"
                      :platForms [{:id 1 :name "VRChat"} {:id 2 :name "VRoidHub"} {:id 3 :name "VirtualCast"}]
                      :image "https://storage.googleapis.com/boxp-tmp/profile_sample.jpg"}
                 :step :nickname}))

(defn- handle-next-nickname-step
  [nickname]
  (println nickname))

(defn- handle-click-skip []
  (println "Skip!"))

(defn wizard
  [params]
  [component/wizard (merge @mock-wizard-state
                           {:handleNextNicknameStep handle-next-nickname-step
                            :handleClickSkip handle-click-skip})])

(util/universal-set-loaded! :wizard)
