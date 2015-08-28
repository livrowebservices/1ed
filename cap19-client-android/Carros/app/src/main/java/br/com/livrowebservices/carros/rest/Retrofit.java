package br.com.livrowebservices.carros.rest;

import retrofit.RestAdapter;

/**
 * Created by ricardo on 08/08/15.
 */
public class Retrofit {
    public static CarroREST getCarroREST() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint("http://livrowebservices.com.br/rest/")
                .build();

        CarroREST service = restAdapter.create(CarroREST.class);

        return service;
    }
}
