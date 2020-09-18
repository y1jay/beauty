package com.yijun.beauty.api;

import com.yijun.beauty.model.BeautyReq;
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

  @GET("/api/v1/user/my_info")
    Call<UserCheck> info_User(@Query("nick_name") String nick_name,
                              @Query("phone_number") String phone_number);
  @GET("/api/v1/user/check")
  Call<UserCheck> checkUser(@Query("email") String email);
  //
  @PUT("/api/v1/user/change")
    Call<UserRes> changeUser(@Query("phone_number") String phone_number,
                             @Query("new_nick_name") String new_nick_name,
                             @Query("nick_name") String nick_name);
//
  @DELETE("/api/v1/user/del")
    Call<UserRes> delUser (@Body String phone_number,
                           @Body String nick_name);

//
  @POST("/api/v1/user/beauty_add")
  Call<UserRes> beautyUser(@Body String nick_name,
                           @Body String phone_number,
                           @Body Boolean info_agree);

  @GET("/api/v1/user/login")
  Call<UserRes> loginUser(@Body UserReq userReq);

  @GET("/api/v1/user/check_id")
  Call<UserCheck> checkId (@Query("nick_name") String nick_name);



  @POST("/api/v1/user/find_id")
  Call<UserRes> findID (@Body String phone_number);




}
