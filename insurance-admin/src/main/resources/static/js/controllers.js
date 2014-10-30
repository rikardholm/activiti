var activitiApp = angular.module('activitiApp',[]);

activitiApp.controller('TaskController', function($scope) {
    $scope.tasks = [
        {"title": "Kolla upp person", "description": "Detta behöver kollas."},
        {"title": "Sälj av innehav", "description": "Dags att flytta ut."},
        {"title": "Skicka välkomstbrev", "description": "Glöm inte hämta pappret."}
    ];
});