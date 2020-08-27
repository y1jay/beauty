package com.yijun.beauty;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yijun.beauty.api.NetworkClient;
import com.yijun.beauty.api.UserApi;
import com.yijun.beauty.model.UserReq;
import com.yijun.beauty.model.UserRes;
import com.yijun.beauty.url.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button reservation;
    Button login;
    Button address;
    Button signUp;
    private AlertDialog dialog;
    EditText editID;
    EditText editPasswd;
    TextView txtNO ;
    TextView txtYES;
    CheckBox Auto;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i = new Intent(MainActivity.this,LodingActivity.class);
        int key = getIntent().getIntExtra("key",0);
        if(key==1){

        }else{
            startActivity(i);
        }

        sp = getSharedPreferences(Utils.PREFERENCES_NAME, MODE_PRIVATE);
        boolean auto_login = sp.getBoolean("auto_login",false);
        if (auto_login==true){
            Intent a = new Intent(MainActivity.this,AfterLogin.class);
            sp = getSharedPreferences(Utils.PREFERENCES_NAME, MODE_PRIVATE);
            String token = sp.getString("token",null);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("token", token);
            editor.putBoolean("auto_login",true);
            editor.apply();
            startActivity(a);
            finish();

        }else if (auto_login == false){
        }
        reservation = findViewById(R.id.reservation);
        login = findViewById(R.id.login);
        address = findViewById(R.id.address);
        signUp = findViewById(R.id.signUp);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                  createPopupDialog();

            }
        });
        reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "로그인 후 이용 가능합니다.",
                        Toast.LENGTH_SHORT).show();
                return;
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SignUpActivity.class);
                finish();
                startActivity(i);
            }
        });
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Address.class);
                i.putExtra("add",1);
                startActivity(i);
            }
        });
    }
    public void createPopupDialog(){
        AlertDialog.Builder alert = new AlertDialog.Builder
                (MainActivity.this);
        View alertView = getLayoutInflater().inflate(R.layout.login,null);
        editID = alertView.findViewById(R.id.editID);
        editPasswd = alertView.findViewById(R.id.editPasswd);
        txtNO = alertView.findViewById(R.id.txtNO);
        txtYES = alertView.findViewById(R.id.txtYES);
         Auto = alertView.findViewById(R.id.Auto);


        txtYES.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nick_name = editID.getText().toString().trim();
                String passwd= editPasswd.getText().toString().trim();
                if(nick_name.isEmpty()){
                    Toast.makeText(MainActivity.this,"아이디를 입력하세요",
                            Toast.LENGTH_SHORT).show();
                    return;
                }else if ( passwd.isEmpty()){
                    Toast.makeText(MainActivity.this,"비밀번호를 입력하세요",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                UserReq userReq = new UserReq(nick_name,passwd);

                Retrofit retrofit = NetworkClient.getRetrofitClient(MainActivity.this);
                UserApi userApi = retrofit.create(UserApi.class);

                Call<UserRes> call = userApi.loginUser(userReq);

                call.enqueue(new Callback<UserRes>() {
                    @Override
                    public void onResponse(Call<UserRes> call, Response<UserRes> response) {
                        // 상태코드가 200 인지 확인
                        if (response.isSuccessful()){
                            // response.body() 가 UserRes.이다.
                            boolean success = response.body().isSuccess();
                            String token = response.body().getToken();
                            Log.i("AAAA","success : "+success +" token : " + token);

                              SharedPreferences sp = getSharedPreferences(Utils.PREFERENCES_NAME, MODE_PRIVATE);
                              SharedPreferences.Editor editor = sp.edit();
                              editor.putString("token", token);
                              editor.putString("ID",nick_name);
                              editor.putString("passwd",passwd);
                              editor.apply();

                            Intent i = new Intent(MainActivity.this,AfterLogin.class);
                            Toast.makeText(MainActivity.this,"환영합니다.",Toast.LENGTH_SHORT).show();

                            startActivity(i);
                            finish();
                            dialog.cancel();
                        } else if (response.isSuccessful()==false){
                            Toast.makeText(MainActivity.this,"입력하신 정보가 맞지 않습니다.",Toast.LENGTH_SHORT).show();

                        }

                    }

                    @Override
                    public void onFailure(Call<UserRes> call, Throwable t) {

                    }
                });

               if (Auto.isChecked()){
                   SharedPreferences.Editor editor = sp.edit();
                   editor.putBoolean("auto_login",true);
                   editor.apply();
               }else{
                   SharedPreferences.Editor editor = sp.edit();
                   editor.putBoolean("auto_login",false);
                   editor.apply();
               }



            }
        });
        txtNO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });




        alert.setView(alertView);

        dialog=alert.create();
        dialog.setCancelable(false);
        dialog.show();
    }
}