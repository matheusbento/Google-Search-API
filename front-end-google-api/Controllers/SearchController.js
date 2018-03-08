var app = angular.module('GoogleApp', []);
app.controller('SearchController', function($scope, SearchService) {

	$scope.listar = function(text){
		SearchService.listar(text).then(function(response) {
			$scope.items = response.data;

			//console.log($scope.clients);
		});
	}
	
});
app.service('SearchService', function($http){
	var api = "http://localhost:8080/back-end-google-api/rest/Search?q=";
	this.listar = function(text){
	    return $http.get(api+text)
	};
});