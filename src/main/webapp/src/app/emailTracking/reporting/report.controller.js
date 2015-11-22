angular.module('emailanalytics')
    .controller('ReportCtrl', function ($rootScope, $scope, $routeParams, $alert, $location, Tracking) {
        $scope.results = Tracking.query();
    });
