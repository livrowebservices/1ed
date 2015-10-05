package br.com.livrowebservices.carros.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.com.livrowebservices.carros.R;
import br.com.livrowebservices.carros.utils.AlertUtils;
import br.com.livrowebservices.carros.utils.PermissionUtils;

/**
 * Splash para listar as permissões.
 */
public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Valida lista de permissões.
        String permissions[] = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.CAMERA,

        };
        boolean ok = PermissionUtils.validate(this,0, permissions);

        if(ok) {
            // Tudo OK, pode entrar.
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        for (int result: grantResults) {
            if(result == PackageManager.PERMISSION_DENIED) {
                // Negou a permissão. Mostra alerta e fecha.
                AlertUtils.show(getContext(), R.string.app_name, R.string.msg_alerta_permissao, new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                });
                return;
            }
        }
        
        // ~Permissões concedidas, pode entrar.
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
}
