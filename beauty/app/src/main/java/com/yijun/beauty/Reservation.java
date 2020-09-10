package com.yijun.beauty;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.yijun.beauty.activity.CheckoutActivity;
import com.yijun.beauty.api.NetworkClient;
import com.yijun.beauty.api.ReservationApi;
import com.yijun.beauty.api.UserApi;
import com.yijun.beauty.model.ReservationReq;
import com.yijun.beauty.model.ReservationRes;
import com.yijun.beauty.model.UserReq;
import com.yijun.beauty.model.UserRes;
import com.yijun.beauty.url.Utils;

import org.w3c.dom.Text;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Reservation extends AppCompatActivity {

    SharedPreferences sp;
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

        sp = getSharedPreferences(Utils.PREFERENCES_NAME,MODE_PRIVATE);

        check_main_menu1 = findViewById(R.id.check_main_menu1);
        main_menu1 = findViewById(R.id.main_menu1);
        pay_main1 = findViewById(R.id.pay_main1);

        check_main_menu2 = findViewById(R.id.check_main_menu2);
        main_menu2 = findViewById(R.id.main_menu2);
        pay_main2 = findViewById(R.id.pay_main2);

        check_main_menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check_main_menu1.isChecked() == true){
                    String main = main_menu1.getText().toString().trim();
                    String pay = pay_main1.getText().toString().trim();

                    add_menu(main, pay);
                }
            }
        });

        check_main_menu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check_main_menu2.isChecked() == true){
                    String main = main_menu2.getText().toString().trim();
                    String pay = pay_main2.getText().toString().trim();

                    add_menu(main, pay);
                }
            }
        });

        btn_payment = findViewById(R.id.btn_payment);
        btn_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(Reservation.this);
                View alertView = getLayoutInflater().inflate(R.layout.order,null);
                menu = alertView.findViewById(R.id.menu);
                price = alertView.findViewById(R.id.price);
                total = alertView.findViewById(R.id.total);
                order_no = alertView.findViewById(R.id.order_no);
                order_payment = alertView.findViewById(R.id.order_payment);

                String nick_name =sp.getString("nick_name",null);

                Retrofit retrofit = NetworkClient.getRetrofitClient(Reservation.this);
                ReservationApi reservationApi = retrofit.create(ReservationApi.class);

                Call<ReservationRes> call = reservationApi.selectMenu(nick_name);

                call.enqueue(new Callback<ReservationRes>() {
                    @Override
                    public void onResponse(Call<ReservationRes> call, Response<ReservationRes> response) {
                        // 상태코드가 200 인지 확인
                        if (response.isSuccessful()){
                            String me = response.body().getMenu();
                            menu.setText(me);
                            String pr = response.body().getPrice();
                            price.setText(pr);

                        }
                    }

                    @Override
                    public void onFailure(Call<ReservationRes> call, Throwable t) {

                    }
                });

                order_payment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//
//                        Intent i = new Intent(Reservation.this, CheckoutActivity.class);
//                        i.putExtra("main", menu);
//                        i.putExtra("pay",pay);
//                        startActivity(i);
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
//                    Intent i = new Intent(Reservation.this, CheckoutActivity.class);
//                    i.putExtra("menu", menu);
//                    i.putExtra("price", price);
//                    startActivity(i);
                }
            }

            @Override
            public void onFailure(Call<ReservationRes> call, Throwable t) {

            }
        });
    }

    public void select_menu(){
        String nick_name =sp.getString("nick_name",null);

        Retrofit retrofit = NetworkClient.getRetrofitClient(Reservation.this);
        ReservationApi reservationApi = retrofit.create(ReservationApi.class);

        Call<ReservationRes> call = reservationApi.selectMenu(nick_name);

        call.enqueue(new Callback<ReservationRes>() {
            @Override
            public void onResponse(Call<ReservationRes> call, Response<ReservationRes> response) {
                // 상태코드가 200 인지 확인
                if (response.isSuccessful()){
                    String menu = response.body().getMenu();
                    for (int i = 0 ; i < response.body().getMenu().length(); i++){

                    }
                    String price = response.body().getPrice();
                    for (int p = 0; p < response.body().getPrice().length(); p++){

                    }
                }
            }

            @Override
            public void onFailure(Call<ReservationRes> call, Throwable t) {

            }
        });
    }

}
