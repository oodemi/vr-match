(ns vr-match.approach.component
  (:require [cljs.spec.alpha :as s]
            [reagent.core :as r]
            [vr-match.lib.components.material-ui :as mui]))

(def card-item-styles
  #js {"card" #js {"maxWidth" "80%"}
       "media" #js {"objectFit" "cover"}})

(defn card-item-component
  [{:keys [classes] :as props}]
  [mui/card {:class-name (.-card classes)}
   [mui/card-action-area
    [mui/card-media {:component "img"
                     :alt "サンプル画像"
                     :height "60%"

                     :image "https://storage.googleapis.com/boxp-tmp/profile_sample.jpg"
                     :title "サンプル画像"}]
    [mui/card-content
     [mui/typo-graphy {:gutterBottom true
                       :variant "subheading"
                       :component "h2"}
      "一箱"]
     [mui/typo-graphy {:component "p"}
      "バーチャル清楚系女子高校生Webアプリケーションエンジニアおじさんです。こっそりプログラミングしてます。"]]]])

(defn card-item
  [props]
  [(r/adapt-react-class ((mui/with-styles card-item-styles) (r/reactify-component card-item-component))) props])

(def cards-styles
  #js {})

(defn cards-component
  [{:keys [classes] :as props}]
  [card-item {}])

(defn cards
  [props]
  [(r/adapt-react-class ((mui/with-styles cards-styles) (r/reactify-component cards-component))) props])

(def approach-styles
  #js {})

(defn approach-component
  [{:keys [classes] :as props}]
  [cards {}])

(defn approach
  [params]
  [(r/adapt-react-class ((mui/with-styles approach-styles) (r/reactify-component approach-component))) params])
