package br.com.livrowebservices.carros.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import br.com.livrowebservices.carros.CarrosApplication;
import br.com.livrowebservices.carros.R;
import br.com.livrowebservices.carros.activity.CarroActivity;
import br.com.livrowebservices.carros.databinding.FragmentCarroBinding;
import br.com.livrowebservices.carros.domain.Carro;
import br.com.livrowebservices.carros.domain.CarroService;
import br.com.livrowebservices.carros.domain.event.BusEvent;
import br.com.livrowebservices.carros.utils.ImageUtils;
import livroandroid.lib.fragment.BaseFragment;
import livroandroid.lib.utils.IntentUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class CarroFragment extends BaseFragment implements OnMapReadyCallback, CarroActivity.ClickHeaderListener {

    private static final int REQUEST_CODE_SALVAR = 1;
    protected ImageView img;
    protected TextView tUrlVideo;
    protected TextView tLatLng;

    protected TextView tLat;
    protected TextView tLng;
    private GoogleMap map;
    protected Carro carro;
    protected FragmentCarroBinding binding;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_carro, container, false);

        View view = binding.getRoot();

        initViews(view);

        if (carro != null) {
            setCarro(carro);
        }

        // Mapa
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        // Inicia o Google Maps dentro do fragment
        mapFragment.getMapAsync(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (carro != null) {
            startTask("loadFavoritos", taskLoadFavoritos(), R.id.progress);
        }
    }

    protected void initViews(View view) {
        img = (ImageView) view.findViewById(R.id.img);
        tUrlVideo = (TextView) view.findViewById(R.id.tUrlVideo);
        tLatLng = (TextView) view.findViewById(R.id.tLatLng);

        tLat = (TextView) view.findViewById(R.id.tLat);
        tLng = (TextView) view.findViewById(R.id.tLng);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;

        if (carro != null && map != null) {

            // Ativa o botão para mostrar minha localização
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            map.setMyLocationEnabled(true);

            // Cria o objeto LatLng com a coordenada da fábrica
            double lat = carro.getLatitude();
            double lng = carro.getLongitude();

            if (lat > 0 && lng > 0) {
                LatLng location = new LatLng(lat, lng);
                // Posiciona o mapa na coordenada da fábrica (zoom = 13)

                CameraUpdate update = CameraUpdateFactory.newLatLngZoom(location, 13);
                //map.moveCamera(update);

                map.animateCamera(update, 2000, null);

                // Marcador no local da fábrica
                map.addMarker(new MarkerOptions()
                        .title(carro.nome)
                        .snippet(carro.desc)
                        .position(location));
            }

            // Tipo do mapa: MAP_TYPE_NORMAL,
            // MAP_TYPE_TERRAIN, MAP_TYPE_HYBRID and MAP_TYPE_NONE
            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }
    }

    private void setCarro(Carro c) {
        if (c != null) {
            if (img != null) {
                ImageUtils.setImage(getContext(), c.urlFoto, img);
                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showVideo();
                    }
                });
            }

            // Data Binding
            binding.setCarro(c);

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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_frag_carro, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_edit) {
            Intent intent = new Intent(getActivity(), CarroActivity.class);
            intent.putExtra("carro", carro);
            intent.putExtra("editMode", true);
            ActivityOptionsCompat opts = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity());
            ActivityCompat.startActivityForResult(getActivity(), intent, REQUEST_CODE_SALVAR, opts.toBundle());
            // Por definição, vamos fechar esta tela para ficar somente a de editar.
            getActivity().finish();
            return true;
        } else if (id == R.id.action_video) {
            showVideo();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void showVideo() {
        // Abre o vídeo no Player de Vídeo Nativo
        if (carro.urlVideo != null && carro.urlVideo.trim().length() > 0) {
            if (URLUtil.isValidUrl(carro.urlVideo)) {
                IntentUtils.showVideo(getContext(), carro.urlVideo);
            } else {
                toast(getString(R.string.msg_url_invalida));
            }

        } else {
            toast(getString(R.string.msg_carro_sem_video));
        }
    }

    @Override
    public void onHeaderClicked() {
        toast("Para alterar a foto precisa editar o carro.");
    }

    @Override
    public void onFabButtonClicked(final Carro carro) {
        // Favoritar o carro
        if(carro != null) {
            startTask("favorito", taskFavoritar(carro), R.id.progress);
        }
    }

    @NonNull
    protected BaseTask<Boolean> taskFavoritar(final Carro carro) {
        return new BaseTask<Boolean>(){
            @Override
            public Boolean execute() throws Exception {
                CarroService.toogleFavorite(getContext(),carro);
                return true;
            }

            @Override
            public void updateView(Boolean response) {
                super.updateView(response);
                // Atualiza o FAB button
                CarroActivity activity = (CarroActivity) getActivity();
                activity.toogleFavorite(carro.favorited);

                // Envia o evento para o bus
                CarrosApplication.getInstance().getBus().post(new BusEvent.FavoritosEvent());
            }
        };
    }

    /**
     * // Atualiza a cor do FAB button, conforme se o carro está favoritado ou não
     */
    @NonNull
    protected BaseTask<Boolean> taskLoadFavoritos() {
        return new BaseTask<Boolean>(){
            @Override
            public Boolean execute() throws Exception {
                // Verifica se o carro está favoritado.
                Boolean favorito = CarroService.isFavorito(getContext(),carro);
                return favorito;
            }

            @Override
            public void updateView(Boolean favorito) {
                super.updateView(favorito);

                // Atualiza a cor do FAB button.
                CarroActivity activity = (CarroActivity) getActivity();
                activity.setFavoriteColor(favorito);
            }
        };
    }
}
