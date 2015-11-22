angular.module('emailanalytics')
    .controller('CreateTrackingRefCtrl', function ($scope, $alert, $location, Tracking) {

        $scope.metadatas = [];

        $scope.addMetadata = function() {
          $scope.metadatas.push({
              key: $scope.key,
              value: $scope.value
          })
        };

        $scope.removeMetadata = function(index) {
            $scope.metadatas.splice(index, 1);
        };

        $scope.createTracker = function () {

            Tracking.save({
                metadata: $scope.metadatas
            }).$promise.then(function (response) {
                    $alert({
                        content: 'Upload has been created.',
                        animation: 'fadeZoomFadeDown',
                        type: 'material',
                        duration: 3
                    });
                    $location.path('/tracking');
                }).catch(function (response) {
                    $alert({
                        title: 'Server error',
                        content: response.data.message,
                        animation: 'fadeZoomFadeDown',
                        type: 'material',
                        duration: 3
                    });
                });
        };
    });
