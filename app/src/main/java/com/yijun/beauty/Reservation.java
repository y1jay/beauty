package com.yijun.beauty;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.yijun.beauty.activity.CheckoutActivity;
import com.yijun.beauty.adapter.OrderSheetAdapter;
import com.yijun.beauty.adapter.ReviewclerViewAdapter;
import com.yijun.beauty.api.NetworkClient;
import com.yijun.beauty.api.ReservationApi;
import com.yijun.beauty.model.Orders;
import com.yijun.beauty.model.ReservationReq;
import com.yijun.beauty.model.ReservationRes;
import com.yijun.beauty.model.Rows;
import com.yijun.beauty.url.Utils;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Reservation extends AppCompatActivity {

    public static Context mContext;
    Double total_price;

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
    RecyclerView recyclerView;
    OrderSheetAdapter adapter;
    ArrayList<Orders> orderArrayList = new ArrayList<>();
    TextView price;

    MaterialSpinner people_spinner;
    MaterialSpinner spinner_month;
    MaterialSpinner spinner_day;
    MaterialSpinner spinner_hour;
    String people;
    String month;
    String day;
    String hour;
    int pp;
    int mm;
    int dd;
    int hh;

    RadioGroup radio_group;
    RadioButton take_out;
    RadioButton store;
    Button order_no;
    Button order_payment;

    SharedPreferences sp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        mContext = this;

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


        // check box 확인
        check_main_menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String main = main_menu1.getText().toString().trim();
                String pay = pay_main1.getText().toString().trim();

                if (check_main_menu1.isChecked() == true){
                    add_menu(main, pay);
                }else {
                    delete_menu(main, pay);
                }
            }
        });
        check_main_menu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String main = main_menu2.getText().toString().trim();
                String pay = pay_main2.getText().toString().trim();

                if (check_main_menu2.isChecked() == true){
                    add_menu(main, pay);
                }else {
                    delete_menu(main, pay);
                }
            }
        });
        check_main_menu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String main = main_menu3.getText().toString().trim();
                String pay = pay_main3.getText().toString().trim();

                if (check_main_menu3.isChecked() == true){
                    add_menu(main, pay);
                }else {
                    delete_menu(main, pay);
                }
            }
        });
        check_set_menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String main = set_menu1.getText().toString().trim();
                String pay = pay_set1.getText().toString().trim();

                if (check_set_menu1.isChecked() == true){
                    add_menu(main, pay);
                }else {
                    delete_menu(main, pay);
                }
            }
        });
        check_set_menu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String main = set_menu2.getText().toString().trim();
                String pay = pay_set2.getText().toString().trim();

                if (check_set_menu2.isChecked() == true){
                    add_menu(main, pay);
                }else {
                    delete_menu(main, pay);
                }
            }
        });
        check_chicken_feet_big1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String main = chicken_feet1.getText().toString().trim();
                String pay = pay_chicken_feet_big1.getText().toString().trim();

                if (check_chicken_feet_big1.isChecked() == true){
                    add_menu(main+"(대)", pay);
                }else {
                    delete_menu(main+"(대)", pay);
                }
            }
        });
        check_chicken_feet_middle1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String main = chicken_feet1.getText().toString().trim();
                String pay = pay_chicken_feet_middle1.getText().toString().trim();

                if (check_chicken_feet_middle1.isChecked() == true){
                    add_menu(main+"(중)", pay);
                }else {
                    delete_menu(main+"(중)", pay);
                }
            }
        });
        check_chicken_feet_small1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String main = chicken_feet1.getText().toString().trim();
                String pay = pay_chicken_feet_small1.getText().toString().trim();

                if (check_chicken_feet_small1.isChecked() == true){
                    add_menu(main+"(소)", pay);
                }else {
                    delete_menu(main+"(소)", pay);
                }
            }
        });
        check_chicken_feet_big2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String main = chicken_feet2.getText().toString().trim();
                String pay = pay_chicken_feet_big2.getText().toString().trim();

                if (check_chicken_feet_big2.isChecked() == true){
                    add_menu(main+"(대)", pay);
                }else {
                    delete_menu(main+"(대)", pay);
                }
            }
        });
        check_chicken_feet_middle2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String main = chicken_feet2.getText().toString().trim();
                String pay = pay_chicken_feet_middle2.getText().toString().trim();

                if (check_chicken_feet_middle2.isChecked() == true){
                    add_menu(main+"(중)", pay);
                }else {
                    delete_menu(main+"(중)", pay);
                }
            }
        });
        check_chicken_feet_small2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String main = chicken_feet2.getText().toString().trim();
                String pay = pay_chicken_feet_small2.getText().toString().trim();

                if (check_chicken_feet_small2.isChecked() == true){
                    add_menu(main+"(소)", pay);
                }else {
                    delete_menu(main+"(소)", pay);
                }
            }
        });
        check_chicken_feet_big3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String main = chicken_feet3.getText().toString().trim();
                String pay = pay_chicken_feet_big3.getText().toString().trim();

                if (check_chicken_feet_big3.isChecked() == true){
                    add_menu(main+"(대)", pay);
                }else {
                    delete_menu(main+"(대)", pay);
                }
            }
        });
        check_chicken_feet_middle3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String main = chicken_feet3.getText().toString().trim();
                String pay = pay_chicken_feet_middle3.getText().toString().trim();

                if (check_chicken_feet_middle3.isChecked() == true){
                    add_menu(main+"(중)", pay);
                }else {
                    delete_menu(main+"(중)", pay);
                }
            }
        });
        check_chicken_feet_small3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String main = chicken_feet3.getText().toString().trim();
                String pay = pay_chicken_feet_small3.getText().toString().trim();

                if (check_chicken_feet_small3.isChecked() == true){
                    add_menu(main+"(소)", pay);
                }else {
                    delete_menu(main+"(소)", pay);
                }
            }
        });
        check_chicken_feet_big4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String main = chicken_feet4.getText().toString().trim();
                String pay = pay_chicken_feet_big4.getText().toString().trim();

                if (check_chicken_feet_big4.isChecked() == true){
                    add_menu(main+"(대)", pay);
                }else {
                    delete_menu(main+"(대)", pay);
                }
            }
        });
        check_chicken_feet_middle4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String main = chicken_feet4.getText().toString().trim();
                String pay = pay_chicken_feet_middle4.getText().toString().trim();

                if (check_chicken_feet_middle4.isChecked() == true){
                    add_menu(main+"(중)", pay);
                }else {
                    delete_menu(main+"(중)", pay);
                }
            }
        });
        check_chicken_feet_small4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String main = chicken_feet4.getText().toString().trim();
                String pay = pay_chicken_feet_small4.getText().toString().trim();

                if (check_chicken_feet_small4.isChecked() == true){
                    add_menu(main+"(소)", pay);
                }else {
                    delete_menu(main+"(소)", pay);
                }
            }
        });
        check_chicken_feet_big5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String main = chicken_feet5.getText().toString().trim();
                String pay = pay_chicken_feet_big5.getText().toString().trim();

                if (check_chicken_feet_big5.isChecked() == true){
                    add_menu(main+"(대)", pay);
                }else {
                    delete_menu(main+"(대)", pay);
                }
            }
        });
        check_chicken_feet_middle5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String main = chicken_feet5.getText().toString().trim();
                String pay = pay_chicken_feet_middle5.getText().toString().trim();

                if (check_chicken_feet_middle5.isChecked() == true){
                    add_menu(main+"(중)", pay);
                }else {
                    delete_menu(main+"(중)", pay);
                }
            }
        });
        check_chicken_feet_small5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String main = chicken_feet5.getText().toString().trim();
                String pay = pay_chicken_feet_small5.getText().toString().trim();

                if (check_chicken_feet_small5.isChecked() == true){
                    add_menu(main+"(소)", pay);
                }else {
                    delete_menu(main+"(소)", pay);
                }
            }
        });
        check_chicken_feet_big6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String main = chicken_feet6.getText().toString().trim();
                String pay = pay_chicken_feet_big6.getText().toString().trim();

                if (check_chicken_feet_big6.isChecked() == true){
                    add_menu(main+"(대)", pay);
                }else {
                    delete_menu(main+"(대)", pay);
                }
            }
        });
        check_chicken_feet_middle6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String main = chicken_feet6.getText().toString().trim();
                String pay = pay_chicken_feet_middle6.getText().toString().trim();

                if (check_chicken_feet_middle6.isChecked() == true){
                    add_menu(main+"(중)", pay);
                }else {
                    delete_menu(main+"(중)", pay);
                }
            }
        });
        check_chicken_feet_small6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String main = chicken_feet6.getText().toString().trim();
                String pay = pay_chicken_feet_small6.getText().toString().trim();

                if (check_chicken_feet_small6.isChecked() == true){
                    add_menu(main+"(소)", pay);
                }else {
                    delete_menu(main+"(소)", pay);
                }
            }
        });
        check_pocha_menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String main = pocha_menu1.getText().toString().trim();
                String pay = pay_pocha_menu1.getText().toString().trim();

                if (check_pocha_menu1.isChecked() == true){
                    add_menu(main, pay);
                }else {
                    delete_menu(main, pay);
                }
            }
        });
        check_pocha_menu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String main = pocha_menu2.getText().toString().trim();
                String pay = pay_pocha_menu2.getText().toString().trim();

                if (check_pocha_menu2.isChecked() == true){
                    add_menu(main, pay);
                }else {
                    delete_menu(main, pay);
                }
            }
        });
        // 치즈떡볶이 보통맛
        check_pocha_menu_basick3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String main = pocha_menu3.getText().toString().trim();
                String pay = pay_pocha_menu3.getText().toString().trim();

                if (check_pocha_menu_basick3.isChecked() == true){
                    add_menu(main+"(보통맛)", pay);
                }else {
                    delete_menu(main+"(보통맛)", pay);
                }
            }
        });
        // 치즈떡볶이 매운맛
        check_pocha_menu_hot3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String main = pocha_menu3.getText().toString().trim();
                String pay = pay_pocha_menu3.getText().toString().trim();

                if (check_pocha_menu_hot3.isChecked() == true){
                    add_menu(main+"(매운맛)", pay);
                }else {
                    delete_menu(main+"(매운맛)", pay);
                }
            }
        });
        check_pocha_menu4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String main = pocha_menu4.getText().toString().trim();
                String pay = pay_pocha_menu4.getText().toString().trim();

                if (check_pocha_menu4.isChecked() == true){
                    add_menu(main, pay);
                }else {
                    delete_menu(main, pay);
                }
            }
        });
        check_pocha_menu5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String main = pocha_menu5.getText().toString().trim();
                String pay = pay_pocha_menu5.getText().toString().trim();

                if (check_pocha_menu5.isChecked() == true){
                    add_menu(main, pay);
                }else {
                    delete_menu(main, pay);
                }
            }
        });
        check_pocha_menu6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String main = pocha_menu6.getText().toString().trim();
                String pay = pay_pocha_menu6.getText().toString().trim();

                if (check_pocha_menu6.isChecked() == true){
                    add_menu(main, pay);
                }else {
                    delete_menu(main, pay);
                }
            }
        });
        check_pocha_menu7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String main = pocha_menu7.getText().toString().trim();
                String pay = pay_pocha_menu7.getText().toString().trim();

                if (check_pocha_menu7.isChecked() == true){
                    add_menu(main, pay);
                }else {
                    delete_menu(main, pay);
                }
            }
        });
        check_side1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String main = side_menu1.getText().toString().trim();
                String pay = pay_side1.getText().toString().trim();

                if (check_side1.isChecked() == true){
                    add_menu(main, pay);
                }else {
                    delete_menu(main, pay);
                }
            }
        });
        check_side2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String main = side_menu2.getText().toString().trim();
                String pay = pay_side2.getText().toString().trim();

                if (check_side2.isChecked() == true){
                    add_menu(main, pay);
                }else {
                    delete_menu(main, pay);
                }
            }
        });
        check_side3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String main = side_menu3.getText().toString().trim();
                String pay = pay_side3.getText().toString().trim();

                if (check_side3.isChecked() == true){
                    add_menu(main, pay);
                }else {
                    delete_menu(main, pay);
                }
            }
        });
        check_side4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String main = side_menu4.getText().toString().trim();
                String pay = pay_side4.getText().toString().trim();

                if (check_side4.isChecked() == true){
                    add_menu(main, pay);
                }else {
                    delete_menu(main, pay);
                }
            }
        });
        check_add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String main = add_menu1.getText().toString().trim();
                String pay = pay_add1.getText().toString().trim();

                if (check_add1.isChecked() == true){
                    add_menu(main, pay);
                }else {
                    delete_menu(main, pay);
                }
            }
        });
        check_add2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String main = add_menu2.getText().toString().trim();
                String pay = pay_add2.getText().toString().trim();

                if (check_add2.isChecked() == true){
                    add_menu(main, pay);
                }else {
                    delete_menu(main, pay);
                }
            }
        });
        check_add3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String main = add_menu3.getText().toString().trim();
                String pay = pay_add3.getText().toString().trim();

                if (check_add3.isChecked() == true){
                    add_menu(main, pay);
                }else {
                    delete_menu(main, pay);
                }
            }
        });
        check_add4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String main = add_menu4.getText().toString().trim();
                String pay = pay_add4.getText().toString().trim();

                if (check_add4.isChecked() == true){
                    add_menu(main, pay);
                }else {
                    delete_menu(main, pay);
                }
            }
        });
        check_add5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String main = add_menu5.getText().toString().trim();
                String pay = pay_add5.getText().toString().trim();

                if (check_add5.isChecked() == true){
                    add_menu(main, pay);
                }else {
                    delete_menu(main, pay);
                }
            }
        });
        check_add6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String main = add_menu6.getText().toString().trim();
                String pay = pay_add6.getText().toString().trim();

                if (check_add6.isChecked() == true){
                    add_menu(main, pay);
                }else {
                    delete_menu(main, pay);
                }
            }
        });
        check_add7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String main = add_menu7.getText().toString().trim();
                String pay = pay_add7.getText().toString().trim();

                if (check_add7.isChecked() == true){
                    add_menu(main, pay);
                }else {
                    delete_menu(main, pay);
                }
            }
        });
        check_add8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String main = add_menu8.getText().toString().trim();
                String pay = pay_add8.getText().toString().trim();

                if (check_add8.isChecked() == true){
                    add_menu(main, pay);
                }else {
                    delete_menu(main, pay);
                }
            }
        });
        check_add9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String main = add_menu9.getText().toString().trim();
                String pay = pay_add9.getText().toString().trim();

                if (check_add9.isChecked() == true){
                    add_menu(main, pay);
                }else {
                    delete_menu(main, pay);
                }
            }
        });
        check_add10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String main = add_menu10.getText().toString().trim();
                String pay = pay_add10.getText().toString().trim();

                if (check_add10.isChecked() == true){
                    add_menu(main, pay);
                }else {
                    delete_menu(main, pay);
                }
            }
        });
        check_add11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String main = add_menu11.getText().toString().trim();
                String pay = pay_add11.getText().toString().trim();

                if (check_add11.isChecked() == true){
                    add_menu(main, pay);
                }else {
                    delete_menu(main, pay);
                }
            }
        });
        check_add12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String main = add_menu12.getText().toString().trim();
                String pay = pay_add12.getText().toString().trim();

                if (check_add12.isChecked() == true){
                    add_menu(main, pay);
                }else {
                    delete_menu(main, pay);
                }
            }
        });
        check_add13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String main = add_menu13.getText().toString().trim();
                String pay = pay_add13.getText().toString().trim();

                if (check_add13.isChecked() == true){
                    add_menu(main, pay);
                }else {
                    delete_menu(main, pay);
                }
            }
        });
        check_drink1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String main = drink1.getText().toString().trim();
                String pay = pay_drink1.getText().toString().trim();

                if (check_drink1.isChecked() == true){
                    add_menu(main, pay);
                }else {
                    delete_menu(main, pay);
                }
            }
        });
        check_drink2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String main = drink2.getText().toString().trim();
                String pay = pay_drink2.getText().toString().trim();

                if (check_drink2.isChecked() == true){
                    add_menu(main, pay);
                }else {
                    delete_menu(main, pay);
                }
            }
        });
        check_drink3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String main = drink3.getText().toString().trim();
                String pay = pay_drink3.getText().toString().trim();

                if (check_drink3.isChecked() == true){
                    add_menu(main, pay);
                }else {
                    delete_menu(main, pay);
                }
            }
        });


        sp = getSharedPreferences(Utils.PREFERENCES_NAME,MODE_PRIVATE);

        btn_payment = findViewById(R.id.btn_payment);
        btn_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(Reservation.this);
                View alertView = getLayoutInflater().inflate(R.layout.order,null);
                price = alertView.findViewById(R.id.price);
                people_spinner = alertView.findViewById(R.id.people_spinner);
                spinner_month = alertView.findViewById(R.id.spinner_month);
                spinner_day = alertView.findViewById(R.id.spinner_day);
                spinner_hour = alertView.findViewById(R.id.spinner_hour);
                radio_group = alertView.findViewById(R.id.radio_group);
                take_out = alertView.findViewById(R.id.take_out);
                store = alertView.findViewById(R.id.store);
                order_no = alertView.findViewById(R.id.order_no);
                order_payment = alertView.findViewById(R.id.order_payment);
                recyclerView = alertView.findViewById(R.id.recyclerView);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(Reservation.this));

                ArrayAdapter people_adapter = ArrayAdapter.createFromResource(Reservation.mContext, R.array.people_number, android.R.layout.simple_spinner_dropdown_item);
                people_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                people_spinner.setAdapter(people_adapter);

                ArrayAdapter month_adapter = ArrayAdapter.createFromResource(Reservation.mContext, R.array.month, android.R.layout.simple_spinner_dropdown_item);
                month_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_month.setAdapter(month_adapter);

                ArrayAdapter day_adapter = ArrayAdapter.createFromResource(Reservation.mContext, R.array.day, android.R.layout.simple_spinner_dropdown_item);
                day_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_day.setAdapter(day_adapter);

                ArrayAdapter hour_adapter = ArrayAdapter.createFromResource(Reservation.mContext, R.array.hour, android.R.layout.simple_spinner_dropdown_item);
                hour_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_hour.setAdapter(hour_adapter);


                people_spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                        people = item.toString().trim().replace("명","");
                        if (people.isEmpty()){
                            Toast.makeText(Reservation.mContext, "인원 수를 선택해주세요.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        pp = Integer.parseInt(people);
                    }
                });

                spinner_month.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                        month = item.toString().trim().replace("월","");
                        mm = Integer.parseInt(month);
                    }
                });

                spinner_day.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                        day = item.toString().trim().replace("일","");
                        dd = Integer.parseInt(day);
                    }
                });

                spinner_hour.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                        hour = item.toString().trim().replace("시","");
                        hh = Integer.parseInt(hour);
                    }
                });

                String nick_name = sp.getString("nick_name", null);

                Retrofit retrofit = NetworkClient.getRetrofitClient(Reservation.this);
                ReservationApi reservationApi = retrofit.create(ReservationApi.class);

                Call<ReservationRes> call = reservationApi.selectMenu(nick_name);

                call.enqueue(new Callback<ReservationRes>() {
                    @Override
                    public void onResponse(Call<ReservationRes> call, Response<ReservationRes> response) {
                        // 상태코드가 200 인지 확인
                        if (response.isSuccessful()) {
                            orderArrayList = response.body().getRows();
                            if (orderArrayList.isEmpty()){
                                Toast.makeText(Reservation.this, "메뉴를 선택해주세요", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            adapter = new OrderSheetAdapter(Reservation.this, orderArrayList);
                            recyclerView.setAdapter(adapter);
                            Log.i("menu", orderArrayList.toString());

                            price_total(price);

                            radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(RadioGroup group, int checkedId) {
                                    if (month.isEmpty() || day.isEmpty() || hour.isEmpty()){
                                        Toast.makeText(Reservation.mContext, "예약시간을 선택해주세요.", Toast.LENGTH_SHORT).show();
                                    }

                                    add_store(0, pp, "2020-"+mm+"-"+dd+" "+hh);
                                    if (checkedId == R.id.take_out){
                                        add_take_out(1, "2020-"+mm+"-"+dd+" "+hh);
                                    }else if (checkedId == R.id.store) {
                                        add_store(0, pp, "2020-"+mm+"-"+dd+" "+hh);
                                    }
                                }
                            });

                        }else {
                            Log.i("menu", "success = fail");
                        }
                    }

                    @Override
                    public void onFailure(Call<ReservationRes> call, Throwable t) {
                        Log.i("menu", "fail");
                    }
                });

                order_payment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Reservation.this, CheckoutActivity.class);
                        i.putExtra("total_price", total_price);
                        startActivity(i);
                    }
                });

                order_no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        cancle();
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


    // 메뉴 추가
    public void add_menu(String menu, String price){
        String nick_name =sp.getString("nick_name",null);

        ReservationReq reservationReq = new ReservationReq(menu, price, nick_name);

        Retrofit retrofit = NetworkClient.getRetrofitClient(Reservation.this);
        ReservationApi reservationApi = retrofit.create(ReservationApi.class);

        Call<ReservationRes> call = reservationApi.addMenu(reservationReq);

        call.enqueue(new Callback<ReservationRes>() {
            @Override
            public void onResponse(Call<ReservationRes> call, Response<ReservationRes> response) {
                // 상태코드가 200 인지 확인
                if (response.isSuccessful()){

                }else {
                    return;
                }
            }

            @Override
            public void onFailure(Call<ReservationRes> call, Throwable t) {

            }
        });
    }

    // 메뉴 삭제
    public void delete_menu(String menu, String price){
        String nick_name =sp.getString("nick_name",null);

        Retrofit retrofit = NetworkClient.getRetrofitClient(Reservation.this);
        ReservationApi reservationApi = retrofit.create(ReservationApi.class);

        Call<ReservationRes> call = reservationApi.deleteMenu(nick_name, menu, price);
        call.enqueue(new Callback<ReservationRes>() {
            @Override
            public void onResponse(Call<ReservationRes> call, Response<ReservationRes> response) {
                // 상태코드가 200 인지 확인
                if (response.isSuccessful()){

                }else {
                    return;
                }
            }

            @Override
            public void onFailure(Call<ReservationRes> call, Throwable t) {

            }
        });
    }

    // 메뉴 총합
    public void price_total(TextView textView){
        String nick_name =sp.getString("nick_name",null);

        Retrofit retrofit = NetworkClient.getRetrofitClient(Reservation.this);
        ReservationApi reservationApi = retrofit.create(ReservationApi.class);

        Call<ReservationRes> call = reservationApi.total(nick_name);
        call.enqueue(new Callback<ReservationRes>() {
            @Override
            public void onResponse(Call<ReservationRes> call, Response<ReservationRes> response) {
                // 상태코드가 200 인지 확인
                if (response.isSuccessful()){
                    String rows = response.body().getTotal();
                    total_price = Double.parseDouble(rows);
                    DecimalFormat format = new DecimalFormat("###,###");//콤마
                    String total = format.format(total_price);
                    Log.i("total", total);
                    textView.setText(total);
                }else {
                    return;
                }
            }

            @Override
            public void onFailure(Call<ReservationRes> call, Throwable t) {
                Log.i("total", t.toString());
            }
        });
    }

    // 추가 사항(store)
    public void add_store(int take_out, int people_number, String time){
        String nick_name =sp.getString("nick_name",null);

        Retrofit retrofit = NetworkClient.getRetrofitClient(Reservation.this);
        ReservationApi reservationApi = retrofit.create(ReservationApi.class);

        Call<ReservationRes> call = reservationApi.add(nick_name, take_out, people_number, time);
        call.enqueue(new Callback<ReservationRes>() {
            @Override
            public void onResponse(Call<ReservationRes> call, Response<ReservationRes> response) {
                // 상태코드가 200 인지 확인
                if (response.isSuccessful()){

                }else {
                    return;
                }
            }

            @Override
            public void onFailure(Call<ReservationRes> call, Throwable t) {
                Log.i("total", t.toString());
            }
        });
    }

    // 추가 사항(take_out)
    public void add_take_out(int take_out, String time){
        String nick_name =sp.getString("nick_name",null);

        Retrofit retrofit = NetworkClient.getRetrofitClient(Reservation.this);
        ReservationApi reservationApi = retrofit.create(ReservationApi.class);

        Call<ReservationRes> call = reservationApi.add(nick_name, take_out, 0, time);
        call.enqueue(new Callback<ReservationRes>() {
            @Override
            public void onResponse(Call<ReservationRes> call, Response<ReservationRes> response) {
                // 상태코드가 200 인지 확인
                if (response.isSuccessful()){

                }else {
                    return;
                }
            }

            @Override
            public void onFailure(Call<ReservationRes> call, Throwable t) {
                Log.i("total", t.toString());
            }
        });
    }

    // nick_name = nick_name 인 사람의 주문서 전부 삭제
    public void cancle(){
        String nick_name =sp.getString("nick_name",null);

        Retrofit retrofit = NetworkClient.getRetrofitClient(Reservation.this);
        ReservationApi reservationApi = retrofit.create(ReservationApi.class);

        Call<ReservationRes> call = reservationApi.cancle(nick_name);
        call.enqueue(new Callback<ReservationRes>() {
            @Override
            public void onResponse(Call<ReservationRes> call, Response<ReservationRes> response) {
                // 상태코드가 200 인지 확인
                if (response.isSuccessful()){

                }else {
                    return;
                }
            }

            @Override
            public void onFailure(Call<ReservationRes> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        cancle();
        super.onBackPressed();
    }

    @Override
    protected void onRestart() {
        cancle();
        super.onRestart();
    }
}
