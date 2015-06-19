'use strict';
var app = angular.module('Fablab');
app.controller('RevisionListController', function ($scope, $filter, $location,
        ngTableParams, RevisionService, NotificationService) {
    $scope.currency = App.CONFIG.CURRENCY;
    $scope.tableParams = new ngTableParams(
            angular.extend({
                page: 1, // show first page
                count: 25, // count per page
                sorting: {
                    revisionDate: 'asc',
                    note: 'asc',
                    cost: 'asc',
                    machine: 'asc'
                }
            }, $location.search()), {
        getData: function ($defer, params) {
            if ($scope.revisions) {
                params.total($scope.revisions.length);
                $location.search(params.url());
                var filteredData = params.filter() ? $filter('filter')($scope.revisions, params.filter()) : $scope.revisions;
                var orderedData = params.sorting() ? $filter('orderBy')(filteredData, params.orderBy()) : filteredData;
                $defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
            }
        }
    });
    var updateRevisionList = function () {
        RevisionService.list(function (data) {
            $scope.revisions = data;
            $scope.tableParams.reload();
        });
    };
    $scope.remove = function (revision) {
        RevisionService.remove(revision.id, function () {
            NotificationService.notify("success", "revision.notification.removed");
            updateRevisionList();
        });
    };
    $scope.softRemove = function (revision) {
        RevisionService.softRemove(revision.id, function () {
            NotificationService.notify("success", "revision.notification.removed");
            updateRevisionList();
        });
    };
    updateRevisionList();
});

