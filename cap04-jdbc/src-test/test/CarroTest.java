package test;

import java.util.List;
import br.com.livro.domain.Carro;
import br.com.livro.domain.CarroService;
import junit.framework.TestCase;

public class CarroTest extends TestCase {
	public void testListaCarros() {
		List<Carro> carros = CarroService.getCarros();
		assertNotNull(carros);
		// Valida se encontrou algo
		assertTrue(carros.size() > 0);
		// Valida se encontrou o Tucker
		Carro tucker = CarroService.findByName("Tucker 1948");
		assertEquals("Tucker 1948", tucker.getNome());
		// Valida se encontrou a Ferrari
		Carro ferrari = CarroService.findByName("Ferrari FF");
		assertEquals("Ferrari FF", ferrari.getNome());
		// Valida se encontrou o Bugatti
		Carro bugatti = CarroService.findByName("Bugatti Veyron");
		assertEquals("Bugatti Veyron", bugatti.getNome());
	}
	
	public void testSalvarDeletarCarro() {
		Carro c = new Carro();
		c.setNome("Teste");
		c.setDesc("Teste desc");
		c.setUrlFoto("url foto aqui");
		c.setUrlVideo("url video aqui");
		c.setLatitude("lat");
		c.setLongitude("lng");
		c.setTipo("tipo");
		CarroService.save(c);
		// id do carro salvo
		Long id = c.getId();
		assertNotNull(id);
		// Busca no banco de dados para confirmar que o carro foi salvo
		c = CarroService.getCarro(id);
		assertEquals("Teste", c.getNome());
		assertEquals("Teste desc", c.getDesc());
		assertEquals("url foto aqui", c.getUrlFoto());
		assertEquals("url video aqui", c.getUrlVideo());
		assertEquals("lat", c.getLatitude());
		assertEquals("lng", c.getLongitude());
		assertEquals("tipo", c.getTipo());
		// Atualiza o carro
		c.setNome("Teste UPDATE");
		CarroService.save(c);
		// Busca o carro novamente (vai estar atualizado)
		c = CarroService.getCarro(id);
		assertEquals("Teste UPDATE", c.getNome());
		// Deleta o carro
		CarroService.delete(id);
		// Busca o carro novamente
		c = CarroService.getCarro(id);
		// Desta vez o carro não existe mais.
		assertNull(c);
	}

}
