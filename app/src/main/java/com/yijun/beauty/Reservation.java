package com.yijun.beauty;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
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
import com.yijun.beauty.network.CheckNetwork;
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


    ImageView img_chicken1;
    ImageView img_chicken2;
    ImageView img_chicken3;
    ImageView img_chicken4;
    ImageView img_chicken5;
    ImageView img_chicken6;

    ImageView img_pocha1;
    ImageView img_pocha2;
    ImageView img_pocha3;
    ImageView img_pocha4;
    ImageView img_pocha5;
    ImageView img_pocha6;


    // 주문서 다이얼로그
    AlertDialog alertDialog;
    RecyclerView recyclerView;
    OrderSheetAdapter adapter;
    ArrayList<Orders> orderArrayList = new ArrayList<>();
    TextView price;

    RadioGroup radio_group;
    RadioButton take_out;
    RadioButton store;
    Button order_no;
    Button order_payment;

    SharedPreferences sp;

    // take_out 다이얼로그
    AlertDialog dialog_take_out;
    MaterialSpinner spinner_month;
    MaterialSpinner spinner_day;
    MaterialSpinner spinner_hour;
    String month;
    String day;
    String hour;
    int mm;
    int dd;
    int hh;

    // store 다이얼로그
    AlertDialog dialog_store;
    MaterialSpinner people_spinner;
    MaterialSpinner month_spinner;
    MaterialSpinner day_spinner;
    MaterialSpinner hour_spinner;
    String people_s;
    String month_s;
    String day_s;
    String hour_s;
    int pp;
    int mm_s;
    int dd_s;
    int hh_s;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        mContext = this;
        sp = getSharedPreferences(Utils.PREFERENCES_NAME,MODE_PRIVATE);
        cancle();

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


        img_chicken1 = findViewById(R.id.img_chicken1);
        img_chicken2 = findViewById(R.id.img_chicken2);
        img_chicken3 = findViewById(R.id.img_chicken3);
        img_chicken4 = findViewById(R.id.img_chicken4);
        img_chicken5 = findViewById(R.id.img_chicken5);
        img_chicken6 = findViewById(R.id.img_chicken6);

        img_pocha1 = findViewById(R.id.img_pocha1);
        img_pocha2 = findViewById(R.id.img_pocha2);
        img_pocha3 = findViewById(R.id.img_pocha3);
        img_pocha4 = findViewById(R.id.img_pocha5);
        img_pocha5 = findViewById(R.id.img_pocha6);



        img_chicken1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createMenuDialog();
            }
        });

        img_chicken2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createMenuDialog2();
            }
        });

        img_chicken3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createMenuDialog2();
            }
        });

        img_chicken4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createMenuDialog2();
            }
        });

        img_chicken5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createMenuDialog3();
            }
        });

        img_chicken6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createMenuDialog3();
            }
        });

        img_pocha1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createMenuDialog4();
            }
        });

        img_pocha2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createMenuDialog5();
            }
        });

        img_pocha3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createMenuDialog6();
            }
        });

        img_pocha4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createMenuDialog7();
            }
        });

        img_pocha5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createMenuDialog8();
            }
        });




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

        btn_payment = findViewById(R.id.btn_payment);
        btn_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!CheckNetwork.isNetworkAvailable(Reservation.this)){
                    Toast.makeText(Reservation.this, "네트워크 연결을 확인해 주세요", Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }

                List<Integer> check = new ArrayList();
                if(check_main_menu1.isChecked()==true){check.add(1000);}
                if(check_main_menu2.isChecked()==true){check.add(1001);}
                if(check_main_menu3.isChecked()==true){check.add(1002);}
                if(check_set_menu1.isChecked()==true){check.add(1003);}
                if(check_set_menu2.isChecked()==true){check.add(1004);}
                if(check_chicken_feet_big1.isChecked()==true){check.add(1005);}
                if(check_chicken_feet_middle1.isChecked()==true){check.add(1006);}
                if(check_chicken_feet_small1.isChecked()==true){check.add(1007);}
                if(check_chicken_feet_big2.isChecked()==true){check.add(1008);}
                if(check_chicken_feet_middle2.isChecked()==true){check.add(1009);}
                if(check_chicken_feet_small2.isChecked()==true){check.add(1010);}
                if(check_chicken_feet_big3.isChecked()==true){check.add(1011);}
                if(check_chicken_feet_middle3.isChecked()==true){check.add(1012);}
                if(check_chicken_feet_small3.isChecked()==true){check.add(1013);}
                if(check_chicken_feet_big4.isChecked()==true){check.add(1014);}
                if(check_chicken_feet_middle4.isChecked()==true){check.add(1015);}
                if(check_chicken_feet_small4.isChecked()==true){check.add(1016);}
                if(check_chicken_feet_big5.isChecked()==true){check.add(1017);}
                if(check_chicken_feet_middle5.isChecked()==true){check.add(1018);}
                if(check_chicken_feet_small5.isChecked()==true){check.add(1019);}
                if(check_chicken_feet_big6.isChecked()==true){check.add(1020);}
                if(check_chicken_feet_middle6.isChecked()==true){check.add(1021);}
                if(check_chicken_feet_small6.isChecked()==true){check.add(1022);}
                if(check_pocha_menu1.isChecked()==true){check.add(1023);}
                if(check_pocha_menu2.isChecked()==true){check.add(1024);}
                if(check_pocha_menu_basick3.isChecked()==true){check.add(1025);}
                if(check_pocha_menu_hot3.isChecked()==true){check.add(1026);}
                if(check_pocha_menu4.isChecked()==true){check.add(1027);}
                if(check_pocha_menu5.isChecked()==true){check.add(1028);}
                if(check_pocha_menu6.isChecked()==true){check.add(1029);}
                if(check_pocha_menu7.isChecked()==true){check.add(1030);}
                if(check_side1.isChecked()==true){check.add(1);}
                if(check_side2.isChecked()==true){check.add(2);}
                if(check_side3.isChecked()==true){check.add(3);}
                if(check_side4.isChecked()==true){check.add(4);}
                if(check_add1.isChecked()==true){check.add(5);}
                if(check_add2.isChecked()==true){check.add(6);}
                if(check_add3.isChecked()==true){check.add(7);}
                if(check_add4.isChecked()==true){check.add(8);}
                if(check_add5.isChecked()==true){check.add(9);}
                if(check_add6.isChecked()==true){check.add(10);}
                if(check_add7.isChecked()==true){check.add(11);}
                if(check_add8.isChecked()==true){check.add(12);}
                if(check_add9.isChecked()==true){check.add(13);}
                if(check_add10.isChecked()==true){check.add(14);}
                if(check_add11.isChecked()==true){check.add(15);}
                if(check_add12.isChecked()==true){check.add(16);}
                if(check_add13.isChecked()==true){check.add(17);}
                if(check_drink1.isChecked()==true){check.add(18);}
                if(check_drink2.isChecked()==true){check.add(19);}
                if(check_drink3.isChecked()==true){check.add(20);}


                if (check.isEmpty()){
                    Toast.makeText(Reservation.this, "메뉴를 선택해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                int side = 0;
                for (int i = 0; i<check.size(); i++){
                    side = side + check.get(i);
                    if (side<1000){// 사이드를 추가하면 무조건 1000이 넘어감
                        Toast.makeText(Reservation.this, "사이드 메뉴만으로는 주문이 불가능 합니다", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }




                // 주문서 다이얼로그
                AlertDialog.Builder alert = new AlertDialog.Builder(Reservation.this);
                View alertView = getLayoutInflater().inflate(R.layout.order,null);
                price = alertView.findViewById(R.id.price);
                radio_group = alertView.findViewById(R.id.radio_group);
                take_out = alertView.findViewById(R.id.take_out);
                store = alertView.findViewById(R.id.store);
                order_no = alertView.findViewById(R.id.order_no);
                order_payment = alertView.findViewById(R.id.order_payment);
                recyclerView = alertView.findViewById(R.id.recyclerView);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(Reservation.this));

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
                                if (orderArrayList.isEmpty()) {
                                    Toast.makeText(Reservation.this, "메뉴를 선택해주세요", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                adapter = new OrderSheetAdapter(Reservation.this, orderArrayList);
                                recyclerView.setAdapter(adapter);

                                    Log.i("menu", orderArrayList.toString());
                                    String menuuuu = response.body().getMenu();

                                    price_total(price);

                                radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                                        if (checkedId == R.id.take_out) {
                                            add_take_out(1, "2020-" + mm + "-" + dd + " " + hh);
                                        } else if (checkedId == R.id.store) {
                                            add_store(0, pp, "2020-" + mm + "-" + dd + " " + hh);
                                        }else if (group.isClickable() == false){
                                            Toast.makeText(Reservation.this, "매장과 포장중 선택 하셔야합니다.", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                    }
                                });

                            } else {

                            }
                        }

                        @Override
                        public void onFailure(Call<ReservationRes> call, Throwable t) {

                        }
                    });

                    order_payment.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(Reservation.this, CheckoutActivity.class);
                            i.putExtra("total_price", total_price);
                            finish();
                            alertDialog.dismiss();
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
        AlertDialog.Builder alert = new AlertDialog.Builder(Reservation.this);
        View alertView = getLayoutInflater().inflate(R.layout.store,null);
        people_spinner = alertView.findViewById(R.id.people_spinner);
        spinner_month = alertView.findViewById(R.id.spinner_month);
        spinner_day = alertView.findViewById(R.id.spinner_day);
        spinner_hour = alertView.findViewById(R.id.spinner_hour);

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

        alert.setView(alertView);

        dialog_store = alert.create();
        dialog_store.setCancelable(false);
        dialog_store.show();
    }

    // 추가 사항(take_out)
    public void add_take_out(int take_out, String time){
        AlertDialog.Builder alert = new AlertDialog.Builder(Reservation.this);
        View alertView = getLayoutInflater().inflate(R.layout.take_out,null);
        month_spinner = alertView.findViewById(R.id.month_spinner);
        day_spinner = alertView.findViewById(R.id.day_spinner);
        hour_spinner = alertView.findViewById(R.id.hour_spinner);

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

        alert.setView(alertView);

        dialog_take_out = alert.create();
        dialog_take_out.setCancelable(false);
        dialog_take_out.show();
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

    public void createMenuDialog(){
        AlertDialog.Builder alert = new AlertDialog.Builder(Reservation.this);
        View enterview = getLayoutInflater().inflate(R.layout.list_menu3,null);

        alert.setView(enterview);

        alertDialog = alert.create();
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void createMenuDialog2(){
        AlertDialog.Builder alert = new AlertDialog.Builder(Reservation.this);
        View enterview = getLayoutInflater().inflate(R.layout.list_menu1,null);

        alert.setView(enterview);

        alertDialog = alert.create();
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void createMenuDialog3(){
        AlertDialog.Builder alert = new AlertDialog.Builder(Reservation.this);
        View enterview = getLayoutInflater().inflate(R.layout.menu1,null);

        alert.setView(enterview);

        alertDialog = alert.create();
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void createMenuDialog4(){
        AlertDialog.Builder alert = new AlertDialog.Builder(Reservation.this);
        View enterview = getLayoutInflater().inflate(R.layout.menu4,null);

        alert.setView(enterview);

        alertDialog = alert.create();
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void createMenuDialog5(){
        AlertDialog.Builder alert = new AlertDialog.Builder(Reservation.this);
        View enterview = getLayoutInflater().inflate(R.layout.menu9,null);

        alert.setView(enterview);

        alertDialog = alert.create();
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void createMenuDialog6(){
        AlertDialog.Builder alert = new AlertDialog.Builder(Reservation.this);
        View enterview = getLayoutInflater().inflate(R.layout.list_menu2,null);

        alert.setView(enterview);

        alertDialog = alert.create();
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void createMenuDialog7(){
        AlertDialog.Builder alert = new AlertDialog.Builder(Reservation.this);
        View enterview = getLayoutInflater().inflate(R.layout.menu6,null);

        alert.setView(enterview);

        alertDialog = alert.create();
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void createMenuDialog8(){
        AlertDialog.Builder alert = new AlertDialog.Builder(Reservation.this);
        View enterview = getLayoutInflater().inflate(R.layout.menu10,null);

        alert.setView(enterview);

        alertDialog = alert.create();
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

}
