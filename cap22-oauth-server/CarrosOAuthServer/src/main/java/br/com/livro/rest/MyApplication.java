package br.com.livro.rest;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.ws.rs.core.Application;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import javax.ws.rs.core.MultivaluedMap;
import org.glassfish.jersey.internal.util.collection.MultivaluedStringMap;
import org.glassfish.jersey.server.oauth1.DefaultOAuth1Provider;
import org.glassfish.jersey.server.oauth1.OAuth1ServerFeature;
import org.glassfish.jersey.server.oauth1.OAuth1ServerProperties;

public class MyApplication extends Application {
	public static final String APP_OWNER = "Livro Lecheta";
	public static final String CONSUMER_KEY = "XXX";
	public static final String CONSUMER_SECRET = "YYY";

	@Override
	public Set<Object> getSingletons() {
		Set<Object> singletons = new HashSet<>();
		// Suporte ao File Upload
		singletons.add(new MultiPartFeature());
		// OAuth Server
		DefaultOAuth1Provider oauthProvider = new DefaultOAuth1Provider();
		MultivaluedMap<String, String> attributes = new MultivaluedStringMap();
		oauthProvider.registerConsumer(APP_OWNER, CONSUMER_KEY,
				CONSUMER_SECRET, attributes);
		singletons.add(new OAuth1ServerFeature(oauthProvider));
		return singletons;
	}

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> s = new HashSet<Class<?>>();
		// Segurança
		s.add(RolesAllowedDynamicFeature.class);
		return s;
	}

	@Override
	public Map<String, Object> getProperties() {
		Map<String, Object> properties = new HashMap<>();
		// Configura o pacote para fazer scan das classes com anotações REST.
		properties
				.put("jersey.config.server.provider.packages", "br.com.livro");
		// Ativa as URLs /requestToken e /accessToken do Jersey.
		properties.put(OAuth1ServerProperties.ENABLE_TOKEN_RESOURCES,
				Boolean.TRUE);
		return properties;
	}
}
