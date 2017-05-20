let webpack = require('webpack');
let config = require('./webpack');
const API_ENTRY_POINT = 'http://stam.io/v1/';

config.devtool = 'source-map';
config.plugins = [
  new webpack.optimize.CommonsChunkPlugin(
    'vendor', 'vendor.js'
  ),
  new webpack.DefinePlugin({
    ENVIRONMENT: JSON.stringify('production'),
    WEBPACK_API_ENTRY_POINT: JSON.stringify(API_ENTRY_POINT)
  }),
  new webpack.optimize.UglifyJsPlugin({
    minimize: true,
    mangle: false,
    comments: false
  })
];

module.exports = config;
