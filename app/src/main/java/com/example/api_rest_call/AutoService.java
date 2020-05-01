package com.example.api_rest_call;

import android.text.style.UpdateAppearance;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AutoService {

    /**
     * Definicion de ruta para GET
     */
    String API_ROUTE_ALL = "app/api/read";

    /**
     * Metodo abstracto para utilizar HTTP.GET
     *
     * @return
     */
    @GET(API_ROUTE_ALL)
    Call<List<Auto>> getAutos();

    /**
     * Definicion de ruta para que muestre un auto.
     */

    String API_ROUTE_GET_ITEM = "app/api/read/{id}";

    @GET(API_ROUTE_GET_ITEM)
    Call<Auto> getAuto(@Path("id") String idAuto);

    /**
     * Definicion de ruta pare el Update.
     */

    String API_ROUTE_UPDATE = "app/api/update/{id}";

    @PUT(API_ROUTE_UPDATE)
    Call<Auto> updateAuto(@Path("id") String idAuto);


    /**
     *  Definicion de ruta para borrar auto.
     */
    String API_ROUTE_DELETE = "app/api/delete/{id}";

    @DELETE(API_ROUTE_DELETE)
    Call<Auto> removeAuto(@Path("id") String idAuto);

    /**
     *  Definicion de ruta para crear auto.
     */

    String API_ROUTE_CREATE = "/app/api/create";
    @POST(API_ROUTE_CREATE)
    Call<Auto> createAuto(Auto auto);


}
