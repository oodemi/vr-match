(ns vr-match.approach.component
  (:require [cljs.spec.alpha :as s]
            [reagent.core :as r]
            [vr-match.lib.components.material-ui :as mui]
            [vr-match.lib.component :refer [navigation-bar-layout]]
            [vr-match.approach.components.cards :refer [cards]]
            [vr-match.approach.components.action-buttons :refer [action-buttons]]
            [vr-match.approach.components.matching-dialog :refer [matching-dialog]]))

(def approach-state
  (r/atom {:firstCard nil
           :secondCard nil
           :matchingPartner nil
           :isSwipe false
           :isFavorite false
           :isOpenMatchingDialog false}))

(def approach-styles
  #js {"root" #js {"height" "100%"}})

(defn- component-did-mount
  [this]
  (let [props (r/props this)]
    (swap! approach-state
           #(-> %
                (assoc :firstItem (-> props :cardItems first))
                (assoc :secondItem (-> props :cardItems second))
                (assoc :isOpenMatchingDialog false)))
    (when (= (-> props :cardItems count) 0)
      ((:handleDidMount props)))))

(defn- component-did-update
  [this [_ old-props]]
  (let [new-props (r/props this)]
    (when (and (not= (some-> new-props :cardItems first .-id)
                     (some-> old-props :cardItems first .-id))
               (not (-> @approach-state :isSwipe))
               (not (-> @approach-state :isFavorite)))
      (swap! approach-state
             #(-> %
                  (assoc :firstItem (-> new-props :cardItems first))
                  (assoc :secondItem (-> new-props :cardItems second)))))))

(defn- onClickSkip
  [props]
  (swap! approach-state
         #(-> % (assoc :isSwipe true)))
  ((:handleClickSkip props)))

(defn- onClickFavorite
  [props]
  (swap! approach-state
         #(-> % (assoc :isFavorite true)
                (assoc :isOpenMatchingDialog true)
                (assoc :matchingPartner (-> @approach-state :firstItem))))
  ((:handleClickFavorite props)))

(defn- handleOnExit
  [props]
  (swap! approach-state
         #(-> %
              (assoc :isSwipe false)
              (assoc :isFavorite false)
              (assoc :firstItem (-> props :cardItems first))
              (assoc :secondItem (-> props :cardItems second)))))

(defn- handleClickGoToProfile
  [props id]
  ((:handleClickGoToProfile props) id))

(defn- handleCloseMatchingDialog []
  (swap! approach-state
         #(-> % (assoc :isOpenMatchingDialog false))))

(def approach-component
  (with-meta
    (fn
      [{:keys [me
               classes
               cardItems
               handleClickSkip
               handleClickFavorite] :as props}]
      [navigation-bar-layout {:title "アバターをさがす"}
       [mui/grid {:container true
                  :align-items "center"
                  :justify "space-around"
                  :direction "column"
                  :class-name (.-root classes)}
        [mui/grid {:container true
                   :justify "center"}
         [cards {:firstItem (-> @approach-state :firstItem)
                 :secondItem (-> @approach-state :secondItem)
                 :isFavorite (-> @approach-state :isFavorite)
                 :isSwipe (-> @approach-state :isSwipe)
                 :handleClickCardItem #(handleClickGoToProfile props %)
                 :handleOnExit #(handleOnExit props)}]]
        [action-buttons {:onClickSkip #(onClickSkip props)
                         :onClickFavorite #(onClickFavorite props)}]
        [matching-dialog {:isOpen (:isOpenMatchingDialog @approach-state)
                          :me (js->clj me :keywordize-keys true)
                          :partner (-> @approach-state :matchingPartner (js->clj :keywordize-keys true))
                          :handleClickGoToProfile #(handleClickGoToProfile props %)
                          :handleClickBack handleCloseMatchingDialog}]]])
    {:component-did-mount component-did-mount
     :component-did-update component-did-update}))

(def approach
  (r/adapt-react-class
   ((mui/with-styles approach-styles)
    (r/reactify-component approach-component))))
