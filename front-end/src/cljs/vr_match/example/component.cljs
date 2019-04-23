(ns vr-match.example.component)

(defn box
  []
  [:div {:style {:width "100%"
                 :height "30vh"
                 :margin-top "20vh"
                 :display "flex"
                 :flex-direction "column"
                 :justify-content "space-around"
                 :align-items "center"}}
   [:div
    [:span {:style {:color "#393e46"
                    :font-weight "bold"
                    :font-size "32px"}}
     "Example"]]])
