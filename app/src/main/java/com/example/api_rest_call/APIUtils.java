package com.example.api_rest_call;

import retrofit2.Retrofit;

public class APIUtils {
    private APIUtils() {

    }

    ;

    public static final String API_URL = "https://us-central1-be-tp3-a.cloudfunctions.net/";

    public static AutoService getAutoService() {
        return RetrofitClient.getClient(API_URL).create(AutoService.class);
    }
}
