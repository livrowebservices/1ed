package br.com.livrowebservices.carros.utils;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import livroandroid.lib.utils.ImageResizeUtils;
import livroandroid.lib.utils.SDCardUtils;

/**
 * Created by ricardo on 26/08/15.
 */
public class CameraUtil {

    private static final String TAG = "camera";
    private File file;

    public void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            // Se girou a tela recupera o estado
            file = (File) savedInstanceState.getSerializable("file");
        }
    }

    public Intent open(String fileName) {
        // Cria o o arquivo no sdcard
        file = SDCardUtils.getPublicFile(fileName);
        Log.d(TAG, "onClickCamera: file: " + file);
        // Chama a intent informando o arquivo para salvar a foto
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        return i;
    }

    public File getFile() {
        return file;
    }

    public void onSaveInstanceState(Bundle outState) {
        if (file != null) {
            outState.putSerializable("file", file);
        }
    }

    public Bitmap getBitmap(int w, int h) {
        if (file != null && file.exists()) {
            Log.d(TAG, file.getAbsolutePath());

            // Resize
            Uri uri = Uri.fromFile(file);
            Bitmap bitmap = ImageResizeUtils.getResizedImage(uri, w, h, false);
            Log.d(TAG, "w/h: " + bitmap.getWidth() + "/" + bitmap.getHeight());

            return bitmap;
        }
        return null;
    }

    // Salva o bitmap alterado no arquivo
    public void save(Bitmap bitmap) {
        if (file != null) {
            OutputStream out = null;
            try {
                out = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.close();

                Log.d("foto", "file compress ok: " + file.getAbsolutePath());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
