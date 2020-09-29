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

    // 클라이언트가 주문한 목록 디비에  넣어주는 api
    @POST("/api/v1/reservation/order")
    Call<ReservationRes> addMenu(@Body ReservationReq reservationReq);

    // 클라이언트가 주문 완료했을때  디비에 넣어주는 api
    @POST("/api/v1/reservation/paymentorder")
    Call<ReservationRes> paymentOrder();


    // 클라이언트가 주문한 목록 보여주는 api
    @GET("api/v1/reservation/record")
    Call<ReservationRes> selectMenu(@Query("nick_name") String nick_name);

    // 클라이언트가  결제한 주문 목록 보여주는 api
    @GET("api/v1/reservation/myrecord")
    Call<ReservationRes> myselectMenu(@Query("nick_name") String nick_name);


    // 클라이언트가 주문을 취소하면 디비에서 없어지는 api
    @DELETE("api/v1/reservation/delete")
    Call<ReservationRes> deleteMenu(@Query("nick_name") String nick_name,
                                    @Query("menu") String menu,
                                    @Query("price") String price);

    // 클라이언트가 뒤로가기 누르면 디비에서 없어지는 api
    @DELETE("api/v1/reservation/cancle")
    Call<ReservationRes> cancle(@Query("nick_name") String nick_name);

    //  총 금액 보여주는 api
    @GET("api/v1/reservation")
    Call<ReservationRes> total(@Query("nick_name") String nick_name);

    // 매장에서 먹는거 체크하는 api
    @PUT("api/v1/reservation/add_store")
    Call<ReservationRes> add_store(@Query("nick_name") String nick_name,
                                  @Query("people_number") int people_number,
                                  @Query("time") String time);

    // 포장 체크하는 api
    @PUT("api/v1/reservation/add_take_out")
    Call<ReservationRes> add_take_out(@Query("nick_name") String nick_name,
                             @Query("time") String time);
}
