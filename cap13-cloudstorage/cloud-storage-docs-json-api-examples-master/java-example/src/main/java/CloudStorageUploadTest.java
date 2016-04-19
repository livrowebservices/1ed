import java.io.File;
import com.google.api.services.storage.model.StorageObject;

public class CloudStorageUploadTest {

	public static void main(String[] args) throws Exception {
		CloudStorageUtil c = new CloudStorageUtil("Livro Lecheta");
		
		File p12File = new File(Constants.P12_FILE);
		
		// Conecta
		c.connect(Constants.ACCOUNT_ID, p12File);
		// Upload
		System.out.println("Fazendo upload...");
		File file = new File("Ferrari_FF.png");
		String contentType = "image/png";
		String storageProjectId = "1881627642";
		StorageObject obj = c.upload(Constants.BUCKET_NAME, file, contentType,
				storageProjectId);
		System.out.println("Upload OK, objeto: " + obj.getName());
	}
}
