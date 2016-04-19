import java.io.File;
import java.util.List;
import com.google.api.services.storage.model.Bucket;
import com.google.api.services.storage.model.StorageObject;

public class CloudStorageTest {
	

	public static void main(String[] args) throws Exception {
		CloudStorageUtil c = new CloudStorageUtil("Livro Lecheta");
		// Campo Email address criado no console.
		String accountId = Constants.ACCOUNT_ID;
		// Arquivo p12 baixado no console no momento de criar a chave.
		File p12File = new File(Constants.P12_FILE);
		// Conecta
		c.connect(accountId, p12File);
		// Consulta o bucket
		Bucket bucket = c.getBucket(Constants.BUCKET_NAME);
		System.out.println("name: " + bucket.getName());
		System.out.println("location: " + bucket.getLocation());
		System.out.println("timeCreated: " + bucket.getTimeCreated());
		System.out.println("owner: " + bucket.getOwner());
		// Lista os arquivos
		List<StorageObject> files = c.getBucketFiles(bucket);
		for (StorageObject object : files) {
			System.out.println("> " + object.getName() + " ("+ object.getSize() + " bytes)");
		}
	}
}
