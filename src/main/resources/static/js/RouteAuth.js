angular.module('SpringBootRestApp').run(
		function($rootScope, $location, userService) {

			$rootScope.$on('$routeChangeStart', function(angularEvent, newUrl) {
				if (newUrl.requireAuth && !userService.isAuthenticated()) {
					// User isn’t authenticated
					console.log("Routing to /login");
					$location.path("/login");
				}
			});

		});