(ns vr-match.server
  (:require
    [cljs.reader :as reader]
    [cljs.loader :as loader]
    [re-frame.core :as re-frame]
    [re-frame.db :as db]
    [reagent.core :refer [adapt-react-class]]
    [reagent.dom.server :as r]
    [secretary.core :as secretary]
    [vr-match.example.container]
    [vr-match.approach.container]
    [vr-match.profile.container]
    [vr-match.login.container]
    [vr-match.register.container]
    [vr-match.wizard.container]
    [vr-match.favorite.container]
    [vr-match.lib.component :as component]
    [vr-match.lib.components.material-ui :as mui]
    [vr-match.config :as config]
    [vr-match.events :as events]
    [vr-match.route]))

(def express (js/require "express"))
(def compression (js/require "compression"))
(def ^:export app (express))

(def api-endpoint (or js/process.env.API_ENDPOINT "http://localhost:8080"))

(def JssProvider (-> (js/require "react-jss/lib/JssProvider") .-default adapt-react-class))
(def jss (js/require "react-jss/lib/jss"))
(def sheets-registry (.-SheetsRegistry jss))

(goog-define static-file-path "/")
(goog-define dev? false)

(defn dev-setup []
  (when dev?
    (enable-console-print!)
    (println "dev mode")))

(defn app-component
  [registry generate-class-name theme sheets-manager]
  [JssProvider {:registry registry
                :generateClassName generate-class-name}
   [mui/MuiThemeProvider {:theme theme
                          :sheetsManager sheets-manager}
    [component/app]]])

