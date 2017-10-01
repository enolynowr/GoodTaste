package com.goodtaste.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by LG Electronics on 2017/08/20.
 */

public class ApiServiceImpl {

    public ApiService getApiService (){

        //@@@@@ http setting log
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        HttpLoggingInterceptor intereptor = new HttpLoggingInterceptor();
        intereptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(intereptor);
        OkHttpClient client = httpClient.build();

        //http 通信(Retrofit2)
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConfig.GOOD_TASTE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit.create(ApiService.class);
    }


}
