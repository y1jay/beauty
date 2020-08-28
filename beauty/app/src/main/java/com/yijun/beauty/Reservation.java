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
    CheckBox mainmenu1;
    CheckBox mainmenu2;
    CheckBox mainmenu3;
    CheckBox mainmenu4;
    CheckBox mainmenu5;
    CheckBox mainmenu6;
    CheckBox setmenu1;
    CheckBox setmenu2;
    CheckBox setmenusmall3;
    CheckBox setmenumedium3;
    CheckBox setmenubig3;
    CheckBox setmenusmall4;
    CheckBox setmenumedium4;
    CheckBox setmenubig4;
    CheckBox setmenusmall5;
    CheckBox setmenumedium5;
    CheckBox setmenubig5;
    CheckBox setmenusmall6;
    CheckBox setmenumedium6;
    CheckBox setmenubig6;
    CheckBox setmenusmall7;
    CheckBox setmenumedium7;
    CheckBox setmenu8;
    CheckBox menusmall1;
    CheckBox menumedium1;
    CheckBox menubig1;
    CheckBox menusmall2;
    CheckBox menumedium2;
    CheckBox menubig2;
    CheckBox menusmall3;
    CheckBox menumedium3;
    CheckBox menusmall4;
    CheckBox menumedium4;
    CheckBox menubig4;
    CheckBox menusmall5;
    CheckBox menumedium5;
    CheckBox menubig5;
    CheckBox menu6;
    CheckBox menu7;
    CheckBox sidemenu1;
    CheckBox sidemenu2;
    CheckBox sidemenu3;
    CheckBox sidemenu4;
    CheckBox drink1;
    CheckBox drink2;
    CheckBox drink3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);


        mainmenu1 = findViewById(R.id.mainmenu1);
        mainmenu2 = findViewById(R.id.mainmenu2);
        mainmenu3 = findViewById(R.id.mainmenu3);
        mainmenu4 = findViewById(R.id.mainmeun4);
        mainmenu5 = findViewById(R.id.mainmenu5);
        mainmenu6 = findViewById(R.id.mainmenu6);
        setmenu1 = findViewById(R.id.setmenu1);
        setmenu2 = findViewById(R.id.setmenu2);
        setmenusmall3 = findViewById(R.id.setmenusmall3);
        setmenumedium3 = findViewById(R.id.setmenumedium3);
        setmenubig3 = findViewById(R.id.setmenubig3);
        setmenusmall4 = findViewById(R.id.setmenusmall4);
        setmenumedium4 = findViewById(R.id.setmenumedium4);
        setmenubig4 = findViewById(R.id.setmenubig4);
        setmenusmall5 = findViewById(R.id.setsmall5);
        setmenumedium5 = findViewById(R.id.setmenumedium5);
        setmenubig5 = findViewById(R.id.setmenubig5);
        setmenusmall6 = findViewById(R.id.setmenusmall6);
        setmenumedium6 = findViewById(R.id.setmenumedium6);
        setmenubig6 = findViewById(R.id.setmenubig6);
        setmenusmall7 = findViewById(R.id.setmenusmall7);
        setmenumedium7 = findViewById(R.id.setmenumedium7);
        setmenu8 = findViewById(R.id.setmenu8);
        menusmall1 = findViewById(R.id.menusmall1);
        menumedium1 = findViewById(R.id.menumedium1);
        menubig1 = findViewById(R.id.menubig1);
        menusmall2 = findViewById(R.id.menusmall2);
        menumedium2 = findViewById(R.id.menumedium2);
        menubig2 = findViewById(R.id.menubig2);
        menusmall3 = findViewById(R.id.menusmall3);
        menumedium3 = findViewById(R.id.menumedium3);
        menusmall4 = findViewById(R.id.menusmall4);
        menumedium4 = findViewById(R.id.menumedium4);
        menubig4 = findViewById(R.id.menubig4);
        menusmall5 = findViewById(R.id.menusmall5);
        menumedium5 = findViewById(R.id.menumedium5);
        menubig5 = findViewById(R.id.menubig5);
        menu6 = findViewById(R.id.menu6);
        menu7 = findViewById(R.id.menu7);
        sidemenu1 = findViewById(R.id.sidemenu1);
        sidemenu2 = findViewById(R.id.sidemenu2);
        sidemenu3 = findViewById(R.id.sidemenu3);
        sidemenu4 = findViewById(R.id.sidemenu4);
        drink1 = findViewById(R.id.drink1);
        drink2 = findViewById(R.id.drink2);
        drink3 = findViewById(R.id.drink3);
        
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
