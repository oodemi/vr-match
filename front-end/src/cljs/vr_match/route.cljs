(ns vr-match.route
  (:require
   [re-frame.core :as re-frame]
   [vr-match.util :as util]
   [vr-match.events :as events]
   [secretary.core :as secretary :refer-macros [defroute]]))

;; prefixなし
(secretary/set-config! :prefix "/")

(def route-table
  {:example {:container #(resolve 'vr-match.example.container/box)
             :module-name :example}
   :approach {:container #(resolve 'vr-match.approach.container/approach)
              :module-name :approach}
   :profile {:container #(resolve 'vr-match.profile.container/profile)
             :module-name :profile}
   :login {:container #(resolve 'vr-match.login.container/login)
           :module-name :login}
   :register {:container #(resolve 'vr-match.register.container/register)
              :module-name :register}
   :wizard {:container #(resolve 'vr-match.wizard.container/wizard)
            :module-name :wizard}
   :favorite {:container #(resolve 'vr-match.favorite.container/favorite)
              :module-name :favorite}})

(defn- lazy-push
  [key params]
  (util/universal-load (-> route-table key :module-name) #(re-frame/dispatch-sync [::events/universal-push key params])))

;; ルーティング定義
(defroute root-path "/" []
  (lazy-push :login {}))

(defroute profile-path "/profile/:id" [id]
  (lazy-push :profile {:id id}))

(defroute register-path "/register" []
  (lazy-push :register {}))

(defroute approach-path "/approach" []
  (lazy-push :approach {}))

(defroute wizard-path "/wizard" []
  (lazy-push :wizard {}))

(defroute favorite-path "/favorite" []
  (lazy-push :favorite {}))

(defroute not-found-path "*" []
  (lazy-push :approach {}))
