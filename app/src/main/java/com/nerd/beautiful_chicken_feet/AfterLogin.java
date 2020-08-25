package com.nerd.beautiful_chicken_feet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.nerd.beautiful_chicken_feet.activity.CheckoutActivity;

public class AfterLogin extends AppCompatActivity {
Button logout;
Button reservation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);
        logout = findViewById(R.id.logout);
        reservation = findViewById(R.id.reservation);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AfterLogin.this, CheckoutActivity.class);
                startActivity(i);
            }
        });
    }
}
