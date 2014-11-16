angular.module('activitiApp', [])
    .controller('ProcessController', function ($scope, $http, $compile) {
        $http.get('/processes').success(function (data) {
            $scope.processes = data;
        });

        $scope.showForm = function (process) {
            console.info(process.key);
            $http.get('/form/'+process.key).success(
                function (html) {
                    $scope.form = $compile(html);
                    console.log('boink');
                    console.log($scope.form);
                });
        }
    }).controller('TaskController', function ($scope) {
        $scope.tasks = [
            {"title": "Kolla upp person", "description": "Detta behöver kollas."},
            {"title": "Sälj av innehav", "description": "Dags att flytta ut."},
            {"title": "Skicka välkomstbrev", "description": "Glöm inte hämta pappret."}
        ];
    }).service('ProcessService', function ($http) {

    });