package com.yijun.beauty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kakao.auth.AuthType;
import com.kakao.auth.Session;
import com.kakao.usermgmt.LoginButton;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.yijun.beauty.api.NetworkClient;
import com.yijun.beauty.api.UserApi;
import com.yijun.beauty.model.FindReq;
import com.yijun.beauty.model.ID;
import com.yijun.beauty.model.UserCheck;
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

    Button address;
    Button signUp;
    private AlertDialog dialog;
    private AlertDialog dialog1;
    private AlertDialog dialog2;

    EditText findPhone;

    EditText findName;
    EditText passName;
    EditText passID;
    EditText passPhone;
    EditText newPass;

    Button btnSet;
    Button btnIDNO;
    Button btnPASSNO;

    Button find;
    SharedPreferences sp;

    private Button btn_custom_login;


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


        btn_custom_login = (Button) findViewById(R.id.btn_custom_login);

        btn_custom_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Session session = Session.getCurrentSession();
                session.addCallback(new SessionCallback());
                session.open(AuthType.KAKAO_LOGIN_ALL, MainActivity.this);

                reservation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(MainActivity.this, Reservation.class);
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
        });

        reservation = findViewById(R.id.reservation);

        address = findViewById(R.id.address);



        reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "로그인 후 이용가능합니다.", Toast.LENGTH_LONG).show();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menus, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.myInfo){
            Intent i = new Intent(MainActivity.this, MyInfo.class);
            startActivity(i);
            return true;
        }else if (id == R.id.reservation_check){
            Intent i = new Intent(MainActivity.this, ReservationRecord.class);
            startActivity(i);
            return true;
        }else if (id == R.id.logout){
            /**카카오톡 로그아웃 요청**/
            //한번 로그인이 성공하면 세션 정보가 남아있어서 로그인창이 뜨지 않고 바로 onSuccess()메서드를 호출합니다.
            //테스트 하시기 편하라고 매번 로그아웃 요청을 수행하도록 코드를 넣었습니다 ^^
            UserManagement.requestLogout(new LogoutResponseCallback() {
                @Override
                public void onCompleteLogout() {
                    //로그아웃 성공 후 하고싶은 내용 코딩 ~
                    Toast.makeText(MainActivity.this, "로그아웃되었습니다.", Toast.LENGTH_LONG).show();
                }
            });
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();

    }
//    public void createPopupDialog(){
//        AlertDialog.Builder alert = new AlertDialog.Builder
//                (MainActivity.this);
//        View alertView = getLayoutInflater().inflate(R.layout.login,null);
//        editID = alertView.findViewById(R.id.editID);
//        editPasswd = alertView.findViewById(R.id.editPasswd);
//        txtNO = alertView.findViewById(R.id.txtNO);
//        txtYES = alertView.findViewById(R.id.txtYES);
//         Auto = alertView.findViewById(R.id.Auto);
//        find_id = alertView.findViewById(R.id.find_id);
//        find_pass = alertView.findViewById(R.id.find_pass);
//
//
//        txtYES.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String nick_name = editID.getText().toString().trim();
//                String passwd= editPasswd.getText().toString().trim();
//                if(nick_name.isEmpty()){
//                    Toast.makeText(MainActivity.this,"아이디를 입력하세요",
//                            Toast.LENGTH_SHORT).show();
//                    return;
//                }else if ( passwd.isEmpty()){
//                    Toast.makeText(MainActivity.this,"비밀번호를 입력하세요",
//                            Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                UserReq userReq = new UserReq(nick_name,passwd);
//
//                Retrofit retrofit = NetworkClient.getRetrofitClient(MainActivity.this);
//                UserApi userApi = retrofit.create(UserApi.class);
//
//                Call<UserRes> call = userApi.loginUser(userReq);
//
//                call.enqueue(new Callback<UserRes>() {
//                    @Override
//                    public void onResponse(Call<UserRes> call, Response<UserRes> response) {
//                        // 상태코드가 200 인지 확인
//                        if (response.isSuccessful()){
//                            // response.body() 가 UserRes.이다.
//                            boolean success = response.body().isSuccess();
//                            String token = response.body().getToken();
//                            Log.i("AAAA","success : "+success +" token : " + token);
//
//                              SharedPreferences sp = getSharedPreferences(Utils.PREFERENCES_NAME, MODE_PRIVATE);
//                              SharedPreferences.Editor editor = sp.edit();
//                              editor.putString("token", token);
//
//                              editor.apply();
//
//                            Intent i = new Intent(MainActivity.this,AfterLogin.class);
//                            Toast.makeText(MainActivity.this,"환영합니다.",Toast.LENGTH_SHORT).show();
//
//                            startActivity(i);
//                            finish();
//                            dialog.cancel();
//                        } else if (response.isSuccessful()==false){
//                            Toast.makeText(MainActivity.this,"입력하신 정보가 맞지 않습니다.",Toast.LENGTH_SHORT).show();
//
//                        }
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<UserRes> call, Throwable t) {
//
//                    }
//                });
//
//               if (Auto.isChecked()){
//                   SharedPreferences.Editor editor = sp.edit();
//                   editor.putBoolean("auto_login",true);
//                   editor.apply();
//               }else{
//                   SharedPreferences.Editor editor = sp.edit();
//                   editor.putBoolean("auto_login",false);
//                   editor.apply();
//               }
//
//
//
//            }
//        });
//        txtNO.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               dialog.cancel();
//            }
//        });
//        find_id.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            find_idPopupDialog();
//            }
//        });
//        find_pass.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//              find_passPopupDialog();
//            }
//        });
//
//
//        alert.setView(alertView);
//
//        dialog=alert.create();
//        dialog.setCancelable(false);
//        dialog.show();
//    }



}