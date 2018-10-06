// material-ui の universal 対応のための workaround
// もっといい手があれば知りたい
global.window = global;
global.window.navigator = {};
global.window.navigator.userAgent = "";
