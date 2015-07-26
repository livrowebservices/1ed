package br.com.livro.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/twitter")
public class TwitterResource {
	@GET
	public String get() {
		return "Olá Twitter";
	}
}
