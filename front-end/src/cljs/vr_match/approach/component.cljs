(ns vr-match.approach.component
  (:require [cljs.spec.alpha :as s]
            [reagent.core :as r]
            [vr-match.lib.components.material-ui :as mui]
            [vr-match.lib.component :refer [navigation-bar-layout]]
            [vr-match.approach.components.cards :refer [cards]]
            [vr-match.approach.components.swipe-card-item :refer [swipe-card-item]]
            [vr-match.approach.components.action-buttons :refer [action-buttons]]
            [vr-match.approach.components.favorite-overlay :refer [favorite-overlay]]
            [vr-match.approach.components.skip-overlay :refer [skip-overlay]]
            [vr-match.approach.components.matching-dialog :refer [matching-dialog]]))

(def swipe-return-limit 150)

(def approach-state
  (r/atom {:firstCard nil
           :secondCard nil
           :matchingPartner nil
           :isDragging false
           :isSkip false
           :isFavorite false
           :isReturning false
           :isOpenMatchingDialog false
           :swipeStartPosition {:x 0
                                :y 0}
           :swipeCurrentPosition {:x 0
                                  :y 0}}))

(def card-ref (r/atom nil))

(defn- onClickSkip
  [props]
  (swap! approach-state
         #(-> % (assoc :isSkip true))))

(defn- onClickFavorite
  [props]
  (swap! approach-state
         #(-> %
              (assoc :isFavorite true)
              (assoc :isOpenMatchingDialog true)
              (assoc :matchingPartner (-> @approach-state :firstItem)))))

(defn- onSwipeCardTouchStart
  [event]
  (let [position-x (-> event .-targetTouches (aget 0) .-pageX)
        position-y (-> event .-targetTouches (aget 0) .-pageY)]
    (-> approach-state
        (swap! #(-> %
                    (assoc :isDragging true)
                    (assoc :isReturning false)
                    (assoc :isSkip false)
                    (assoc :isFavorite false)
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
              (assoc-in [:swipeCurrentPosition :y] position-y))))
    (.. event preventDefault)))

(defn- onSwipeCardTouchEnd
  [event]
  (let [add-x (- (-> @approach-state :swipeCurrentPosition :x)
                 (-> @approach-state :swipeStartPosition :x))]
    (cond (< add-x (- swipe-return-limit))
          (-> approach-state
              (swap! #(-> % (assoc :isSkip true)
                          (assoc :isDragging false))))
          (> add-x swipe-return-limit)
          (-> approach-state
              (swap! #(-> % (assoc :isFavorite true)
                          (assoc :isDragging false))))
          :else
          (-> approach-state
              (swap! #(-> % (assoc :isReturning true)
                          (assoc :isDragging false)))))))

(defn- handleOnExit
  [props]
  (letfn [(shift-card-items [state]
            (-> state
                (assoc :firstItem (-> props :cardItems second))
                (assoc :secondItem (-> props :cardItems (nth 3)))))]
    (cond (:isSkip @approach-state)
          (do (swap! approach-state shift-card-items)
              (:handleClickSkip props))
          (:isFavorite @approach-state)
          (do (swap! approach-state shift-card-items)
              (:handleClickFavorite props)))
    (swap! approach-state
           #(-> %
                (assoc :isSkip false)
                (assoc :isFavorite false)
                (assoc :isReturning false)
                (assoc :swipeStartPosition {:x 0 :y 0})
                (assoc :swipeCurrentPosition {:x 0 :y 0})))))

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
   .return-animation {
     animation-name: returnSwipeCard;
     animation-duration: 150ms;
     animation-timing-function: ease-out;
     animation-iteration-count: 1;
     animation-fill-mode: both;
   }"))

