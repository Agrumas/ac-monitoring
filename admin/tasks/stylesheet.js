let gulp = require('gulp');
let sass = require('gulp-sass');
let config = require('./config').client;

module.exports = function (singleRun) {
  return () => {
    const opt = {
      includePaths: ['node_modules/susy/sass']
    };
    if (singleRun) {
      opt.outputStyle = 'compressed';
    }
    let gulpStream = gulp.src('./client/app.scss')
      .pipe(sass(opt).on('error', sass.logError));

    return gulpStream.pipe(gulp.dest(config.destination));
  };
};
