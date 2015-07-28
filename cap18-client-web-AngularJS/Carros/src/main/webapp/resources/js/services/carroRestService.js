/**
 * API para acessar os web services REST dos carros
 */
(function() {
	angular
		.module('carroRestService', []);
	
	carroRestService = function($http, Upload, APP_CONFIG) {
		var urlApi = APP_CONFIG.API_REST + '/carros';

		function findAll() {
			return $http.get(this.api);
		}
		
		function save(carro) {
			if(carro.id) {
				return $http.put(this.api, carro);
			}
			return $http.post(this.api, carro);
		}
		
		function find(id) {
			return $http.get(this.api + '/' + id);
		}
		
		function deleteCarro(carro, file) {
			return $http.delete(this.api + '/' + carro.id);
		}
		
		function uploadFoto(file) {
			return Upload.upload({
                url: this.api,
                file: file
            });
		}
		
		function findByTipo(tipo) {
			return $http.get(this.api + '/tipo/' + tipo);
		}
		
		function findByNome(nome) {
			return $http.get(this.api + '/nome/' + nome);
		}
		
		function searchByNome(nome) {
			return $http.get(this.api + '/nome/' + nome);
		}
		
		return {
			api: urlApi,
			save: save,
			findAll: findAll,
			find: find,
			findByTipo: findByTipo,
			findByNome: findByNome,
			delete: deleteCarro,
			searchByNome: searchByNome,
			uploadFoto: uploadFoto
		}
	}
	angular
		.module('carroRestService')
		.service('carroRestService', carroRestService);
} ());