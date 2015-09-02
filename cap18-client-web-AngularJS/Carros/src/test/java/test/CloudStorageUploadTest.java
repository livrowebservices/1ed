package test;
import java.io.File;

import br.com.livro.util.CloudStorageUtil;

import com.google.api.services.storage.model.StorageObject;

public class CloudStorageUploadTest {
	private static final String BUCKET_NAME = "livrows";

	public static void main(String[] args) throws Exception {
		CloudStorageUtil c = new CloudStorageUtil("Livro Lecheta");
		// Campo Email address criado no console.
		String accountId = "862293491083-du0b3c04d34f2a2hdn78j2cohie12874@developer.gserviceaccount.com";
		String storageProjectId = "862293491083";
		// Arquivo p12 baixado no console no momento de criar a chave.
		File p12File = new File("Teste-2982d075601d.p12");
		// Conecta
		c.connect(accountId, p12File);
		// Upload
		System.out.println("Fazendo upload...");
		File file = new File("Ferrari_FF.png");
		String contentType = "image/png";
		
		StorageObject obj = c.upload(BUCKET_NAME, file, contentType,
				storageProjectId);
		System.out.println("Upload OK, objeto: " + obj.getName());
	}
}
