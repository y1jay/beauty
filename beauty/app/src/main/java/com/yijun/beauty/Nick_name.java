package com.yijun.beauty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yijun.beauty.api.NetworkClient;
import com.yijun.beauty.api.UserApi;
import com.yijun.beauty.model.UserCheck;
import com.yijun.beauty.model.UserReq;
import com.yijun.beauty.model.UserRes;
import com.yijun.beauty.url.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Nick_name extends AppCompatActivity {
Button btn_check;
TextView txt_email;
EditText edit_nick_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nick_name);

        btn_check = findViewById(R.id.btn_check);
        txt_email = findViewById(R.id.txt_email);
        edit_nick_name = findViewById(R.id.edit_nick_name);

        String email = getIntent().getStringExtra("email");

        txt_email.setText("이메일  "+email);

        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = getIntent().getStringExtra("email");
                String nick_name = edit_nick_name.getText().toString().trim();
                if (nick_name.isEmpty()){
                    Toast.makeText(Nick_name.this,"닉네임을 입력해주세요.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                UserReq userReq = new UserReq(email,nick_name);

                Retrofit retrofit = NetworkClient.getRetrofitClient(Nick_name.this);
                UserApi userApi = retrofit.create(UserApi.class);

                Call<UserRes> call = userApi.createUser(userReq);

                call.enqueue(new Callback<UserRes>() {
                    @Override
                    public void onResponse(Call<UserRes> call, Response<UserRes> response) {
                        // 상태코드가 200 인지 확인
                        if (response.isSuccessful()){

                            Intent i = new Intent(Nick_name.this,AfterLogin.class);
                            i.putExtra("nick_name", nick_name);
                            finish();
                            startActivity(i);

                        }
                        else if (response.isSuccessful()==false){
                            Toast.makeText(Nick_name.this,"닉네임이 중복되었습니다.",Toast.LENGTH_SHORT).show();
                            Log.i("tlqkf", "tlqkftlqkf");
                        }

                    }

                    @Override
                    public void onFailure(Call<UserRes> call, Throwable t) {

                    }
                });


            }
        });
    }
}