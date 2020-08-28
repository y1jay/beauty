package com.yijun.beauty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

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

    TextView txtmain1;
    TextView txtmain2;
    TextView txtmain3;
    TextView txtmain4;
    TextView txtmain5;
    TextView txtmain6;
    TextView txtset1;
    TextView txtset2;
    TextView txtset3;
    TextView txtset4;
    TextView txtset5;
    TextView txtset6;
    TextView txtset7;
    TextView txtset8;
    TextView txtmenu1;
    TextView txtmenu2;
    TextView txtmenu3;
    TextView txtmenu4;
    TextView txtmenu5;
    TextView txtmenu6;
    TextView txtmenu7;
    TextView txtside1;
    TextView txtside2;
    TextView txtside3;
    TextView txtside4;
    TextView txtdrink1;
    TextView txtdrink2;
    TextView txtdrink3;

    TextView paymain1;
    TextView paymain2;
    TextView paymain3;
    TextView paymain4;
    TextView paymain5;
    TextView paymain6;
    TextView payset1;
    TextView payset2;
    TextView paysetsmall3;
    TextView paysetmedium3;
    TextView paysetbig3;
    TextView paysetsmall4;
    TextView paysetmedium4;
    TextView paysetbig4;
    TextView paysetsmall5;
    TextView paysetmedium5;
    TextView paysetbig5;
    TextView paysetsmall6;
    TextView paysetmedium6;
    TextView paysetbig6;
    TextView paysetsmall7;
    TextView paysetmedium7;
    TextView paysetbig7;
    TextView payset8;
    TextView paymenusmall1;
    TextView paymenumedium1;
    TextView paymenubig1;
    TextView paymenusmall2;
    TextView paymenumedium2;
    TextView paymenubig2;
    TextView paymenusmall3;
    TextView paymenumedium3;
    TextView paymenusmall4;
    TextView paymenumedium4;
    TextView paymenubig4;
    TextView paymenusmall5;
    TextView paymenumedium5;
    TextView paymenubig5;
    TextView paymenu6;
    TextView paymenu7;
    TextView payside1;
    TextView payside2;
    TextView payside3;
    TextView payside4;
    TextView paydrink1;
    TextView paydrink2;
    TextView paydrink3;











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

        txtmain1 = findViewById(R.id.txtmain1);
        txtmain2 = findViewById(R.id.txtmain2);
        txtmain3 = findViewById(R.id.txtmain3);
        txtmain4 = findViewById(R.id.txtmain4);
        txtmain5 =findViewById(R.id.txtmain5);
        txtmain5 = findViewById(R.id.txtmain5);
        txtmain6 = findViewById(R.id.txtmain6);
        txtset1 = findViewById(R.id.txtset1);
        txtset2 = findViewById(R.id.txtset2);
        txtset3 = findViewById(R.id.txtset3);
        txtset4 = findViewById(R.id.txtset4);
        txtset5 = findViewById(R.id.txtset5);
        txtset6 = findViewById(R.id.txtset6);
        txtset7 = findViewById(R.id.txtset7);
        txtset8 = findViewById(R.id.txtset8);
        txtmenu1 = findViewById(R.id.txtmenu1);
        txtmenu2 = findViewById(R.id.txtmenu2);
        txtmenu3 = findViewById(R.id.txtmenu3);
        txtmenu4 =findViewById(R.id.txtmenu4);
        txtmenu5 = findViewById(R.id.txtmenu5);
        txtmenu6 = findViewById(R.id.txtmenu6);
        txtmenu7 = findViewById(R.id.txtmenu7);
        txtside1 = findViewById(R.id.txtside1);
        txtside2 = findViewById(R.id.txtside2);
        txtside3 = findViewById(R.id.txtside3);
        txtside4 = findViewById(R.id.txtside4);
        txtdrink1 = findViewById(R.id.txtdrink1);
        txtdrink2 =findViewById(R.id.txtdrink2);
        txtdrink3 =findViewById(R.id.txtdrink3);

        paymain1 = findViewById(R.id.paymain1);
        paymain2 = findViewById(R.id.paymain2);
        paymain3 = findViewById(R.id.paymain3);
        paymain4 = findViewById(R.id.paymain4);
        paymain5 = findViewById(R.id.paymain5);
        paymain6 = findViewById(R.id.paymain6);

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
