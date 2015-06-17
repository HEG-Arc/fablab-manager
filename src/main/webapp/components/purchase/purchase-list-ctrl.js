'use strict';
var app = angular.module('Fablab');
app.controller('PurchaseListController', function ($scope, $filter, $location,
        ngTableParams, PurchaseService, NotificationService) {
    $scope.tableParams = new ngTableParams(
            angular.extend({
                page: 1, // show first page
                count: 25, // count per page
                sorting: {
                    purchaseDate:'asc',
                    quantity:'asc',
                    purchasePrice:'asc',
                    supply:'asc',
                    user:'asc'
                }
            }, $location.search()), {
        getData: function ($defer, params) {
            if ($scope.purchases) {
                params.total($scope.purchases.length);
                $location.search(params.url());
                var filteredData = params.filter() ? $filter('filter')($scope.purchases, params.filter()) : $scope.purchases;
                var orderedData = params.sorting() ? $filter('orderBy')(filteredData, params.orderBy()) : filteredData;
                $defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
            }
        }
    });
    var updatePurchaseList = function () {
        PurchaseService.list(function (data) {
            $scope.purchases = data;
            $scope.tableParams.reload();
        });
    };
    $scope.remove = function (purchase) {
        PurchaseService.remove(purchase.id, function () {
            NotificationService.notify("success", "purchase.notification.removed");
            updatePurchaseList();
        });
    };
    $scope.softRemove = function (purchase) {
        PurchaseService.softRemove(purchase.id, function () {
            NotificationService.notify("success", "purchase.notification.removed");
            updatePurchaseList();
        });
    };
    updatePurchaseList();
});
