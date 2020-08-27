package com.yijun.beauty;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yijun.beauty.api.NetworkClient;
import com.yijun.beauty.api.UserApi;
import com.yijun.beauty.model.Rows;
import com.yijun.beauty.model.UserRes;
import com.yijun.beauty.url.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MyInfo extends AppCompatActivity {

    TextView txt_nick_name;
    TextView txt_name;
    TextView txt_phone;
    TextView txt_created_at;
    Button btn_die;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);

        txt_nick_name = findViewById(R.id.txt_nick_name);
        txt_name = findViewById(R.id.txt_name);
        txt_phone = findViewById(R.id.txt_phone);
        txt_created_at = findViewById(R.id.txt_created_at);


        sp=getSharedPreferences(
                Utils.PREFERENCES_NAME,MODE_PRIVATE);
        final String token = sp.getString("token",null);

        Retrofit retrofit = NetworkClient.getRetrofitClient(MyInfo.this);
        UserApi userApi = retrofit.create(UserApi.class);
        Call<Rows> call = userApi.myInfo("Bearer "+token);
        call.enqueue(new Callback<Rows>() {
            @Override
            public void onResponse(Call<Rows> call, Response<Rows> response) {
                if (response.isSuccessful()){
                    if (response.body().isSuccess()){
                        String nick_name = response.body().getNick_name();
                        String name = response.body().getName();
                        String phone = response.body().getPhone();
                        String created_at = response.body().getCreated_at();

                        txt_nick_name.setText("닉네임 : "+nick_name);
                        txt_name.setText("이름 : "+name);
                        txt_phone.setText("전화번호 : "+phone);

                        SimpleDateFormat df = new SimpleDateFormat("YYYY-mm-dd", Locale.getDefault());
                        df.setTimeZone(TimeZone.getTimeZone("UTC"));    // 위의 시간을 utc로 맞추는것.(우리는 이미 서버에서 utc로 맞춰놔서 안해도 되는데 혹시몰라서 해줌)
                        try {
                            Date date = df.parse(created_at);
                            df.setTimeZone(TimeZone.getDefault());      // 내 폰의 로컬 타임존으로 바꿔줌.
                            String strDate = df.format(date);
                            txt_created_at.setText("가입날짜 : "+strDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<Rows> call, Throwable t) {
                Log.i("AAAA","? ",t);
            }
        });


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
