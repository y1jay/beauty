package com.yijun.beauty;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.yijun.beauty.api.NetworkClient;
import com.yijun.beauty.api.UserApi;
import com.yijun.beauty.model.UserRes;
import com.yijun.beauty.url.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MyInfo extends AppCompatActivity {


    Button btn_die;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);


        btn_die = findViewById(R.id.btn_die);
        btn_die.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp=getSharedPreferences(
                        Utils.PREFERENCES_NAME,MODE_PRIVATE);
                final String token = sp.getString("token",null);

                Retrofit retrofit = NetworkClient.getRetrofitClient(MyInfo.this);
                UserApi userApi = retrofit.create(UserApi.class);
                Call<UserRes> call = userApi.delUser("Bearer "+token);
                call.enqueue(new Callback<UserRes>() {
                    @Override
                    public void onResponse(Call<UserRes> call, Response<UserRes> response) {
                        if (response.isSuccessful()){
                            if (response.body().isSuccess()){
                                sp = getSharedPreferences(Utils.PREFERENCES_NAME,MODE_PRIVATE);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString("token",null);
                                editor.putBoolean("auto_login",false);
                                editor.apply();
                                Intent i = new Intent(MyInfo.this,MainActivity.class);
                                i.putExtra("key",1);

                                finish();
                                startActivity(i);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UserRes> call, Throwable t) {
                        Log.i("AAAA","? ",t);
                    }
                });
            }
        });

    }
}
