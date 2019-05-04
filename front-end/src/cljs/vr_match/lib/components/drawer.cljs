(ns vr-match.lib.components.drawer
  (:require
   [reagent.core :as r]
   [vr-match.lib.components.material-ui :as mui]))

(defn styles
  [theme]
  #js {"root" #js {"zIndex" (.. theme -zIndex -drawer)}})

(defn drawer-component
  [{:keys [classes
           isOpen
           handleClickClose
           handleClickMyProfile
           handleClickSearch
           handleClickFavorite
           handleClickMatching] :as props}]
  (let [me (-> props :me (js->clj :keywordize-keys true))]
    [mui/drawer {:open isOpen
                 :onClose handleClickClose
                 :class-name (.. classes -root)}
     [:div {:style {:padding-top "56px"}}
      [mui/list
       [mui/list-item {:key "my-profile"
                       :button true
                       :on-click handleClickMyProfile}
        [mui/list-item-avatar
         [mui/avatar {:alt (:userName me)
                      :src (-> me :image first)}]]
        [mui/list-item-text {:primary (:userName me)}]]
       [mui/divider]
       [mui/list-item {:key "search"
                       :button true
                       :on-click handleClickSearch}
        [mui/list-item-icon
         [mui/icon "search"]]
        [mui/list-item-text {:primary "さがす"}]]
       [mui/list-item {:key "favorite"
                       :button true
                       :on-click handleClickFavorite}
        [mui/list-item-icon
         [mui/icon "favorite"]]
        [mui/list-item-text {:primary "お気に入り"}]]
       [mui/list-item {:key "matching"
                       :button true
                       :on-click handleClickMatching}
        [mui/list-item-icon
         [mui/icon "people"]]
        [mui/list-item-text "マッチング"]]]]]))

(def drawer
  (r/adapt-react-class ((mui/with-styles styles) (r/reactify-component drawer-component))))
