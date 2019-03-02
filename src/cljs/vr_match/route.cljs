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
              :module-name :approach}
   :profile {:title "Profile"
             :container #(resolve 'vr-match.profile.container/profile)
             :module-name :profile}
   :login {:title "Login"
           :container #(resolve 'vr-match.login.container/login)
           :module-name :login}
   :register {:title "Register"
              :container #(resolve 'vr-match.register.container/register)
              :module-name :register}
   :wizard {:title "Wizard"
            :container #(resolve 'vr-match.wizard.container/wizard)
            :module-name :wizard}})

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

(defroute not-found-path "*" []
  (lazy-push :example {}))
