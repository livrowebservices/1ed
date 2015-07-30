package br.com.livrowebservices.carros.fragment;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.com.livrowebservices.carros.R;
import br.com.livrowebservices.carros.activity.CarroActivity;
import br.com.livrowebservices.carros.activity.CarroEditActivity;
import br.com.livrowebservices.carros.domain.Carro;
import br.com.livrowebservices.carros.domain.CarroService;
import br.com.livrowebservices.carros.domain.Response;
import br.com.livrowebservices.carros.fragment.dialog.DeletarCarroDialog;
import br.com.livrowebservices.carros.utils.BroadcastUtil;
import br.com.livrowebservices.carros.utils.ImageUtils;
import livroandroid.lib.fragment.BaseFragment;
import livroandroid.lib.utils.IntentUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class CarroFragment extends BaseLibFragment {

    private static final int REQUEST_CODE_SALVAR = 1;
    private TextView tNome;
    private TextView tDesc;
    private TextView tLat;
    private TextView tLng;
//    private ImageView img;
    private Carro c;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            toast("OH> " + intent.getAction());
            if (BroadcastUtil.ACTION_CARRO_SALVO.equals(intent.getAction())) {
                // Atualiza o carro
                c = (Carro) getArguments().getParcelable("carro");
                setCarro(c);
                // Persiste nos arguments
                getArguments().putParcelable("carro",c);
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Registra receiver para receber broadcasts
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(receiver, new IntentFilter(BroadcastUtil.ACTION_CARRO_SALVO));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_carro, container, false);
        setHasOptionsMenu(true);

//        img = (ImageView) view.findViewById(R.id.img);
        tNome = (TextView) view.findViewById(R.id.tNome);
        tDesc = (TextView) view.findViewById(R.id.tDesc);
        tLat = (TextView) view.findViewById(R.id.tLat);
        tLng = (TextView) view.findViewById(R.id.tLng);

        if(getArguments() != null) {
            c = (Carro) getArguments().getParcelable("carro");
            setCarro(c);
        }
        return view;
    }

    private void setCarro(Carro c) {
        if (c != null) {
//            ImageUtils.setImage(getContext(), c.urlFoto, img);

            /**
             * Aumenta a descriçao, para fazer scroll :-)
             */
            String desc = c.desc;
            for (int i=0;i<10;i++){
                desc += "\n"+c.desc;
            }

            tNome.setText(c.nome);
            tDesc.setText(desc);
            tLat.setText(c.latitude);
            tLng.setText(c.longitude);

            CarroActivity activity = (CarroActivity) getActivity();
            activity.setAppBarHeaderImage(c.urlFoto);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_frag_carro, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_edit) {
            Intent intent = new Intent(getActivity(), CarroEditActivity.class);
            intent.putExtra("carro", c);
            ActivityOptionsCompat opts = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity());
            ActivityCompat.startActivityForResult(getActivity(), intent, REQUEST_CODE_SALVAR,opts.toBundle());
            return true;
        } else if (id == R.id.action_remove) {
            DeletarCarroDialog.show(getFragmentManager(), new DeletarCarroDialog.Callback() {
                @Override
                public void deleteCarro() {
                    startTask("deletarCarro",taskDeleteCarro());
                }
            });

            return true;
        } else if (id == R.id.action_video) {
            // Abre o vídeo no Player de Vídeo Nativo
            if(c.urlVideo != null && c.urlVideo.trim().length() > 0) {
                if(URLUtil.isValidUrl(c.urlVideo)) {
                    IntentUtils.showVideo(getContext(), c.urlVideo);
                } else {
                    toast(getString(R.string.msg_url_invalida));
                }

            } else {
                toast(getString(R.string.msg_carro_sem_video));
            }

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private BaseTask taskDeleteCarro() {
        return new BaseTask<Response>(){
            @Override
            public Response execute() throws Exception {
                return CarroService.delete(getContext(),c);
            }

            @Override
            public void updateView(Response response) {
                super.updateView(response);
                if(response != null && "OK".equals(response.getStatus())) {
                    Intent intent = new Intent(BroadcastUtil.ACTION_CARRO_EXCLUIDO);
                    intent.putExtra("carro", c);
                    BroadcastUtil.broadcast(getContext(), intent);
                    getActivity().finish();
                } else {
                    toast("Erro ao excluir o carro " + c.nome);
                }
            }
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(receiver);
    }
}
