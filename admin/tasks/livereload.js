let miniLr = require('mini-lr');
let liveReload = miniLr();
let config = require('./config').liveReload;

module.exports = function () {
  return () => {
    liveReload.listen(config.port);
  };
};

module.exports.notifyChanged = function (files) {
  liveReload.changed({
    body: {
      files: files
    }
  });
};
