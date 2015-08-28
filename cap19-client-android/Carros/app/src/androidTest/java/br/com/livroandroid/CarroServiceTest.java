package br.com.livroandroid;

import android.test.AndroidTestCase;
import android.util.Log;

import java.io.IOException;

import br.com.livrowebservices.carros.domain.CarroService;
import br.com.livrowebservices.carros.rest.ResponseWithURL;

/**
 * Created by ricardo on 30/07/15.
 */
public class CarroServiceTest extends AndroidTestCase {

    public void testUploadFotoTest() throws IOException {
        String base64 = "xxx";
        //Log.d("test","base64 b > " + base64);

        ResponseWithURL response = CarroService.postFotoBase64(getContext(), "donald.png",base64);
        Log.d("test","test > " + response);
    }
}
