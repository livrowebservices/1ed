//[START all]
/*
 * Copyright (c) 2014 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.storage.Storage;
import com.google.api.services.storage.StorageScopes;
import com.google.api.services.storage.model.Bucket;
import com.google.api.services.storage.model.StorageObject;

/**
 * Main class for the Cloud Storage API command line sample. Demonstrates how to
 * make an authenticated API call using OAuth 2 helper classes.
 */
public class StorageSample2 {

	/**
	 * Be sure to specify the name of your application. If the application name
	 * is {@code null} or blank, the application will log a warning. Suggested
	 * format is "MyCompany-ProductName/1.0". If you are running the sample on a
	 * machine where you have access to a browser, set AUTH_LOCAL_WEBSERVER to
	 * true.
	 */
	private static final String APPLICATION_NAME = "Livro do Lecheta";
	private static final String BUCKET_NAME = Constants.BUCKET_NAME;

	/** Global instance of the JSON factory. */
	private static final JsonFactory JSON_FACTORY = JacksonFactory
			.getDefaultInstance();

	/** Global instance of the HTTP transport. */
	private static HttpTransport httpTransport;

	private static Storage client;

	/** Authorizes the installed application to access user's protected data. */
	private static Credential authorize() throws Exception {
		Set<String> scopes = new HashSet<String>();
		scopes.add(StorageScopes.DEVSTORAGE_FULL_CONTROL);
		scopes.add(StorageScopes.DEVSTORAGE_READ_ONLY);
		scopes.add(StorageScopes.DEVSTORAGE_READ_WRITE);
		// Campo Email address criado no console.
		String accountId = Constants.ACCOUNT_ID;
		// Arquivo p12 baixado no console no momento de criar a chave.
		File p12File = new File(Constants.P12_FILE);
		// Autoriza a aplicação
		JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
		HttpTransport httpTransport = GoogleNetHttpTransport
				.newTrustedTransport();
		GoogleCredential credential = new GoogleCredential.Builder()
				.setTransport(httpTransport).setJsonFactory(JSON_FACTORY)
				.setServiceAccountId(accountId)
				.setServiceAccountPrivateKeyFromP12File(p12File)
				.setServiceAccountScopes(scopes).build();
		return credential;

	}

	public static void main(String[] args) {
		try {
			// Initialize the transport.
			httpTransport = GoogleNetHttpTransport.newTrustedTransport();

			// Authorization.
			Credential credential = authorize();

			// Set up global Storage instance.
			client = new Storage.Builder(httpTransport, JSON_FACTORY,
					credential).setApplicationName(APPLICATION_NAME).build();

			// Get metadata about the specified bucket.
			Storage.Buckets.Get getBucket = client.buckets().get(BUCKET_NAME);
			getBucket.setProjection("full");
			Bucket bucket = getBucket.execute();
			System.out.println("name: " + BUCKET_NAME);
			System.out.println("location: " + bucket.getLocation());
			System.out.println("timeCreated: " + bucket.getTimeCreated());
			System.out.println("owner: " + bucket.getOwner());

			// List the contents of the bucket.
			Storage.Objects.List listObjects = client.objects().list(
					BUCKET_NAME);
			com.google.api.services.storage.model.Objects objects;
			do {
				objects = listObjects.execute();
				List<StorageObject> items = objects.getItems();
				if (null == items) {
					System.out
							.println("There were no objects in the given bucket; try adding some and re-running.");
					break;
				}
				for (StorageObject object : items) {
					System.out.println(object.getName() + " ("
							+ object.getSize() + " bytes)");
				}
				listObjects.setPageToken(objects.getNextPageToken());
			} while (null != objects.getNextPageToken());

		} catch (IOException e) {
			System.err.println(e.getMessage());
		} catch (Throwable t) {
			t.printStackTrace();
		}
		System.exit(1);
	}
}
// [END all]
