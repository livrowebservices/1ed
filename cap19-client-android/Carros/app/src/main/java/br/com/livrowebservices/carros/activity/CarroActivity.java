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
import br.com.livrowebservices.carros.fragment.CarroFragment;
import br.com.livrowebservices.carros.fragment.dialog.DeletarCarroDialog;
import livroandroid.lib.activity.BaseActivity;


public class CarroActivity extends BaseActivity {
    CollapsingToolbarLayout collapsingToolbar;

    private Carro c;
    private ImageView header;

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

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast("Você pode favoritar esse carro...");
            }
        });

        // Título da CollapsingToolbarLayout
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        // Header
        header = (ImageView) findViewById(R.id.header);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.header_appbar);

        // Palleta cores
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                int mutedColor = palette.getMutedColor(R.attr.colorPrimary);
                collapsingToolbar.setContentScrimColor(mutedColor);
            }
        });

        c = getIntent().getParcelableExtra("carro");
        setAppBarInfo(c);

        if (savedInstanceState == null) {
            CarroFragment frag = new CarroFragment();
            frag.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().replace(R.id.layoutFrag, frag).commit();
        }
    }

    public void setAppBarInfo(Carro c) {
        if(c != null) {
            String nome = c.nome;
            String url = c.nome;

            collapsingToolbar.setTitle(nome);

            ImageView img = this.header;
            if(url != null && url.trim().length() > 0) {
                //Picasso.with(this).load(c.urlFoto).resizeDimen(R.dimen.img_carro_width, R.dimen.img_carro_height).centerCrop().placeholder(R.drawable.placeholder).into(img);
                Picasso.with(this).load(c.urlFoto).placeholder(R.drawable.placeholder).into(img);
            } else {
                img.setImageResource(R.drawable.placeholder);
            }
        }
    }
}
