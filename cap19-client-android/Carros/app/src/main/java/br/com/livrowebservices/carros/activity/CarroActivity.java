package br.com.livrowebservices.carros.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import br.com.livrowebservices.carros.R;
import br.com.livrowebservices.carros.domain.Carro;


public class CarroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carro);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null) {
            setSupportActionBar(toolbar);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Carro c = (Carro) getIntent().getSerializableExtra("carro");
        if (c != null) {
            ImageView img = (ImageView) findViewById(R.id.img);

            setTitle(c.nome);

            if(c.urlFoto != null && c.urlFoto.trim().length() > 0) {
                Picasso.with(this).load(c.urlFoto).placeholder(R.drawable.placeholder).into(img);
            } else {
                img.setImageResource(R.drawable.placeholder);
            }
        }
    }
}
