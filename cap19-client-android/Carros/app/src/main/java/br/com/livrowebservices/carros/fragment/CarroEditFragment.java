package br.com.livrowebservices.carros.fragment;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.location.LocationListener;

import java.io.File;

import br.com.livrowebservices.carros.R;
import br.com.livrowebservices.carros.activity.CarroActivity;
import br.com.livrowebservices.carros.domain.Carro;
import br.com.livrowebservices.carros.domain.CarroService;
import br.com.livrowebservices.carros.rest.Response;
import br.com.livrowebservices.carros.rest.ResponseWithURL;
import br.com.livrowebservices.carros.utils.BroadcastUtil;
import br.com.livrowebservices.carros.utils.CameraUtil;
import livroandroid.lib.utils.GooglePlayServicesHelper;

/**
 * Fragment com form para editar o carro.
 * <p>
 * Herda do CarroFragment para aproveitar a lógica de visualização.
 */
public class CarroEditFragment extends CarroFragment implements CarroActivity.ClickHeaderListener, LocationListener {
    // Camera Foto
    private CameraUtil camera = new CameraUtil();
    private GooglePlayServicesHelper gps;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carro_edit, container, false);
        setHasOptionsMenu(true);

        initViews(view);

        if (savedInstanceState != null) {
            // Se girou a tela recupera o estado
            camera.onCreate(savedInstanceState);
        }

        CarroActivity activity = (CarroActivity) getActivity();
        activity.setClickHeaderListener(this);

        // Ligar o Google Play Services
        if (carro == null) {
            // Se não existe carro, liga GPS
            gps = new GooglePlayServicesHelper(getContext(), true);
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Conecta no Google Play Services
        if (gps != null) {
            gps.onResume(this);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        // Desconecta no Google Play Services
        if (gps != null) {
            gps.onPause();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        camera.onSaveInstanceState(outState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_frag_edit_carro, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_salvar) {

            if (carro == null) {
                // Novo carro
                carro = new Carro();
                carro.tipo = "esportivos";
            }

            boolean formOk = validate(tNome, tDesc);
            if (formOk) {
                // Validação de campos preenchidos
                carro.nome = tNome.getText().toString();
                carro.desc = tDesc.getText().toString();
                carro.latitude = tLat.getText().toString();
                carro.longitude = tLng.getText().toString();
                carro.urlVideo = tUrlVideo.getText().toString();
                carro.tipo = getTipo();

                Log.d(TAG, "Salvar carro tipo: " + carro.tipo);

                startTask("salvar", taskSaveCarro());
            }

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean validate(TextView... array) {
        for (TextView t : array) {
            String s = t.getText().toString();
            if (s == null || s.trim().length() == 0) {
                t.setError(getString(R.string.msg_error_campo_obrigatorio));
                return false;
            }
        }
        return true;
    }

    private BaseTask taskSaveCarro() {
        return new BaseTask<Response>() {
            @Override
            public Response execute() throws Exception {
                // Faz upload da foto
                File file = camera.getFile();
                if (file != null) {
                    //ResponseWithURL response = CarroREST.postFotoBase64(getContext(), file);
                    ResponseWithURL response = CarroService.postFotoBase64(getContext(), file);
                    if (response != null && response.isOk()) {
                        // Atualiza a foto do carro
                        carro.urlFoto = response.getUrl();
                    }
                }
                // Salva o carro
                Response response = CarroService.saveCarro(getContext(), carro);
                //Response response = Retrofit.getCarroREST().saveCarro(carro);
                return response;
            }

            @Override
            public void updateView(Response response) {
                super.updateView(response);
                if (response != null && "OK".equals(response.getStatus())) {
                    // Retorna resultado para o frag do carro
                    Intent intent = new Intent(BroadcastUtil.ACTION_CARRO_SALVO);
                    intent.putExtra("carro", carro);
                    BroadcastUtil.broadcast(getContext(), intent);
                    getActivity().finish();
                } else {
                    toast("Erro ao salvar o carro " + carro.nome);
                }
            }
        };
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            // Resize da imagem
            Bitmap bitmap = camera.getBitmap(600, 600);

            if (bitmap != null) {
                // Salva arquivo neste tamanho
                camera.save(bitmap);

                // Atualiza imagem do Header
                CarroActivity activity = (CarroActivity) getActivity();
                activity.setImage(bitmap);
            }
        }
    }

    private void setImage(File file) {
        //ImageUtils.setImage(getContext(), file.getAbsolutePath().toString(), imgView);
        Log.d(TAG, "setImage: " + file);
        ((CarroActivity) getActivity()).setImage(file);
    }

    public void setImage(String url) {
        //ImageUtils.setImage(getContext(),url, imgView);
        ((CarroActivity) getActivity()).setImage(url);
    }

    @Override
    public void onHeaderClicked() {
        // Se clicar na imagem de header, tira a foto
        // Cria o o arquivo no sdcard
        long ms = System.currentTimeMillis();
        String fileName = String.format("foto_carro_%s_%s.jpg", carro != null ? carro.id : ms, ms);
        // A classe Camera cria a intent e o arquivo no sdcard.
        Intent intent = camera.open(fileName);
        startActivityForResult(intent, 0);
    }

    @Override
    public void onLocationChanged(Location location) {
        if (gps != null) {
            // Atualiza GPS quando abre o formulário vazio.
            tLat.setText(String.valueOf(location.getLatitude()));
            tLng.setText(String.valueOf(location.getLongitude()));
        }
    }
}
