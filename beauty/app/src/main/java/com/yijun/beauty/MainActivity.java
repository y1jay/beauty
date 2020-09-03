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

import com.google.android.gms.tasks.OnSuccessListener;
import com.kakao.auth.AuthType;
import com.kakao.auth.Session;
import com.kakao.usermgmt.LoginButton;
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

    Button reservation;
    Button address;
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

            }
        });


        reservation = findViewById(R.id.reservation);

        address = findViewById(R.id.address);



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


}