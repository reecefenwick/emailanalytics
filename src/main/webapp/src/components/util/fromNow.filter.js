angular.module('emailanalytics')
    .filter('fromNow', function () {
        return function (date) {
            return moment(date).fromNow();
        }
    });
