package br.com.livrowebservices.carros.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.com.livrowebservices.carros.R;
import br.com.livrowebservices.carros.domain.Carro;
import br.com.livrowebservices.carros.fragment.dialog.DeletarCarroDialog;
import livroandroid.lib.activity.BaseActivity;


public class CarroActivity extends BaseActivity {
    CollapsingToolbarLayout collapsingToolbar;

    private TextView tNome;
    private TextView tDesc;
    private TextView tLat;
    private TextView tLng;
    private ImageView img;
    private ImageView header;
    private Carro c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carro);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Título da CollapsingToolbarLayout
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        img = (ImageView) findViewById(R.id.img);
        tNome = (TextView) findViewById(R.id.tNome);
        tDesc = (TextView) findViewById(R.id.tDesc);
        tLat = (TextView) findViewById(R.id.tLat);
        tLng = (TextView) findViewById(R.id.tLng);

        // Header
        header = (ImageView) findViewById(R.id.header);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.header_intel);

        // Palleta cores
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                int mutedColor = palette.getMutedColor(R.attr.colorPrimary);
                collapsingToolbar.setContentScrimColor(mutedColor);
            }
        });

        c = (Carro) getIntent().getSerializableExtra("carro");
        if (c != null) {
            setTitle(c.nome);
            collapsingToolbar.setTitle(c.nome);
            setCarro(c);
        }

        /*if (savedInstanceState == null) {
            CarroFragment frag = new CarroFragment();
            frag.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().replace(R.id.layoutFrag, frag).commit();
        }*/
    }

    private void setCarro(Carro c) {
        if (c != null) {
            this.img.setVisibility(View.GONE);
            ImageView img = this.header;
            if(c.urlFoto != null && c.urlFoto.trim().length() > 0) {
                Picasso.with(this).load(c.urlFoto).placeholder(R.drawable.placeholder).into(img);
            } else {
                img.setImageResource(R.drawable.placeholder);
            }
        }

        tNome.setText(c.nome);

        c.desc = "AHA"+c.desc;
        for (int i=0;i<10;i++){
            c.desc += "\n"+c.desc;
        }

        tDesc.setText(c.desc);
        tLat.setText(c.latitude);
        tLng.setText(c.longitude);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_carro, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_edit) {
            toast("Editar: " + c.nome);
            return true;
        } else if (id == R.id.action_remove) {
            DeletarCarroDialog.show(getSupportFragmentManager(), c, new DeletarCarroDialog.Callback() {
                @Override
                public void deleteCarro(Carro carro) {
                    toast("Carro [" + carro.nome + "] deletado.");

                }
            });

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
