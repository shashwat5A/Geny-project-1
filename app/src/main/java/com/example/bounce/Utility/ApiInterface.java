package com.example.bounce.Utility;

import com.example.bounce.Model.OtpResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("otp/{fcm_token}")
    Call<OtpResponse> loginUser(@Path("fcm_token") String otp);

}
