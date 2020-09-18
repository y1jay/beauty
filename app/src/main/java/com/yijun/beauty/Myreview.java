package com.yijun.beauty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.yijun.beauty.adapter.MyReviewclerViewAdapter;
import com.yijun.beauty.adapter.ReviewclerViewAdapter;
import com.yijun.beauty.api.NetworkClient;
import com.yijun.beauty.api.ReviewApi;
import com.yijun.beauty.model.ReviewRes;
import com.yijun.beauty.model.Rows;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Myreview extends AppCompatActivity {

    RecyclerView recyclerView;
    MyReviewclerViewAdapter adapter;
    ArrayList<Rows> reviewArrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myreview);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(Myreview.this));

        getNetworkData();

    }

    private void getNetworkData() {

        Retrofit retrofit = NetworkClient.getRetrofitClient(Myreview.this);

        ReviewApi reviewApi = retrofit.create(ReviewApi.class);

        Call<ReviewRes> call = reviewApi.myReview("kim",0,25);
        call.enqueue(new Callback<ReviewRes>() {
            @Override
            public void onResponse(Call<ReviewRes> call, Response<ReviewRes> response) {

                Log.i("AAAA",response.body().getSuccess().toString());

                Log.i("AAAA",response.body().getCnt().toString());

                reviewArrayList = response.body().getRows();


                adapter = new MyReviewclerViewAdapter(Myreview.this, reviewArrayList);
                recyclerView.setAdapter(adapter);

            }
            @Override
            public void onFailure(Call<ReviewRes> call, Throwable t) {

            }
        });

    }

}