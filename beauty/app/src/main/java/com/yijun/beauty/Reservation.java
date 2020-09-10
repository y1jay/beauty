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

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Reservation extends AppCompatActivity {

    Button btn_payment;



    CheckBox check_main_menu1;
    CheckBox check_main_menu2;
    CheckBox check_main_menu3;
    CheckBox check_set_menu1;
    CheckBox check_set_menu2;
    CheckBox check_chicken_feet_big1;
    CheckBox check_chicken_feet_middle1;
    CheckBox check_chicken_feet_small1;
    CheckBox check_chicken_feet_big2;
    CheckBox check_chicken_feet_middle2;
    CheckBox check_chicken_feet_small2;
    CheckBox check_chicken_feet_big3;
    CheckBox check_chicken_feet_middle3;
    CheckBox check_chicken_feet_small3;
    CheckBox check_chicken_feet_big4;
    CheckBox check_chicken_feet_middle4;
    CheckBox check_chicken_feet_small4;
    CheckBox check_chicken_feet_big5;
    CheckBox check_chicken_feet_middle5;
    CheckBox check_chicken_feet_small5;
    CheckBox check_chicken_feet_big6;
    CheckBox check_chicken_feet_middle6;
    CheckBox check_chicken_feet_small6;
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
    TextView pay_chicken_feet_small2;
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
    TextView pay_pocha_menu3;
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
        check_main_menu2 = findViewById(R.id.check_main_menu2);
        check_main_menu3 = findViewById(R.id.check_main_menu3);
        check_set_menu1 = findViewById(R.id.check_set_menu1);
        check_set_menu2 = findViewById(R.id.check_set_menu2);
        check_chicken_feet_big1 = findViewById(R.id.check_chicken_feet_big1);
        check_chicken_feet_middle1 = findViewById(R.id.check_chicken_feet_middle1);
        check_chicken_feet_small1 = findViewById(R.id.check_chicken_feet_small1);
        check_chicken_feet_big2 = findViewById(R.id.check_chicken_feet_big2);
        check_chicken_feet_middle2 = findViewById(R.id.check_chicken_feet_middle2);
        check_chicken_feet_small2 = findViewById(R.id.check_chicken_feet_small2);
        check_chicken_feet_big3 = findViewById(R.id.check_chicken_feet_big3);
        check_chicken_feet_middle3 = findViewById(R.id.check_chicken_feet_middle3);
        check_chicken_feet_small3  = findViewById(R.id.check_chicken_feet_small3);
        check_chicken_feet_big4 = findViewById(R.id.check_chicken_feet_big4);
        check_chicken_feet_middle4 = findViewById(R.id.check_chicken_feet_middle4);
        check_chicken_feet_small4 = findViewById(R.id.check_chicken_feet_small4);
        check_chicken_feet_big5 = findViewById(R.id.check_chicken_feet_big5);
        check_chicken_feet_middle5 = findViewById(R.id.check_chicken_feet_middle5);
        check_chicken_feet_small5 = findViewById(R.id.check_chicken_feet_small5);
        check_chicken_feet_big6 = findViewById(R.id.check_chicken_feet_big6);
        check_chicken_feet_middle6 = findViewById(R.id.check_chicken_feet_middle6);
        check_chicken_feet_small6 = findViewById(R.id.check_chicken_feet_small6);
        check_pocha_menu1 = findViewById(R.id.check_pocha_menu1);
        check_pocha_menu2 = findViewById(R.id.check_pocha_menu2);
        check_pocha_menu_basick3 = findViewById(R.id.check_pocha_menu_basic3);
        check_pocha_menu_hot3 = findViewById(R.id.check_pocha_menu_hot3);
        check_pocha_menu4 = findViewById(R.id.check_pocha_menu4);
        check_pocha_menu5 = findViewById(R.id.check_pocha_menu5);
        check_pocha_menu6 = findViewById(R.id.check_pocha_menu6);
        check_pocha_menu7 = findViewById(R.id.check_pocha_menu7);
        check_side1 = findViewById(R.id.check_side1);
        check_side2 = findViewById(R.id.check_side2);
        check_side3 = findViewById(R.id.check_side3);
        check_side4 = findViewById(R.id.check_side4);
        check_add1 = findViewById(R.id.check_add1);
        check_add2 = findViewById(R.id.check_add2);
        check_add3 = findViewById(R.id.check_add3);
        check_add4 = findViewById(R.id.check_add4);
        check_add5 = findViewById(R.id.check_add5);
        check_add6 = findViewById(R.id.check_add6);
        check_add7 = findViewById(R.id.check_add7);
        check_add8 = findViewById(R.id.check_add8);
        check_add9 = findViewById(R.id.check_add9);
        check_add10 = findViewById(R.id.check_add10);
        check_add11 =findViewById(R.id.check_add11);
        check_add12 = findViewById(R.id.check_add12);
        check_add13 = findViewById(R.id.check_add13);
        check_drink1 = findViewById(R.id.check_drink1);
        check_drink2 = findViewById(R.id.check_drink2);
        check_drink3 = findViewById(R.id.check_drink3);









        main_menu1 = findViewById(R.id.main_menu1);
        main_menu2 = findViewById(R.id.main_menu2);
        main_menu3 = findViewById(R.id.main_menu3);
        set_menu1 = findViewById(R.id.set_menu1);
        set_menu2 = findViewById(R.id.set_menu2);
        chicken_feet1 = findViewById(R.id.chicken_feet1);
        chicken_feet2 = findViewById(R.id.chicken_feet2);
        chicken_feet3 = findViewById(R.id.chicken_feet3);
        chicken_feet4 = findViewById(R.id.chicken_feet4);
        chicken_feet5 = findViewById(R.id.chicken_feet5);
        chicken_feet6 = findViewById(R.id.chicken_feet6);
        pocha_menu1 = findViewById(R.id.pocha_menu1);
        pocha_menu2 = findViewById(R.id.pocha_menu2);
        pocha_menu3 = findViewById(R.id.pocha_menu3);
        pocha_menu4 = findViewById(R.id.pocha_menu4);
        pocha_menu5 = findViewById(R.id.pocha_menu5);
        pocha_menu6 = findViewById(R.id.pocha_menu6);
        pocha_menu7 = findViewById(R.id.pocha_menu7);
        side_menu1 = findViewById(R.id.side_menu1);
        side_menu2 = findViewById(R.id.set_menu2);
        side_menu3 = findViewById(R.id.side_menu3);
        side_menu4 = findViewById(R.id.side_menu4);
        add_menu1 = findViewById(R.id.add_menu1);
        add_menu2 = findViewById(R.id.add_menu2);
        add_menu3 = findViewById(R.id.add_menu3);
        add_menu4 = findViewById(R.id.add_menu4);
        add_menu5= findViewById(R.id.add_menu5);
        add_menu6 = findViewById(R.id.add_menu6);
        add_menu7 = findViewById(R.id.add_menu7);
        add_menu8 = findViewById(R.id.add_menu8);
        add_menu9 = findViewById(R.id.add_menu9);
        add_menu10 = findViewById(R.id.add_menu10);
        add_menu11 = findViewById(R.id.add_menu11);
        add_menu12 = findViewById(R.id.add_menu12);
        add_menu13 = findViewById(R.id.add_menu13);
        drink1 = findViewById(R.id.drink1);
        drink2 = findViewById(R.id.drink2);
        drink3 = findViewById(R.id.drink3);






        pay_main1 = findViewById(R.id.pay_main1);
        pay_main2 = findViewById(R.id.pay_main2);
        pay_main3 = findViewById(R.id.pay_main3);
        pay_set1 = findViewById(R.id.pay_set1);
        pay_set2 = findViewById(R.id.pay_set2);
        pay_chicken_feet_big1 = findViewById(R.id.pay_chicken_feet_big1);
        pay_chicken_feet_middle1 = findViewById(R.id.pay_chicken_feet_middle1);
        pay_chicken_feet_small1 = findViewById(R.id.pay_chicken_feet_small1);
        pay_chicken_feet_big2 = findViewById(R.id.pay_chicken_feet_big2);
        pay_chicken_feet_middle2 = findViewById(R.id.pay_chicken_feet_middle2);
        pay_chicken_feet_small2 = findViewById(R.id.pay_chicken_feet_small2);
        pay_chicken_feet_big3 = findViewById(R.id.pay_chicken_feet_big3);
        pay_chicken_feet_middle3 = findViewById(R.id.pay_chicken_feet_middle3);
        pay_chicken_feet_small3 = findViewById(R.id.pay_chicken_feet_small3);
        pay_chicken_feet_big4 = findViewById(R.id.pay_chicken_feet_big4);
        pay_chicken_feet_middle4 = findViewById(R.id.pay_chicken_feet_middle4);
        pay_chicken_feet_small4 = findViewById(R.id.pay_chicken_feet_small4);
        pay_chicken_feet_big5 = findViewById(R.id.pay_chicken_feet_big5);
        pay_chicken_feet_middle5 = findViewById(R.id.pay_chicken_feet_middle5);
        pay_chicken_feet_small5 = findViewById(R.id.pay_chicken_feet_small5);
        pay_chicken_feet_big6 = findViewById(R.id.pay_chicken_feet_big6);
        pay_chicken_feet_middle6 = findViewById(R.id.pay_chicken_feet_middle6);
        pay_chicken_feet_small6 =findViewById(R.id.pay_chicken_feet_small6);
        pay_pocha_menu1 = findViewById(R.id.pay_pocha_menu1);
        pay_pocha_menu2 = findViewById(R.id.pay_pocha_menu2);
        pay_pocha_menu3 = findViewById(R.id.pay_pocha_menu3);
        pay_pocha_menu4 = findViewById(R.id.pay_pocha_menu4);
        pay_pocha_menu5 = findViewById(R.id.pay_pocha_menu5);
        pay_pocha_menu6 = findViewById(R.id.pay_pocha_menu6);
        pay_pocha_menu7 = findViewById(R.id.pay_pocha_menu7);
        pay_side1 = findViewById(R.id.pay_side1);
        pay_side2 = findViewById(R.id.pay_side2);
        pay_side3 = findViewById(R.id.pay_side3);
        pay_side4 = findViewById(R.id.pay_side4);
        pay_add1 = findViewById(R.id.pay_add1);
        pay_add2 = findViewById(R.id.pay_add2);
        pay_add3 = findViewById(R.id.pay_add3);
        pay_add4 = findViewById(R.id.pay_add4);
        pay_add5 = findViewById(R.id.pay_add5);
        pay_add6 = findViewById(R.id.pay_add6);
        pay_add7 = findViewById(R.id.pay_add7);
        pay_add8 = findViewById(R.id.pay_add8);
        pay_add9 = findViewById(R.id.pay_add9);
        pay_add10 = findViewById(R.id.pay_add10);
        pay_add11 = findViewById(R.id.pay_add11);
        pay_add12 = findViewById(R.id.pay_add12);
        pay_add13 = findViewById(R.id.pay_add13);
        pay_drink1 = findViewById(R.id.pay_drink1);
        pay_drink2 = findViewById(R.id.pay_drink2);
        pay_drink3 = findViewById(R.id.pay_drink3);





        btn_payment = findViewById(R.id.btn_payment);
        btn_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder alert = new AlertDialog.Builder(Reservation.this);
                View alertView = getLayoutInflater().inflate(R.layout.order,null);
                menu = alertView.findViewById(R.id.menu);
                price = alertView.findViewById(R.id.price);
                order_no = alertView.findViewById(R.id.order_no);
                order_payment = alertView.findViewById(R.id.order_payment);


//                if (check_main_menu1.isChecked() == true){
//
//                    String main = main_menu1.getText().toString().trim();
//                    String pay = pay_main1.getText().toString().trim();
//
//                    menu.setText(main+"/n");
//                    String nowon = pay.replace("원", "");
//                    long value = Long.parseLong(nowon);
//                    DecimalFormat format = new DecimalFormat("###,###");//콤마
//                    format.format(value);
//                    String result_int = format.format(value);
//                    int num = Integer.parseInt(result_int);
//
//
//
//                }

                if (check_main_menu2.isChecked() == true && check_main_menu1.isChecked() == true ){


                    String main = main_menu1.getText().toString().trim();
                    String pay = pay_main1.getText().toString().trim();
                    String nowon = pay.replace("원", "");
                    String nocomma = nowon.replace(",","");


                    String main2 = main_menu2.getText().toString().trim();
                    String pay2 = pay_main2.getText().toString().trim();

                    menu.setText(main+"\n"+main2);
                    String noowon = pay2.replace("원", "");
                    String nocomma1 = noowon.replace(",","");



                    int num1 = Integer.parseInt(nocomma1);
                    int num = Integer.parseInt(nocomma);

                    int result = num+num1;
                    String string = Integer.toString(result);



                    long valoue = Long.parseLong(string);
                    DecimalFormat format = new DecimalFormat("###,###");//콤마
                    format.format(valoue);
                    String result_int = format.format(valoue);





                    price.setText(result_int);



                }

                order_payment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Reservation.this, CheckoutActivity.class);
//                        i.putExtra("main", main );
//                        i.putExtra("pay",);
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
