(ns vr-match.approach.components.matching-dialog
  (:require [vr-match.lib.components.material-ui :as mui]))

(defn matching-dialog
  [{:keys [isOpen
           me
           partner
           handleClickGoToProfile
           handleClickBack] :as props}]
  [mui/dialog {:full-screen true
               :open isOpen
               :on-close #(println "close")
               :aria-labelledby "matching-dialog"}
   [mui/dialog-content
    [mui/grid {:container true
               :direction "column"
               :justify "center"
               :align-items "center"}
     [mui/grid {:container true
                :direction "row"
                :justify "center"
                :align-items "center"}
      [mui/grid {:item true}
       [mui/avatar {:alt (:userName me)
                    :src (:image me)}]]
      [mui/grid {:item true}
       [mui/avatar {:alt (:userName partner)
                    :src (:image partner)}]]]
     [mui/grid {:container true
                :direction "column"
                :justify "center"
                :align-items "center"}
      [mui/grid {:item true}
       [mui/button {:color "primary"
                    :on-click #(handleClickGoToProfile (:id partner))}
        "相手のプロフィールを見る"]
       [mui/button {:on-click handleClickBack}
        "戻る"]]]]]])
