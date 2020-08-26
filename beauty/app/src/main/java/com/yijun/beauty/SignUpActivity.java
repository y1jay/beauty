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
import com.yijun.beauty.model.UserReq;
import com.yijun.beauty.model.UserRes;
import com.yijun.beauty.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignUpActivity extends AppCompatActivity {
    EditText edtname;
    EditText edtphoe;
    Button btncheck;
    EditText edtid;
    Button btnidcheck;
    EditText edtpasswd;
    EditText edtpasswdcheck;
    Button btnpasswdcheck;
    Button btnsignup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        edtname = findViewById(R.id.edtname);
        edtphoe = findViewById(R.id.edtphone);
        btncheck = findViewById(R.id.btncheck);
        edtid = findViewById(R.id.edtid);
        btnidcheck = findViewById(R.id.btnidcheck);
        edtpasswd = findViewById(R.id.edtpasswd);
        edtpasswdcheck = findViewById(R.id.edtpasswdcheck);
        btnpasswdcheck = findViewById(R.id.btnpasswdcheck);
        btnsignup = findViewById(R.id.btnsignup);


        btncheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = edtphoe.getText().toString().trim();

                if(phone == null){
                    Toast.makeText(SignUpActivity.this,"핸드폰 번호를 입력하세요",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });


        btnidcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nick_name = edtid.getText().toString().trim();
                String name = edtname.getText().toString().trim();
                String passwd = edtpasswd.getText().toString().trim();
                String phone = edtphoe.getText().toString().trim();

                UserReq userReq = new UserReq(name,nick_name,passwd,phone);

                Retrofit retrofit = NetworkClient.getRetrofitClient(SignUpActivity.this);
                UserApi userApi = retrofit.create(UserApi.class);

                Call<UserRes> call = userApi.createUser(userReq);

                call.enqueue(new Callback<UserRes>() {
                    @Override
                    public void onResponse(Call<UserRes> call, Response<UserRes> response) {
                        // 상태코드가 200 인지 확인
                        if (response.isSuccessful()){
                              Toast.makeText(SignUpActivity.this,"이미 있는 아이디입니다",Toast.LENGTH_SHORT).show();
                              return;
                        }
                    }
                    @Override
                    public void onFailure(Call<UserRes> call, Throwable t) {
                    }
                });
                }
        });
        btnpasswdcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String passwd  = edtpasswd.getText().toString();
                String passwdcheck = edtpasswdcheck.getText().toString();

                if (passwd.length() >13 || passwd.length() < 5){
                    Toast.makeText(SignUpActivity.this, "비밀번호를 6이상 13이하로",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                if (passwd.compareTo(passwdcheck)!= 0){
                    Toast.makeText(SignUpActivity.this,
                            "비밀번호가 일치하지않습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(passwd.compareTo(passwdcheck)== 0){
                    Toast.makeText(SignUpActivity.this,
                            "비밀번호가 일치합니다", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String nick_name = edtid.getText().toString().trim();
                final String name = edtname.getText().toString().trim();
                final String passwd = edtpasswd.getText().toString().trim();
                final String phone = edtphoe.getText().toString().trim();

                UserReq userReq = new UserReq(name,nick_name,passwd,phone);

                Retrofit retrofit = NetworkClient.getRetrofitClient(SignUpActivity.this);
                UserApi userApi = retrofit.create(UserApi.class);

                Call<UserRes> call = userApi.createUser(userReq);

                call.enqueue(new Callback<UserRes>() {
                    @Override
                    public void onResponse(Call<UserRes> call, Response<UserRes> response) {
                        // 상태코드가 200 인지 확인
                        if (response.isSuccessful()){
                            // response.body() 가 UserRes.이다.
                            boolean success = response.body().isSuccess();
                            String token = response.body().getToken();
                            Log.i("AAAA","success : "+success +" token : " + token);

//                            SharedPreferences sp = getSharedPreferences(Utils.PREFERENCES_NAME,MODE_PRIVATE);
//                            SharedPreferences.Editor editor = sp.edit();
//                            editor.putString("token",token);
//                            editor.apply();

                            Intent i = new Intent(SignUpActivity.this,AfterLogin.class);

                            startActivity(i);
                            finish();

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
