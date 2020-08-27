package com.yijun.beauty.api;

import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.yijun.beauty.model.Rows;
import com.yijun.beauty.model.UserCheck;
import com.yijun.beauty.model.UserReq;
import com.yijun.beauty.model.UserRes;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UserApi {
    // http 메소드와 경로 설정
  @POST("/api/v1/user/add")

    Call<UserRes> createUser(@Body UserReq userReq);

  @POST("/api/v1/user/login")

    Call<UserRes> loginUser(@Body UserReq userReq);

  @POST("/api/v1/user/checkID")
    Call<UserCheck> checkId (@Body UserReq userReq);

  @DELETE("/api/v1/user/logout")
  Call<UserRes> logoutUser (@Header("Authorization") String token);

  @DELETE("/api/v1/user/del")
  Call<UserRes> delUser (@Header("Authorization") String token);

  @GET("/api/v1/user/me")
  Call<Rows> myInfo (@Header("Authorization") String token);
}
