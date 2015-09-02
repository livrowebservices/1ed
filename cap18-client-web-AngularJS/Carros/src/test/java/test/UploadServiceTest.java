package test;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.FileUtils;

import junit.framework.TestCase;
import br.com.livro.domain.Carro;
import br.com.livro.domain.CarroService;
import br.com.livro.domain.UploadService;

public class UploadServiceTest extends TestCase {
	private UploadService uploadService;
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		// Cria o "bean" pelo Spring.
		uploadService = (UploadService) SpringUtil.getInstance().getBean(UploadService.class);
		
		System.setProperty("p12File","R:/git/livrows/capitulos/cap18-client-web-AngularJS/Carros/Teste-2982d075601d.p12");
	}


	public void testUpload() throws Exception {
		InputStream in = FileUtils.openInputStream(new File("donald.jpg"));
		String s = uploadService.upload("opa.jpg", in);
		System.out.println("OK: " + s);
	}


}
