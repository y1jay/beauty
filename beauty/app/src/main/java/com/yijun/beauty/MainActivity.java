package com.yijun.beauty;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button reservation;
    Button login;
    Button address;
    Button signUp;
    private AlertDialog dialog;
    EditText editID;
    EditText editPasswd;
    TextView txtNO ;
    TextView txtYES;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i = new Intent(MainActivity.this,LodingActivity.class);
        int key = getIntent().getIntExtra("key",0);
        if(key==1){

        }else {
            startActivity(i);
        }
        reservation = findViewById(R.id.reservation);
        login = findViewById(R.id.login);
        address = findViewById(R.id.address);
        signUp = findViewById(R.id.signUp);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPopupDialog();

            }
        });
        reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "로그인 후 이용 가능합니다.",
                        Toast.LENGTH_SHORT).show();
                return;
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SignUpActivity.class);
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
    public void createPopupDialog(){
        AlertDialog.Builder alert = new AlertDialog.Builder
                (MainActivity.this);
        View alertView = getLayoutInflater().inflate(R.layout.login,null);
        editID = alertView.findViewById(R.id.editID);
        editPasswd = alertView.findViewById(R.id.editPasswd);
        txtNO = alertView.findViewById(R.id.txtNO);
        txtYES = alertView.findViewById(R.id.txtYES);

        txtYES.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ID = editID.getText().toString().trim();
                String Passwd= editPasswd.getText().toString().trim();
                if(ID.isEmpty()){
                    Toast.makeText(MainActivity.this,"아이디를 입력하세요",
                            Toast.LENGTH_SHORT).show();
                    return;
                }else if ( Passwd.isEmpty()){
                    Toast.makeText(MainActivity.this,"비밀번호를 입력하세요",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent i = new Intent(MainActivity.this,AfterLogin.class);
               finish();
                startActivity(i);

                dialog.cancel();
            }
        });
        txtNO.setOnClickListener(new View.OnClickListener() {
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
}