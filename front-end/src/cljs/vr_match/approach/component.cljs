(ns vr-match.approach.component
  (:require [cljs.spec.alpha :as s]
            [reagent.core :as r]
            [vr-match.lib.components.material-ui :as mui]
            [vr-match.lib.component :refer [navigation-bar-layout]]
            [vr-match.approach.components.cards :refer [cards]]
            [vr-match.approach.components.swipe-card-item :refer [swipe-card-item]]
            [vr-match.approach.components.action-buttons :refer [action-buttons]]
            [vr-match.approach.components.matching-dialog :refer [matching-dialog]]))

(def approach-state
  (r/atom {:firstCard nil
           :secondCard nil
           :matchingPartner nil
           :isSwipe false
           :isFavorite false
           :isReturning false
           :isOpenMatchingDialog false
           :swipeStartPosition {:x 0
                                :y 0}
           :swipeCurrentPosition {:x 0
                                  :y 0}}))

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

(defn- onSwipeCardTouchStart
  [event]
  (let [position-x (-> event .-targetTouches (aget 0) .-pageX)
        position-y (-> event .-targetTouches (aget 0) .-pageY)]
    (-> approach-state
        (swap! #(-> %
                    (assoc :isReturning false)
                    (assoc-in [:swipeStartPosition :x] position-x)
                    (assoc-in [:swipeStartPosition :y] position-y)
                    (assoc-in [:swipeCurrentPosition :x] position-x)
                    (assoc-in [:swipeCurrentPosition :y] position-y))))))

(defn- onSwipeCardTouchMoved
  [event]
  (let [position-x (-> event .-targetTouches (aget 0) .-pageX)
        position-y (-> event .-targetTouches (aget 0) .-pageY)]
    (-> approach-state
        (swap!
         #(-> %
              (assoc-in [:swipeCurrentPosition :x] position-x)
              (assoc-in [:swipeCurrentPosition :y] position-y))))))

(defn- onSwipeCardTouchEnd []
  (-> approach-state
      (swap!
       #(-> %
            (assoc :isReturning true)))))

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

(defn- state->current-swipe-card-transform
  [state]
  (let [add-x (- (-> state :swipeCurrentPosition :x)
                 (-> state :swipeStartPosition :x))
        add-y (- (-> state :swipeCurrentPosition :y)
                 (-> state :swipeStartPosition :y))
        rotate (/ add-x 26)]
    (str "translate("
         add-x "px,"
         add-y "px) "
         "rotate("
         rotate "deg)")))

(defn- state->return-swipe-card-animation
  [state]
  (str
  "@keyframes returnSwipeCard {
     from {
       transform: " (state->current-swipe-card-transform state) "
     }
     to {
       transform: translate(0, 0) rotate(0);
     }
   }
   .swipe-animation {
     animation-name: returnSwipeCard;
     animation-duration: 150ms;
     animation-timing-function: ease-out;
     animation-iteration-count: 1;
     animation-fill-mode: both;
   }"))

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
         ;; [cards {:firstItem (-> @approach-state :firstItem)
         ;;         :secondItem (-> @approach-state :secondItem)
         ;;         :isFavorite (-> @approach-state :isFavorite)
         ;;         :isSwipe (-> @approach-state :isSwipe)
         ;;         :handleClickCardItem #(handleClickGoToProfile props %)
         ;;         :handleOnExit #(handleOnExit props)}]
         [:style (state->return-swipe-card-animation @approach-state)]
         [:div {:style {:will-change "transform"
                        :transform (when-not (:isReturning @approach-state)
                                     (state->current-swipe-card-transform @approach-state))
                        :animation-play-state (:isReturning @approach-state)}
                :class (when (:isReturning @approach-state)
                         "swipe-animation")
                :on-touch-start onSwipeCardTouchStart
                :on-touch-move onSwipeCardTouchMoved
                :on-touch-end onSwipeCardTouchEnd}
          [swipe-card-item {:item (-> @approach-state :firstItem)
                            :handleClickCard #()}]]]
        [:div {:style {:will-change "transform"
                       :width "100%"}}
         [action-buttons {:onClickSkip #(onClickSkip props)
                          :onClickFavorite #(onClickFavorite props)}]]
        
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
