package com.yijun.beauty;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yijun.beauty.adapter.MyReviewclerViewAdapter;
import com.yijun.beauty.api.NetworkClient;
import com.yijun.beauty.api.ReviewApi;
import com.yijun.beauty.model.MyreviewReq;
import com.yijun.beauty.model.ReservationReq;
import com.yijun.beauty.model.Review;
import com.yijun.beauty.model.ReviewRes;
import com.yijun.beauty.model.Rows;
import com.yijun.beauty.model.UserRes;
import com.yijun.beauty.url.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Myreview extends AppCompatActivity {

    RecyclerView recyclerView;
    MyReviewclerViewAdapter adapter;
   ArrayList<Rows> reviewArrayList = new ArrayList<>();




    SharedPreferences sp;

    Context mContext;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myreview);

        mContext = this;

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(Myreview.this));


        sp = getSharedPreferences(Utils.PREFERENCES_NAME,MODE_PRIVATE);

        getNetworkData();

    }

    private void getNetworkData() {


        String nick_name =sp.getString("nick_name",null);

        Retrofit retrofit = NetworkClient.getRetrofitClient(Myreview.this);

        ReviewApi reviewApi = retrofit.create(ReviewApi.class);

        Call<ReviewRes> call = reviewApi.myReview(nick_name,0,25);
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

    public void deleteReviw(final int position){

        Rows rows = reviewArrayList.get(position);
        int id = rows.getId();
        String nick_name =sp.getString("nick_name",null);
//        String nick_name = rows.getNick_name();
        String txt_review = rows.getReview();
        Float rating = rows.getRating();

        Log.i("delete", nick_name+ txt_review+rating);
//        Review review = new Review(nick_name,txt_review,rating);

        Retrofit retrofit = NetworkClient.getRetrofitClient(mContext);

        ReviewApi reviewApi = retrofit.create(ReviewApi.class);

        Call<UserRes> call = reviewApi.deleteMyReview(nick_name, txt_review, rating);
        call.enqueue(new Callback<UserRes>() {
            @Override
            public void onResponse(Call<UserRes> call, Response<UserRes> response) {
              if (response.isSuccessful() == true){
//                  adapter.notifyDataSetChanged();
              }
            }

            @Override
            public void onFailure(Call<UserRes> call, Throwable t) {

            }

        });

    }

}