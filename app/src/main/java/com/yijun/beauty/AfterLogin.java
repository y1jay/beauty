package com.yijun.beauty;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.yijun.beauty.network.CheckNetwork;
import com.yijun.beauty.url.Utils;


public class AfterLogin extends AppCompatActivity {
    Button review;
    Button logout;
    Button reservation;
    Button address;
    SharedPreferences sp;

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
   TextView textView2;
    private AlertDialog dialog;

    Button btn_NO;
    Button btn_out;

    private long time = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);



        textView2 = findViewById(R.id.textView2);
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:010-2842-5656")); startActivity(myIntent);

            }
        });

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



        if(!CheckNetwork.isNetworkAvailable(AfterLogin.this)){
            Toast.makeText(AfterLogin.this, "네트워크 연결을 확인해 주세요", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        int key = getIntent().getIntExtra("key", 0);
        if (key == 1) {
            Intent i = new Intent(AfterLogin.this, LodingActivity.class);
            startActivity(i);
        } else {
            String nick_name = getIntent().getStringExtra("nick_name");
            Toast.makeText(AfterLogin.this, nick_name + " 님 환영합니다", Toast.LENGTH_SHORT).show();
        }

        review = findViewById(R.id.review);
        logout = findViewById(R.id.logout);
        reservation = findViewById(R.id.reservation);
        address = findViewById(R.id.address);

        String nick_name = getIntent().getStringExtra("nick_name");
        sp = getSharedPreferences(Utils.PREFERENCES_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("nick_name", nick_name);
        editor.apply();


        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!CheckNetwork.isNetworkAvailable(AfterLogin.this)){
                    Toast.makeText(AfterLogin.this, "네트워크 연결을 확인해 주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent i = new Intent(AfterLogin.this, ReviewList.class);
                CheckTypesTask task = new CheckTypesTask();
                task.execute();
                startActivity(i);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutDialog();
            }
        });

        reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!CheckNetwork.isNetworkAvailable(AfterLogin.this)){
                    Toast.makeText(AfterLogin.this, "네트워크 연결을 확인해 주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent i = new Intent(AfterLogin.this, Reservation.class);
                CheckTypesTask task = new CheckTypesTask();
                task.execute();
                startActivity(i);
            }
        });

        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!CheckNetwork.isNetworkAvailable(AfterLogin.this)){
                    Toast.makeText(AfterLogin.this, "네트워크 연결을 확인해 주세요", Toast.LENGTH_SHORT).show();
                    return ;
                }
                Intent i = new Intent(AfterLogin.this, Address.class);
                i.putExtra("add", 3);
                CheckTypesTask task = new CheckTypesTask();
                task.execute();
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

        if (id == R.id.myInfo) {
            if(!CheckNetwork.isNetworkAvailable(AfterLogin.this)){
                Toast.makeText(AfterLogin.this, "네트워크 연결을 확인해 주세요", Toast.LENGTH_SHORT).show();
                return false;
            }
            Intent i = new Intent(AfterLogin.this, MyInfo.class);
            CheckTypesTask task = new CheckTypesTask();
            task.execute();
            startActivity(i);
            return true;
        } else if (id == R.id.reservation_check) {
            if(!CheckNetwork.isNetworkAvailable(AfterLogin.this)){
                Toast.makeText(AfterLogin.this, "네트워크 연결을 확인해 주세요", Toast.LENGTH_SHORT).show();
                return false;
            }
            Intent i = new Intent(AfterLogin.this, ReservationRecord.class);
            CheckTypesTask task = new CheckTypesTask();
            task.execute();
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
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

    public void logoutDialog(){
        new AlertDialog.Builder(AfterLogin.this)
                .setMessage("로그아웃하시겠습니까?")
                .setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "정상적으로 로그아웃되었습니다.", Toast.LENGTH_SHORT).show();

                        UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
                            @Override
                            public void onCompleteLogout() {
                                Intent intent = new Intent(AfterLogin.this, MainActivity.class);
                                intent.putExtra("key", 1);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        });

                        sp = getSharedPreferences(Utils.PREFERENCES_NAME,MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putBoolean("auto_login",false);
                        editor.apply();

                        dialog.dismiss();
                    }
                })
                .setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //"아니오" 버튼 클릭 시 할 동작
                        dialog.dismiss(); //팝업창 종료
                    }
                })
                .setCancelable(false)
                .show();

    }

    private  class CheckTypesTask extends AsyncTask<Void, Integer, Boolean> {
        ProgressDialog asyncDialog = new ProgressDialog(AfterLogin.this);

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
        AlertDialog.Builder alert = new AlertDialog.Builder(AfterLogin.this);
        View enterview = getLayoutInflater().inflate(R.layout.menu1,null);





        alert.setView(enterview);


        dialog = alert.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));




    }



    public void createMenuDialog2(){
        AlertDialog.Builder alert = new AlertDialog.Builder(AfterLogin.this);
        View enterview = getLayoutInflater().inflate(R.layout.menu2,null);






        alert.setView(enterview);


        dialog = alert.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));




    }

    public void createMenuDialog3(){
        AlertDialog.Builder alert = new AlertDialog.Builder(AfterLogin.this);
        View enterview = getLayoutInflater().inflate(R.layout.menu3,null);




        alert.setView(enterview);


        dialog = alert.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));




    }

    public void createMenuDialog4(){
        AlertDialog.Builder alert = new AlertDialog.Builder(AfterLogin.this);
        View enterview = getLayoutInflater().inflate(R.layout.menu4,null);





        alert.setView(enterview);


        dialog = alert.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));




    }
    public void createMenuDialog5(){
        AlertDialog.Builder alert = new AlertDialog.Builder(AfterLogin.this);
        View enterview = getLayoutInflater().inflate(R.layout.menu5,null);






        alert.setView(enterview);

        dialog = alert.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));




    }
    public void createMenuDialog6(){
        AlertDialog.Builder alert = new AlertDialog.Builder(AfterLogin.this);
        View enterview = getLayoutInflater().inflate(R.layout.menu6,null);






        alert.setView(enterview);


        dialog = alert.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));




    }
    public void createMenuDialog7(){
        AlertDialog.Builder alert = new AlertDialog.Builder(AfterLogin.this);
        View enterview = getLayoutInflater().inflate(R.layout.menu7,null);






        alert.setView(enterview);


        dialog = alert.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));




    }

    public void createMenuDialog9(){
        AlertDialog.Builder alert = new AlertDialog.Builder(AfterLogin.this);
        View enterview = getLayoutInflater().inflate(R.layout.menu9,null);





        alert.setView(enterview);


        dialog = alert.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));




    }
    public void createMenuDialog10(){
        AlertDialog.Builder alert = new AlertDialog.Builder(AfterLogin.this);
        View enterview = getLayoutInflater().inflate(R.layout.menu10,null);





        alert.setView(enterview);

        dialog = alert.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));




    }
    public void createMenuDialog11(){
        AlertDialog.Builder alert = new AlertDialog.Builder(AfterLogin.this);
        View enterview = getLayoutInflater().inflate(R.layout.menu11,null);





        alert.setView(enterview);

        dialog = alert.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));




    }


}

