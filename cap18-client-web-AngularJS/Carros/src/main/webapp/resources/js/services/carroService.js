(function() {

	angular
		.module('carroService', []);
	
	function carro() {
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
	
	angular
		.module('carroService')
		.factory('carroService', carro);
}());