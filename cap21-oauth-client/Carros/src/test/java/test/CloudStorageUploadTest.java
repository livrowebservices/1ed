package test;
import java.io.File;

import br.com.livro.util.CloudStorageUtil;

import com.google.api.services.storage.model.StorageObject;

public class CloudStorageUploadTest {
	private static final String BUCKET_NAME = "livrolecheta";

	public static void main(String[] args) throws Exception {
		CloudStorageUtil c = new CloudStorageUtil("Livro Lecheta");
		// Campo Email address criado no console.
		String accountId = "1881627642-iiat9b1c53jgtdpptqjnkb193gp12lk5@developer.gserviceaccount.com";
		// Arquivo p12 baixado no console no momento de criar a chave.
		File p12File = new File("Teste-b8c965c676bc.p12");
		// Conecta
		c.connect(accountId, p12File);
		// Upload
		System.out.println("Fazendo upload...");
		File file = new File("Ferrari_FF.png");
		String contentType = "image/png";
		String storageProjectId = "1881627642";
		StorageObject obj = c.upload(BUCKET_NAME, file, contentType,
				storageProjectId);
		System.out.println("Upload OK, objeto: " + obj.getName());
	}
}
