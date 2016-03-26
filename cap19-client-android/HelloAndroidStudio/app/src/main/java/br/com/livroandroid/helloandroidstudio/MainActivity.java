package br.com.livroandroid.helloandroidstudio;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends DebugActivity implements View.OnClickListener {
    private static final String TAG = "livro";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btLogin = (Button) findViewById(R.id.btLogin);
        btLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        TextView tLogin = (TextView) findViewById(R.id.tLogin);
        TextView tSenha = (TextView) findViewById(R.id.tSenha);
        String login = tLogin.getText().toString();
        String senha = tSenha.getText().toString();
        if ("ricardo".equals(login) && "123".equals(senha)) {
            alert("Bem-vindo, login realizado com sucesso!");

            // Navega para a próxima tela
            Intent intent = new Intent(getContext(), BemVindoActivity.class);
            Bundle params = new Bundle();
            params.putString("nome", "Ricardo Lecheta");
            intent.putExtras(params);
            startActivity(intent);
        } else {
            alert("Login e senha incorretos.");
        }
    }
    private Context getContext() {
        return this;
    }
    private void alert(String s) {
// A classe Toast mostra um alerta temporário muito comum no Android
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}
