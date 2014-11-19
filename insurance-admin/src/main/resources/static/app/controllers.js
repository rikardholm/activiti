angular.module('activitiApp', [])
    .controller('ProcessController', function ($scope, $http) {
        $http.get('/processes').success(function (data) {
            $scope.processes = [];
            data.forEach(function (processInfo) {
                var process = { "info": processInfo };
                $scope.processes.push(process);
            });

        });

        $scope.showForm = function (process) {
            $http.get('/processes/' + process.info.key + '/form')
                .success(function (data) {
                    process.form = data;
                });
        };

        $scope.templateUrlOf = function (field) {
           return 'app/' + field.type + '.html';
        };
    }).service('ProcessService', function ($http) {

    });