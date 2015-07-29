package br.com.livrowebservices.carros.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.com.livrowebservices.carros.R;
import br.com.livrowebservices.carros.domain.Carro;
import livroandroid.lib.fragment.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class CarroFragment extends BaseFragment {

    private TextView tNome;
    private TextView tDesc;
    private TextView tLat;
    private TextView tLng;
    private ImageView img;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_carro, container, false);
        setHasOptionsMenu(true);

        img = (ImageView) view.findViewById(R.id.img);
        tNome = (TextView) view.findViewById(R.id.tNome);
        tDesc = (TextView) view.findViewById(R.id.tDesc);
        tLat = (TextView) view.findViewById(R.id.tLat);
        tLng = (TextView) view.findViewById(R.id.tLng);

        if(getArguments() != null) {
            Carro c = (Carro) getArguments().getSerializable("carro");
            setCarro(c);
        }

        return view;
    }

    private void setCarro(Carro c) {
        if (c != null) {
            if(c.urlFoto != null && c.urlFoto.trim().length() > 0) {
                Picasso.with(getContext()).load(c.urlFoto).placeholder(R.drawable.placeholder).into(img);
            } else {
                img.setImageResource(R.drawable.placeholder);
            }
        }

        tNome.setText(c.nome);
        tDesc.setText(c.desc);
        tLat.setText(c.latitude);
        tLng.setText(c.longitude);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_frag_carro, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_done) {
            toast("Salvar");
            getActivity().finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
