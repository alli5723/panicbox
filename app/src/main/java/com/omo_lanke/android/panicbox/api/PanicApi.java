package com.omo_lanke.android.panicbox.api;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by omo_lanke on 31/12/2017.
 */

public interface PanicApi {
    @FormUrlEncoded
    @POST("/api/panic/register")
    void register(@Field("username") String username, @Field("first_name") String first_name, @Field("last_name") String last_name,
               @Field("email") String email, @Field("phone") String phone, @Field("address") String address,
                  @Field("em_phone") String emergency_phone, @Field("em_email") String emergency_email,
                  @Field("em_first_name") String em_first_name, @Field("em_last_name") String em_last_name, Callback<Response> cb);

    @GET("/api/panic")
    void incidents(Callback<Response> cb);

    @FormUrlEncoded
    @POST("/api/panic")
    void panic(@Field("incident") String report_type, @Field("lat") String lat,
               @Field("lng") String lng, Callback<Response> cb);

    @FormUrlEncoded
    @POST("/api/panic")
    void panic2(@Field("username") String username, @Field("incident") String report_type,
                @Field("lat") String lat, @Field("lng") String lng, Callback<Response> cb);
}

