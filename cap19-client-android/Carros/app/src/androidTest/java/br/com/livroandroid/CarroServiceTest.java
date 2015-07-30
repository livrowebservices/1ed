package br.com.livroandroid;

import android.test.AndroidTestCase;
import android.util.Log;

import java.io.File;
import java.io.IOException;

import br.com.livrowebservices.carros.domain.CarroService;
import br.com.livrowebservices.carros.domain.ResponseWithURL;

/**
 * Created by ricardo on 30/07/15.
 */
public class CarroServiceTest extends AndroidTestCase {

    public void testUploadFotoTest() throws IOException {
        ResponseWithURL response = CarroService.postFotoBase64(getContext(), new File("testcase.png"));
        Log.d("test","test > " + response);
    }
}
