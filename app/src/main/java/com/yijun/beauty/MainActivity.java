package com.yijun.beauty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kakao.auth.ApiErrorCode;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.OptionalBoolean;
import com.kakao.util.exception.KakaoException;
import com.yijun.beauty.api.NetworkClient;
import com.yijun.beauty.api.UserApi;
import com.yijun.beauty.model.UserCheck;
import com.yijun.beauty.url.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;



public class MainActivity extends AppCompatActivity {


    Button reservation;
    Button review;
    Button address;

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


    private AlertDialog dialog;

    SharedPreferences sp;

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
                CheckTypesTask task = new CheckTypesTask();
                task.execute();
                startActivity(i);
            }
        });

        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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




    }

}



