/**
 * Arquivo que mantém a lista o estado da lista de carros. 
 */
(function() {

	angular.module('carroService', []);
	
	function carro() {
		// lista de carros
		var carros = [];

		function setCarros(carros) {
			this.carros = carros;
		}
		
		function getCarros() {
			return this.carros;
		}
		
		return {
			carros: carros,
			set : setCarros,
			get : getCarros
		}
	}
	
	angular.module('carroService').factory('carroService', carro);
}());