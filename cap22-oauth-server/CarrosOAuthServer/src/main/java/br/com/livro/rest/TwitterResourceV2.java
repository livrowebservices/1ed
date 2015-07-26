package br.com.livro.rest;

import javax.servlet.http.HttpServletRequest;
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

import br.com.livro.rest.oauth.TwitterOAuthFilter;

@Path("/twitterv2")
public class TwitterResourceV2 {
	@Context
	private HttpServletRequest request;

	@GET
	public String get() {
		return "Hey Twitter";
	}

	@GET
	@Path("/timeline")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public String timeline() {
		// Cria o cliente da API REST
		Client client = getClient();
		// Utiliza a API REST para ler a timeline do Twitter
		Response response = client
				.target("https://api.twitter.com/1.1/statuses/user_timeline.json")
				.request().get();
		// Lê uma lista de TwitterStatus
		String json = response.readEntity(String.class);
		// Retorna o json
		return json;
	}

	// Cria o objeto Client da API REST do Jersey
	private Client getClient() {
		// Recupera o Token da sessão
		AccessToken accessToken = (AccessToken) request.getSession()
				.getAttribute("accessToken");
		// Utiliza as credenciais de acesso OAuth do provedor (Twitter)
		ConsumerCredentials credentials = new ConsumerCredentials(
				TwitterOAuthFilter.CONSUMER_KEY,
				TwitterOAuthFilter.CONSUMER_SECRET);
		Feature feature = OAuth1ClientSupport.builder(credentials).feature()
				.accessToken(accessToken).build();
		Client client = ClientBuilder.newClient();
		client.register(feature);
		// Habilita o Google-GSON
		client.register(GsonMessageBodyHandler.class);
		return client;
	}
}
