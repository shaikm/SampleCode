package com.nytimes.application.api;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.nytimes.application.api.NyClient.getClient;

public class ApiManager {

    private final Retrofit client;
    private final String apiKey = "Ag3kGAyLkdSHT1hyi5dsR7DmmXiUrL6W";

    public ApiManager() {
        client = getClient();
    }


    public void getArticles(String period,final ApiCallback<Article> callback) {
        NyInterface services = client.create(NyInterface.class);
        services.getAllArticles(period, apiKey).enqueue(new Callback<Article>() {
            @Override
            public void onResponse(Call<Article> call, Response<Article> response) {
                if (response.isSuccessful()) {
                    callback.success(response.body());
                } else {
                    callback.failure(response.code());
                }
            }

            @Override
            public void onFailure(Call<Article> call, Throwable t) {
                callback.failure(500);
            }
        });
    }


    public interface ApiCallback<T> {
        void success(T response);
        void failure(int responseCode);
    }
}


