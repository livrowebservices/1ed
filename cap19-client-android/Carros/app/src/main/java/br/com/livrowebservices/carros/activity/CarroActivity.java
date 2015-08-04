package br.com.livrowebservices.carros.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;

import br.com.livrowebservices.carros.R;
import br.com.livrowebservices.carros.domain.Carro;
import br.com.livrowebservices.carros.fragment.CarroEditFragment;
import br.com.livrowebservices.carros.fragment.CarroFragment;
import br.com.livrowebservices.carros.utils.ImageUtils;
import livroandroid.lib.activity.BaseActivity;


public class CarroActivity extends BaseActivity {
    CollapsingToolbarLayout collapsingToolbar;

    private Carro carro;
    private ImageView header;
    private FloatingActionButton fabButton;
    private ClickHeaderListener clickHeaderListener;

    public interface ClickHeaderListener{
        void onHeaderClicked();
    }

    public void setClickHeaderListener(ClickHeaderListener clickHeaderListener) {
        this.clickHeaderListener = clickHeaderListener;
    }

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

        // Header
        header = (ImageView) findViewById(R.id.header);
        header.setOnClickListener(onClickImgHeader());
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

        // Args
        carro = getIntent().getParcelableExtra("carro");
        final boolean editMode = getIntent().getBooleanExtra("editMode", false);
        setAppBarInfo(carro);

        // FAB
        fabButton = (FloatingActionButton) findViewById(R.id.fab);
        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editMode) {
                    CarroEditFragment frag = (CarroEditFragment) getSupportFragmentManager().findFragmentByTag("frag");
                    frag.onClickCamera();
                } else {
                    toast("Você pode favoritar esse carro...");
                }
            }
        });

        // Fragment
        if (savedInstanceState == null) {

            if(editMode) {
                fabButton.setImageResource(R.drawable.ic_action_camera);
            }

            CarroFragment frag = editMode ? new CarroEditFragment() : new CarroFragment();
            frag.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().replace(R.id.layoutFrag, frag,"frag").commit();
        }
    }

    private View.OnClickListener onClickImgHeader() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickHeaderListener != null) {
                    // Delegate para notificar o fragment que teve clique.
                    clickHeaderListener.onHeaderClicked();
                }
            }
        };
    }

    public void setAppBarInfo(Carro c) {
        if(c != null) {
            String nome = c.nome;
            String url = c.urlFoto;

            collapsingToolbar.setTitle(nome);

            setImage(url);
        } else {
            // Novo Carro
            collapsingToolbar.setTitle(getString(R.string.novo_carro));

//            setImage("http://www.livroandroid.com.br/livro/carros/esportivos/Ferrari_FF.png");
        }
    }

    public void setImage(String url) {
        ImageUtils.setImage(this,url,header);
    }

    public void setImage(File file) {
        ImageUtils.setImage(this,file,header);
    }
}
