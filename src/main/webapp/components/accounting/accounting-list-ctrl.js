(function () {
	'use strict';

	var app = angular.module('Fablab');
	app.controller('AccountingListController', function ($scope, AccountingService) {
		$scope.criteria = {
			dateFrom: null,
			dateTo: null
		};
		$scope.intervals = [{
				label: 'today',
				unit: 'day',
				delta: 0
			}, {
				label: 'yesterday',
				unit: 'day',
				delta: 1
			}, {
				label: 'thisMonth',
				unit: 'month',
				delta: 0
			}, {
				label: 'lastMonth',
				unit: 'month',
				delta: 1
			}, {
				label: 'thisYear',
				unit: 'year',
				delta: 0
			}, {
				label: 'lastYear',
				unit: 'year',
				delta: 1
			}];

		$scope.dateManuallyUpdated = function () {
			$scope.selectedInterval = null;
		};

		$scope.periodPreset = function (interval) {
			var start, end;
			var u = interval.unit;
			start = moment().startOf(u);
			end = moment().endOf(u);
			if (interval.delta !== 0) {
				start = start.subtract(interval.delta, u);
				end = end.subtract(interval.delta, u);
			}
			$scope.criteria.dateFrom = start.toDate();
			$scope.criteria.dateTo = end.toDate();
			$scope.selectedInterval = interval;
			$scope.updateAccounting();
		};

		var computeInAndOut = function(){
			var moneyIn = 0;
			var sell = 0;
			angular.forEach($scope.history, function(h){
				if(h.amount>0){
					moneyIn += h.amount;
				}else{
					sell -= h.amount;
				}
			});
			$scope.results = {
				moneyIn : moneyIn,
				sell: sell,
				delta : moneyIn-sell
			};
		};

		$scope.updateAccounting = function () {
			AccountingService.search($scope.criteria.dateFrom, $scope.criteria.dateTo, function(data){
				$scope.history = data;
				computeInAndOut();
			});
		};
		
		$scope.export = function(){
			alert('todo');
		};

		$scope.periodPreset($scope.intervals[2]);//this month
	});
}());