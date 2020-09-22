package com.yijun.beauty.api;

import com.yijun.beauty.model.ReviewRes;

import com.yijun.beauty.model.Rows;
import com.yijun.beauty.model.UserRes;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ReviewApi {

    @POST("/api/v1/review/add")
    Call<UserRes> createReview(@Query("nick_name") String nick_name,
                               @Body Rows rows);

    @GET("/api/v1/review/select")
    Call<ReviewRes> selectReview(@Query("offset") int offset,
                                 @Query("limit")int limit);

    @GET("/api/v1/review/my")
    Call<ReviewRes> myReview(@Query("nick_name")String nick_name,
                             @Query("offset") int offset,
                             @Query("limit")int limit);


    @FormUrlEncoded
    @DELETE("/api/v1/review/delete")
    Call<ReviewRes> deleteMyReview(@Field("nick_name") String nick_name,
                                     @Field("id") int id);


}