(defn- state->favorite-swipe-card-animation
  [state]
  (let [add-x (- (-> state :swipeCurrentPosition :x)
                 (-> state :swipeStartPosition :x))
        add-y (- (-> state :swipeCurrentPosition :y)
                 (-> state :swipeStartPosition :y))
        rotate (+ (/ add-x 26) 30)]
    (str
     "@keyframes favoriteSwipeCard {
     from {
       transform: " (state->current-swipe-card-transform state) "
     }
     to {
       transform: translate(150%, " add-y "px) rotate(" rotate "deg);
     }
   }
   .favorite-animation {
     animation-name: favoriteSwipeCard;
     animation-duration: 300ms;
     animation-timing-function: linear;
     animation-iteration-count: 1;
     animation-fill-mode: both;
   }")))

(defn- state->skip-swipe-card-animation
  [state]
  (let [add-x (- (-> state :swipeCurrentPosition :x)
                 (-> state :swipeStartPosition :x))
        add-y (- (-> state :swipeCurrentPosition :y)
                 (-> state :swipeStartPosition :y))
        rotate (- (/ add-x 26) 30)]
    (str
     "@keyframes skipSwipeCard {
     from {
       transform: " (state->current-swipe-card-transform state) "
     }
     to {
       transform: translate(-150%, " add-y "px) rotate(" rotate "deg);
     }
   }
   .skip-animation {
     animation-name: skipSwipeCard;
     animation-duration: 300ms;
     animation-timing-function: linear;
     animation-iteration-count: 1;
     animation-fill-mode: both;
   }")))

(defn- state->favorite-overlay-opacity
  [state]
  (let [add-x (- (-> state :swipeCurrentPosition :x)
                 (-> state :swipeStartPosition :x))]
    (/ add-x swipe-return-limit)))

(defn- state->skip-overlay-opacity
  [state]
  (let [add-x (- (-> state :swipeCurrentPosition :x)
                 (-> state :swipeStartPosition :x))]
    (/ (- add-x) swipe-return-limit)))

(defn- component-did-mount
  [this]
  (let [props (r/props this)]
    (swap! approach-state
           #(-> %
                (assoc :firstItem (-> props :cardItems first))
                (assoc :secondItem (-> props :cardItems second))
                (assoc :isOpenMatchingDialog false)))
    (some-> @card-ref
            (.addEventListener "touchstart" onSwipeCardTouchStart))
    (some-> @card-ref
            (.addEventListener "touchmove"
                               onSwipeCardTouchMoved
                               #js {"passive" false}))
    (some-> @card-ref
            (.addEventListener "touchend" onSwipeCardTouchEnd))
    (some-> @card-ref
            (.addEventListener "animationend" #(-> this r/props handleOnExit)))
    (when (= (-> props :cardItems count) 0)
      ((:handleDidMount props)))))

(defn- component-did-update
  [this [_ old-props]]
  (let [new-props (r/props this)]
    (when (and (not= (some-> new-props :cardItems first .-id)
                     (some-> old-props :cardItems first .-id))
               (not (-> @approach-state :isSkip))
               (not (-> @approach-state :isFavorite))
               (not (-> @approach-state :isReturning))
               (not (-> @approach-state :isDragging)))
      (swap! approach-state
             #(-> %
                  (assoc :firstItem (-> new-props :cardItems first))
                  (assoc :secondItem (-> new-props :cardItems second)))))))

(defn- component-will-unmount
  [this]
  (some-> @card-ref
          (.removeEventListener "touchstart" onSwipeCardTouchStart))
  (some-> @card-ref
          (.removeEventListener "touchmove" onSwipeCardTouchMoved))
  (some-> @card-ref
          (.removeEventListener "touchend" onSwipeCardTouchEnd)))

(def approach
  (r/create-class
   {:display-name "approach-component"
    :reagent-render
    (fn [{:keys [me
                 classes
                 cardItems
                 handleClickSkip
                 handleClickFavorite] :as props}]
      [navigation-bar-layout {:title "アバターをさがす"}
       [:div {:style {:height "100%"
                      :display "flex"
                      :flex-direction "column"
                      :align-items "center"
                      :justify-content "space-around"}}
        [:div {:style {:display "flex"
                       :justify-content "center"
                       :flex-direction "column"}}
         ;; [cards {:firstItem (-> @approach-state :firstItem)
         ;;         :secondItem (-> @approach-state :secondItem)
         ;;         :isFavorite (-> @approach-state :isFavorite)
         ;;         :isSwipe (-> @approach-state :isSkip)
         ;;         :handleClickCardItem #(handleClickGoToProfile props %)
         ;;         :handleOnExit #(handleOnExit props)}]
         [:style (cond (:isReturning @approach-state)
                       (state->return-swipe-card-animation @approach-state)
                       (:isFavorite @approach-state)
                       (state->favorite-swipe-card-animation @approach-state)
                       (:isSkip @approach-state)
                       (state->skip-swipe-card-animation @approach-state)
                       :else nil)]
         [:div {:style {:margin-bottom "-64vh"
                        :z-index "1000"
                        :position "relative"
                        :will-change "transform"}}
          [swipe-card-item {:item (-> @approach-state :secondItem)
                            :handleClickCard #()}]]
         [:div {:style {:will-change "transform"
                        :transform (when (:isDragging @approach-state)
                                     (state->current-swipe-card-transform @approach-state))
                        :animation-play-state (when
                                               (or (:isReturning @approach-state)
                                                   (:isFavorite @approach-state)
                                                   (:isSkip @approach-state))
                                                "running")
                        :position "relative"
                        :z-index "1200"}
                :class (cond (:isReturning @approach-state)
                             "return-animation"
                             (:isFavorite @approach-state)
                             "favorite-animation"
                             (:isSkip @approach-state)
                             "skip-animation"
                             :else nil)
                :ref (fn [ref] (reset! card-ref ref))}
          [swipe-card-item {:item (-> @approach-state :firstItem)
                            :handleClickCard #()}]
          [:div {:style {:will-change "opacity"
                         :position "absolute"
                         :height "100%"
                         :width "100%"
                         :top 0
                         :opacity (state->favorite-overlay-opacity @approach-state)}}
           [favorite-overlay]]
          [:div {:style {:will-change "opacity"
                         :position "absolute"
                         :height "100%"
                         :width "100%"
                         :top 0
                         :opacity (state->skip-overlay-opacity @approach-state)}}
           [skip-overlay]]]]
        [:div {:style {:will-change "transform"
                       :width "100%"}}
         [action-buttons {:onClickSkip #(onClickSkip props)
                          :onClickFavorite #(onClickFavorite props)}]]
        [matching-dialog {:isOpen (:isOpenMatchingDialog @approach-state)
                          :me (js->clj me :keywordize-keys true)
                          :partner (-> @approach-state :matchingPartner (js->clj :keywordize-keys true))
                          :handleClickGoToProfile #(handleClickGoToProfile props %)
                          :handleClickBack handleCloseMatchingDialog}]]])
    :component-did-mount component-did-mount
    :component-did-update component-did-update
    :component-will-unmount component-will-unmount}))
