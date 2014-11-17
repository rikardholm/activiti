angular.module('activitiApp', [])
    .controller('ProcessController', function ($scope, $http) {
        $http.get('/processes').success(function (data) {
            $scope.processes = data;
        });

        $scope.showForm = function (process) {
            $http.get('/processes/' + process.key + '/form')
                .success(function (data) {
                    $scope.form = data;
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