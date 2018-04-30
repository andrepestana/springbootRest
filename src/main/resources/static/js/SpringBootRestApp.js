(function() {

	var app = angular.module("SpringBootRestApp", [ "ngRoute" ]);

	app.config(function($routeProvider) {
		$routeProvider.when("/index", {
			templateUrl : "main.html",
			controller : "MainController",
			cache : false,
			requireAuth : true
		}).when("/login", {
			templateUrl : "login.html",
			controller : "LoginController",
			cache : false
		}).when("/users", {
			templateUrl : "users.html",
			controller : "UserController",
			cache : false,
			requireAuth : true
		}).when("/new_user", {
			templateUrl : "add_user.html",
			controller : "UserController",
			cache : false,
			requireAuth : true
		}).when("/update_user", {
			templateUrl : "edit_user.html",
			controller : "UserController",
			cache : false,
			requireAuth : true
		}).when("/not_found", {
			templateUrl : "not_found.html",
			cache : false,
			requireAuth : false
		}).otherwise({
			redirectTo : "/not_found"
		});
	});

	app.factory('AuthInterceptor', [
			'$q',
			'$window',
			'$location',
			'$rootScope',
			function($q, $window, $location, $rootScope) {
				return {
					'request' : function(config) {
						config.headers = config.headers || {};

						// If you have a token in local storage for example:
						if ($window.localStorage.token) {
							// Add the token to "Authorization" header in every
							// request
							config.headers.Authentication = $window.localStorage.token;
							// In your server you can check if the token is
							// valid and if it's not,
							// in responseError method you can take some action
						}

						// Handle something else

						return config;
					},

					// Optional method
					'requestError' : function(rejection) {
						// do something on request error

						// if (canRecover(rejection)) {
						// return responseOrNewPromise
						// }
						return $q.reject(rejection);
					},

					// Optional method
					'response' : function(response) {
						// do something on response success
						return response;
					},

					// optional method
					'responseError' : function(rejection) {
						// Here you can do something in response error, like
						// handle errors, present error messages etc.

						if (rejection.status === 401) { // Unauthorized
							console.log("Unauthorized");
							console.log(rejection.data.message);
							$rootScope.message = rejection.data.message;
							$location.path("/login");
						}else if (rejection.status === 400) { // 
							console.log("Bad Request");
							console.log(rejection.data.message);
							$rootScope.message = rejection.data.message;
							
						}

						// if (canRecover(rejection)) {
						// return responseOrNewPromise
						// }
						return $q.reject(rejection);
					}
				};
			} ]);

	app.config(function($httpProvider) {
		$httpProvider.interceptors.push('AuthInterceptor');
	});

}());
