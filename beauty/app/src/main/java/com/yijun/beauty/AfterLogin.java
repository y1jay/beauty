package com.yijun.beauty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.yijun.beauty.activity.CheckoutActivity;


public class AfterLogin extends AppCompatActivity {
Button logout;
Button reservation;
Button address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);
        logout = findViewById(R.id.logout);
        reservation = findViewById(R.id.reservation);
        address = findViewById(R.id.address);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AfterLogin.this,MainActivity.class);
               i.putExtra("key",1);
                finish();
                startActivity(i);

            }
        });
        reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AfterLogin.this, CheckoutActivity.class);
                startActivity(i);
            }
        });
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AfterLogin.this,Address.class);
                startActivity(i);
            }
        });
    }
}
