angular.module('emailanalytics', ['ngCacheBuster', 'ngResource', 'ngMessages',
    'ngRoute', 'ngAnimate', 'ngSanitize', 'mgcrea.ngStrap'])
    .config(function ($routeProvider, $locationProvider) {
        $locationProvider.html5Mode(true);

        $routeProvider
            .when('/demo', {
                templateUrl: 'src/app/emailTracking/demo/trackingDemo.tpl.html',
                controller: 'DemoCtrl'
            })
            .when('/tracking/create', {
                templateUrl: 'src/app/emailTracking/create/createTrackingRef.tpl.html',
                controller: 'CreateTrackingRefCtrl'
            })
            .when('/tracking/:id', {
                templateUrl: 'src/app/emailTracking/reporting/report.tpl.html',
                controller: 'ReportCtrl'
            })
            .otherwise({
                redirectTo: '/demo'
            });
    })
    .config(function ($httpProvider, httpRequestInterceptorCacheBusterProvider) {
        //Cache everything except rest api requests
        httpRequestInterceptorCacheBusterProvider.setMatchlist([/.*api.*/, /.*protected.*/], true);

        $httpProvider.interceptors.push(function ($rootScope, $q, $window, $location) {
            return {
                request: function (config) {
                    if (config.headers.Authorization === undefined && $window.localStorage.token) {
                        config.headers.Authorization = 'Bearer ' + $window.localStorage.token;
                    }
                    return config;
                },
                responseError: function (response) {
                    if (response.status === 401) {
                        $location.path('/login');
                    }
                    return $q.reject(response);
                }
            }
        });
    });
