(function() {
	var app = angular.module("SpringBootRestApp");

	var MainController = function($scope, $location, userService, $window) {
		console.log("Main Controller....")

	};

	app.controller("MainController", [ "$scope", "$location", "userService",
			"$window", MainController ]);
}());