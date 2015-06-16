(function () {
    'use strict';

    angular.module('Fablab').directive('userPaymentAddPayment', function (PaymentService, NotificationService) {
        return {
            restrict: 'EA',
            scope: {
                user: '=',
                minDate: '=',
                callback: '&'
            },
            templateUrl: 'components/payment/directive-add-payment.html',
            controller: function ($scope) {

                var resetValues = function () {
                    $scope.addPayment = {
                        amount: null,
                        date: new Date(),
                        comment: null
                    };
                };
                $scope.currency = App.CONFIG.CURRENCY;

                $scope.execute = function () {
                    var payment = {
                        total: $scope.addPayment.amount,
                        datePayment: $scope.addPayment.date,
                        comment: $scope.addPayment.comment,
                        'payment-user': $scope.user
                    };
                    PaymentService.addPayment(payment, function () {
                        NotificationService.notify("success", "payment.notification.paymentAdded");
                        $scope.callback();
                        resetValues();
                    });
                }
                resetValues();

                $scope.today = function () {
                    $scope.dt = new Date();
                };
                $scope.today();

                $scope.clear = function () {
                    $scope.dt = null;
                };

                $scope.open = function ($event) {
                    $event.preventDefault();
                    $event.stopPropagation();

                    $scope.opened = true;
                };

                $scope.dateOptions = {
                    formatYear: 'yy',
                    startingDay: 1
                };

                $scope.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
                $scope.format = $scope.formats[2];

                var tomorrow = new Date();
                tomorrow.setDate(tomorrow.getDate() + 1);
                var afterTomorrow = new Date();
                afterTomorrow.setDate(tomorrow.getDate() + 2);
                $scope.events =
                        [
                            {
                                date: tomorrow,
                                status: 'full'
                            },
                            {
                                date: afterTomorrow,
                                status: 'partially'
                            }
                        ];

                $scope.getDayClass = function (date, mode) {
                    if (mode === 'day') {
                        var dayToCheck = new Date(date).setHours(0, 0, 0, 0);

                        for (var i = 0; i < $scope.events.length; i++) {
                            var currentDay = new Date($scope.events[i].date).setHours(0, 0, 0, 0);

                            if (dayToCheck === currentDay) {
                                return $scope.events[i].status;
                            }
                        }
                    }

                    return '';
                };
            }
        };
    });
}());