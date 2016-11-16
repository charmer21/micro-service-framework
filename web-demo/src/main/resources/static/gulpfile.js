var gulp = require('gulp');
var sass = require('gulp-sass');
var uglify = require('gulp-uglify');
var group = require('gulp-group-files');

// sass文件编译输出配置
var sassFiles = {
    "main": {
        src: "./src/scss/main.scss",
        dest: "./css/"
    }
};

// 编译scss文件
gulp.task('sass:compile', function () {
    return group(sassFiles, function (key, fileset) {
        return gulp.src(fileset.src)
            .pipe(sass().on('error', sass.logError))
            .pipe(gulp.dest(fileset.dest));
    })();
});

// sass文件变化自动编译
gulp.task('sass:watch', function () {
    gulp.watch('src/scss/**/*.scss', ['sass:compile'])
});

// js压缩
gulp.task('js:uglify', function () {
    gulp.src('./src/js/**/*.js').pipe(uglify()).pipe(gulp.dest('./js/'));
});

// js文件变化自动压缩
gulp.task('js:watch', function(){
    gulp.watch('src/js/**/*.js', ['js:uglify']);
});

// 启动任务
gulp.task('default', ['sass:compile', 'sass:watch', 'js:uglify', 'js:watch']);