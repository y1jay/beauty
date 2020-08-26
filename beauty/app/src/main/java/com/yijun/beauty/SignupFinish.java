package com.yijun.beauty;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SignupFinish extends AppCompatActivity {
    TextView txtfinish;
    Button btnfinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_finish);

        txtfinish= findViewById(R.id.txtfinish);
        btnfinish = findViewById(R.id.btnfinish);

        String email = getIntent().getStringExtra("email");
        txtfinish.setText(email);
    }
}