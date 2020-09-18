package com.yijun.beauty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yijun.beauty.api.NetworkClient;
import com.yijun.beauty.api.UserApi;
import com.yijun.beauty.model.BeautyReq;
import com.yijun.beauty.model.UserCheck;
import com.yijun.beauty.model.UserReq;
import com.yijun.beauty.model.UserRes;
import com.yijun.beauty.url.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class SignUpActivity extends AppCompatActivity {

    EditText edtphoe;
    EditText edtid;
    Button btnidcheck;

    Button btnsignup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        edtphoe = findViewById(R.id.edtphone);

        edtid = findViewById(R.id.edtid);
        btnidcheck = findViewById(R.id.btnidcheck);
        btnsignup = findViewById(R.id.btnsignup);




        btnidcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nick_name = edtid.getText().toString().trim();
                if (nick_name.isEmpty()){
                    Toast.makeText(SignUpActivity.this,"아이디를 입력해주세요",Toast.LENGTH_SHORT).show();
                    return;
                }

                UserReq userReq = new UserReq(nick_name);

                Retrofit retrofit = NetworkClient.getRetrofitClient(SignUpActivity.this);
                UserApi userApi = retrofit.create(UserApi.class);

                Call<UserRes> call = userApi.checkId(userReq);
                call.enqueue(new Callback<UserRes>() {
                    @Override
                    public void onResponse(Call<UserRes> call, Response<UserRes> response) {
                        // 상태코드가 200 인지 확인
                        if (response.isSuccessful()==false){
                              Toast.makeText(SignUpActivity.this,
                                      "이미 있는 닉네임입니다",Toast.LENGTH_SHORT).show();
                              edtid.setText("");
                        }else{
                            Toast.makeText(SignUpActivity.this,
                                    "사용 가능한 닉네임입니다.",Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<UserRes> call, Throwable t) {
                    }
                });
                }
        });

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String nick_name = edtid.getText().toString().trim();
                final String phone = edtphoe.getText().toString().trim();
                final Boolean info_agree = true;
                if (nick_name.isEmpty()){
                    Toast.makeText(SignUpActivity.this,"닉네임을 입력해주세요",Toast.LENGTH_SHORT).show();
                  return;
                }else if (phone.isEmpty()){
                    Toast.makeText(SignUpActivity.this,"휴대폰 번호를을 입력해주세요",Toast.LENGTH_SHORT).show();
                    return;
            }

               BeautyReq beautyReq = new BeautyReq(nick_name,phone,info_agree);

                Retrofit retrofit = NetworkClient.getRetrofitClient(SignUpActivity.this);
                UserApi userApi = retrofit.create(UserApi.class);

                Call<UserRes> call = userApi.beautyUser(beautyReq);

                call.enqueue(new Callback<UserRes>() {
                    @Override
                    public void onResponse(Call<UserRes> call, Response<UserRes> response) {
                        // 상태코드가 200 인지 확인
                        if (response.isSuccessful()){
                            // response.body() 가 UserRes.이다.
                            boolean success = response.body().isSuccess();

                            SharedPreferences sp = getSharedPreferences(Utils.PREFERENCES_NAME,MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("nick_name",nick_name);
                            editor.putString("phone_number",phone);
                            editor.putBoolean("info_agree",info_agree);
                            editor.apply();

                            Intent i = new Intent(SignUpActivity.this,AfterLogin.class);
                            Toast.makeText(SignUpActivity.this,"회원가입이 완료되었습니다.",Toast.LENGTH_SHORT).show();

                            startActivity(i);
                            finish();

                        } else if (response.isSuccessful()==false){
                            Toast.makeText(SignUpActivity.this,"입력하신 정보가 맞지 않습니다.",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserRes> call, Throwable t) {

                    }
                });
            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(SignUpActivity.this,MainActivity.class);
        i.putExtra("key",1);
        finish();
        startActivity(i);
    }

}
