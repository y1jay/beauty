package com.yijun.beauty.api;

import com.yijun.beauty.model.UserCheck;
import com.yijun.beauty.model.UserReq;
import com.yijun.beauty.model.UserRes;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface UserApi {
    // http 메소드와 경로 설정
  @POST("/api/v1/user/add")
    Call<UserRes> createUser(@Body UserReq userReq);

  @GET("/api/v1/user/check")
    Call<UserCheck> checkUser(@Query("email") String email);

  @PUT("/api/v1/user/change")
    Call<UserRes> changeUser(@Query("email") String email,
                             @Query("new_nick_name") String new_nick_name);

  @DELETE("/api/v1/user/del")
    Call<UserRes> delUser (@Query("email") String email);

}
