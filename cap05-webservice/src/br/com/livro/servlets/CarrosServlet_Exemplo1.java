/*package br.com.livro.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.livro.domain.Carro;
import br.com.livro.domain.CarroService;
import br.com.livro.domain.ListaCarros;
import br.com.livro.util.ServletUtil;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@WebServlet("/carros/*")
public class CarrosServlet_Exemplo1 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		List<Carro> carros = CarroService.getCarros();
		ListaCarros lista = new ListaCarros();
		lista.setCarros(carros);
		// Gera o XML/JSON com JAXB
		// String xml = JAXBUtil.toJSON(lista);

		// Gera o JSON
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(lista);

		// Escreve o XML na response do servlet com application/xml
		ServletUtil.writeJSON(resp, json);

	}
}
*/