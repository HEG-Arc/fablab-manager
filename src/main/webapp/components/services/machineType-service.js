(function () {
    'use strict';
    var app = angular.module('Fablab');
    app.factory('MachineTypeService', function ($log, $resource, $http) {
        var machineType = $resource(App.API.MACHINE_TYPE_API + "/:id", {id: '@id'});

        return {
            list: function (successFn) {
                $http(
                        {
                            method: 'GET',
                            url: App.API.MACHINE_TYPE_API
                        }
                ).success(successFn);
            },
            remove: function (id, successFn) {
                $log.debug("MachineTypeService: remove...");
                machineType.remove({id: id}, successFn);
            },
             softRemove: function (id, successFn) {
                $http.get(App.API.MACHINE_TYPE_API + "/softRemove?id=" + id).success(successFn);
                $log.debug("MachineTypeService: soft remove...");
            },
            save: function (machineTypeParam, successFn, errorFn) {
                $log.debug("MachineTypeService: save...");
                console.log(machineTypeParam.restricted);
                return machineType.save(machineTypeParam, successFn, errorFn);
            },
            
            get: function (id, successFn) {
                $log.debug("MachineTypeService: get...");
                return machineType.get({id: id}, successFn);
            }
        };
    });

}());
