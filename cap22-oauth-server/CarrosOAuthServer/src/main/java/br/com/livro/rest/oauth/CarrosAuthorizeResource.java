package br.com.livro.rest.oauth;

import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.glassfish.jersey.server.oauth1.DefaultOAuth1Provider;
import org.glassfish.jersey.server.oauth1.DefaultOAuth1Provider.Token;

@Path("/authorize")
@Consumes(APPLICATION_FORM_URLENCODED)
public class CarrosAuthorizeResource {
	@Context
	// Provedor do OAuth registrado no MyApplication
	private DefaultOAuth1Provider provider;

	@GET
	public Response get(@QueryParam("oauth_token") String oauth_token)
			throws URISyntaxException {
		if (oauth_token == null) {
			throw new WebApplicationException(Status.BAD_REQUEST);
		}
		java.net.URI uri = new java.net.URI(String.format(
				"../authorize.jsp?oauth_token=%s", oauth_token));
		return Response.temporaryRedirect(uri).build();
	}

	@POST
	public Response post(@FormParam("oauth_token") String token,
			final @FormParam("nome") String nome,
			@FormParam("login") String login, @FormParam("senha") String senha)
			throws URISyntaxException {
		// Token de requisição
		final Token requestToken = provider.getRequestToken(token);
		// Perfil do usuário
		Set<String> roles = new HashSet<>();
		if ("livro".equals(login) && "livro123".equals(senha)) {
			roles.add("user");
		} else if ("admin".equals(login) && "admin123".equals(senha)) {
			roles.add("admin");
		} else {
			throw new NotAuthorizedException("Login incorreto");
		}
		Principal userPrincipal = new Principal() {
			@Override
			public String getName() {
				// Para simplificar utiliza o nome digitado no formulário
				return nome;
			}
		};
		// Cria o código verificador
		String verifier = provider.authorizeToken(requestToken, userPrincipal,
				roles);
		// URL de retorno (callback) com parâmetros
		String callbackUrl = requestToken.getCallbackUrl();
		callbackUrl += String.format("?oauth_verifier=%s&oauth_token=%s",
				verifier, token);
		// Redirect
		URI uri = new URI(callbackUrl.toString());
		return Response.status(Status.FOUND).location(uri).build();
	}
}
