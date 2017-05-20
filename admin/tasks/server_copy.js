let gulp = require('gulp');
let config = require('./config').server;

module.exports = function () {
  return () => {
    return gulp.src(config.source)
      .pipe(gulp.dest(config.destination));
  };
};
