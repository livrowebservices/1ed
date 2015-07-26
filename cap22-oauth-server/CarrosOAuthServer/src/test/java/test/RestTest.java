package test;
import java.util.Base64;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import junit.framework.TestCase;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import br.com.livro.domain.Carro;
import br.com.livro.domain.ResponseWithURL;
import br.com.livro.rest.GsonMessageBodyHandler;


public class RestTest extends TestCase {
	public void testGetCarroId() {
		// Cria o cliente da API
		ClientConfig clientConfig = new ClientConfig();
		Client client = ClientBuilder.newClient(clientConfig);
		client.register(HttpAuthenticationFeature.basic("livro", "livro123"));
		// Registra o parser com o Google-GSON
		client.register(GsonMessageBodyHandler.class);
		// URL do web service
		String URL = "http://localhost:8080/Carros/rest/";
		// Cria a requisição com o "caminho"
		WebTarget target = client.target(URL).path("/carros/11");
		// Faz a requisição do tipo GET solicitando um JSON como resposta.
		Response response = target.request(MediaType.APPLICATION_JSON).get();
		// Status HTTP de retorno
		int status = response.getStatus();
		// L� um Carro (converte diretamente da string do JSON)
		Carro c = response.readEntity(Carro.class);
		assertEquals(200, status);
		assertEquals("Ferrari FF", c.getNome());
	}
	
	public void testDeleteCarroId() {
		// Cria o cliente da API
		ClientConfig clientConfig = new ClientConfig();
		Client client = ClientBuilder.newClient(clientConfig);
		client.register(HttpAuthenticationFeature.basic("livro", "livro123"));
		// Registra o parser com o Google-GSON
		client.register(GsonMessageBodyHandler.class);
		// URL do web service
		String URL = "http://localhost:8080/Carros/rest/";
		// Cria a requisição com o "caminho"
		WebTarget target = client.target(URL).path("/carros/12");
				
		// O teste de deletar s� funciona se o carro existir, ent�o vamos verificar antes.
		Response responseGet =  
				target.request(MediaType.APPLICATION_JSON)
				.get();
		if(responseGet.getStatus() != 200) {
			// N�o deixa prosseguir no teste se o carro n�o existe
			System.err.println("Carro para deletr n�o existe, abortando teste.");
			return;
		}
		
		// Faz a requisição do tipo GET solicitando um JSON como resposta.
		Response response =  
				target.request(MediaType.APPLICATION_JSON)
				.delete();
		// Valida se a requisição foi OK
		assertEquals(200, response.getStatus());
		// Lê a response do pacote domain
		br.com.livro.domain.Response s = response.readEntity(br.com.livro.domain.Response.class);
		assertEquals("OK", s.getStatus());
		assertEquals("Carro deletado com sucesso", s.getMsg());
	}
	
	public void testPostFormParams() {
		ClientConfig clientConfig = new ClientConfig();
		Client client = ClientBuilder.newClient(clientConfig);
		client.register(HttpAuthenticationFeature.basic("admin", "admin123"));
		client.register(GsonMessageBodyHandler.class);
		// Cria os par�metros do formul�rio
		String base64 = Base64.getEncoder().encodeToString("Ricardo Lecheta".getBytes());
		Form form = new Form();
		form.param("fileName", "nome2.xt");
		form.param("base64", base64);
		String URL = "http://localhost:8080/Carros/";
		// Faz a requisição do tipo POST com x-www-form-urlencoded
		WebTarget target = client.target(URL).path("/rest/carros/postFotoBase64");
		Entity<Form> entity = Entity.entity(form,MediaType.APPLICATION_FORM_URLENCODED_TYPE);
		Response response = target.request(MediaType.APPLICATION_JSON).post(entity);
		// OK
		assertEquals(200, response.getStatus());
		// Converte para ResponseWithURL
		ResponseWithURL r = response.readEntity(ResponseWithURL.class);
		assertEquals("OK", r.getStatus());
		assertEquals("Arquivo recebido com sucesso", r.getMsg());
	}

	public void testSalvarNovoCarro() {
		ClientConfig clientConfig = new ClientConfig();
		Client client = ClientBuilder.newClient(clientConfig);
		client.register(HttpAuthenticationFeature.basic("admin", "admin123"));
		client.register(GsonMessageBodyHandler.class);
		// Cria o objeto normalmente.
		Carro c = new Carro();
		c.setNome("Novo Carro");
		String URL = "http://localhost:8080/Carros/";
		WebTarget target = client.target(URL).path("/rest/carros");
		// Envia o objeto como JSON no corpo da requisição
		Entity<Carro> entity = Entity.entity(c, MediaType.APPLICATION_JSON);
		Response response = target.request(MediaType.APPLICATION_JSON).post(entity, Response.class);
		// Valida se a requisição foi OK
		assertEquals(200, response.getStatus());
		// Lê a response do pacote domain
		br.com.livro.domain.Response s = response.readEntity(br.com.livro.domain.Response.class);
		assertEquals("OK", s.getStatus());
		assertEquals("Carro salvo com sucesso", s.getMsg());
		
		// Depois de salvar o carro, vou buscá-lo pelo nome para excluir.
		target = client.target(URL).path("/rest/carros/nome/Novo Carro");
		response = target.request(MediaType.APPLICATION_JSON).get();
		assertEquals(200, response.getStatus());
		c = response.readEntity(Carro.class);
		assertEquals("Novo Carro", c.getNome());
		Long id = c.getId();

		// Deleta o carro que foi salvo no teste, para n�o deixar sujeira a base.
		target = client .target(URL).path("/rest/carros/"+id);
		response =  target.request(MediaType.APPLICATION_JSON).delete();
		assertEquals(200, response.getStatus());
	}


}
