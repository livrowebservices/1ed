package br.com.livrowebservices.carros.activity;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import org.parceler.Parcels;

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
    private ImageView appBarImg;
    private FloatingActionButton fabButton;
    private ClickHeaderListener clickHeaderListener;

    public interface ClickHeaderListener{
        void onHeaderClicked();

        void onFabButtonClicked(Carro carro);
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
        appBarImg = (ImageView) findViewById(R.id.appBarImg);
        appBarImg.setOnClickListener(onClickImgHeader());
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.header_appbar);

        // Palleta cores
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                int color = ContextCompat.getColor(getContext(), R.color.primary);
                int mutedColor = palette.getMutedColor(color);
                collapsingToolbar.setContentScrimColor(mutedColor);
            }
        });

        // Args
        this.carro = Parcels.unwrap(getIntent().getExtras().getParcelable("carro"));
        final boolean editMode = getIntent().getBooleanExtra("editMode", false);
        setAppBarInfo(carro);

        // FAB
        fabButton = (FloatingActionButton) findViewById(R.id.fab);
        if(carro != null) {
            if(carro.favorited) {
                int color = ContextCompat.getColor(this,R.color.amarelo);
                fabButton.setBackgroundTintList(new ColorStateList(new int[][]{new int[]{0}}, new int[]{color}));
            }
            fabButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(clickHeaderListener != null) {
                        clickHeaderListener.onFabButtonClicked(carro);
                    }
                }
            });
            fabButton.hide();
        } else {
            fabButton.show();
        }


        // Fragment
        if (savedInstanceState == null) {
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
        }
    }

    public void setImage(String url) {
        ImageUtils.setImage(this,url, appBarImg);
    }

    public void setImage(File file) {
        ImageUtils.setImage(this,file, appBarImg);
    }

    public void setImage(Bitmap bitmap) {
        if(bitmap != null) {
            appBarImg.setImageBitmap(bitmap);
        }
    }

    public void toogleFavorite(boolean b) {
        int c = b ? R.color.amarelo : R.color.cinza;
        int color = ContextCompat.getColor(this,c);
        fabButton.setBackgroundTintList(new ColorStateList(new int[][]{new int[]{0}}, new int[]{color}));
        if(b) {
            snack(appBarImg, "Carro " + carro.nome + " marcado nos favoritos.");
        } else {
            snack(appBarImg, "Carro " + carro.nome + " desmarcado dos favoritos.");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            // up navigation
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
