angular.module('emailanalytics')
    .controller('ReportCtrl', function ($rootScope, $scope, $routeParams, $alert, $location, Tracking) {

        $scope.tooltip = {
            "title": "Click me to simulate a user opening the email!",
            "checked": true
        };

        function searchForResults(query) {
            var payload = null;

            if (query) {
                payload = { query: query };
            }

            $scope.results = Tracking.query(payload);
        }

        $scope.$watch('searchTerm', function(newValue) {
            console.log("Change detected to search term");
            searchForResults(newValue);
        });

        // Call on load
        searchForResults();
    });
