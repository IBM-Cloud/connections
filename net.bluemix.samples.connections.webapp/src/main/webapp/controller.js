'use strict';

var simpleApp = angular.module('simpleApp', [ 'ngSanitize' ]);

simpleApp.controller('ProfileCtrlHelper', function($scope, $http) {
		$http.get('userprofile').success(function(data) {
			$scope.profile = data;
			if (data.displayName != null) {
				$scope.singedIn = true;
			} else {
				$scope.singedIn = false;
			}
		});
});