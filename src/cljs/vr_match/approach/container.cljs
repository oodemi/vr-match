(ns vr-match.approach.container
  (:require [reagent.core :as reagent]
            [vr-match.approach.component :as component]
            [vr-match.util :as util]
            [re-frame.core :as re-frame]))

(declare mock-approach-state)

(defn handle-click-swipe []
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
            #(assoc % :cardItems
                    [{:id 1
                      :title "サンプル画像"
                      :userName "一箱"
                      :introduction "バーチャル清楚系女子高校生Webアプリケーションエンジニアおじさんです。こっそりプログラミングしてます。"
                      :platForms [{:id 1 :name "VRChat"} {:id 2 :name "VRoidHub"} {:id 3 :name "VirtualCast"}]
                      :image "https://storage.googleapis.com/boxp-tmp/profile_sample.jpg"}
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
                      :image "https://storage.googleapis.com/boxp-tmp/profile_sample_3.jpg"}])))
   300))

;; TODO: re-frameとつなぎこんで消す
(def mock-approach-state
  (reagent/atom {:handleClickSkip handle-click-swipe
                 :handleClickFavorite handle-click-favorite
                 :handleDidMount handle-did-mount
                 :cardItems []}))

(defn approach
  [params]
  [component/approach @mock-approach-state])

(util/universal-set-loaded! :approach)
