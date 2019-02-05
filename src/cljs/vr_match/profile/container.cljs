(ns vr-match.profile.container
  (:require [reagent.core :as r]
            [vr-match.profile.component :as component]
            [vr-match.util :as util]))

(def profile-state (r/atom {:id 1
                            :title "サンプル画像"
                            :userName "一箱"
                            :introduction "バーチャル清楚系女子高校生Webアプリケーションエンジニアおじさんです。こっそりプログラミングしてます。"
                            :platForms [{:id 1 :name "VRChat"} {:id 2 :name "VRoidHub"} {:id 3 :name "VirtualCast"}]
                            :image "https://storage.googleapis.com/boxp-tmp/profile_sample.jpg"}))

(defn profile
  [params]
  [component/profile @profile-state])

(util/universal-set-loaded! :profile)
