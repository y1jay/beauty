package com.yijun.beauty.api;

import android.content.Context;

import com.yijun.beauty.url.Utils;

public class NetworkClient {

    public static Retrofit retrofit;

    public static Retrofit getRetrofitClient(Context context){
        if(retrofit == null){
            OkHttpClient httpClient = new OkHttpClient.Builder().build();
            retrofit = new Retrofit.Builder().baseUrl(Utils.BASE_URL)
                    .client(httpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }return retrofit;
    }
}