(defn index
  [app-html css]
  [:html {:lang "en"}
   [:head
    [:meta {:charset "utf-8"}]
    [:meta {:name "viewport"
            :content "width=device-width,initial-scale=1,user-scalable=no"}]
    (when-not dev?
      [:link {:rel "manifest" :href "/manifest.json"}])
    [:link {:rel "stylesheet"
            :href "https://fonts.googleapis.com/icon?family=Material+Icons"}]
    [:style "
      body {
        font-family: -apple-system, BlinkMacSystemFont, Helvetica Neue, YuGothic, ヒラギノ角ゴ ProN W3, Hiragino Kaku Gothic ProN, Arial, メイリオ, Meiryo, sans-serif;
      }

      /* http://meyerweb.com/eric/tools/css/reset/
         v2.0 | 20110126
       License: none (public domain)
       */

      html, body, div, span, applet, object, iframe,
      h1, h2, h3, h4, h5, h6, p, blockquote, pre,
      a, abbr, acronym, address, big, cite, code,
      del, dfn, em, img, ins, kbd, q, s, samp,
      small, strike, strong, sub, sup, tt, var,
      b, u, i, center,
      dl, dt, dd, ol, ul, li,
      fieldset, form, label, legend,
      table, caption, tbody, tfoot, thead, tr, th, td,
      article, aside, canvas, details, embed,
      figure, figcaption, footer, header, hgroup,
      menu, nav, output, ruby, section, summary,
      time, mark, audio, video {
        margin: 0;
        padding: 0;
        border: 0;
        font-size: 100%;
        font: inherit;
        vertical-align: baseline;
      }
      /* HTML5 display-role reset for older browsers */
      article, aside, details, figcaption, figure,
      footer, header, hgroup, menu, nav, section {
        display: block;
      }
      body {
        line-height: 1;
      }
      ol, ul {
        list-style: none;
      }
      blockquote, q {
        quotes: none;
      }
      blockquote:before, blockquote:after,
      q:before, q:after {
        content: '';
        content: none;
      }
      table {
        border-collapse: collapse;
        border-spacing: 0;
      }
     "]
    [:style {:id "jss-server-side"} css]]
   [:body
    [:div#app
     {:dangerouslySetInnerHTML
      {:__html app-html}}]
    [:script {:src "/static/js/compiled/cljs_base.js"}]
    [:script {:src "/static/js/compiled/app.js"}]]
   [:div
    {:dangerouslySetInnerHTML
     {:__html  (str "<script>window.preload = '" (-> @db/app-db pr-str) "'</script>")}}]
   (when-not dev?
     [:div
      {:dangerouslySetInnerHTML
       {:__html "
        <script>
        // Check that service workers are registered
        if ('serviceWorker' in navigator) {
            // Use the window load event to keep the page load performant
            window.addEventListener('load', () => {
                  navigator.serviceWorker.register('/sw.js');
            });
        }
        </script>
        <script>
        LUX=(function(){var a=('undefined'!==typeof(LUX)&&'undefined'!==typeof(LUX.gaMarks)?LUX.gaMarks:[]);var d=('undefined'!==typeof(LUX)&&'undefined'!==typeof(LUX.gaMeasures)?LUX.gaMeasures:[]);var j='LUX_start';var k=window.performance;var l=('undefined'!==typeof(LUX)&&LUX.ns?LUX.ns:(Date.now?Date.now():+(new Date())));if(k&&k.timing&&k.timing.navigationStart){l=k.timing.navigationStart}function f(){if(k&&k.now){return k.now()}var o=Date.now?Date.now():+(new Date());return o-l}function b(n){if(k){if(k.mark){return k.mark(n)}else{if(k.webkitMark){return k.webkitMark(n)}}}a.push({name:n,entryType:'mark',startTime:f(),duration:0});return}function m(p,t,n){if('undefined'===typeof(t)&&h(j)){t=j}if(k){if(k.measure){if(t){if(n){return k.measure(p,t,n)}else{return k.measure(p,t)}}else{return k.measure(p)}}else{if(k.webkitMeasure){return k.webkitMeasure(p,t,n)}}}var r=0,o=f();if(t){var s=h(t);if(s){r=s.startTime}else{if(k&&k.timing&&k.timing[t]){r=k.timing[t]-k.timing.navigationStart}else{return}}}if(n){var q=h(n);if(q){o=q.startTime}else{if(k&&k.timing&&k.timing[n]){o=k.timing[n]-k.timing.navigationStart}else{return}}}d.push({name:p,entryType:'measure',startTime:r,duration:(o-r)});return}function h(n){return c(n,g())}function c(p,o){for(i=o.length-1;i>=0;i--){var n=o[i];if(p===n.name){return n}}return undefined}function g(){if(k){if(k.getEntriesByType){return k.getEntriesByType('mark')}else{if(k.webkitGetEntriesByType){return k.webkitGetEntriesByType('mark')}}}return a}return{mark:b,measure:m,gaMarks:a,gaMeasures:d}})();LUX.ns=(Date.now?Date.now():+(new Date()));LUX.ac=[];LUX.cmd=function(a){LUX.ac.push(a)};LUX.init=function(){LUX.cmd(['init'])};LUX.send=function(){LUX.cmd(['send'])};LUX.addData=function(a,b){LUX.cmd(['addData',a,b])};LUX_ae=[];window.addEventListener('error',function(a){LUX_ae.push(a)});LUX_al=[];if('function'===typeof(PerformanceObserver)){var LongTaskObserver=new PerformanceObserver(function(c){var b=c.getEntries();for(var a=0;a<b.length;a++){var d=b[a];LUX_al.push(d)}});try{LongTaskObserver.observe({entryTypes:['longtask'],buffered:true})}catch(e){}};
        </script>
        <script src='https://cdn.speedcurve.com/js/lux.js?id=294838527' async defer></script>"}}])])

(defn- render-index
  [sheets-registry generate-class-name theme sheets-manager]
  (let [app-html (r/render-to-string [app-component sheets-registry generate-class-name theme sheets-manager])]
    (r/render-to-string [index app-html (.toString sheets-registry)])))

(defn handle-render
  [req res]
  (let [request-path (.-baseUrl req)
        sheets-registry (new sheets-registry)
        sheets-manager (new js/Map)
        generate-class-name (mui/create-generate-class-name)
        theme (mui/theme)]
    (re-frame/dispatch-sync [::events/initialize {:api-endpoint api-endpoint}])
    (secretary/dispatch! request-path)
    (.format res #js {"text/html" #(.send res (render-index sheets-registry
                                                            generate-class-name
                                                            theme
                                                            sheets-manager))})))

(defn serve
  [port]
  (.listen app port))

(defn -main
  [& args]
  (let [port (-> args first js/parseInt)]
    (dev-setup)
    (serve port)))

(doto app
  (.use (compression))

  (.use "/sw.js" (.static express (str static-file-path "sw.js")))
  (.use "/manifest.json" (.static express (str static-file-path "manifest.json")))
  (.use "/favicon.ico" (.static express (str static-file-path "favicon.ico")))
  (.use "static" (.static express static-file-path))
  (.use "/static" (.static express static-file-path))
  (.use "/*" handle-render))

(set! *main-cli-fn* -main)
