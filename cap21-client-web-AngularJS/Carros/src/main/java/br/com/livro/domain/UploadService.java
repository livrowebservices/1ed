package br.com.livro.domain;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import br.com.livro.util.CloudStorageUtil;

import com.google.api.services.storage.model.StorageObject;
import com.google.common.io.Files;

@Component
public class UploadService {
	private static final String PROJECT_ID = "1881627642";
	private static final String ACCOUNT_ID = "1881627642-iiat9b1c53jgtdpptqjnkb193gp12lk5@developer.gserviceaccount.com";
	private static final String APP_NAME = "Livro Lecheta";
	private static final String BUCKET_NAME = "livrolecheta";

	public String upload(String fileName, InputStream in) throws Exception {
		// 1) Salva o arquivo na pasta temporária da JVM
		File file = saveToTmpDir(fileName, in);
		// 2) Faz upload para o Cloud Storage
		String url = uploadToCloudStorage(file);
		// 3) Retorna a URL do arquivo
		return url;
	}

	private String uploadToCloudStorage(File file) throws Exception {
		// Arquivo .p12 chave privada
		String s = System.getProperty("p12File");
		if (s == null) {
			throw new IOException("Erro no servidor.");
		}
		File p12File = new File(s);
		if (!p12File.exists()) {
			throw new IOException("Erro no servidor.");
		}
		// Conecta no Cloud Storage
		CloudStorageUtil c = new CloudStorageUtil(APP_NAME);
		c.connect(ACCOUNT_ID, p12File);
		// Upload
		String fileName = file.getName();
		String contentType = getContentType(fileName);
		String storageProjectId = PROJECT_ID;
		StorageObject obj = c.upload(BUCKET_NAME, file, contentType,
				storageProjectId);
		if (obj == null) {
			throw new IOException("Erro ao fazer upload.");
		}
		// Retorna a URL pública
		String url = String.format("https://storage.googleapis.com/%s/%s",
				BUCKET_NAME, obj.getName());
		return url;
	}

	// Retorna o content-type para a extensão fornecida
	private String getContentType(String fileName) {
		String ext = Files.getFileExtension(fileName);
		if ("png".equals(ext)) {
			return "image/png";
		} else if ("jpg".equals(ext) || "jpeg".equals(ext)) {
			return "image/jpg";
		} else if ("gif".equals(ext)) {
			return "image/gif";
		}
		return "text/plain";
	}

	private File saveToTmpDir(String fileName, InputStream in)
			throws FileNotFoundException, IOException {
		if (fileName == null || in == null) {
			throw new IllegalArgumentException("Parâmetros inválidos");
		}
		// Pasta temporária da JVM
		File tmpDir = new File(System.getProperty("java.io.tmpdir"), "carros");
		if (!tmpDir.exists()) {
			// Cria a pasta carros se não existe
			tmpDir.mkdir();
		}
		// Cria o arquivo
		File file = new File(tmpDir, fileName);
		// Abre a OutputStream para escrever no arquivo
		FileOutputStream out = new FileOutputStream(file);
		// Escreve os dados no arquivo
		IOUtils.copy(in, out);
		IOUtils.closeQuietly(out);
		return file;
	}
}
