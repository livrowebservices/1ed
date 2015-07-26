var injector = angular.injector([ 'ng', 'AppCarro' ]);

var init = {
	setup : function() {
		this.$scope = injector.get('$rootScope').$new();
	}
};

QUnit.module('Carro TestCase', init);

/**
 * TestCase usando requisições assincronas, assim podemos realizar testes unitários
 * sobre os serviços rest criados durante o livro, assim conseguimos diminuir
 * e muito os riscos da aplicação acabar quebrando do lado do cliente.
 */

QUnit.asyncTest('Buscando carros no webservice de carros', function() {
	var carroRestService = injector.get('carroRestService');
	carroRestService.findAll().success(function(response, status) {
		QUnit.ok(response.length > 0, 'WebService de carros retornou ' + response.length + ' carros');
		QUnit.start();
	}).error(function(response, status, error, headers) {
		QUnit.ok(false, "Erro ao executar o webservice de pesquisa de carro - Status retornado: " + status +" na URL : " + headers.url);
	});
});

QUnit.asyncTest('Busncando um carro no webservice de carros', function() {
	var carroRestService = injector.get('carroRestService');
	carroRestService.find(20).success(function(response, status) {
		if(status == 200) {
			var carro = response;
			QUnit.ok(typeof carro == 'object');
			QUnit.ok(carro.id == 20);
		} else {
			QUnit.ok(status == 204, "Nenhum carro cadastrado no banco de dados");
		}
		QUnit.start();
	});
});

QUnit.asyncTest("Salvando um carro no Webservice de carros", function() {
	var carroRestService = injector.get('carroRestService');
	var carro = {
			"tipo": "classicos",
			"nome": "Teste case",
			"desc": "Descrição Test caso de cadastro de carro",
			"urlFoto": "http://www.livroandroid.com.br/livro/carros/classicos/Chevrolet_BelAir.png",
			"urlVideo": "http://www.livroiphone.com.br/carros/classicos/chevrolet_bel_air.mp4",
			"latitude": "-23.564224",
			"longitude": "-46.653156"
	}
	
	carroRestService.save(carro).success(function(response, status) {
		QUnit.equal(status, 200);
		QUnit.equal(response.status, "OK");
		QUnit.notEqual(response.msg, undefined);
		QUnit.start();
	});
});

QUnit.asyncTest('Editando um carro no webservice de carros', function() {
	var carroRestService = injector.get('carroRestService');
	carroRestService.find(20).success(function(response, status) {
		if(status == 200) {
			var carro = response;
			QUnit.ok(typeof carro == 'object');
			QUnit.ok(carro.id == 20);
			
			carro.nome = "Carro editado"
				carroRestService.save(carro).success(function(response, status) {
				QUnit.equal(status, 200);
				QUnit.equal(response.status, "OK");
				QUnit.notEqual(response.msg, undefined);
				QUnit.start();
			});
		} else {
			QUnit.ok(status == 204, "Nenhum carro cadastrado no banco de dados");
			QUnit.start();
		}
	});
});
