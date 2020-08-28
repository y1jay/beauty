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
import com.yijun.beauty.model.UserCheck;
import com.yijun.beauty.model.UserReq;
import com.yijun.beauty.model.UserRes;
import com.yijun.beauty.url.Utils;

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
                if (nick_name.isEmpty()){
                    Toast.makeText(SignUpActivity.this,"아이디를 입력해주세요",Toast.LENGTH_SHORT).show();
                    return;
                }
                UserReq userReq = new UserReq(nick_name);

                Retrofit retrofit = NetworkClient.getRetrofitClient(SignUpActivity.this);
                UserApi userApi = retrofit.create(UserApi.class);

                Call<UserCheck> call = userApi.checkId(userReq);
                call.enqueue(new Callback<UserCheck>() {
                    @Override
                    public void onResponse(Call<UserCheck> call, Response<UserCheck> response) {
                        // 상태코드가 200 인지 확인
                        if (response.isSuccessful()==false){
                              Toast.makeText(SignUpActivity.this,"이미 있는 아이디입니다",Toast.LENGTH_SHORT).show();
                              edtid.setText("");
                        }else{
                            Toast.makeText(SignUpActivity.this,"사용 가능한 아이디입니다.",Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<UserCheck> call, Throwable t) {
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
                final String passwdcheck = edtpasswdcheck.getText().toString().trim();
                if (name.isEmpty()){
                    Toast.makeText(SignUpActivity.this,"이름를 입력해주세요",Toast.LENGTH_SHORT).show();
                  return;
                }else if (name.length()<2){
                    Toast.makeText(SignUpActivity.this,"이름은 두글자 부터 가능합니다",Toast.LENGTH_SHORT).show();
                    return;
                }else if (name.length()>10){
                    Toast.makeText(SignUpActivity.this,"이름를 입력해주세요",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (phone.isEmpty()){
                    Toast.makeText(SignUpActivity.this,"휴대폰 번호를을 입력해주세요",Toast.LENGTH_SHORT).show();
                    return;
                }else if (nick_name.isEmpty()){
                    Toast.makeText(SignUpActivity.this,"아이디를 입력해주세요",Toast.LENGTH_SHORT).show();
                    return;
                }else if (passwd.isEmpty()){
                    Toast.makeText(SignUpActivity.this,"비밀번호를 입력해주세요",Toast.LENGTH_SHORT).show();
                    return;
                } else if (passwd.equals(passwdcheck)==false){
                Toast.makeText(SignUpActivity.this,"비밀번호를 확인해주세요",Toast.LENGTH_SHORT).show();
                return;
            }





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

                            SharedPreferences sp = getSharedPreferences(Utils.PREFERENCES_NAME,MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("token",token);
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
