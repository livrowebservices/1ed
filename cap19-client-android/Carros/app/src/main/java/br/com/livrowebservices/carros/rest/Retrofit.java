package br.com.livrowebservices.carros.rest;

import retrofit.RestAdapter;

/**
 * Created by ricardo on 08/08/15.
 */
public class Retrofit {
    public static CarroService getCarroService() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint("http://livrowebservices.com.br/rest/")
                .build();

        CarroService service = restAdapter.create(CarroService.class);

        return service;
    }
}
