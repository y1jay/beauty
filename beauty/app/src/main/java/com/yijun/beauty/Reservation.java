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


    TextView main_menu1;
    TextView main_menu2;



    TextView pay_main1;
    TextView pay_main2;

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
//                AlertDialog.Builder alert = new AlertDialog.Builder(Reservation.this);
//                View alertView = getLayoutInflater().inflate(R.layout.order,null);
//                menu = alertView.findViewById(R.id.menu);
//                price = alertView.findViewById(R.id.price);
//                total = alertView.findViewById(R.id.total);
//                order_no = alertView.findViewById(R.id.order_no);
//                order_payment = alertView.findViewById(R.id.order_payment);
//
//                String menu1 = menuArrayList.get(0).toString();
//                String price1 = priceArrayList.get(0).toString();
//
//                String menu2 = menuArrayList.get(1).toString();
//                String price2 = priceArrayList.get(1).toString();
//
//                menu.setText(menu1+"\n"+menu2);
//                price.setText(price1+"\n"+price2);
//
//                total.setText(price1+price2);

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
