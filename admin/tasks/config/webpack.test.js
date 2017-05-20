let webpack = require('webpack');
let config = require('./webpack');
const API_ENTRY_POINT = 'http://localhost:8000/v1/';

config.plugins = [
  new webpack.DefinePlugin({
    ENVIRONMENT: JSON.stringify('test'),
    WEBPACK_API_ENTRY_POINT: JSON.stringify(API_ENTRY_POINT)
  })
];
config.devtool = 'inline-cheap-source-map';

module.exports = config;
