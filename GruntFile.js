// Generated on 2015-11-20 using generator-jhipster 2.24.0
'use strict';
var fs = require('fs');

module.exports = function(grunt) {
    require('load-grunt-tasks')(grunt);

    grunt.initConfig({
        watch: {
            browserSync: {
                files: ['Gruntfile.js', 'pom.xml'],
                tasks: ['serve:dev']
            }
        },
        browserSync: {
            dev: {
                bsFiles: {
                    src : [
                        'src/main/webapp/**/*.html',
                        'src/main/webapp/**/*.json',
                        'src/main/webapp/assets/styles/**/*.css',
                        'src/main/webapp/src/app/**/*.{js,html}',
                        'src/main/webapp/assets/images/**/*.{png,jpg,jpeg,gif,webp,svg}',
                        'tmp/**/*.{css,js}'
                    ]
                }
            },
            options: {
                watchTask: true,
                proxy: "localhost:8090"

            }
        }
    });

    grunt.registerTask('serve', [
        'browserSync',
        'watch'
    ]);

    grunt.registerTask('default', ['serve:dev']);
};
