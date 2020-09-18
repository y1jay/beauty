package com.yijun.beauty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yijun.beauty.api.NetworkClient;
import com.yijun.beauty.api.UserApi;
import com.yijun.beauty.model.BeautyReq;
import com.yijun.beauty.model.UserCheck;
import com.yijun.beauty.model.UserRes;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignUpActivity extends AppCompatActivity {
    EditText edtid;
    Button btnidcheck;
    TextView txtphone;
    Button btnsignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        txtphone = findViewById(R.id.txtphone);

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

//                Nick_name nick_name1 = new Nick_name(nick_name);

                Retrofit retrofit = NetworkClient.getRetrofitClient(SignUpActivity.this);
                UserApi userApi = retrofit.create(UserApi.class);

                Call<UserCheck> call = userApi.checkId(nick_name);
                call.enqueue(new Callback<UserCheck>() {
                    @Override
                    public void onResponse(Call<UserCheck> call, Response<UserCheck> response) {
                        // 상태코드가 200 인지 확인
                        if (response.isSuccessful()==false){
                            Toast.makeText(SignUpActivity.this,"이미있는 닉네임입니다.",Toast.LENGTH_SHORT).show();
                            edtid.setText("");
                            return;
                        }else{
                            Toast.makeText(SignUpActivity.this,"사용가능한 닉네임입니다.",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    @Override
                    public void onFailure(Call<UserCheck> call, Throwable t) {
                    }
                });
            }
        });

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String nick_name = edtid.getText().toString().trim();
                final String phone = txtphone.getText().toString().trim();
                final Boolean info_agree = true;
                if (nick_name.isEmpty()){
                    Toast.makeText(SignUpActivity.this,"닉네임을 입력해주세요",Toast.LENGTH_SHORT).show();
                    return;
                }else if (phone.isEmpty()){
                    Toast.makeText(SignUpActivity.this,"휴대폰 번호를을 입력해주세요",Toast.LENGTH_SHORT).show();
                    return;
                }

//                BeautyReq beautyReq = new BeautyReq(nick_name,phone,info_agree);

                Retrofit retrofit = NetworkClient.getRetrofitClient(SignUpActivity.this);
                UserApi userApi = retrofit.create(UserApi.class);

                Call<UserRes> call = userApi.beautyUser(nick_name,phone,info_agree);

                call.enqueue(new Callback<UserRes>() {
                    @Override
                    public void onResponse(Call<UserRes> call, Response<UserRes> response) {
                        // 상태코드가 200 인지 확인
                        if (response.isSuccessful()){
                            // response.body() 가 UserRes.이다.
                            boolean success = response.body().isSuccess();


                            Intent i = new Intent(SignUpActivity.this,AfterLogin.class);
                            Toast.makeText(SignUpActivity.this,"회원가입이 완료되었습니다.",Toast.LENGTH_SHORT).show();

                            i.putExtra("nick_name",nick_name);
                            i.putExtra("phone_number",phone);
                            i.putExtra("info_agree",info_agree);
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
