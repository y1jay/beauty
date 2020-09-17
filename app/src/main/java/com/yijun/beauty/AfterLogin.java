package com.yijun.beauty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kakao.auth.AuthType;
import com.kakao.auth.Session;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.util.helper.Utility;
import com.yijun.beauty.api.NetworkClient;
import com.yijun.beauty.api.ReviewApi;
import com.yijun.beauty.api.UserApi;
import com.yijun.beauty.model.ReviewRes;
import com.yijun.beauty.model.Rows;
import com.yijun.beauty.model.UserRes;
import com.yijun.beauty.network.CheckNetwork;
import com.yijun.beauty.url.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class AfterLogin extends AppCompatActivity {
    Button review;
    Button logout;
    Button reservation;
    Button address;
    SharedPreferences sp;

    private AlertDialog dialog;

    Button btn_NO;
    Button btn_out;

    private long time = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);

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
        AlertDialog.Builder alert = new AlertDialog.Builder(AfterLogin.this);
        View alertView = getLayoutInflater().inflate(R.layout.logout_row,null);
        btn_out = alertView.findViewById(R.id.btn_out);
        btn_NO = alertView.findViewById(R.id.btn_NO);

        btn_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });
        btn_NO.setOnClickListener(new View.OnClickListener() {
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
}

