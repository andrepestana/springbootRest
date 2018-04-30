(function() {
	var app = angular.module("SpringBootRestApp");

	var UserController = function($scope, $location, userService, $window,
			$rootScope, $route) {
		console.log("User Controller....");
		console.log("Token: " + $window.localStorage.token);
		console.log($scope.user);

		console.log("=====" + $scope.message);

		var getUsers = function() {
			userService.getUsers().then(function(data) {
				$scope.users = data;
			});
		};
		getUsers();
		$scope.deleteUser = function(id) {
			userService.deleteUser(id).then(function(data){
				$scope.message = "User removed successfully";
				userService.getUsers().then(function(data) {
					$scope.users = data;
				});
			});
		};

		$scope.addUser = function() {
			userService.addUser($scope.user).then(function(data) {
				$scope.message = data.message;
			}, function(error) {
				$scope.message = error.data.message;
			});
		};
		$scope.updateUser = function() {
			userService.updateUser($scope.user).then(function(data) {
				$scope.message = data.message;
				console.log($scope.message);

			});
		};
		$scope.editUser = function(user) {
			$rootScope.user = user;
			$location.path("/update_user");

		};

	};

	app.controller("UserController", [ "$scope", "$location", "userService",
			"$window", '$rootScope', '$route', UserController ]);
}());