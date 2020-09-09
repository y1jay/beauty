package com.yijun.beauty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Nick_name extends AppCompatActivity {
Button btn_check;
TextView txt_email;
EditText edit_nick_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nick_name);

        btn_check = findViewById(R.id.btn_check);
        txt_email = findViewById(R.id.txt_email);
        edit_nick_name = findViewById(R.id.edit_nick_name);

        String email = getIntent().getStringExtra("email");

        txt_email.setText("이메일 = "+email);

        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Nick_name.this,AfterLogin.class);
                finish();
                startActivity(i);
            }
        });
    }
}