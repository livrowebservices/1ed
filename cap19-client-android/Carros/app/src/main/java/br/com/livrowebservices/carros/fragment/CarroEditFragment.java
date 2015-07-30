package br.com.livrowebservices.carros.fragment;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import br.com.livrowebservices.carros.R;
import br.com.livrowebservices.carros.domain.Carro;
import br.com.livrowebservices.carros.domain.CarroService;
import br.com.livrowebservices.carros.domain.Response;
import br.com.livrowebservices.carros.domain.ResponseWithURL;
import br.com.livrowebservices.carros.utils.BroadcastUtil;
import br.com.livrowebservices.carros.utils.ImageUtils;
import livroandroid.lib.utils.ImageResizeUtils;
import livroandroid.lib.utils.SDCardUtils;

/**
 * Fragment com form para editar o carro
 */
public class CarroEditFragment extends BaseLibFragment {
    private ImageView imgView;
    private TextView tNome;
    private TextView tDesc;
    private TextView tLat;
    private TextView tLng;

    private Carro c;

    // Camera Foto
    private File file;
    private boolean updateFoto;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carro_edit, container, false);
        setHasOptionsMenu(true);

        imgView = (ImageView) view.findViewById(R.id.img);
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cria o o arquivo no sdcard
                file = SDCardUtils.getPublicFile("foto_carro.jpg");
                // Chama a intent informando o arquivo para salvar a foto
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                startActivityForResult(i, 0);
            }
        });

        tNome = (TextView) view.findViewById(R.id.tNome);
        tDesc = (TextView) view.findViewById(R.id.tDesc);
        tLat = (TextView) view.findViewById(R.id.tLat);
        tLng = (TextView) view.findViewById(R.id.tLng);

        if(getArguments() != null) {
            c =  getArguments().getParcelable("carro");
            setCarro(c);
        }

        if (savedInstanceState != null) {
            // Se girou a tela recupera o estado
            file = (File) savedInstanceState.getSerializable("file");
            updateFoto = savedInstanceState.getBoolean("updateFoto");
            setImage(file);
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(file != null) {
            outState.putSerializable("file",file);
            outState.putBoolean("updateFoto", updateFoto);
        }
    }

    private void setCarro(Carro c) {
        if (c != null) {
            setImage(c.urlFoto);

            tNome.setText(c.nome);
            tDesc.setText(c.desc);
            tLat.setText(c.latitude);
            tLng.setText(c.longitude);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_frag_edit_carro, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_salvar) {

            c.nome = tNome.getText().toString();

            startTask("salvar", taskSaveCarro());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private BaseTask taskSaveCarro() {
        return new BaseTask<Response>(){
            @Override
            public Response execute() throws Exception {
                if(file != null) {
                    // Faz upload da foto
                    ResponseWithURL response = CarroService.postFotoBase64(getContext(), file);
                    if(response != null && response.isOk()) {
                        // Atualiza a foto do carro
                        c.urlFoto = response.getUrl();
                    }
                }
                // Salva o carro
                return CarroService.saveCarro(getContext(), c);
            }

            @Override
            public void updateView(Response response) {
                super.updateView(response);
                if(response != null && "OK".equals(response.getStatus())) {
                    // Retorna resultado para o frag do carro
                    Intent intent = new Intent(BroadcastUtil.ACTION_CARRO_SALVO);
                    intent.putExtra("carro", c);
                    BroadcastUtil.broadcast(getContext(), intent);
                    getActivity().finish();
                } else {
                    toast("Erro ao salvar o carro " + c.nome);
                }
            }
        };
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && file != null) {
            updateFoto = true;
            showImage(file);
        }
    }

    // Atualiza a imagem na tela
    private void showImage(File file) {
        if (file != null && file.exists()) {
            this.file = file;

            Log.d("foto", file.getAbsolutePath());

            int w = imgView.getWidth();
            int h = imgView.getHeight();
            Bitmap bitmap = ImageResizeUtils.getResizedImage(Uri.fromFile(file), w, h, false);

            imgView.setImageBitmap(bitmap);
        }
    }

    private void setImage(File file) {
        ImageUtils.setImage(getContext(), file.getAbsolutePath().toString(), imgView);
    }

    public void setImage(String url) {
        ImageUtils.setImage(getContext(),url, imgView);
    }
}
