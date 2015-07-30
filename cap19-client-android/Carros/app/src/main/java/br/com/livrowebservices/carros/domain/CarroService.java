package br.com.livrowebservices.carros.domain;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.DOMException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import br.com.livrowebservices.carros.utils.HttpHelper;

public class CarroService {
    private static final String URL_BASE = "http://livrowebservices.com.br/rest/carros";
    private static final boolean LOG_ON = true;
    private static final String TAG = "CarroService";

    public static List<Carro> getCarros(Context context, String tipo) throws IOException {
        String url = URL_BASE + "/tipo/" + tipo;
        String json = HttpHelper.doGet(url);
        List<Carro> carros = parserJSON(context, json);
        return carros;
    }

    public static Response saveCarro(Context context,Carro carro) throws IOException {
        String url = URL_BASE;

        String jsonCarro = new Gson().toJson(carro);
        Log.d(TAG,">> saveCarro: " + jsonCarro);
        String json = HttpHelper.doPost(url,jsonCarro.getBytes(),"UTF-8");
        Log.d(TAG,"<< saveCarro: " + json);

        Response response = new Gson().fromJson(json, Response.class);

        return response;
    }

    public static List<Carro> buscaCarros(Context context, String nome) throws IOException {
        String url = URL_BASE + "/nome/" + nome;
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
                c.id = jsonCarro.optLong("id");
                c.tipo = jsonCarro.optString("tipo");
                c.nome = jsonCarro.optString("nome");
                c.desc = jsonCarro.optString("desc");
                c.urlFoto = jsonCarro.optString("urlFoto");
                c.urlInfo = jsonCarro.optString("urlInfo");
                c.latitude = jsonCarro.optString("latitude");
                c.longitude = jsonCarro.optString("longitude");
                if (LOG_ON) {
                    Log.d(TAG, "Carro ("+c.id+") " + c.nome + " > " + c.urlFoto);
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

    public static Response delete(Context context, Carro c) throws IOException {
        String url = URL_BASE + "/"+c.id;
        String json = HttpHelper.doDelete(url);
        Log.d(TAG,"Delete json: " + json);

        Gson gson = new Gson();
        Response response = gson.fromJson(json,Response.class);
        return response;
    }


}