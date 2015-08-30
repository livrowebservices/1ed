package br.com.livrowebservices.carros.utils;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

/**
 * Created by ricardo on 29/07/15.
 */
public class BroadcastUtil {
    public static final String ACTION_CARRO_EXCLUIDO = "ACTION_CARRO_EXCLUIDO";
    public static final String ACTION_CARRO_SALVO = "ACTION_CARRO_SALVO";
    public static final String ACTION_REFRESH_FAVORITOS = "ACTION_REFRESH_FAVORITOS";

    public static void broadcast(Context context, String action) {
        LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(action));
    }

    public static void broadcast(Context context, Intent intent) {
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
}
