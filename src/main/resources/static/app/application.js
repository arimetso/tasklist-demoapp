'use strict';

var app = angular.module("DemoTodoList", []);

app.factory("lodash", function lodashFactory($window) {
    return $window._;
});

app.factory("TodoListService", ["$http", function($http) {
    return {
        _buildId: function(name, password) {
            var sha256 = new jsSHA("SHA-256", "TEXT");
            sha256.update("::" + name + "::sd98g79kfd8gdfs8::" + password);
            return sha256.getHash("HEX");
        },

        load: function(name, password) {
            var listId = this._buildId(name, password);
            return $http.get("/rest/list/" + listId);
        },

        save: function(name, password, items) {
            var listId = this._buildId(name, password);
            return $http.post("/rest/list/" + listId, {"items": items});
        }
    }
}]);

app.controller("MainCtrl", [
    "$scope",
    "$timeout",
    "$log",
    "TodoListService",
    "lodash",
    function($scope, $timeout, $log, todoListService, _) {
        $scope.name = "";
        $scope.password = "";
        $scope.items = [];
        $scope.newitem = {text: ""};
        $scope.uploader = null;
        $scope.messages = "";
        $scope.justLoaded = false;

        $scope.hasValidNameAndPassword = function () {
            return $scope.name && $scope.name.trim().length > 2 && $scope.password && $scope.password.trim().length > 4;
        };

        $scope.load = function () {
            if ($scope.hasValidNameAndPassword()) {
                todoListService.load($scope.name, $scope.password).then(
                    function (response) {
                        $scope.justLoaded = true;
                        $scope.items = response.data.items || [];
                    }
                )
            }
        };

        $scope.save = function (items) {
            if ($scope.justLoaded) {
                $scope.justLoaded = false;
                return;
            }
            if ($scope.hasValidNameAndPassword()) {
                todoListService.save($scope.name, $scope.password, items).then(
                    function success(response) {
                        $scope.messages = "List saved successfully.";
                        $scope.justLoaded = true;
                        $scope.items = response.data.items;
                        $timeout(function() {
                            $scope.messages = null;
                        }, 500);
                    },
                    function failure(response) {
                        $scope.messages = "Save failed: " + response.data.message;
                        $timeout(function() {
                            $scope.messages = null;
                        }, 500);
                    }
                );
            }
        };

        $scope.moveUp = function (item) {
            var index = _.indexOf($scope.items, item),
                preceding = null;
            if (index > 0) {
                preceding = $scope.items[index - 1];
                $scope.items[index] = preceding;
                $scope.items[index - 1] = item;
            }
        };

        $scope.moveDown = function (item) {
            var index = _.indexOf($scope.items, item),
                following = null;
            if (index < $scope.items.length - 1) {
                following = $scope.items[index + 1];
                $scope.items[index] = following;
                $scope.items[index + 1] = item;
            }
        };

        $scope.add = function () {
            $scope.items.push($scope.newitem);
            $scope.newitem = {text: ""};
        };

        $scope.remove = function (item) {
            _.pull($scope.items, item);
        };

        $scope.$watch("items", function (newval, oldval) {
            if (angular.equals(newval, oldval)) {
                return;
            }
            $timeout.cancel($scope.uploader);
            $scope.uploader = $timeout($scope.save, 2000, true, newval);
        }, true);
    }
]);
