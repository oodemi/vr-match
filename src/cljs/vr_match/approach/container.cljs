(ns vr-match.approach.container
  (:require [reagent.core :as reagent]
            [vr-match.approach.component :as component]
            [vr-match.util :as util]
            [re-frame.core :as re-frame]))

;; TODO: re-frameとつなぎこんで消す
(declare approach-state)

(defn handle-click-swipe []
  (swap! approach-state #(assoc % :isSwiped true)))

(defn handle-click-favorite []
  (swap! approach-state #(assoc % :isFavorited true)))

(def approach-state
  (reagent/atom {:isSwiped false
                 :isFavorited false
                 :handleClickSkip handle-click-swipe
                 :handleClickFavorite handle-click-favorite
                 :cardItems [
                              {:id 1
                               :title "サンプル画像"
                               :userName "一箱"
                               :introduction "バーチャル清楚系女子高校生Webアプリケーションエンジニアおじさんです。こっそりプログラミングしてます。"
                               :image "https://storage.googleapis.com/boxp-tmp/profile_sample.jpg"}
                              {:id 2
                               :title "サンプル画像"
                               :userName "ヒマリ"
                               :introduction "バーチャル清楚系女子高校生Webアプリケーションエンジニアおじさんです。こっそりプログラミングしてます。"
                               :image "https://storage.googleapis.com/boxp-tmp/profile_sample.jpg"}
                              ]}))

(defn approach
  [params]
  [component/approach @approach-state])

(util/universal-set-loaded! :approach)
