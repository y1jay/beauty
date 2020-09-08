package com.yijun.beauty;

import android.app.Activity;
import android.app.Application;

import com.kakao.auth.KakaoSDK;
import com.yijun.beauty.adapter.App;
import com.yijun.beauty.adapter.KakaoSDKAdapter;

public class GlobalApplication extends Application {

    private static GlobalApplication instance;

    public static GlobalApplication getGlobalApplicationContext() {

        if (instance == null) {

            throw new IllegalStateException("This Application does not inherit com.kakao.GlobalApplication");
        }

        return instance;

    }

    @Override

    public void onCreate() {

        super.onCreate();

        instance = this;



        // Kakao Sdk 초기화

        KakaoSDK.init(new KakaoSDKAdapter());

    }

    @Override

    public void onTerminate() {

        super.onTerminate();

        instance = null;

    }

}