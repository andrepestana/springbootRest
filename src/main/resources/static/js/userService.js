(function() {

	var userService = function($http, $window) {

		var getUser = function(username) {
			return $http.get("https://api.github.com/users/" + username).then(
					function(response) {
						return response.data;
					});
		};

		var getUsers = function() {
			return $http.get("/rest/auth/users").then(
					function(response) {
						return response.data;
					});
		};
		
		var deleteUser = function(id){
			return $http.delete("/rest/auth/users/"+id)
			  .then(function(response) {
				  return response.data;
			  });
		};

		var isAuthenticated = function() {
			return (typeof $window.localStorage.token === "string" && $window.localStorage.token.length > 0);
		};

		var logon = function(username, password) {
			return $http.post("/rest/login", {
				"username" : username,
				"password" : password
			}).then(function(response) {
				content = response.data;
				statuscode = response.status;
				$window.localStorage.token = response.data.token;
			});
		};
		
		var addUser = function(user){
			return $http.post("/rest/auth/users/", 
				      {username:user.username, 
				      password:user.password, 
				      firstName: user.firstName, 
				      lastName: user.lastName, 
				      birthday: user.birthday }
			      ).then(function(response) {
			    	  return {message: 'User added successfully.'}
			  });
		};
		var updateUser = function(user){
			return $http.put("/rest/auth/users/"+user.id, 
				      {username:user.username, 
				      password:user.password, 
				      firstName: user.firstName, 
				      lastName: user.lastName, 
				      birthday: user.birthday }
			      ).then(function(response) {
			    	  return {message: 'User updated successfully.'}
			  });
		};

		return {
			getUser : getUser,
			getUsers : getUsers,
			deleteUser: deleteUser,
			addUser: addUser,
			updateUser: updateUser,
			isAuthenticated : isAuthenticated,
			logon : logon
		};
	};

	var module = angular.module("SpringBootRestApp");
	module.factory("userService", userService);

}());