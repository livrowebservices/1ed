package br.com.livrowebservices.carros.domain;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.livrowebservices.carros.utils.HttpHelper;


public class CarroService {
    private static final String URL = "http://livrowebservices.com.br/rest/carros/tipo/{tipo}";
    private static final boolean LOG_ON = false;
    private static final String TAG = "CarroService";
    public static List<Carro> getCarros(Context context, String tipo) throws IOException {
        String url = URL.replace("{tipo}", tipo);
        String json = HttpHelper.doGet(url);
        List<Carro> carros = parserJSON(context, json);
        return carros;
    }
    private static List<Carro> parserJSON(Context context, String json) throws IOException {
        List<Carro> carros = new ArrayList<Carro>();
        try {
//            JSONObject root = new JSONObject(json);
//            JSONObject obj = root.getJSONObject("carros");
//            JSONArray jsonCarros = obj.getJSONArray("carro");

            JSONArray jsonCarros = new JSONArray(json);

            // Insere cada carro na lista
            for (int i = 0; i < jsonCarros.length(); i++) {
                JSONObject jsonCarro = jsonCarros.getJSONObject(i);
                Carro c = new Carro();
                // Lê as informações de cada carro
                c.nome = jsonCarro.optString("nome");
                c.desc = jsonCarro.optString("desc");
                c.urlFoto = jsonCarro.optString("urlFoto");
                c.urlInfo = jsonCarro.optString("urlInfo");
                c.latitude = jsonCarro.optString("latitude");
                c.longitude = jsonCarro.optString("longitude");
                if (LOG_ON) {
                    Log.d(TAG, "Carro " + c.nome + " > " + c.urlFoto);
                }
                carros.add(c);
            }
            if (LOG_ON) {
                Log.d(TAG, carros.size() + " encontrados.");
            }
        } catch (JSONException e) {
            Log.e(TAG,e.getMessage(),e);
            throw new IOException(e.getMessage(), e);
        } catch (Exception e) {
            Log.e(TAG,e.getMessage(),e);
            throw new IOException("Erro ao ler os dados: " + e.getMessage(), e);
        }
        return carros;
    }
}