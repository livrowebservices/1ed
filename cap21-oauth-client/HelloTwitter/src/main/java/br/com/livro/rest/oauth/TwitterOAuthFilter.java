package br.com.livro.rest.oauth;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.glassfish.jersey.client.oauth1.AccessToken;
import org.glassfish.jersey.client.oauth1.ConsumerCredentials;
import org.glassfish.jersey.client.oauth1.OAuth1AuthorizationFlow;
import org.glassfish.jersey.client.oauth1.OAuth1Builder.FlowBuilder;
import org.glassfish.jersey.client.oauth1.OAuth1ClientSupport;

import br.com.livro.util.ServletUtil;

@WebFilter("/rest/twitterv2/*")
public class TwitterOAuthFilter implements Filter {
	public static String CONSUMER_KEY = "yKffgFE3MjleOzXjTH7YA5ntW";
	public static String CONSUMER_SECRET = "LrcTj8hPw1CBM9LxwIL4Bx831iVhzT69HUEqiAhXcpizY1JDTU";

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		AccessToken accessToken = (AccessToken) req.getSession().getAttribute("accessToken");
		if (accessToken == null) {
			String oauth_verifier = req.getParameter("oauth_verifier");
			String oauth_token = req.getParameter("oauth_token");
			if (oauth_verifier != null && oauth_token != null) {
				// (2) Voltou do Twitter, verifica o código
				verify(req, oauth_verifier);
			} else {
				// (1) Precisa redirecionar para o Twitter
				auth(req, resp);
				return;
			}
		}
		// (3) Continua a requisição
		chain.doFilter(req, resp);
	}

	// Inicia o fluxo de autorização (Redireciona a URL para o Twitter)
	private void auth(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// limpa o flow
		req.getSession().removeAttribute("authFlow");
		String url = ServletUtil.getRequestURL(req);
		OAuth1AuthorizationFlow authFlow = getAuthorizationFlow(req, url);
		String authorizationUri = authFlow.start();
		resp.sendRedirect(authorizationUri);
	}

	// Valida o código verificador retornado pelo Twitter (depois do usuário
	// autorizar)
	private void verify(HttpServletRequest req, String oauth_verifier) {
		AccessToken accessToken;
		OAuth1AuthorizationFlow authFlow = (OAuth1AuthorizationFlow) req
				.getSession().getAttribute("authFlow");
		accessToken = authFlow.finish(oauth_verifier);
		req.getSession().setAttribute("accessToken", accessToken);
		// limpa o flow
		req.getSession().removeAttribute("authFlow");
	}

	// Cria o fluxo de autorização
	public OAuth1AuthorizationFlow getAuthorizationFlow(
			HttpServletRequest request, String callbackUri) {
		ConsumerCredentials consumerCredentials = new ConsumerCredentials(
				CONSUMER_KEY, CONSUMER_SECRET);
		FlowBuilder builder = OAuth1ClientSupport.builder(consumerCredentials)
				.authorizationFlow(
						"https://api.twitter.com/oauth/request_token",
						"https://api.twitter.com/oauth/access_token",
						"https://api.twitter.com/oauth/authorize");
		if (callbackUri != null) {
			builder.callbackUri(callbackUri);
		}
		OAuth1AuthorizationFlow authFlow = builder.build();
		request.getSession().setAttribute("authFlow", authFlow);
		return authFlow;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// Método chamado na inicialização do servidor.
	}

	@Override
	public void destroy() {
		// Método chamado ao parar o servidor.
	}
}
