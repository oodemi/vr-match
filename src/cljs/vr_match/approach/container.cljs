(ns vr-match.approach.container
  (:require [reagent.core :as reagent]
            [vr-match.approach.component :as component]
            [vr-match.util :as util]
            [re-frame.core :as re-frame]))

;; TODO: re-frameとつなぎこんで消す
(declare approach-state)

(defn handle-click-skip []
  (swap! approach-state #(update % :cardItems rest)))

(def approach-state
  (reagent/atom {:swiped? false
                 :favorited? false
                 :handleClickSkip handle-click-skip
                 :handleClickFavorite handle-click-skip
                 :cardItems [
                              {:title "サンプル画像"
                               :userName "一箱"
                               :introduction "バーチャル清楚系女子高校生Webアプリケーションエンジニアおじさんです。こっそりプログラミングしてます。"
                               :image "https://storage.googleapis.com/boxp-tmp/profile_sample.jpg"}
                              {:title "サンプル画像"
                               :userName "ヒマリ"
                               :introduction "バーチャル清楚系女子高校生Webアプリケーションエンジニアおじさんです。こっそりプログラミングしてます。"
                               :image "https://storage.googleapis.com/boxp-tmp/profile_sample.jpg"}
                              ]}))

(defn approach
  [params]
  [component/approach @approach-state])

(util/universal-set-loaded! :approach)
