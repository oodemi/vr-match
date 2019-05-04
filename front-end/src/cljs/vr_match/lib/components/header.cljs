(ns vr-match.lib.components.header
  (:refer-clojure :exclude [subs])
  (:require [cljs.spec.alpha :as s]
            [reagent.core :as r]
            [vr-match.lib.models.me :as me]
            [vr-match.lib.components.material-ui :as mui]
            [vr-match.lib.components.drawer :refer [drawer]]
            [vr-match.subs :as subs]
            [vr-match.events :as events]
            [re-frame.core :as re-frame]))

(defn styles
  [theme]
  (js/console.log theme)
  #js {"root" #js {"flexGrow" 1}
       "appBar" #js {"zIndex" (+ (.. theme -zIndex -drawer) 1)}
       "grow" #js {"flexGrow" 1}
       "iconButton" #js {"width" 24
                         "height" 24}
       "menuButton" #js {"marginLeft" -12
                         "marginRight" 20}})

(s/def ::title string?)
(s/def ::classses object?)
(s/def ::isOpen boolean?)
(s/def ::me ::me/me)
(s/def ::handleOpenDrawer
  (s/fspec :args (s/cat)
           :ret nil))
(s/def ::handleClickClose
  (s/fspec :args (s/cat)
           :ret nil))
(s/def ::handleClickMyProfile
  (s/fspec :args (s/cat)
           :ret nil))
(s/def ::handleClickSearch
  (s/fspec :args (s/cat)
           :ret nil))
(s/def ::handleClickFavorite
  (s/fspec :args (s/cat)
           :ret nil))
(s/def ::handleClickMatching
  (s/fspec :args (s/cat)
           :ret nil))
(s/def ::header-component-props
  (s/keys :req [::title
                ::classes
                ::isOpen
                ::me
                ::handleOpenDrawer
                ::handleClickClose
                ::handleClickMyProfile
                ::handleClickSearch
                ::handleClickFavorite
                ::handleClickMatching]))
(s/fdef header-component
  :args (s/cat :props ::header-component-props)
  :ret vector?)
(defn- header-component
  [{:keys [title
           classes
           isOpen
           me
           handleOpenDrawer
           handleClickClose
           handleClickMyProfile
           handleClickSearch
           handleClickFavorite
           handleClickMatching] :as props}]
  [:<>
   [:div {:class-name (.-root classes)}
    [mui/app-bar {:position "fixed"
                  :class-name (.-appBar classes)}
     [mui/tool-bar
      [mui/icon-button {:color "inherit"
                        :aria-label "Menu"
                        :class-name (.-menuButton classes)
                        :on-click (if isOpen handleClickClose handleOpenDrawer)}
       [mui/icon "menu"]]
      [mui/typo-graphy {:variant "title"
                        :color "inherit"
                        :class-name (.-grow classes)}
       title]]]]
   [drawer (-> props
               (dissoc :title)
               (dissoc :classes)
               (dissoc :handleOpenDrawer))]])

(defn- handle-open-drawer []
  (re-frame/dispatch [::events/open-drawer]))

(defn- handle-close-drawer []
  (re-frame/dispatch [::events/close-drawer]))

(defn- handle-go-to-my-profile []
  (re-frame/dispatch [::events/push "/myprofile"])
  (handle-close-drawer))

(defn- handle-click-my-profile []
  (re-frame/dispatch [::events/push "/myprofile"])
  (handle-close-drawer))

(defn- handle-click-search []
  (re-frame/dispatch [::events/push "/approach"])
  (handle-close-drawer))

(defn- handle-click-favorite []
  (re-frame/dispatch [::events/push "/favorite"])
  (handle-close-drawer))

(defn- handle-click-matching []
  (re-frame/dispatch [::events/push "/matching"])
  (handle-close-drawer))

(s/def ::header-container-props
  (s/keys :req [::title ::classes]))
(s/fdef header-container
  :args (s/cat :props ::header-container-props)
  :ret vector?)
(defn- header-container
  [{:keys [title classes]}]
  (let [open-drawer? (re-frame/subscribe [::subs/open-drawer?])
        me {:id 1
            :title "サンプル画像"
            :userName "一箱"
            :introduction "バーチャル清楚系女子高校生Webアプリケーションエンジニアおじさんです。こっそりプログラミングしてます。"
            :platForms [{:id 1 :name "VRChat"} {:id 2 :name "VRoidHub"} {:id 3 :name "VirtualCast"}]
            :image ["https://storage.googleapis.com/boxp-tmp/profile_sample.png"]}]
    [header-component {:classes classes
                       :title title
                       :isOpen @open-drawer?
                       :me me
                       :handleClickClose handle-close-drawer
                       :handleOpenDrawer handle-open-drawer
                       :handleClickMyProfile handle-click-my-profile
                       :handleClickSearch handle-click-search
                       :handleClickFavorite handle-click-favorite
                       :handleClickMatching handle-click-matching}]))

(s/def ::header-props
  (s/keys :req [::title]))
(s/fdef header
  :args (s/cat :props ::header-props)
  :ret vector?)
(def header
  (r/adapt-react-class ((mui/with-styles styles) (r/reactify-component header-container))))
