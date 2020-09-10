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
import com.yijun.beauty.adapter.RecyclerViewAdapter;
import com.yijun.beauty.api.NetworkClient;
import com.yijun.beauty.api.ReviewApi;
import com.yijun.beauty.api.UserApi;
import com.yijun.beauty.model.ReviewRes;
import com.yijun.beauty.model.Rows;
import com.yijun.beauty.model.UserRes;
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
    Button logout;
    Button reservation;
    Button address;
    SharedPreferences sp;
    RecyclerView reviewcyclerView;
    RecyclerViewAdapter adapter;

    List<Rows> reviewArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);
        int key = getIntent().getIntExtra("key",0);
        if (key==1){
            Intent i = new Intent(AfterLogin.this,LodingActivity.class);
            startActivity(i);
        }else{
            String nick_name = getIntent().getStringExtra("nick_name");
            Toast.makeText(AfterLogin.this,nick_name+" 님 환영합니다",Toast.LENGTH_SHORT).show();
        }

        logout = findViewById(R.id.logout);
        reservation = findViewById(R.id.reservation);
        address = findViewById(R.id.address);
        reviewcyclerView = findViewById(R.id.reviewcyclerView);
        reviewcyclerView.setHasFixedSize(true);
        reviewcyclerView.setLayoutManager(new LinearLayoutManager(AfterLogin.this));

        String nick_name = getIntent().getStringExtra("nick_name");
        sp = getSharedPreferences(Utils.PREFERENCES_NAME,MODE_PRIVATE);
        SharedPreferences.Editor editor= sp.edit();
        editor.putString("nick_name", nick_name);
        editor.apply();
        getNetworkData();



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "정상적으로 로그아웃되었습니다.", Toast.LENGTH_SHORT).show();

                UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
                    @Override
                    public void onCompleteLogout() {
                        Intent intent = new Intent(AfterLogin.this, MainActivity.class);
                        intent.putExtra("key",1);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        CheckTypesTask task = new CheckTypesTask();
                        task.execute();
                        startActivity(intent);
                    }
                });

            }
        });

        reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AfterLogin.this, Reservation.class);
                CheckTypesTask task = new CheckTypesTask();
                task.execute();
                startActivity(i);
            }
        });

        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AfterLogin.this,Address.class);
                i.putExtra("add",3);
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

        if (id == R.id.myInfo){
            Intent i = new Intent(AfterLogin.this, MyInfo.class);
            CheckTypesTask task = new CheckTypesTask();
            task.execute();
            startActivity(i);
            return true;
        }else if (id == R.id.reservation_check){
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
        super.onBackPressed();
        finish();

    }

    private void getNetworkData() {
        Retrofit retrofit = NetworkClient.getRetrofitClient(AfterLogin.this);

        ReviewApi reviewApi = retrofit.create(ReviewApi.class);

        Call<ReviewRes> call = reviewApi.selectReview(0,25);
        call.enqueue(new Callback<ReviewRes>() {
            @Override
            public void onResponse(Call<ReviewRes> call, Response<ReviewRes> response) {
                // response.body() ==> PostRes 클래스
                Log.i("AAAA",response.body().getSuccess().toString());
                //PostRes.get(0) => List<row>의 첫번째 Item 객체.
                // PostRes.get(0).getPosting()=> 위의 Row 객체에 저장된 Posting 값

                Log.i("AAAA",response.body().getCnt().toString());

                reviewArrayList = response.body().getRows();

                adapter = new RecyclerViewAdapter(AfterLogin.this, reviewArrayList);
                reviewcyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ReviewRes> call, Throwable t) {

            }
        });

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
