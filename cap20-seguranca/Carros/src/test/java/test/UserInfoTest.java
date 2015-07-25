package test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import junit.framework.TestCase;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import br.com.livro.rest.GsonMessageBodyHandler;

public class UserInfoTest extends TestCase {
	String URL = "http://localhost:8080/Carros/rest/userInfo";
	// Faz login com "livro/livro123"
	public void testUserInfoLoginLivro() {
		// Cria o cliente da API
		ClientConfig clientConfig = new ClientConfig();
		Client client = ClientBuilder.newClient(clientConfig);
		client.register(HttpAuthenticationFeature.basic("livro", "livro123"));
		client.register(GsonMessageBodyHandler.class);
		WebTarget target = client.target(URL);
		String s = target.request().get(String.class);
		assertEquals("Você é um usuário: livro", s);
	}
	// Faz login com "admin/admin123"
	public void testUserInfoLoginAdmin() {
		// Cria o cliente da API
		ClientConfig clientConfig = new ClientConfig();
		Client client = ClientBuilder.newClient(clientConfig);
		client.register(HttpAuthenticationFeature.basic("admin", "admin123"));
		client.register(GsonMessageBodyHandler.class);
		WebTarget target = client.target(URL);
		String s = target.request().get(String.class);
		assertEquals("Você é um administrador: admin", s);
	}
	// Faz login com usuário que não existe.
	public void testUserInfoAcessoNegado() {
		// Cria o cliente da API
		ClientConfig clientConfig = new ClientConfig();
		Client client = ClientBuilder.newClient(clientConfig);
		client.register(HttpAuthenticationFeature.basic("xxx", "yyy"));
		client.register(GsonMessageBodyHandler.class);
		WebTarget target = client.target(URL);
		Response response = target.request().get();
		// Acesso não autorizado
		assertEquals(401, response.getStatus());
	}
}
