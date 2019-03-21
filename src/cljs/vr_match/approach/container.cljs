(ns vr-match.approach.container
  (:require [reagent.core :as reagent]
            [vr-match.approach.component :as component]
            [vr-match.util :as util]
            [vr-match.events :as events]
            [re-frame.core :as re-frame]))

(declare mock-approach-state)

(defn handle-click-skip []
  (js/setTimeout
   (fn []
     (swap! mock-approach-state
            #(update % :cardItems rest)))
   300))

(defn handle-click-favorite []
  (js/setTimeout
   (fn []
     (swap! mock-approach-state
            #(update % :cardItems rest)))
   300))

(defn handle-did-mount
  []
  (js/setTimeout
   (fn []
     (swap! mock-approach-state
            #(-> %
                 (assoc :cardItems
                        (->>
                          [{:id 1
                            :title "サンプル画像"
                            :userName "一箱"
                            :introduction "バーチャル清楚系女子高校生Webアプリケーションエンジニアおじさんです。こっそりプログラミングしてます。"
                            :platForms [{:id 1 :name "VRChat"} {:id 2 :name "VRoidHub"} {:id 3 :name "VirtualCast"}]
                            :image "https://storage.googleapis.com/boxp-tmp/profile_sample.png"}
                           {:id 2
                            :title "サンプル画像"
                            :userName "ヒマリ"
                            :introduction "一箱さんちのヒマリです！"
                            :platForms [{:id 1 :name "VRChat"} {:id 3 :name "VirtualCast"}]
                            :image "https://storage.googleapis.com/boxp-tmp/profile_sample_2.jpg"}
                           {:id 3
                            :title "サンプル画像"
                            :userName "アリシア・ソリッド"
                            :introduction "ニコニ立体で公式キャラクターやってます。よろしくお願いします！"
                            :platForms [{:id 3 :name "VirtualCast"}]
                            :image "https://storage.googleapis.com/boxp-tmp/profile_sample_3.jpg"}]
                          cycle
                          (take 1000)))
                 (assoc :me {:id 1
                             :title "サンプル画像"
                             :userName "一箱"
                             :introduction "バーチャル清楚系女子高校生Webアプリケーションエンジニアおじさんです。こっそりプログラミングしてます。"
                             :platForms [{:id 1 :name "VRChat"} {:id 2 :name "VRoidHub"} {:id 3 :name "VirtualCast"}]
                             :image "https://storage.googleapis.com/boxp-tmp/profile_sample.jpg"}))))
   300))

(defn handle-click-go-to-profile
  [id]
  (re-frame/dispatch [::events/push (str "/profile/" id)]))

;; TODO: re-frameとつなぎこんで消す
(def mock-approach-state
  (reagent/atom {:cardItems []}))

(defn approach
  [params]
  [component/approach (merge @mock-approach-state {:handleClickSkip handle-click-skip
                                                   :handleClickFavorite handle-click-favorite
                                                   :handleClickGoToProfile handle-click-go-to-profile
                                                   :handleDidMount handle-did-mount})])

(util/universal-set-loaded! :approach)
