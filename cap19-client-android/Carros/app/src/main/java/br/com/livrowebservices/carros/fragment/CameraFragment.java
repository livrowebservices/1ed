package br.com.livrowebservices.carros.fragment;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

import br.com.livrowebservices.carros.R;
import br.com.livrowebservices.carros.utils.ImageUtils;
import livroandroid.lib.utils.ImageResizeUtils;
import livroandroid.lib.utils.SDCardUtils;

/**
 * Fragment para encapsular a lógica da câmera e foto.
 */
public class CameraFragment extends BaseLibFragment {

    private ImageView imgView;
    private File file;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera, container, false);

        imgView = (ImageView) view.findViewById(R.id.img);
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cria o o arquivo no sdcard
                String s = "foto.jpg";
                file = SDCardUtils.getPrivateFile(getContext(), s, Environment.DIRECTORY_PICTURES);
                // Chama a intent informando o arquivo para salvar a foto
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                startActivityForResult(i, 0);
            }
        });

        if (savedInstanceState != null) {
            // Se girou a tela recupera o estado
            file = (File) savedInstanceState.getSerializable("file");
            setImage(file);
        }

        return view;
    }

    private void setImage(File file) {
        ImageUtils.setImage(getContext(),file.getAbsolutePath().toString(), imgView);
    }

    public void setImage(String url) {
        ImageUtils.setImage(getContext(),url, imgView);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && file != null) {
            showImage(file);
        }
    }

    // Atualiza a imagem na tela
    private void showImage(File file) {
        if (file != null && file.exists()) {
            Log.d("foto", file.getAbsolutePath());

            int w = imgView.getWidth();
            int h = imgView.getHeight();
            Bitmap bitmap = ImageResizeUtils.getResizedImage(Uri.fromFile(file), w, h, false);

            imgView.setImageBitmap(bitmap);
        }
    }
}
