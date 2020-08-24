package com.nerd.beautiful_chicken_feet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {
    EditText edtname;
    EditText edtphoe;
    Button btncheck;
    EditText edtid;
    Button btnidcheck;
    EditText edtpasswd;
    EditText edtpasswdcheck;
    Button btnpasswdcheck;
    Button btnsignup;

    EditText edtcostomphone;
    EditText edtcostomphonecheck;
    Button btncostomphonecheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        edtname = findViewById(R.id.edtname);
        edtphoe = findViewById(R.id.edtphone);
        btncheck = findViewById(R.id.btncheck);
        edtid = findViewById(R.id.edtid);
        btnidcheck = findViewById(R.id.btnidcheck);
        edtpasswd = findViewById(R.id.edtpasswd);
        edtpasswdcheck = findViewById(R.id.edtpasswdcheck);
        btnpasswdcheck = findViewById(R.id.btnpasswdcheck);
        btnsignup = findViewById(R.id.btnsignup);



        btncheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = edtphoe.getText().toString().trim();

                if(phone == null){
                    Toast.makeText(SignUpActivity.this,"핸드폰 번호를 입력하세요",
                            Toast.LENGTH_SHORT).show();
                    return;
                }



            }
        });


        btnidcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtid.getText().toString();

                if (email.contains("@")== false) {
                    Toast.makeText(SignUpActivity.this, "이메일이 형식이 다름",
                            Toast.LENGTH_SHORT).show();
                    return;

//                }else if("중복체크api랑 연결할 부분"){
//                }

                }
            }
        });
        btnpasswdcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String passwd  = edtpasswd.getText().toString();
                String passwdcheck = edtpasswdcheck.getText().toString();

                if (passwd.length() >13 || passwd.length() < 5){
                    Toast.makeText(SignUpActivity.this, "비밀번호를 6이상 13이하로",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                if (passwd.compareTo(passwdcheck)!= 0){
                    Toast.makeText(SignUpActivity.this,
                            "비밀번호가 일치하지않습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(passwd.compareTo(passwdcheck)== 0){
                    Toast.makeText(SignUpActivity.this,
                            "비밀번호가 일치합니다", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtid.getText().toString();
                Intent i = new Intent(SignUpActivity.this,SignupFinish.class);
                i.putExtra("email",email);
                startActivity(i);
                finish();
            }
        });
    }
}
