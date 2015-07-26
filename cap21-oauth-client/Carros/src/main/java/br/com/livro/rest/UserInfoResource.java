package br.com.livro.rest;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

@Path("userInfo")
@Produces(MediaType.TEXT_PLAIN + ";charset=utf-8")
@PermitAll
public class UserInfoResource {
	@Context
	SecurityContext securityContext;

	@GET
	public String userInfo() {
		String name = securityContext.getUserPrincipal().getName();
		if (securityContext.isUserInRole("admin")) {
			return "Você é um administrador: " + name;
		}
		if (securityContext.isUserInRole("user")) {
			return "Você é um usuário: " + name;
		}
		return "Nenhum dos dois";
	}
	
	@GET
	@Path("/somenteUser")
	@RolesAllowed("user")
	public String somenteUser() {
		return "Parabéns você é um usuário";
	}
	@GET
	@Path("/somenteAdmin")
	@RolesAllowed("admin")
	public String somenteAdmin() {
		return "Parabéns você é um administrador";
	}
	@GET
	@Path("/userOuAdmin")
	@RolesAllowed({"user","admin"})
	public String userOuAdmin () {
		return "Parabéns você é um usuário ou administrador";
	}
}
