package br.com.livroandroid;

import android.test.AndroidTestCase;
import android.util.Base64;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import br.com.livrowebservices.carros.R;
import br.com.livrowebservices.carros.domain.CarroService;
import br.com.livrowebservices.carros.domain.ResponseWithURL;
import livroandroid.lib.utils.IOUtils;

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
