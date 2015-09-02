/**
 * Arquivo que registra o módulo "main" do AngularJS.
 * Registra as dependências, exemplo: ngRoute, ngDialog, ngFileUpload
 * 
 * Define URL base para os web services.
 */
(function() {

	angular.module('AppCarro', [ 'ngRoute', 'ngDialog', 'ngFileUpload',
			'CarroControllers' ]);

	angular.module('AppCarro').constant("APP_CONFIG", {
		"API_REST" : "/rest"
	});

	function Config($routeProvider) {
		$routeProvider.when('/', {
			controller : 'CarroListController',
			templateUrl : './pages/carros/list.html'
		}).otherwise({
			redirectTo : '/'
		});
	}

	angular.module('AppCarro').config(Config);
}());