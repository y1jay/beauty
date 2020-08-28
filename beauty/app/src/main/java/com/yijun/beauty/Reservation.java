package com.yijun.beauty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.yijun.beauty.activity.CheckoutActivity;

public class Reservation extends AppCompatActivity {

    Button btn_payment;
    CheckBox checkmainmenu1;
    CheckBox checkmainmenu2;
    CheckBox checkmainmenu3;
    CheckBox checkmainmenu4;
    CheckBox checkmainmenu5;
    CheckBox checkmainmenu6;
    CheckBox checksetmenu1;
    CheckBox checksetmenu2;
    CheckBox checksetmenusmall3;
    CheckBox checksetmenumedium3;
    CheckBox checksetmenubig3;
    CheckBox checksetmenusmall4;
    CheckBox checksetmenumedium4;
    CheckBox checksetmenubig4;
    CheckBox checksetmenusmall5;
    CheckBox checksetmenumedium5;
    CheckBox checksetmenubig5;
    CheckBox checksetmenusmall6;
    CheckBox checksetmenumedium6;
    CheckBox checksetmenubig6;
    CheckBox checksetmenusmall7;
    CheckBox checksetmenumedium7;
    CheckBox checksetmenu8;
    CheckBox checkmenusmall1;
    CheckBox checkmenumedium1;
    CheckBox checkmenubig1;
    CheckBox checkmenusmall2;
    CheckBox checkmenumedium2;
    CheckBox checkmenubig2;
    CheckBox checkmenusmall3;
    CheckBox checkmenumedium3;
    CheckBox checkmenusmall4;
    CheckBox checkmenumedium4;
    CheckBox checkmenubig4;
    CheckBox checkmenusmall5;
    CheckBox checkmenumedium5;
    CheckBox checkmenubig5;
    CheckBox checkmenu6;
    CheckBox checkmenu7;
    CheckBox checksidemenu1;
    CheckBox checksidemenu2;
    CheckBox checksidemenu3;
    CheckBox checksidemenu4;
    CheckBox checkdrink1;
    CheckBox checkdrink2;
    CheckBox checkdrink3;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);


        checkmainmenu1 = findViewById(R.id.checkmainmenu1);
        checkmainmenu2 = findViewById(R.id.checkmainmenu2);
        checkmainmenu3 = findViewById(R.id.checkmainmenu3);
        checkmainmenu4 = findViewById(R.id.checkmainmeun4);
        checkmainmenu5 = findViewById(R.id.checkmainmenu5);
        checkmainmenu6 = findViewById(R.id.checkmainmenu6);
        checksetmenu1 = findViewById(R.id.checksetmenu1);
        checksetmenu2 = findViewById(R.id.checksetmenu2);
        checksetmenusmall3 = findViewById(R.id.checksetmenusmall3);
        checksetmenumedium3 = findViewById(R.id.checksetmenumedium3);
        checksetmenubig3 = findViewById(R.id.checksetmenubig3);
        checksetmenusmall4 = findViewById(R.id.checksetmenusmall4);
        checksetmenumedium4 = findViewById(R.id.checksetmenumedium4);
        checksetmenubig4 = findViewById(R.id.checksetmenubig4);
        checksetmenusmall5 = findViewById(R.id.checksetsmall5);
        checksetmenumedium5 = findViewById(R.id.checksetmenumedium5);
        checksetmenubig5 = findViewById(R.id.checksetmenubig5);
        checksetmenusmall6 = findViewById(R.id.checksetmenusmall6);
        checksetmenumedium6 = findViewById(R.id.checksetmenumedium6);
        checksetmenubig6 = findViewById(R.id.checksetmenubig6);
        checksetmenusmall7 = findViewById(R.id.checksetmenusmall7);
        checksetmenumedium7 = findViewById(R.id.checksetmenumedium7);
        checksetmenu8 = findViewById(R.id.checksetmenu8);
        checkmenusmall1 = findViewById(R.id.checkmenusmall1);
        checkmenumedium1 = findViewById(R.id.checkmenumedium1);
        checkmenubig1 = findViewById(R.id.checkmenubig1);
        checkmenusmall2 = findViewById(R.id.checkmenusmall2);
        checkmenumedium2 = findViewById(R.id.checkmenumedium2);
        checkmenubig2 = findViewById(R.id.checkmenubig2);
        checkmenusmall3 = findViewById(R.id.checkmenusmall3);
        checkmenumedium3 = findViewById(R.id.checkmenumedium3);
        checkmenusmall4 = findViewById(R.id.checkmenusmall4);
        checkmenumedium4 = findViewById(R.id.checkmenumedium4);
        checkmenubig4 = findViewById(R.id.checkmenubig4);
        checkmenusmall5 = findViewById(R.id.checkmenusmall5);
        checkmenumedium5 = findViewById(R.id.checkmenumedium5);
        checkmenubig5 = findViewById(R.id.checkmenubig5);
        checkmenu6 = findViewById(R.id.checkmenu6);
        checkmenu7 = findViewById(R.id.checkmenu7);
        checksidemenu1 = findViewById(R.id.checksidemenu1);
        checksidemenu2 = findViewById(R.id.checksidemenu2);
        checksidemenu3 = findViewById(R.id.checksidemenu3);
        checksidemenu4 = findViewById(R.id.checksidemenu4);
        checkdrink1 = findViewById(R.id.checkdrink1);
        checkdrink2 = findViewById(R.id.checkdrink2);
        checkdrink3 = findViewById(R.id.checkdrink3);
        btn_payment = findViewById(R.id.btn_payment);


        btn_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Reservation.this, CheckoutActivity.class);
                startActivity(i);
            }
        });

    }
}
