package com.yijun.beauty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.kakao.auth.ApiErrorCode;
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
import com.yijun.beauty.api.NetworkClient;
import com.yijun.beauty.api.UserApi;
import com.yijun.beauty.model.UserCheck;
import com.yijun.beauty.network.CheckNetwork;
import com.yijun.beauty.url.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;



public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button reservation;
    Button review;
    Button address;
    Button beauty;

    // 다이얼로그
    AlertDialog dialog;
    Button sign_up;
    Button login;
    LoginButton btn_custom_login;

    private long time = 0;
    SharedPreferences sp;

    private SessionCallback sessionCallback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent i = new Intent(MainActivity.this, LodingActivity.class);
        int key = getIntent().getIntExtra("key", 0);
        Log.i("key","key "+key);
        if (key == 1) {

        } else {
            startActivity(i);
        }


        sessionCallback = new SessionCallback(); //SessionCallback 초기화
        Session.getCurrentSession().addCallback(sessionCallback); //현재 세션에 콜백 붙임
        Session.getCurrentSession().checkAndImplicitOpen(); //자동 로그인


        reservation = findViewById(R.id.reservation);
        address = findViewById(R.id.address);
        review = findViewById(R.id.reviewcyclerView);
        beauty = findViewById(R.id.beauty);

        reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "로그인 후 이용가능합니다.", Toast.LENGTH_LONG).show();
            }
        });

        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!CheckNetwork.isNetworkAvailable(MainActivity.this)){
                    Toast.makeText(MainActivity.this, "네트워크 연결을 확인해 주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent i = new Intent(MainActivity.this, Address.class);
                i.putExtra("add", 1);
                CheckTypesTask task = new CheckTypesTask();
                task.execute();
                startActivity(i);
            }
        });

        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!CheckNetwork.isNetworkAvailable(MainActivity.this)){
                    Toast.makeText(MainActivity.this, "네트워크 연결을 확인해 주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
              Intent i = new Intent(MainActivity.this,ReviewList.class);
              i.putExtra("review",1);
                CheckTypesTask task = new CheckTypesTask();
                task.execute();
              startActivity(i);
            }
        });

        beauty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_dialog();
            }
        });
    }

    public void login_dialog(){
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        View alertView = getLayoutInflater().inflate(R.layout.login_menu,null);
        sign_up = alertView.findViewById(R.id.sign_up);
        login = alertView.findViewById(R.id.login);
        btn_custom_login = alertView.findViewById(R.id.btn_custom_login);

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        YoYo.with(Techniques.Tada)
                .duration(700)
                .repeat(5)
                .playOn(sign_up);

        alert.setView(alertView);

        dialog=alert.create();
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
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
                    String email = result.getKakaoAccount().getEmail();
                    Retrofit retrofit = NetworkClient.getRetrofitClient(MainActivity.this);

                    UserApi userApi = retrofit.create(UserApi.class);

                    Call<UserCheck> call = userApi.checkUser(email);
                    call.enqueue(new Callback<UserCheck>() {
                        @Override
                        public void onResponse(Call<UserCheck> call, Response<UserCheck> response) {
                            // response.body() ==> PostRes 클래스
                            if (response.isSuccessful()){
                                Intent i = new Intent(MainActivity.this,AfterLogin.class);
                                i.putExtra("nick_name",response.body().getNick_name());
                                Log.i("nick_name", response.body().getNick_name());
                                finish();
                                startActivity(i);
                            }else if (response.isSuccessful()==false){
                                Intent intent = new Intent(getApplicationContext(), Nick_name.class);
                                if (result.getKakaoAccount().isEmailValid() == OptionalBoolean.TRUE)
                                    intent.putExtra("email", result.getKakaoAccount().getEmail());
                                else
                                    intent.putExtra("email", "none");
                                Log.i("email : ", result.getKakaoAccount().getEmail());

                                finish();
                                CheckTypesTask task = new CheckTypesTask();
                                task.execute();
                                startActivity(intent);

                            }

                        }

                        @Override
                        public void onFailure(Call<UserCheck> call, Throwable t) {

                        }
                    });

                    // email 저장.
                    sp = getSharedPreferences(Utils.PREFERENCES_NAME, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("email", email);
                    editor.apply();

                }
            });
        }

        @Override
        public void onSessionOpenFailed(KakaoException e) {
//            Toast.makeText(getApplicationContext(), "로그인 도중 오류가 발생했습니다. 인터넷 연결을 확인해주세요: " + e.toString(), Toast.LENGTH_SHORT).show();
            Log.e("openfailed ", e.toString());
        }
    }
    private  class CheckTypesTask extends AsyncTask<Void, Integer, Boolean> {
        ProgressDialog asyncDialog = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute(){
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("로딩중..");
            asyncDialog.show();
            asyncDialog.setCancelable(false);
            super.onPreExecute();
        }
        @Override
        protected Boolean doInBackground(Void... strings){

            for(int i = 0; i<10000; i++){
                publishProgress(i);


            }
            return true;

        }

        @Override
        protected void onPostExecute(Boolean s){

            asyncDialog.dismiss();
            super.onPostExecute(s);
        }


        @Override
        protected void onCancelled(Boolean s){
            super.onCancelled(s);
        }

    }
    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - time >= 2000) {
            time = System.currentTimeMillis();
            Toast.makeText(getApplicationContext(), "한번 더 누르면 종료합니다.", Toast.LENGTH_SHORT).show();
        } else if (System.currentTimeMillis() - time < 2000) {
            finish();
        }
    }
}



