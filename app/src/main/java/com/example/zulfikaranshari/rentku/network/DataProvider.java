package com.example.zulfikaranshari.rentku.network;

/**
 * Created by OJI on 18/04/2018.
 */

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by tanwir on 27/07/2017.
 */
public class DataProvider {

    private DataService nService;
    private Retrofit mRetrofitClient;

    /**
     * config Retrofit in initialization
     */
    public DataProvider() {

        OkHttpClient httpClient = new OkHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.BASE_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        nService = retrofit.create(DataService.class);
    }


    public DataService getTService() {
        return nService;
    }

    public Retrofit getRetrofitClient() {
        return mRetrofitClient;
    }
}
