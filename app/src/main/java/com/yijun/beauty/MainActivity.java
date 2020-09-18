package com.yijun.beauty;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
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


    // 로그인 다이얼로그
    AlertDialog dialog;
    AlertDialog dialog0;
    AlertDialog dialog1;
    Button sign_up;
    Button login;
    LoginButton btn_custom_login;

    EditText editID;
    TextView txt_phoneNumber;
    CheckBox Auto;
    TextView txtNO;
    TextView txtYES;

    TextView find_id;
    Button btnIDNO;
    Button btnFind;
    EditText findPhone;

    private long time = 0;
    SharedPreferences sp;
    String email;

    private SessionCallback sessionCallback;

    // agreement 다이얼로그
    AlertDialog agreement_dialog;
    CheckBox check_agree;
    Button btn_next;


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

    // 로그인 다이얼로그
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

                                if (result.getKakaoAccount().isEmailValid() == OptionalBoolean.TRUE)
                                    agree();
                                else
                                    email = "none";
                                Log.i("email : ", result.getKakaoAccount().getEmail());


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

    // 동의 다이얼로그
    public void agree(){
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        View alertView = getLayoutInflater().inflate(R.layout.agreement,null);
        check_agree = alertView.findViewById(R.id.check_agree);
        btn_next = alertView.findViewById(R.id.btn_next);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check_agree.isChecked() == true){
                    Intent intent = new Intent(getApplicationContext(), Nick_name.class);
                    intent.putExtra("email", email);

                    finish();
                    CheckTypesTask task = new CheckTypesTask();
                    task.execute();
                    startActivity(intent);
                }else {
                    Toast.makeText(MainActivity.this, "동의 시 이용 가능합니다.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });

        alert.setView(alertView);
        dialog=alert.create();
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
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

    public void createPopupDialog(){
        AlertDialog.Builder alert = new AlertDialog.Builder
                (MainActivity.this);
        View alertView = getLayoutInflater().inflate(R.layout.login,null);
        editID = alertView.findViewById(R.id.editID);
        txt_phoneNumber = alertView.findViewById(R.id.txt_phoneNumber);
        txtNO = alertView.findViewById(R.id.txtNO);
        txtYES = alertView.findViewById(R.id.txtYES);
        Auto = alertView.findViewById(R.id.Auto);
        find_id = alertView.findViewById(R.id.find_id);

        txtYES.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nick_name = editID.getText().toString().trim();
                String phone_number= txt_phoneNumber.getText().toString().trim();
                Boolean info_agree = true;
                if(nick_name.isEmpty()){
                    Toast.makeText(MainActivity.this,"닉네임을 입력하세요",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                UserReq userReq = new UserReq(nick_name,phone_number,info_agree);

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



                            Intent i = new Intent(MainActivity.this,AfterLogin.class);
                            Toast.makeText(MainActivity.this,"환영합니다.",Toast.LENGTH_SHORT).show();

                            i.putExtra("nick_name",nick_name);
                            i.putExtra("phone_number",phone_number);
                            i.putExtra("info_agree",info_agree);
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
    public void find_idPopupDialog(){
        AlertDialog.Builder alert = new AlertDialog.Builder
                (MainActivity.this);
        View alertView = getLayoutInflater().inflate(R.layout.find_id,null);
        findPhone = alertView.findViewById(R.id.findPhone);

        btnFind = alertView.findViewById(R.id.btnFind);
        btnIDNO = alertView.findViewById(R.id.btnIDNO);

        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = findPhone.getText().toString().trim();

                Retrofit retrofit = NetworkClient.getRetrofitClient(MainActivity.this);
                UserApi userApi = retrofit.create(UserApi.class);

                Call<ID> call = userApi.findID(phone);

                call.enqueue(new Callback<ID>() {
                    @Override
                    public void onResponse(Call<ID> call, Response<ID> response) {
                        // 상태코드가 200 인지 확인
                        if (response.isSuccessful()){
                            // response.body() 가 UserRes.이다.
                            String ID = response.body().getID();
                            Log.i("AAAAA","id : "+ID);
                            Toast.makeText(MainActivity.this,"고객님의 아이디는 "+ID+" 입니다.",Toast.LENGTH_LONG).show();

                            dialog1.cancel();
                        } else if (response.isSuccessful()==false){
                            Toast.makeText(MainActivity.this,"입력하신 정보가 맞지 않습니다.",Toast.LENGTH_SHORT).show();

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




}



