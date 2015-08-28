package br.com.livrowebservices.carros.rest;

import java.util.List;

import br.com.livrowebservices.carros.domain.Carro;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by ricardo on 08/08/15.
 */
public interface CarroREST {

    @GET("/carros/tipo/{tipo}")
    public List<Carro> getCarros(@Path("tipo") String tipo);

    @POST("/carros")
    public Response saveCarro(@Body Carro carro);

    @DELETE("/carros/{id}")
    public Response delete(@Path("id") Long id);
}
