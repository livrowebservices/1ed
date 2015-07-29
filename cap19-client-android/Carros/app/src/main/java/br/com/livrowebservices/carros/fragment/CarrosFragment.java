package br.com.livrowebservices.carros.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import br.com.livrowebservices.carros.R;
import br.com.livrowebservices.carros.activity.CarroActivity;
import br.com.livrowebservices.carros.domain.Carro;
import br.com.livrowebservices.carros.domain.CarroService;
import br.com.livrowebservices.carros.fragment.adapter.CarroAdapter;
import br.com.livrowebservices.carros.utils.BroadcastUtil;
import livroandroid.lib.fragment.BaseFragment;
import livroandroid.lib.utils.AndroidUtils;
import livroandroid.lib.utils.IntentUtils;

/**
 * Created by ricardo on 12/06/15.
 */
public class CarrosFragment extends BaseLibFragment {
    CarroAdapter adapter;
    private RecyclerView recyclerView;
    private List<Carro> carros;
    private String tipo;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(BroadcastUtil.ACTION_CARRO_EXCLUIDO.equals(intent.getAction())) {
                taskCarros();
                snack(recyclerView, "OH EXCLUIDO");
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.tipo = getArguments().getString("tipo");
        }
        setHasOptionsMenu(true);

        // Registra receiver para receber broadcasts
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(receiver,new IntentFilter(BroadcastUtil.ACTION_CARRO_EXCLUIDO));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carros, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new CarroAdapter(getActivity(), carros, onClickCarro());
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void listaCarros() {
        // Atualiza ao fazer o gesto Swipe To Refresh
        if (AndroidUtils.isNetworkAvailable(getContext())) {
            taskCarros();
        } else {
            alert(R.string.error_conexao_indisponivel);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listaCarros();
    }

    private void taskCarros() {
        startTask("carros", new GetCarrosTask(), R.id.progress);
    }

    // Task para buscar os carros
    private class GetCarrosTask implements TaskListener<List<Carro>> {
        @Override
        public List<Carro> execute() throws Exception {
            Thread.sleep(200);
            // Busca os carros em background (Thread)
            return CarroService.getCarros(getContext(), tipo);
        }

        @Override
        public void updateView(List<Carro> carros) {
            if (carros != null) {
                CarrosFragment.this.carros = carros;
                // Atualiza a view na UI Thread
                recyclerView.setAdapter(new CarroAdapter(getContext(), carros, onClickCarro()));
                //toast("update ("+carros.size()+"): " + carros);
            }
        }

        @Override
        public void onError(Exception e) {
            alert("Ocorreu algum erro ao buscar os dados.");
        }

        @Override
        public void onCancelled(String s) {

        }
    }

    protected CarroAdapter.PlanetaOnClickListener onClickCarro() {
        final Intent intent = new Intent(getActivity(), CarroActivity.class);

        return new CarroAdapter.PlanetaOnClickListener() {
            @Override
            public void onClickCarro(CarroAdapter.CarrosViewHolder holder, int idx) {
                Carro c = carros.get(idx);

                ImageView img = holder.img;
                intent.putExtra("carro", c);
                String key = getString(R.string.transition_key);

                // Compat
                ActivityOptionsCompat opts = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), img, key);
                ActivityCompat.startActivity(getActivity(), intent, opts.toBundle());
            }
        };
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_frag_carros, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            listaCarros();
            return true;
        } else if (id == R.id.action_search) {
            toast("Faça a busca");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(receiver);
    }
}