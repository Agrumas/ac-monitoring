let del = require('del');
let config = require('./config').build;

module.exports = function () {
  return function () {
    return del([config.destination], {force: true});
  };
};
