let gulp = require('gulp');
let config = require('./config').general;

module.exports = function () {
  return () => {
    return gulp.src(config.source)
      .pipe(gulp.dest(config.destination));
  };
};
