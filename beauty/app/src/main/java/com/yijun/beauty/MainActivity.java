package com.yijun.beauty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
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

import com.kakao.auth.ApiErrorCode;
import com.kakao.auth.AuthType;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.LoginButton;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.OptionalBoolean;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.Utility;
import com.yijun.beauty.api.NetworkClient;
import com.yijun.beauty.api.UserApi;
import com.yijun.beauty.model.FindReq;
import com.yijun.beauty.model.ID;
import com.yijun.beauty.model.UserCheck;
import com.yijun.beauty.model.UserReq;
import com.yijun.beauty.model.UserRes;
import com.yijun.beauty.url.Utils;

import java.security.MessageDigest;

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
    private SessionCallback sessionCallback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent i = new Intent(MainActivity.this, LodingActivity.class);
        int key = getIntent().getIntExtra("key", 0);
        if (key == 1) {

        } else {
            startActivity(i);
        }


        sessionCallback = new SessionCallback(); //SessionCallback 초기화
        Session.getCurrentSession().addCallback(sessionCallback); //현재 세션에 콜백 붙임
        Session.getCurrentSession().checkAndImplicitOpen(); //자동 로그인

//               Session session = Session.getCurrentSession();
//               session.addCallback(new SessionCallback());
//               session.open(AuthType.KAKAO_LOGIN_ALL, MainActivity.this);
//               getAppKeyHash();


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
                i.putExtra("add", 1);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(sessionCallback);
    }

    private class SessionCallback implements ISessionCallback {
        @Override
        public void onSessionOpened() {
            UserManagement.getInstance().me(new MeV2ResponseCallback() {
                @Override
                public void onFailure(ErrorResult errorResult) {
                    int result = errorResult.getErrorCode();

                    if (result == ApiErrorCode.CLIENT_ERROR_CODE) {
                        Toast.makeText(getApplicationContext(), "네트워크 연결이 불안정합니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "로그인 도중 오류가 발생했습니다: " + errorResult.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onSessionClosed(ErrorResult errorResult) {
                    Toast.makeText(getApplicationContext(), "세션이 닫혔습니다. 다시 시도해 주세요: " + errorResult.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSuccess(MeV2Response result) {
                    Intent intent = new Intent(getApplicationContext(), AfterLogin.class);
                    if (result.getKakaoAccount().isEmailValid() == OptionalBoolean.TRUE)
                        intent.putExtra("email", result.getKakaoAccount().getEmail());
                    else
                        intent.putExtra("email", "none");
                        Log.i("email : ", result.getKakaoAccount().getEmail());
                        startActivity(intent);
                        finish();

                }
            });
        }

        @Override
        public void onSessionOpenFailed(KakaoException e) {
            Toast.makeText(getApplicationContext(), "로그인 도중 오류가 발생했습니다. 인터넷 연결을 확인해주세요: " + e.toString(), Toast.LENGTH_SHORT).show();
            Log.e("openfailed ", e.toString());
        }
    }
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



