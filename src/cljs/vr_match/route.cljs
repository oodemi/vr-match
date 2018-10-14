(ns vr-match.route
  (:require
   [re-frame.core :as re-frame]
   [vr-match.util :as util]
   [vr-match.events :as events]
   [secretary.core :as secretary :refer-macros [defroute]]))

;; prefixなし
(secretary/set-config! :prefix "/")

(def route-table
  {:example {:title "サンプルページ"
             :container #(resolve 'vr-match.example.container/box)
             :module-name :example}
   :approach {:title "Approach"
              :container #(resolve 'vr-match.approach.container/approach)
              :module-name :approach}})

(defn- lazy-push
  [key params]
  (util/universal-load (-> route-table key :module-name) #(re-frame/dispatch-sync [::events/push key params])))

;; ルーティング定義
(defroute root-path "/" []
  (lazy-push :approach {}))

(defroute not-found-path "*" []
  (lazy-push :example {}))
