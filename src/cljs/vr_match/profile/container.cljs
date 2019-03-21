(ns vr-match.profile.container
  (:require [reagent.core :as r]
            [vr-match.profile.component :as component]
            [vr-match.util :as util]))

(def profile-state (r/atom {:id 1
                            :title "サンプル画像"
                            :userName "一箱"
                            :introduction "バーチャル清楚系女子高校生Webアプリケーションエンジニアおじさんです。こっそりプログラミングしてます。"
                            :platForms [{:id 1 :name "VRChat" :link "https://vrchat.net/home/user/usr_3b6403c3-be9f-432c-ab1f-446778946421"} {:id 2 :name "YouTube" :link "https://www.youtube.com/user/BOXPKETARO/about"} {:id 3 :name "VirtualCast" :link ""}]
                            :image "https://storage.googleapis.com/boxp-tmp/profile_sample.png"
                            :isMatched true}))

(defn profile
  [params]
  [component/profile @profile-state])

(util/universal-set-loaded! :profile)
