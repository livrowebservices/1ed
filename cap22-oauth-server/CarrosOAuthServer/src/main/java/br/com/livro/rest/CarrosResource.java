package br.com.livro.rest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;
import java.util.Set;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.livro.domain.Carro;
import br.com.livro.domain.CarroService;
import br.com.livro.domain.Response;
import br.com.livro.domain.ResponseWithURL;
import br.com.livro.domain.UploadService;

@Path("/carros")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Component
@RolesAllowed({"user","admin"})
public class CarrosResource {
	
	@Context
	HttpServletRequest request;
	
	@Autowired
	private CarroService carroService;
	
	@Autowired
	private UploadService uploadService;


	@GET
	public List<Carro> get() {
		List<Carro> carros = carroService.getCarros();
		return carros;
	}

	@GET
	@Path("{id}")
	public Carro get(@PathParam("id") long id) {
		Carro c = carroService.getCarro(id);
		return c;
	}

	@GET
	@Path("/tipo/{tipo}")
	public List<Carro> getByTipo(@PathParam("tipo") String tipo) {
		List<Carro> carros = carroService.findByTipo(tipo);
		return carros;
	}

	@GET
	@Path("/nome/{nome}")
	public List<Carro> getByNome(@PathParam("nome") String nome) {
		List<Carro> carros = carroService.findByName(nome);
		return carros;
	}

	@DELETE
	@Path("{id}")
	@RolesAllowed("admin")
	public Response delete(@PathParam("id") long id) {
		carroService.delete(id);
		return Response.Ok("Carro deletado com sucesso");
	}

	@POST
	@RolesAllowed("admin")
	public Response post(Carro c) {
		carroService.save(c);
		return Response.Ok("Carro salvo com sucesso");
	}

	@PUT
	@RolesAllowed("admin")
	public Response put(Carro c) {
		carroService.save(c);
		return Response.Ok("Carro atualizado com sucesso");
	}
	
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public ResponseWithURL postFoto(final FormDataMultiPart multiPart) {
		Set<String> keys = multiPart.getFields().keySet();
		for (String key : keys) {
			// Obtem a InputStream para ler o arquivo
			FormDataBodyPart field = multiPart.getField(key);
			InputStream in = field.getValueAs(InputStream.class);
			try {
				String fileName = field.getFormDataContentDisposition().getFileName();
				String url = uploadService.upload(fileName, in);
				return ResponseWithURL.Ok("Arquivo recebido com sucesso", url);
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseWithURL.Error("Erro ao enviar o arquivo.");
			}
		}
		return ResponseWithURL.Error("Requisi��o inv�lida.");
	}


	@POST
	@Path("/toBase64")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public String toBase64(final FormDataMultiPart multiPart) {
		if(multiPart != null) {
			Set<String> keys = multiPart.getFields().keySet();
			for (String key : keys) {
				try {
					// Obtem a InputStream para ler o arquivo
					FormDataBodyPart field = multiPart.getField(key);
					InputStream in = field.getValueAs(InputStream.class);
					byte[] bytes = IOUtils.toByteArray(in);
					String base64 = Base64.getEncoder().encodeToString(bytes);
					return base64;
				} catch (Exception e) {
					e.printStackTrace();
					return "Erro: " + e.getMessage();
				}
			}
		}
		return "Requisi��o inv�lida.";
	}

	@POST
	@Path("/postFotoBase64")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public ResponseWithURL postFotoBase64(@FormParam("fileName") String fileName, @FormParam("base64") String base64) {
		if (fileName != null && base64 != null) {
			try {
				// Decode: Converte o Base64 para array de bytes
				byte[] bytes = Base64.getDecoder().decode(base64);
				InputStream in = new ByteArrayInputStream(bytes);
				// Faz o upload (salva o arquivo em uma pasta)
				String url = uploadService.upload(fileName, in);
				// OK
				return ResponseWithURL.Ok("Arquivo recebido com sucesso",url);
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseWithURL.Error("Erro ao enviar o arquivo.");
			}
		}
		return ResponseWithURL.Error("Requisição inv�lida.");
	}

	@GET
	@Path("/toBase64/{texto}")
	@Produces(MediaType.TEXT_PLAIN)
	public String toBase64(@PathParam("texto") String texto) {
		String base64 = Base64.getEncoder().encodeToString(texto.getBytes());
		return base64;
	}

	@GET
	@Path("/toBase64")
	@Produces(MediaType.TEXT_PLAIN)
	public String toBase64ViaQueryParam(@QueryParam("texto") String texto) {
		String base64 = Base64.getEncoder().encodeToString(texto.getBytes());
		return base64;
	}


}
