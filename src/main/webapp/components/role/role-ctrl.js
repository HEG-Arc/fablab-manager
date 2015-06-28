'use strict';
var app = angular.module('Fablab');
app.controller('RoleController', function ($scope,
        NotificationService, RoleService, GroupService) {

    $scope.group;
    GroupService.list(function (groupes) {
        $scope.groupList = groupes;
    });

    $scope.setLists = function () {
        RoleService.list(function (roles) {
            if ($scope.group) {
                var availableRoles = [];
                if ($scope.group.technicalname.toUpperCase() === 'ADMIN') {
                    for (var i = 0; i < roles.length; i++) {
                        if (roles[i].technicalname !== 'ROLE_ADMIN') {
                            availableRoles.push(roles[i]);
                        }
                    }
                } else {
                    availableRoles = roles;
                }
                $scope.availableRoles = availableRoles;
                $scope.assignedRoles = $scope.group.roles;
            }
        });
    };

    $scope.save = function () {
        $scope.group.roles = $scope.assignedRoles;
        var groupCurrent = angular.copy($scope.group);
        GroupService.save(groupCurrent, function (data) {
            $scope.group = data;
            NotificationService.notify("success", "role.notification.saved");
        });
    };
    $scope.settings = {
        bootstrap2: false,
        moveOnSelect: true,
        postfix: '_helperz',
        selectMinHeight: 200,
        filter: true,
        filterValues: true
    };
    $scope.setLists();
});

