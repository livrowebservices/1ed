package br.com.livro.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/twitter")
@Produces(MediaType.TEXT_PLAIN+";charset=utf-8")
public class TwitterResource {
	@GET
	public String get() {
		return "Olá Twitter";
	}
}
