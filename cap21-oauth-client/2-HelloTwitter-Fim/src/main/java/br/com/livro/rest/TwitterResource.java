package br.com.livro.rest;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.jersey.client.oauth1.AccessToken;
import org.glassfish.jersey.client.oauth1.ConsumerCredentials;
import org.glassfish.jersey.client.oauth1.OAuth1AuthorizationFlow;
import org.glassfish.jersey.client.oauth1.OAuth1Builder.FlowBuilder;
import org.glassfish.jersey.client.oauth1.OAuth1ClientSupport;
import twitter.TwitterStatus;

@Path("/twitter")
public class TwitterResource {
	// Consumer Key e Consumer Secret criados no Provedor do OAuth (Twitter)
	String CONSUMER_KEY = "yKffgFE3MjleOzXjTH7YA5ntW";
	String CONSUMER_SECRET = "LrcTj8hPw1CBM9LxwIL4Bx831iVhzT69HUEqiAhXcpizY1JDTU";

	// URL que vai receber o código verificador do OAuth
	String CALLBACK_URI = "http://localhost:8080/HelloTwitter/rest/twitter/verify";

	@Context
	private HttpServletRequest request;

	@GET
	@Produces(MediaType.TEXT_PLAIN+";charset=utf-8")
	public String get() {
		return "Olá Twitter";
	}

	// Cria a classe por controlar o Fluxo de autorização
	private OAuth1AuthorizationFlow getAuthorizationFlow(String callbackUri) {
		// Tenta obter a classe da sessão do browser
		OAuth1AuthorizationFlow authFlow = (OAuth1AuthorizationFlow) request.getSession().getAttribute("authFlow");
		if (authFlow != null) {
			// Já foi criada, apenas retorna
			return authFlow;
		}
		// Cria as credenciais de acessso
		ConsumerCredentials consumerCredentials = new ConsumerCredentials(
				CONSUMER_KEY, CONSUMER_SECRET);
		// Cria o OAuth1AuthorizationFlow
		FlowBuilder builder = OAuth1ClientSupport.builder(consumerCredentials)
				.authorizationFlow(
						"https://api.twitter.com/oauth/request_token",
						"https://api.twitter.com/oauth/access_token",
						"https://api.twitter.com/oauth/authorize");
		// Configura a URL de callback se necessário
		if (callbackUri != null) {
			builder.callbackUri(callbackUri);
		}
		authFlow = builder.build();
		// Armazena este objeto na sessão do browser
		request.getSession().setAttribute("authFlow", authFlow);
		return authFlow;
	}

	@GET
	@Path("/auth")
	public String auth() throws IOException {
		// limpa o flow
		request.getSession().removeAttribute("authFlow");
		// Cria o fluxo de autorização
		OAuth1AuthorizationFlow authFlow = getAuthorizationFlow(null);
		// O método start obtem o Token
		String authorizationUri = authFlow.start();
		// Retorna a URL do provedor do serviço (Twitter)
		return authorizationUri;
	}

	@GET
	@Path("/authRedirect")
	public Response authRedirect() {
		// limpa o flow
		request.getSession().removeAttribute("authFlow");
		OAuth1AuthorizationFlow authFlow = getAuthorizationFlow(CALLBACK_URI);
		String authorizationUri = authFlow.start();
		// Idem método auth() mas este redireciona automaticamente
		Response redirect = Response.seeOther(
				UriBuilder.fromUri(authorizationUri).build()).build();
		return redirect;
	}

	@GET
	@Path("/verify/{verifier}")
	public String verify(@PathParam("verifier") String verifier) {
		// Recupera da sessão o objeto que controla o fluxo de autorização
		OAuth1AuthorizationFlow authFlow = getAuthorizationFlow(null);
		// Recebe o código verificador e valida a requisição
		AccessToken accessToken = authFlow.finish(verifier);
		// Obtem o Token e Token Secret
		String token = accessToken.getToken();
		String tokenSecret = accessToken.getAccessTokenSecret();
		// Remove o objeto que controla o fluxo de autenticação da sessão
		request.getSession().removeAttribute("authFlow");
		// Retorna o Token em formato de texto para ser lido por um programa
		return String.format("token=%s&tokenSecret=%s", token, tokenSecret);
	}

	@GET
	@Path("/verify")
	public String verify(@QueryParam("oauth_token") String oauth_token,
			@QueryParam("oauth_verifier") String oauth_verifier) {
		// Verifica se o código verificador de retorno do provedor (Twitter)
		// está correto
		String s = verify(oauth_verifier);
		// Retorna o Token.
		return s;
	}

	@GET
	@Path("/timeline")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public String timeline(@QueryParam("token") String token,
			@QueryParam("tokenSecret") String tokenSecret) {
		// Demonstra como criar um Token informando os códigos manualmente.
		AccessToken accessToken = new AccessToken(token, tokenSecret);
		Client client = getClient(accessToken);
		if (client == null) {
			return "not authorized";
		}
		// Utiliza a API REST para ler a timeline do Twitter
		Response response = client
				.target("https://api.twitter.com/1.1/statuses/user_timeline.json")
				.request().get();
		// Lê uma lista de TwitterStatus em formato String
		String json = response.readEntity(String.class);
		// Retorna o json
		return json;
	}

	@GET
	@Path("/ultimoTweet")
	@Produces(MediaType.TEXT_PLAIN + ";charset=utf-8")
	public String ultimoTweet(@QueryParam("token") String token,
			@QueryParam("tokenSecret") String tokenSecret) {
		// Demonstra como criar um Token informando os códigos manualmente.
		AccessToken accessToken = new AccessToken(token, tokenSecret);
		Client client = getClient(accessToken);
		if (client == null) {
			return "not authorized";
		}
		// Utiliza a API REST para ler a timeline do Twitter
		Response response = client
				.target("https://api.twitter.com/1.1/statuses/user_timeline.json")
				.request().get();
		// Lê uma lista de TwitterStatus
		final List<TwitterStatus> list = response
				.readEntity(new GenericType<List<TwitterStatus>>() {
				});
		// Retorna o primeiro tweet da lista
		TwitterStatus s = list.get(0);
		return s.getUser().getName() + " >> " + s.getText();
	}

	// Cria o objeto Client da API REST do Jersey
	private Client getClient(AccessToken accessToken) {
		// Utiliza as credenciais de acesso OAuth do provedor (Twitter)
		ConsumerCredentials credentials = new ConsumerCredentials(CONSUMER_KEY,
				CONSUMER_SECRET);
		Feature feature = OAuth1ClientSupport.builder(credentials).feature()
				.accessToken(accessToken).build();
		Client client = ClientBuilder.newClient();
		client.register(feature);
		// Habilita o Google-GSON
		client.register(GsonMessageBodyHandler.class);
		return client;
	}
}
