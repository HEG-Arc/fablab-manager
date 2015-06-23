'use strict';
var app = angular.module('Fablab');
app.factory('GroupService', function ($log, $resource, $http) {
    var group = $resource(App.API.GROUP_API + "/:id", {id: '@id'});
    return {
        list: function (successFn) {
            $http(
                    {
                        method: 'GET',
                        url: App.API.GROUP_API
                    }
            ).success(successFn);
        },
        save: function () {
            console.log("TEST SAVE");
        },
        softRemove: function (id, successFn) {
            $http.get(App.API.GROUP_API + "/softRemove?id=" + id).success(successFn);
            $log.debug("GroupService: soft remove...");
        },
        get: function (id, successFn) {
            $log.debug("GroupService: get...");
            var groupRes = group.get({id: id}, successFn);
            return groupRes;
        }
    };
});


