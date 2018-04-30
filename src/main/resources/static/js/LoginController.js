(function() {
	var app = angular.module("SpringBootRestApp");

	var LoginController = function($scope, $location, userService, $window) {
		console.log("Login Controller....");

		var onLogon = function(data) {
			console.log("onLogon");
			$location.path("/index");
			$window.location.reload();
		};

		var onError = function(reason) {
			$scope.message = reason.data.message;
		}
		$scope.logon = function() {
			console.log("Logon");
			var loggedOn = userService.logon($scope.username, $scope.password)
					.then(onLogon, onError);
		};

		$scope.loggedOn = Boolean(userService.isAuthenticated());

		$scope.logout = function() {
			console.log("Logout");
			$window.localStorage.token = '';
			$location.path("/login");
			$window.location.reload();
		};
	};

	app.controller("LoginController", [ "$scope", "$location", "userService",
			"$window", LoginController ]);
}());