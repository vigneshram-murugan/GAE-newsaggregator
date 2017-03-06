var app = angular.module('app',['ui.materialize']);



app.controller('nyTimesController', function($scope, $http, $rootScope){
	$scope.nyTimes = [];
	$scope.showModal = false;
	$http.get('/memcached').success(function(data){
		$scope.nyTimes = data;
	})
	
	$scope.openModal = function() {
		$scope.modalContent = data;
		$scope.showModal = !$scope.showModal;
	}
	
	$scope.finished = function() {
		$('.materialboxed').materialbox();
	}
})




app.directive('modalCustom', function(){
	return {
		restrict : 'AE',
		templateUrl : '/modal.html',
		scope : {
			show : '@hideshow'
		},
		link : function(scope, ele, attr) {
			console.log(scope.show)
			scope.$watch('show',function(){
				console.log(scope.show)
			},true)
		}
	}
})

