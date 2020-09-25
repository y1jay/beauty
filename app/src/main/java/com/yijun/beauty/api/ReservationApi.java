package com.yijun.beauty.api;

import com.yijun.beauty.model.ReservationReq;
import com.yijun.beauty.model.ReservationRes;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface ReservationApi {

    @POST("/api/v1/reservation/order")
    Call<ReservationRes> addMenu(@Body ReservationReq reservationReq);

    @GET("api/v1/reservation/record")
    Call<ReservationRes> selectMenu(@Query("nick_name") String nick_name);

    @DELETE("api/v1/reservation/delete")
    Call<ReservationRes> deleteMenu(@Query("nick_name") String nick_name,
                                    @Query("menu") String menu,
                                    @Query("price") String price);

    @DELETE("api/v1/reservation/cancle")
    Call<ReservationRes> cancle(@Query("nick_name") String nick_name);

    @GET("api/v1/reservation")
    Call<ReservationRes> total(@Query("nick_name") String nick_name);

    @PUT("api/v1/reservation/add_store")
    Call<ReservationRes> add_store(@Query("nick_name") String nick_name,
                                  @Query("people_number") int people_number,
                                  @Query("time") String time);

    @PUT("api/v1/reservation/add_take_out")
    Call<ReservationRes> add_take_out(@Query("nick_name") String nick_name,
                             @Query("time") String time);
}
