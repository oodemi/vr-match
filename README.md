# Hito Hub

アバター×アバターのマッチングアプリ予定地です

## Setup

このプロジェクトは以下のツールに依存しています。ビルド前にインストールしてください

- Leiningen
- nodejs & npm

### 依存ライブラリのインストール

git clone後やライブラリの変更がある場合は必ず実行してください

```
npm install
```

## 開発環境の起動

Port8888番でSSR用サーバーが立ち上がります

```
npm run watch:dev:server
```

## ビルド

### 本番用

```
npm run build:prod
```

### 開発用差分ビルド

```
npm run figwheel
```

## コードフォーマット

```
npm run format
```

## ディレクトリ構造(TBD)

```
├── k8s: Kubernetes用マニフェストファイル置き場
├── resources
│   └── public: SSR用サーバーから返す静的ファイル置き場(静的画像とビルド後のjsバンドルのみ)
└── src
    ├── clj: マクロ置き場
    ├── cljs
    │   └── vr_match
    │       ├── lib: 全画面共通の実装を置く場所
    │       └── その他のフォルダ: wizard,loginなど機能別の実装を置く場所(re-ducksを参照)
    ├── cljs-client: クライアントサイドのエントリーポイント
    └── cljs-server: SSR用サーバーのエントリーポイント
```

## 参考技術スタック

- Clojure, ClojureScript
- reagent
- re-frame
- React.js
- workbox
- re-ducks
- material-ui
