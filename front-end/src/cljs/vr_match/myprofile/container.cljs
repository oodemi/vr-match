(ns vr-match.myprofile.container
  (:require
   [reagent.core :as r]
   [re-frame.core :as re-frame]
   [vr-match.events :as events]
   [vr-match.myprofile.component :as component]
   [vr-match.util :as util]))

(defn- handle-click-edit-my-profile []
  (re-frame/dispatch [::events/push "/mypage"]))

(def myprofile-state (r/atom {:me
                              {:id 1
                               :title "サンプル画像"
                               :userName "一箱"
                               :introduction "バーチャル清楚系女子高校生Webアプリケーションエンジニアおじさんです。こっそりプログラミングしてます。"
                               :platForms [{:id 1 :name "VRChat" :link "https://vrchat.net/home/user/usr_3b6403c3-be9f-432c-ab1f-446778946421"} {:id 2 :name "YouTube" :link "https://www.youtube.com/user/BOXPKETARO/about"} {:id 3 :name "VirtualCast" :link ""}]
                               :image ["https://storage.googleapis.com/boxp-tmp/profile_sample.png"]
                               :isMatched false}}))

(defn myprofile
  [params]
  [component/myprofile (merge @myprofile-state {:handleClickEditMyProfile handle-click-edit-my-profile})])

(util/universal-set-loaded! :myprofile)
