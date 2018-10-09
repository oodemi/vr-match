(ns vr-match.client
  (:require
   [cljs.loader :as loader]
   [cljs.reader :as reader]
   [pushy.core :as pushy]
   [reagent.core :as reagent]
   [re-frame.core :as re-frame]
   [vr-match.events :as events]
   [vr-match.lib.component :as component]
   [vr-match.lib.components.material-ui :as mui]
   [vr-match.route :as route]
   [vr-match.config :as config]
   [vr-match.util :as util]
   [secretary.core :as secretary :refer-macros [defroute]]
   ["material-ui"]
   ["material-ui/styles"]
   ["material-ui/colors"]))

;; TODO: 現状では、 react-jss をクライアントサイドの ClojureScript で使える手がないので無理やり Context API を使って Material-UI の SSR を実現している
;; from https://github.com/cssinjs/react-jss/blob/master/src/ns.js
(def sheet-options "6fc570d6bd61383819d0f9e7407c452d")

;; (def JSSContext
;;   (.createContext js/React (clj->js {sheet-options {"generateClassName" ""}})))

;; (def JSSContextProvider
;;   (reagent/adapt-react-class (.-Provider JSSContext)))

(defn JSSContextProvider
  [generate-class-name children]
  (reagent/create-class
    {:get-child-context (clj->js {sheet-options {"generateClassName" generate-class-name}})
     :reagent-render (fn [_ children] children)}))

(defn dev-setup []
  (when config/debug?
    (enable-console-print!)
    (println "dev mode")))

(def history
  (pushy/pushy secretary/dispatch!
               (fn [x] (when (secretary/locate-route x) x))))

;; https://github.com/kibu-australia/pushy#routing-libraries から輸入
(defn hook-history []
  (pushy/start! history))

(defn index
  [generate-class-name theme]
  (reagent/create-class
    {:component-did-mount
     (fn []
       ;; Remove the server-side injected CSS.
       (let [jss-styles (.. js/document (getElementById "jss-server-side"))]
         (when (and jss-styles (.-parentNode jss-styles))
           (.. jss-styles -parentNode (removeChild jss-styles)))))
     :reagent-render
     (fn [generate-class-name theme]
       [JSSContextProvider generate-class-name
        [mui/MuiThemeProvider {:theme theme}
         [component/app]]])}))

(defn ^:export mount-root []
  (let [theme (mui/theme)
        generate-class-name (mui/create-generate-class-name)]
   (re-frame/clear-subscription-cache!)
   (reagent/render [index generate-class-name theme]
                   (.getElementById js/document "app"))))

(defn- preload-state []
  (some->
    js/window
    (aget "preload")
    reader/read-string))

(defn ^:export init []
  (let [preload (preload-state)]
    (util/universal-load (-> preload :router :key route/route-table :module-name)
      (fn []
        (re-frame/dispatch-sync [::events/initialize history preload])
        (dev-setup)
        (hook-history)
        (mount-root)))))

(set! (. js/window -onload) init)

(loader/set-loaded! :client)
