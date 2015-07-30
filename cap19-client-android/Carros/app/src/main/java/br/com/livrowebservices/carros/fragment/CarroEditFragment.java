package br.com.livrowebservices.carros.fragment;


import android.content.Intent;
import android.os.Bundle;
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
public class CarroEditFragment extends BaseLibFragment {
    private TextView tNome;
    private TextView tDesc;
    private TextView tLat;
    private TextView tLng;
    private CameraFragment img;
    private Carro c;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carro_edit, container, false);
        setHasOptionsMenu(true);

        img = (CameraFragment) getChildFragmentManager().findFragmentById(R.id.CameraFragment);

        tNome = (TextView) view.findViewById(R.id.tNome);
        tDesc = (TextView) view.findViewById(R.id.tDesc);
        tLat = (TextView) view.findViewById(R.id.tLat);
        tLng = (TextView) view.findViewById(R.id.tLng);

        if(getArguments() != null) {
            c = (Carro) getArguments().getSerializable("carro");
            setCarro(c);
        }

        setHasOptionsMenu(true);
        return view;
    }

    private void setCarro(Carro c) {
        if (c != null) {
            img.setImage(c.urlFoto);

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
            startTask("salvar",taskSaveCarro());
            return true;
        } else if (id == R.id.action_foto) {
            toast("Foto");

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private BaseTask taskSaveCarro() {
        return new BaseTask<Response>(){
            @Override
            public Response execute() throws Exception {
                return CarroService.saveCarro(getContext(), c);
            }

            @Override
            public void updateView(Response response) {
                super.updateView(response);
                if(response != null && "OK".equals(response.getStatus())) {
                    getActivity().finish();
                    BroadcastUtil.broadcast(getContext(), BroadcastUtil.ACTION_CARRO_SALVO);
                } else {
                    toast("Erro ao salvar o carro " + c.nome);
                }
            }
        };
    }
}
