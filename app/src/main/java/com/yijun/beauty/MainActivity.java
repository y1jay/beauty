package com.yijun.beauty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.kakao.auth.ApiErrorCode;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.LoginButton;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.OptionalBoolean;
import com.kakao.util.exception.KakaoException;
import com.yijun.beauty.api.NetworkClient;
import com.yijun.beauty.api.UserApi;
import com.yijun.beauty.model.ID;
import com.yijun.beauty.model.UserCheck;
import com.yijun.beauty.model.UserReq;
import com.yijun.beauty.model.UserRes;
import com.yijun.beauty.network.CheckNetwork;
import com.yijun.beauty.url.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class MainActivity extends AppCompatActivity {

    Button reservation;
    Button review;
    Button address;
    Button beauty;

    ImageView img1;
    ImageView img2;
    ImageView img3;
    ImageView img4;
    ImageView img5;
    ImageView img6;
    ImageView img7;
    ImageView img9;
    ImageView img10;
    ImageView img11;

    // 메뉴 다이얼로그

    TextView txt_delete1;
    TextView txt_delete2;
    TextView txt_delete3;
    TextView txt_delete4;
    TextView txt_delete5;
    TextView txt_delete6;
    TextView txt_delete7;
    TextView txt_delete9;
    TextView txt_delete10;
    TextView txt_delete11;


    // 로그인메뉴 다이얼로그
    AlertDialog dialog;
    Button sign_up;
    Button login;
    LoginButton btn_custom_login;

    // 전화허용
    String my_phone_num;
    private static final int MY_PERMISSION_STORAGE = 1111;

    // 미인닭발 로그인 다이얼로그
    AlertDialog dialog0;
    EditText editID;
    CheckBox Auto;
    TextView txtNO;
    TextView txtYES;

    // 아이디 찾기 다이얼로그
    AlertDialog dialog1;
    TextView find_id;
    Button btnIDNO;
    Button btnFind;
    TextView findPhone;

    private long time = 0;
    SharedPreferences sp;
    String email;
    Boolean auto_login;
    private SessionCallback sessionCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);
        img4 = findViewById(R.id.img4);
        img5 = findViewById(R.id.img5);
        img6 = findViewById(R.id.img6);
        img7 = findViewById(R.id.img7);
        img9 = findViewById(R.id.img9);
        img10 = findViewById(R.id.img10);
        img11 = findViewById(R.id.img11);


        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createMenuDialog();
            }
        });

        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createMenuDialog2();
            }
        });

        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createMenuDialog3();
            }
        });
        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createMenuDialog4();
            }
        });
        img5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createMenuDialog5();
            }
        });
        img6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createMenuDialog6();
            }
        });
        img7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createMenuDialog7();
            }
        });
        img9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createMenuDialog9();
            }
        });
        img10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createMenuDialog10();
            }
        });
        img11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createMenuDialog11();
            }
        });


        Intent i = new Intent(MainActivity.this, LodingActivity.class);
        int key = getIntent().getIntExtra("key", 0);
        Log.i("key","key "+key);
        if (key == 1) {

        } else {
            startActivity(i);
        }

        // 미인닭발 자동로그인
        SharedPreferences sp = getSharedPreferences(Utils.PREFERENCES_NAME,MODE_PRIVATE);
        auto_login = sp.getBoolean("auto_login", false);

        if (auto_login == true){
            getPhone();
            Log.i("id", ""+getPhone());

            Retrofit retrofit = NetworkClient.getRetrofitClient(MainActivity.this);
            UserApi userApi = retrofit.create(UserApi.class);

            Call<ID> call = userApi.findID(my_phone_num);

            call.enqueue(new Callback<ID>() {
                @Override
                public void onResponse(Call<ID> call, Response<ID> response) {
                    // 상태코드가 200 인지 확인
                    if (response.isSuccessful() == true){
                        // response.body() 가 UserRes.이다.
                        String nick_name = response.body().getID();
                        Intent i = new Intent(MainActivity.this, AfterLogin.class);
                        i.putExtra("nick_name", nick_name);
                        finish();
                        startActivity(i);
                    }
                }
                @Override
                public void onFailure(Call<ID> call, Throwable t) {
                    Log.i("id", t.toString());
                }
            });
        }else if(auto_login==false){
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

    // 로그인메뉴 다이얼로그
    public void login_dialog(){
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        View alertView = getLayoutInflater().inflate(R.layout.login_menu,null);
        sign_up = alertView.findViewById(R.id.sign_up);
        login = alertView.findViewById(R.id.login);
        btn_custom_login = alertView.findViewById(R.id.btn_custom_login);

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, SignUpActivity.class);
                dialog.cancel();
                CheckTypesTask task = new CheckTypesTask();
                task.execute();
                startActivity(i);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPhone();
                Toast.makeText(MainActivity.this, "해당 권한이 활성화되었습니다.", Toast.LENGTH_SHORT).show();
                createPopupDialog();
                dialog.cancel();
            }
        });

        YoYo.with(Techniques.SlideInUp)
                .duration(1000)
                .repeat(0)
                .playOn(sign_up);
        YoYo.with(Techniques.SlideInUp)
                .duration(1000)
                .repeat(0)
                .playOn(login);
        YoYo.with(Techniques.SlideInUp)
                .duration(1000)
                .repeat(0)
                .playOn(btn_custom_login);

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
                    email = result.getKakaoAccount().getEmail();
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
                                    intent.putExtra("email", email);
                                else
                                    intent.putExtra("email" ,"none");
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

    // 로딩중
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


    // 메뉴 크게보기
    public void createMenuDialog(){
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        View enterview = getLayoutInflater().inflate(R.layout.menu1,null);

        txt_delete1 = enterview.findViewById(R.id.txt_delete1);
        txt_delete1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        alert.setView(enterview);
        alert.setCancelable(false);

        dialog = alert.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void createMenuDialog2(){
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        View enterview = getLayoutInflater().inflate(R.layout.menu2,null);

        txt_delete2 = enterview.findViewById(R.id.txt_delete2);
        txt_delete2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        alert.setView(enterview);
        alert.setCancelable(false);

        dialog = alert.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void createMenuDialog3(){
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        View enterview = getLayoutInflater().inflate(R.layout.menu3,null);

        txt_delete3 = enterview.findViewById(R.id.txt_delete3);
        txt_delete3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        alert.setView(enterview);
        alert.setCancelable(false);

        dialog = alert.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void createMenuDialog4(){
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        View enterview = getLayoutInflater().inflate(R.layout.menu4,null);

        txt_delete4 = enterview.findViewById(R.id.txt_delete4);
        txt_delete4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        alert.setView(enterview);
        alert.setCancelable(false);

        dialog = alert.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void createMenuDialog5(){
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        View enterview = getLayoutInflater().inflate(R.layout.menu5,null);

        txt_delete5 = enterview.findViewById(R.id.txt_delete5);
        txt_delete5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        alert.setView(enterview);
        alert.setCancelable(false);

        dialog = alert.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void createMenuDialog6(){
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        View enterview = getLayoutInflater().inflate(R.layout.menu6,null);

        txt_delete6 = enterview.findViewById(R.id.txt_delete6);
        txt_delete6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        alert.setView(enterview);
        alert.setCancelable(false);

        dialog = alert.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void createMenuDialog7(){
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        View enterview = getLayoutInflater().inflate(R.layout.menu7,null);

        txt_delete7 = enterview.findViewById(R.id.txt_delete7);
        txt_delete7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        alert.setView(enterview);
        alert.setCancelable(false);

        dialog = alert.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void createMenuDialog9(){
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        View enterview = getLayoutInflater().inflate(R.layout.menu9,null);

        txt_delete9 = enterview.findViewById(R.id.txt_delete9);
        txt_delete9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        alert.setView(enterview);
        alert.setCancelable(false);

        dialog = alert.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void createMenuDialog10(){
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        View enterview = getLayoutInflater().inflate(R.layout.menu10,null);

        txt_delete10 = enterview.findViewById(R.id.txt_delete10);
        txt_delete10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        alert.setView(enterview);
        alert.setCancelable(false);

        dialog = alert.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void createMenuDialog11(){
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        View enterview = getLayoutInflater().inflate(R.layout.menu11,null);

        txt_delete11 = enterview.findViewById(R.id.txt_delete11);
        txt_delete11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        alert.setView(enterview);
        alert.setCancelable(false);

        dialog = alert.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }


    // 미인닭발 로그인 다이얼로그
    public void createPopupDialog(){
        checkPermission();

        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        View alertView = getLayoutInflater().inflate(R.layout.login,null);
        editID = alertView.findViewById(R.id.editID);
        txtNO = alertView.findViewById(R.id.txtNO);
        txtYES = alertView.findViewById(R.id.txtYES);
        Auto = alertView.findViewById(R.id.Auto);
        find_id = alertView.findViewById(R.id.find_id);

        txtYES.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nick_name = editID.getText().toString().trim();
                Boolean info_agree = true;

                if(nick_name.isEmpty()){
                    Toast.makeText(MainActivity.this,"닉네임을 입력하세요",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if(my_phone_num.equals("")){
                    Toast.makeText(MainActivity.this,"휴대폰 번호가 없습니다.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                Retrofit retrofit = NetworkClient.getRetrofitClient(MainActivity.this);
                UserApi userApi = retrofit.create(UserApi.class);

                Call<UserCheck> call = userApi.loginUser(nick_name, my_phone_num);

                call.enqueue(new Callback<UserCheck>() {
                    @Override
                    public void onResponse(Call<UserCheck> call, Response<UserCheck> response) {
                        // 상태코드가 200 인지 확인
                        if (response.isSuccessful()){
                            // response.body() 가 UserRes.이다.
                            boolean success = response.body().isSuccess();

                            Intent i = new Intent(MainActivity.this,AfterLogin.class);
                            i.putExtra("nick_name",nick_name);
                            i.putExtra("info_agree",info_agree);

                            startActivity(i);
                            finish();
                            dialog.cancel();

                        } else if (response.isSuccessful()==false){
                            Toast.makeText(MainActivity.this,"존재하지않는 회원입니다, 회원가입 먼저 해주세오",Toast.LENGTH_SHORT).show();
                            dialog0.dismiss();
                        }

                    }

                    @Override
                    public void onFailure(Call<UserCheck> call, Throwable t) {

                    }
                });

                if (Auto.isChecked() == true){
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
                dialog0.cancel();
            }
        });

       find_id.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               find_idPopupDialog();
           }
       });

        alert.setView(alertView);

        dialog0=alert.create();
        dialog0.setCancelable(false);
        dialog0.show();
    }

    // 미인닭발 아이디 찾기 다이얼로그
    public void find_idPopupDialog(){

        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        View alertView = getLayoutInflater().inflate(R.layout.find_id,null);
        findPhone = alertView.findViewById(R.id.findPhone);
        btnFind = alertView.findViewById(R.id.btnFind);
        btnIDNO = alertView.findViewById(R.id.btnIDNO);

        findPhone.setText(my_phone_num);

        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = NetworkClient.getRetrofitClient(MainActivity.this);
                UserApi userApi = retrofit.create(UserApi.class);

                Call<ID> call = userApi.findID(my_phone_num);

                call.enqueue(new Callback<ID>() {
                    @Override
                    public void onResponse(Call<ID> call, Response<ID> response) {
                        // 상태코드가 200 인지 확인
                        if (response.isSuccessful() == true){
                            // response.body() 가 UserRes.이다.
                            String ID = response.body().getID();
                            Log.i("AAAAA","id : "+ID);
                            Toast.makeText(MainActivity.this,"고객님의 아이디는 "+ID+" 입니다.",Toast.LENGTH_LONG).show();
                            dialog1.cancel();
                        } else{
                            Toast.makeText(MainActivity.this,"존재하지않는 회원입니다.",Toast.LENGTH_SHORT).show();
                            Log.i("AAAAA","id : "+response.toString());
                        }

                    }

                    @Override
                    public void onFailure(Call<ID> call, Throwable t) {

                    }
                });

            }
        });

        btnIDNO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.cancel();
            }
        });

        alert.setView(alertView);
        dialog1=alert.create();
        dialog1.setCancelable(false);
        dialog1.show();
    }

    @SuppressLint({"MissingPermission", "HardwareIds"})

    private String getPhone() {
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return "";
        }
        my_phone_num = tm.getLine1Number();

        if (my_phone_num != null) {

            my_phone_num = my_phone_num.replace("+82", "0");

        }

        return tm.getLine1Number();
    }


    private void checkPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // 다시 보지 않기 버튼을 만드려면 이 부분에 바로 요청을 하도록 하면 됨 (아래 else{..} 부분 제거)
            // ActivityCompat.requestPermissions((Activity)mContext, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSION_CAMERA);

            // 처음 호출시엔 if()안의 부분은 false로 리턴 됨 -> else{..}의 요청으로 넘어감
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE)) {
                new AlertDialog.Builder(this)
                        .setTitle("알림")
                        .setMessage("저장소 권한이 거부되었습니다. 사용을 원하시면 설정에서 해당 권한을 직접 허용하셔야 합니다.")
                        .setPositiveButton("설정", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.setData(Uri.parse("package:" + getPackageName()));
                                startActivity(intent);
                            }
                        })
//                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                finish();
//                            }
//                        })
                        .setCancelable(false)
                        .create()
                        .show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_PHONE_NUMBERS}, MY_PERMISSION_STORAGE);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_STORAGE:
                for (int i = 0; i < grantResults.length; i++) {
                    // grantResults[] : 허용된 권한은 0, 거부한 권한은 -1
                    if (grantResults[i] < 0) {
                        checkPermission();
                        Toast.makeText(MainActivity.this, "해당 권한을 활성화 하셔야 합니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                // 허용했다면 이 부분에서..
                getPhone();
                Toast.makeText(MainActivity.this, "해당 권한이 활성화되었습니다.", Toast.LENGTH_SHORT).show();
                break;
        }
    }

}



