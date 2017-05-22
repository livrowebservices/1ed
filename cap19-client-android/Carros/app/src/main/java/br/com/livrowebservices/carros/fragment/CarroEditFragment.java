package br.com.livrowebservices.carros.fragment;


import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
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
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.gms.location.LocationListener;

import java.io.File;

import br.com.livrowebservices.carros.CarrosApplication;
import br.com.livrowebservices.carros.R;
import br.com.livrowebservices.carros.activity.CarroActivity;
import br.com.livrowebservices.carros.databinding.FragmentCarroBinding;
import br.com.livrowebservices.carros.databinding.FragmentCarroEditBinding;
import br.com.livrowebservices.carros.domain.Carro;
import br.com.livrowebservices.carros.domain.CarroService;
import br.com.livrowebservices.carros.rest.Response;
import br.com.livrowebservices.carros.rest.ResponseWithURL;
import br.com.livrowebservices.carros.domain.event.BusEvent;
import br.com.livrowebservices.carros.utils.CameraUtil;
import br.com.livrowebservices.carros.utils.ImageUtils;
import livroandroid.lib.fragment.BaseFragment;
import livroandroid.lib.utils.GooglePlayServicesHelper;

/**
 * Fragment com form para editar o carro.
 * <p>
 * Herda do CarroFragment para aproveitar a lógica de visualização.
 */
public class CarroEditFragment extends BaseFragment implements LocationListener, CarroActivity.ClickHeaderListener {
    // Camera Foto
    private CameraUtil camera = new CameraUtil();
    private GooglePlayServicesHelper gps;
    private FragmentCarroEditBinding binding;

    protected ImageView img;
    protected TextView tUrlVideo;
    protected TextView tLatLng;

    protected TextView tLat;
    protected TextView tLng;
    protected RadioGroup tTipo;

    private Carro carro;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Lê os argumentos
        carro = getArguments().getParcelable("carro");

        setHasOptionsMenu(true);

        CarroActivity activity = (CarroActivity) getActivity();
        activity.setClickHeaderListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_carro_edit, container, false);

        View view = binding.getRoot();

        initViews(view);

        initViews(view);

        if (carro != null) {
            setCarro(carro);
        }

        if (savedInstanceState != null) {
            // Se girou a tela recupera o estado
            camera.onCreate(savedInstanceState);
        }

        // Ligar o Google Play Services
        if (carro == null) {
            // Se não existe carro, liga GPS
            gps = new GooglePlayServicesHelper(getContext(), true);
        }

        return view;
    }

    protected void initViews(View view) {
        img = (ImageView) view.findViewById(R.id.img);
        tUrlVideo = (TextView) view.findViewById(R.id.tUrlVideo);
        tLatLng = (TextView) view.findViewById(R.id.tLatLng);

        tTipo = (RadioGroup) view.findViewById(R.id.radioTipo);
        tLat = (TextView) view.findViewById(R.id.tLat);
        tLng = (TextView) view.findViewById(R.id.tLng);
    }

    private void setCarro(Carro c) {
        if (c != null) {
            if (img != null) {
//                ImageUtils.setImage(getContext(), c.urlFoto, img);
//                img.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        showVideo();
//                    }
//                });
            }

            // Data Binding
            binding.setCarro(c);

            setTipo(c.tipo);

            if (tLatLng != null) {
                tLatLng.setText(String.format("%s/%s", c.latitude, c.longitude));
            } else {
                tLat.setText(c.latitude);
                tLng.setText(c.longitude);
            }
        }

        // Imagem do Header na Toolbar
        CarroActivity activity = (CarroActivity) getActivity();
        activity.setAppBarInfo(c);
    }

    // Seta o tipo no RadioGroup
    protected void setTipo(String tipo) {
        if ("classicos".equals(tipo)) {
            tTipo.check(R.id.tipoClassico);
        } else if ("esportivos".equals(tipo)) {
            tTipo.check(R.id.tipoEsportivo);
        } else if ("luxo".equals(tipo)) {
            tTipo.check(R.id.tipoLuxo);
        }
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

            boolean formOk = validate(R.id.tNome, R.id.tDesc);
            if (formOk) {
                // Validação de campos preenchidos
                carro.nome = binding.tNome.getText().toString();
                carro.desc = binding.tDesc.getText().toString();
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

    // Retorna o tipo em string conforme marcado no RadioGroup
    protected String getTipo() {
        if (tTipo != null) {
            int id = tTipo.getCheckedRadioButtonId();
            switch (id) {
                case R.id.tipoClassico:
                    return "classicos";
                case R.id.tipoEsportivo:
                    return "esportivos";
                case R.id.tipoLuxo:
                    return "luxo";
            }
        }
        return "classicos";
    }

    private boolean validate(int... textViewIds) {
        for (int id : textViewIds) {
            TextView t = (TextView) getView().findViewById(id);
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
                if (file != null && file.exists()) {
                    ResponseWithURL response = CarroService.postFotoBase64(file);
                    if (response != null && response.isOk()) {
                        // Atualiza a foto do carro
                        carro.urlFoto = response.getUrl();
                    }
                }
                // Salva o carro
                Response response = CarroService.saveCarro(carro);
                //Response response = Retrofit.getCarroREST().saveCarro(carro);
                return response;
            }

            @Override
            public void updateView(Response response) {
                super.updateView(response);
                if (response != null && "OK".equals(response.getStatus())) {
                    // Envia o evento para o bus
                    CarrosApplication.getInstance().getBus().post(new BusEvent.NovoCarroEvent());
                    // Fecha a tela
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

//    @Override
//    public void onHeaderClicked() {
//        // Se clicar na imagem de header, tira a foto
//        // Cria o o arquivo no sdcard
//        long ms = System.currentTimeMillis();
//        String fileName = String.format("foto_carro_%s_%s.jpg", carro != null ? carro.id : ms, ms);
//        // A classe Camera cria a intent e o arquivo no sdcard.
//        Intent intent = camera.open(fileName);
//        startActivityForResult(intent, 0);
//    }

    @Override
    public void onLocationChanged(Location location) {
        if (gps != null) {
            // Atualiza GPS quando abre o formulário vazio.
            tLat.setText(String.valueOf(location.getLatitude()));
            tLng.setText(String.valueOf(location.getLongitude()));
        }
    }

    @Override
    public void onHeaderClicked() {
        // Se clicar na imagem de header, tira a foto
        // Cria o o arquivo no sdcard
        long ms = System.currentTimeMillis();
        String fileName = String.format("foto_carro_%s_%s.jpg", carro != null ? carro.id : ms, ms);
        // A classe Camera cria a intent e o arquivo no sdcard.
        Intent intent = camera.open(getContext(),fileName);
        startActivityForResult(intent, 0);
    }

    @Override
    public void onFabButtonClicked(Carro carro) {

    }
}
