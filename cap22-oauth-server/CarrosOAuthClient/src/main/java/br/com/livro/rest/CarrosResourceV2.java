package br.com.livro.rest;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.oauth1.AccessToken;
import org.glassfish.jersey.client.oauth1.ConsumerCredentials;
import org.glassfish.jersey.client.oauth1.OAuth1ClientSupport;

/**
 * Este serviço utiliza o outro web service "/rest/carros" para demonstrar como utilizar OAuth
 * 
 * O /rest/carros é seguro por OAuth e faz o servidor
 * O /rest/carrosv2 é o cliente do OAuth
 * 
 * http://localhost:8080/CarrosOAuthClient/rest/carrosv2/
 * http://localhost:8080/CarrosOAuthClient/rest/carrosv2/userInfo
 *
 */
@Path("/carrosv2")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class CarrosResourceV2 {
	
	// URL do servidor do web service REST
	private static final String URL = "http://localhost:8080/Carros";

	@Context
	private HttpServletRequest req;
	
	@GET
	public Response getCarros() {
		Client client = getClient();
		Response r = Response.ok(client
						.target(URL).path("/rest/carros")
						.request(MediaType.APPLICATION_JSON)
						.get(String.class)).build();
		return r;
	}

	@GET
	@Path("/userInfo")
	public Response userInfo() {
		Client client = getClient();
		Response r = Response.ok(client
						.target(URL).path("/rest/userInfo")
						.request(MediaType.APPLICATION_JSON)
						.get(String.class)).build();
		return r;
	}

	private Client getClient() {
		AccessToken accessToken = (AccessToken) req.getSession().getAttribute("accessToken");
		String consumerKey = MyApplication.CONSUMER_KEY;
		String consumerSecret = MyApplication.CONSUMER_SECRET;
		ConsumerCredentials credentials = new ConsumerCredentials(consumerKey,consumerSecret);
		Feature feature = OAuth1ClientSupport.builder(credentials).feature().accessToken(accessToken).build();
		Client client = ClientBuilder.newClient().register(feature).register(GsonMessageBodyHandler.class);
		return client;
	}
}
