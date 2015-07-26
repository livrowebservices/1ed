package br.com.livro.rest;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Application;

public class MyApplication extends Application {
	public static final String CONSUMER_KEY = "XXX";
	public static final String CONSUMER_SECRET = "YYY";

	@Override
	public Map<String, Object> getProperties() {
		Map<String, Object> properties = new HashMap<>();
		// Configura o pacote para fazer scan das classes com anotações REST.
		properties.put("jersey.config.server.provider.packages", "br.com.livro");
		return properties;
	}
}
