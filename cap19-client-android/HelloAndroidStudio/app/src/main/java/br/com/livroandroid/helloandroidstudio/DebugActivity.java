package br.com.livroandroid.helloandroidstudio;

import android.os.Bundle;
import android.util.Log;

/**
 * Created by rlech on 24-Feb-16.
 */
public class DebugActivity extends android.support.v7.app.AppCompatActivity {
    protected
    static final String TAG = "livro";

    protected void onCreate(Bundle b) {
        super.onCreate(b);
        Log.i(TAG, getClassName() + ".onCreate() chamado: " + b);


    }

    protected void onStart() {
        super.onStart();
        Log.i(TAG, getClassName() + ".onStart() chamado.");
    }

    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, getClassName() + ".onRestart() chamado.");
    }

    protected void onResume() {
        super.onResume();
        Log.i(TAG, getClassName() + ".onResume() chamado.");
    }

    protected void onPause() {
        super.onPause();
        Log.i(TAG, getClassName() + ".onPause() chamado.");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, getClassName() + ".onSaveInstanceState() chamado.");
    }

    protected void onStop() {
        super.onStop();
        Log.i(TAG, getClassName() + ".onStop() chamado.");
    }

    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, getClassName() + ".onDestroy() chamado.");
    }

    private String getClassName() {
// Retorna o nome da classe sem o pacote
        String s = getClass().getName();
        return s.substring(s.lastIndexOf("."));
    }
}
