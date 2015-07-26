package br.com.livro.rest;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;

@Path("/hello1")
public class Hello1Resource {
	@GET
	public String get() {
		return "HTTP GET";
	}

	@POST
	public String post() {
		return "HTTP POST";
	}

	@PUT
	public String put() {
		return "HTTP PUT";
	}

	@DELETE
	public String delete() {
		return "HTTP DELETE";
	}
}
