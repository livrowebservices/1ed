package br.com.livrowebservices.carros.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

import br.com.livrowebservices.carros.R;

/**
 * Created by rlech on 9/30/2015.
 */
public class AlertUtils {
    public static void show(Context context,int title, int message, final Runnable runnable) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title).setMessage(message);
        // Add the buttons
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if(runnable != null) {
                    runnable.run();
                }
            }
        });
        AlertDialog dialog = builder.create();
    }
}
