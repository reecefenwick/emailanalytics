angular.module('emailanalytics')
    .factory('Tracking', function ($resource) {
        return $resource('/api/tracking/:key');
    });
