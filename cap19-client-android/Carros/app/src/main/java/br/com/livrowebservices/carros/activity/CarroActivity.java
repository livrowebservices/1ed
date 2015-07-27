package br.com.livrowebservices.carros.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.widget.ImageView;

import br.com.livrowebservices.carros.R;


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

        int imgPlaneta = getIntent().getIntExtra("img", 0);
        if (imgPlaneta > 0) {
            ImageView img = (ImageView) findViewById(R.id.img);
            img.setImageResource(imgPlaneta);
        }
    }
}
