(ns vr-match-api.app.my-webapp.resolvers)

(defn approach-list
  [context arguments value]
  (let [{:keys [limit offset]} arguments]
    (->>
     {:id 1
      :title "サンプル画像"
      :userName "一箱"
      :introduction "バーチャル清楚系女子高校生Webアプリケーションエンジニアおじさんです。こっそりプログラミングしてます。"
      :platForms [{:id 1 :name "VRChat"} {:id 2 :name "VRoidHub"} {:id 3 :name "VirtualCast"}]
      :image ["https://storage.googleapis.com/boxp-tmp/profile_sample.png"]}
     (repeat limit))))
