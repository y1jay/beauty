package com.yijun.beauty;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.yijun.beauty.activity.CheckoutActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Reservation extends AppCompatActivity {

    Button btn_payment;



    CheckBox check_main_menu1;
    CheckBox check_main_menu2;
    CheckBox check_main_menu3;
    CheckBox check_set_menu1;
    CheckBox check_set_menu2;
    CheckBox chek_chicken_feet_big1;
    CheckBox chek_chicken_feet_middle1;
    CheckBox chek_chicken_feet_small1;
    CheckBox chek_chicken_feet_big2;
    CheckBox chek_chicken_feet_middle2;
    CheckBox chek_chicken_feet_small2;
    CheckBox chek_chicken_feet_big3;
    CheckBox chek_chicken_feet_middle3;
    CheckBox chek_chicken_feet_small3;
    CheckBox chek_chicken_feet_big4;
    CheckBox chek_chicken_feet_middle4;
    CheckBox chek_chicken_feet_small4;
    CheckBox chek_chicken_feet_big5;
    CheckBox chek_chicken_feet_middle5;
    CheckBox chek_chicken_feet_small5;
    CheckBox chek_chicken_feet_big6;
    CheckBox chek_chicken_feet_middle6;
    CheckBox chek_chicken_feet_small6;
    CheckBox check_pocha_menu1;
    CheckBox check_pocha_menu2;
    CheckBox check_pocha_menu_basick3;
    CheckBox check_pocha_menu_hot3;
    CheckBox check_pocha_menu4;
    CheckBox check_pocha_menu5;
    CheckBox check_pocha_menu6;
    CheckBox check_pocha_menu7;
    CheckBox check_side1;
    CheckBox check_side2;
    CheckBox check_side3;
    CheckBox check_side4;
    CheckBox check_add1;
    CheckBox check_add2;
    CheckBox check_add3;
    CheckBox check_add4;
    CheckBox check_add5;
    CheckBox check_add6;
    CheckBox check_add7;
    CheckBox check_add8;
    CheckBox check_add9;
    CheckBox check_add10;
    CheckBox check_add11;
    CheckBox check_add12;
    CheckBox check_add13;
    CheckBox check_drink1;
    CheckBox check_drink2;
    CheckBox check_drink3;










    TextView main_menu1;
    TextView main_menu2;
    TextView main_menu3;
    TextView set_menu1;
    TextView set_menu2;
    TextView chicken_feet1;
    TextView chicken_feet2;
    TextView chicken_feet3;
    TextView chicken_feet4;
    TextView chicken_feet5;
    TextView chicken_feet6;
    TextView pocha_menu1;
    TextView pocha_menu2;
    TextView pocha_menu3;
    TextView pocha_menu4;
    TextView pocha_menu5;
    TextView pocha_menu6;
    TextView pocha_menu7;
    TextView side_menu1;
    TextView side_menu2;
    TextView side_menu3;
    TextView side_menu4;
    TextView add_menu1;
    TextView add_menu2;
    TextView add_menu3;
    TextView add_menu4;
    TextView add_menu5;
    TextView add_menu6;
    TextView add_menu7;
    TextView add_menu8;
    TextView add_menu9;
    TextView add_menu10;
    TextView add_menu11;
    TextView add_menu12;
    TextView add_menu13;
    TextView drink1;
    TextView drink2;
    TextView drink3;


    TextView pay_main1;
    TextView pay_main2;
    TextView pay_main3;
    TextView pay_set1;
    TextView pay_set2;
    TextView pay_chicken_feet_big1;
    TextView pay_chicken_feet_middle1;
    TextView pay_chicken_feet_small1;
    TextView pay_chicken_feet_big2;
    TextView pay_chicken_feet_middle2;
    TextView getPay_chicken_feet_small2;
    TextView pay_chicken_feet_big3;
    TextView pay_chicken_feet_middle3;
    TextView pay_chicken_feet_small3;
    TextView pay_chicken_feet_big4;
    TextView pay_chicken_feet_middle4;
    TextView pay_chicken_feet_small4;
    TextView pay_chicken_feet_big5;
    TextView pay_chicken_feet_middle5;
    TextView pay_chicken_feet_small5;
    TextView pay_chicken_feet_big6;
    TextView pay_chicken_feet_middle6;
    TextView pay_chicken_feet_small6;
    TextView pay_pocha_menu1;
    TextView pay_pocha_menu2;
    TextView pay_pocha_menu_basic3;
    TextView pay_menu_hot3;
    TextView pay_pocha_menu4;
    TextView pay_pocha_menu5;
    TextView pay_pocha_menu6;
    TextView pay_pocha_menu7;
    TextView pay_side1;
    TextView pay_side2;
    TextView pay_side3;
    TextView pay_side4;
    TextView pay_add1;
    TextView pay_add2;
    TextView pay_add3;
    TextView pay_add4;
    TextView pay_add5;
    TextView pay_add6;
    TextView pay_add7;
    TextView pay_add8;
    TextView pay_add9;
    TextView pay_add10;
    TextView pay_add11;
    TextView pay_add12;
    TextView pay_add13;
    TextView pay_drink1;
    TextView pay_drink2;
    TextView pay_drink3;






    AlertDialog alertDialog;
    TextView menu;
    TextView price;
    TextView total;
    Button order_no;
    Button order_payment;

    ArrayList menuArrayList;
    ArrayList priceArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        check_main_menu1 = findViewById(R.id.check_main_menu1);
        main_menu1 = findViewById(R.id.main_menu1);
        pay_main1 = findViewById(R.id.pay_main1);

        check_main_menu2 = findViewById(R.id.check_main_menu2);
        main_menu2 = findViewById(R.id.main_menu2);
        pay_main2 = findViewById(R.id.pay_main2);

        if (check_main_menu1.isChecked() == true){
            String main = main_menu1.getText().toString().trim();
            String pay = pay_main1.getText().toString().trim();

            menuArrayList.add(0, main);
            priceArrayList.add(0, pay);
        }

        if (check_main_menu2.isChecked() == true){
            String main = main_menu2.getText().toString().trim();
            String pay = pay_main2.getText().toString().trim();

            menuArrayList.add(1, main);
            priceArrayList.add(2, pay);
        }

        btn_payment = findViewById(R.id.btn_payment);
        btn_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (check_main_menu1.isChecked() == true){
//                    String main = main_menu1.getText().toString().trim();
//                    String pay = pay_main1.getText().toString().trim();
//
//                    Intent i = new Intent(Reservation.this, CheckoutActivity.class);
//                    i.putExtra("main", main);
//                    i.putExtra("pay", pay);
//                    startActivity(i);
//                }

                AlertDialog.Builder alert = new AlertDialog.Builder(Reservation.this);
                View alertView = getLayoutInflater().inflate(R.layout.order,null);
                menu = alertView.findViewById(R.id.menu);
                price = alertView.findViewById(R.id.price);
                total = alertView.findViewById(R.id.total);
                order_no = alertView.findViewById(R.id.order_no);
                order_payment = alertView.findViewById(R.id.order_payment);

                String main = main_menu1.getText().toString().trim();
                String pay = pay_main1.getText().toString().trim();
                if (check_main_menu1.isChecked() == true){

                    menu.setText(main);
                    String nowon = pay.replace("원", "");
                    price.setText(nowon);
                    total.setText(pay);
                }

                order_payment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Reservation.this, CheckoutActivity.class);
                        i.putExtra("main", main);
                        i.putExtra("pay",pay);
                        startActivity(i);
                    }
                });

                order_no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.cancel();
                    }
                });

                alert.setView(alertView);

                alertDialog = alert.create();
                alertDialog.setCancelable(false);
                alertDialog.show();
            }
        });



    }
}
