(ns vr-match.lib.component
  (:require [reagent.core :as r]
            [re-frame.core :as re-frame]
            [secretary.core :as secretary]
            [vr-match.events]
            [vr-match.subs]
            [vr-match.route :as route]
            [vr-match.lib.components.header :refer [header]]))

;; (defn- header
;;   [{:keys [title]}]
;;   [:div {:style {:width "100%"
;;                  :height "56px"
;;                  :display "flex"
;;                  :justify-content "space-between"
;;                  :align-items "center"
;;                  :position "fixed"
;;                  :padding "0 16px 0 16px"
;;                  :background-color "#00adb5"
;;                  :box-sizing "border-box"
;;                  :box-shadow "2px 2px 2px 2px rgba(0, 0, 0, 0.3)"}}
;;    [:span {:style {:color "white"
;;                    :font-size "20px"
;;                    :font-weight "bold"}}
;;     title]])

(defn- nav-repository []
  [:a {:style {:text-decoration "none"
               :display "flex"
               :justify-content "center"
               :align-items "center"
               :height "100%"
               :width "100%"}
       :href "/repository"}
   [:span {:style {:color "#222831"
                   :font-size "28px"}}
    [:i.fas.fa-book]]])

(defn- loading []
  [:span "loading..."])

(defn app []
  (let [router (re-frame/subscribe [::vr-match.subs/router])]
    [:div
     [header {:title (-> @router :key route/route-table :title)}]
     [:div {:style {:padding "60px 0 64px 0"}}
      [(or (some-> @router :key route/route-table :container .call) loading)
       (-> @router :params)]]]))
