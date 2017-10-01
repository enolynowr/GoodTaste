package com.goodtaste.api;

import com.goodtaste.model.GoodTasteResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Created by LG Electronics on 2017/08/20.
 */

public interface ApiService {

    @Headers({"Accept: application/json"})
    @GET("user/{id}")
    Call<GoodTasteResponseModel> getGoodTasteResponse(@Path("id") String id);

}
