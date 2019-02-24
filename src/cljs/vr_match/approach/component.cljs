(ns vr-match.approach.component
  (:require [cljs.spec.alpha :as s]
            [reagent.core :as r]
            [vr-match.lib.components.material-ui :as mui]
            [vr-match.approach.components.cards :refer [cards]]
            [vr-match.approach.components.action-buttons :refer [action-buttons]]))

(def approach-state
  (r/atom {:firstCard nil
           :secondCard nil
           :isSwipe false
           :isFavorite false}))

(def approach-styles
  #js {"root" #js {"height" "100%"}})

(defn- component-did-mount
  [this]
  (let [props (r/props this)]
    (swap! approach-state
           #(-> %
                (assoc :firstItem (-> props :cardItems first))
                (assoc :secondItem (-> props :cardItems second))))
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
         #(-> % (assoc :isFavorite true)))
  ((:handleClickFavorite props)))

(defn- handleOnExit
  [props]
  (swap! approach-state
         #(-> %
              (assoc :isSwipe false)
              (assoc :isFavorite false)
              (assoc :firstItem (-> props :cardItems first))
              (assoc :secondItem (-> props :cardItems second)))))

(def approach-component
  (with-meta
    (fn
      [{:keys [classes
               cardItems
               handleClickCardItem
               handleClickSkip
               handleClickFavorite] :as props}]
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
                :handleClickCardItem handleClickCardItem
                :handleOnExit #(handleOnExit props)}]]
       [action-buttons {:onClickSkip #(onClickSkip props)
                        :onClickFavorite #(onClickFavorite props)}]])
    {:component-did-mount component-did-mount
     :component-did-update component-did-update}))

(def approach
  (r/adapt-react-class
   ((mui/with-styles approach-styles)
    (r/reactify-component approach-component))))
