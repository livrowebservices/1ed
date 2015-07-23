package br.com.livro.util;

import java.io.IOException;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.stream.XMLStreamWriter;

import org.codehaus.jettison.mapped.MappedNamespaceConvention;
import org.codehaus.jettison.mapped.MappedXMLStreamWriter;

import br.com.livro.domain.Carro;
import br.com.livro.domain.ListaCarros;
import br.com.livro.domain.Response;


public class JAXBUtil {
	private static JAXBUtil instance;
	private static JAXBContext context;

	public static JAXBUtil getInstance() {
		return instance;
	}

	static {
		try {
			// Informa ao JAXB que é para gerar XML destas classes.
			context = JAXBContext.newInstance(ListaCarros.class, Carro.class, Response.class);
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	public static String toXML(Object object) throws IOException {
		try {
			StringWriter writer = new StringWriter();
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			m.marshal(object, writer);
			String xml = writer.toString();
			return xml;
		} catch (JAXBException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String toJSON(Object object) throws IOException {
		try {
			StringWriter writer = new StringWriter();
			Marshaller m = context.createMarshaller();
			MappedNamespaceConvention con = new MappedNamespaceConvention();
			XMLStreamWriter xmlStreamWriter = new MappedXMLStreamWriter(con, writer);
			m.marshal(object, xmlStreamWriter);
			String json = writer.toString();
			return json;
		} catch (JAXBException e) {
			e.printStackTrace();
			return null;
		}
	}

}
