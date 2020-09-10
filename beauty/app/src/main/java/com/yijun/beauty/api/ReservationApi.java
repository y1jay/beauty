package com.yijun.beauty.api;

import com.yijun.beauty.model.ReservationReq;
import com.yijun.beauty.model.ReservationRes;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ReservationApi {

    @POST("/api/v1/reservation/order")
    Call<ReservationRes> addMenu(@Body ReservationReq reservationReq);

    @GET("api/v1/reservation/record")
    Call<ReservationRes> selectMenu(@Query("nick_name") String nick_name);
}
