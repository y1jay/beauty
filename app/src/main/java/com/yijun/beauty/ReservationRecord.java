package com.yijun.beauty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yijun.beauty.R;
import com.yijun.beauty.activity.CheckoutActivity;
import com.yijun.beauty.adapter.CheckOrderAdapter;
import com.yijun.beauty.adapter.OrderSheetAdapter;
import com.yijun.beauty.api.NetworkClient;
import com.yijun.beauty.api.ReservationApi;
import com.yijun.beauty.model.Orders;
import com.yijun.beauty.model.ReservationRes;
import com.yijun.beauty.url.Utils;

import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ReservationRecord extends AppCompatActivity {

    TextView total_record;
    RecyclerView recyclerView;
    CheckOrderAdapter adapter;
    ArrayList<Orders> orderArrayList = new ArrayList<>();
    SharedPreferences sp;

//    EditText test;
//    EditText test_m;
//    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_record);


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ReservationRecord.this));

        // 주문정보 표시 api
        sp = getSharedPreferences(Utils.PREFERENCES_NAME,MODE_PRIVATE);
        String nick_name = sp.getString("nick_name", null);

        Retrofit retrofit = NetworkClient.getRetrofitClient(ReservationRecord.this);
        ReservationApi reservationApi = retrofit.create(ReservationApi.class);

        Call<ReservationRes> call = reservationApi.myselectMenu(nick_name);

        call.enqueue(new Callback<ReservationRes>() {
            @Override
            public void onResponse(Call<ReservationRes> call, Response<ReservationRes> response) {
                // 상태코드가 200 인지 확인
                if (response.isSuccessful()) {
                    orderArrayList = response.body().getRows();
                    if (orderArrayList.isEmpty()){
                        return;
                    }

//                    price_total(total_record);

                    adapter = new CheckOrderAdapter(ReservationRecord.this, orderArrayList);
                    recyclerView.setAdapter(adapter);
                    Log.i("AAA", orderArrayList.toString());

                }else {
                    Log.i("menu", "success = fail");
                }
            }

            @Override
            public void onFailure(Call<ReservationRes> call, Throwable t) {
                Log.i("menu", "fail");
            }
        });

    }

    // 메뉴 총합
//    public void price_total(TextView textView){
//        String nick_name =sp.getString("nick_name",null);
//
//        Retrofit retrofit = NetworkClient.getRetrofitClient(ReservationRecord.this);
//        ReservationApi reservationApi = retrofit.create(ReservationApi.class);
//
//        Call<ReservationRes> call = reservationApi.total(nick_name);
//        call.enqueue(new Callback<ReservationRes>() {
//            @Override
//            public void onResponse(Call<ReservationRes> call, Response<ReservationRes> response) {
//                // 상태코드가 200 인지 확인
//                if (response.isSuccessful()){
//                    String rows = response.body().getTotal();
//                    Double total_price = Double.parseDouble(rows);
//                    DecimalFormat format = new DecimalFormat("###,###");//콤마
//                    String total = format.format(total_price);
//                    Log.i("total", total);
//                    textView.setText(total);
//                }else {
//                    return;
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ReservationRes> call, Throwable t) {
//                Log.i("total", t.toString());
//            }
//        });
//    }
}
