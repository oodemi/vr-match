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

├── k8s
├── resources
│   └── public
│       ├── css
│       ├── js
│       └── prod
│           ├── img
│           └── js
└── src
    ├── clj
    ├── cljs
    │   └── vr_match
    │       ├── approach
    │       │   └── components
    │       ├── example
    │       ├── lib
    │       │   └── components
    │       ├── login
    │       ├── profile
    │       │   └── components
    │       ├── register
    │       └── wizard
    │           └── components
    ├── cljs-client
    │   └── vr_match
    └── cljs-server
        └── vr_match
