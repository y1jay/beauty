package com.yijun.beauty;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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


    int offset = 0;
    int cnt = 0;

    SharedPreferences sp;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myreview);


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(Myreview.this));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int lastPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                int totalCount = recyclerView.getAdapter().getItemCount();

                lastPosition = lastPosition +1;
                if(lastPosition == totalCount){

                    if(offset >= 25){
//                        offset = cnt + offset;
                        addNetworkData();
                    }else if(offset < 25){
                        Toast.makeText(Myreview.this, "모든 리뷰를 표시했습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
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
                cnt = response.body().getCnt();
                offset = cnt + offset;

                adapter = new MyReviewclerViewAdapter(Myreview.this, reviewArrayList);
                recyclerView.setAdapter(adapter);

            }
            @Override
            public void onFailure(Call<ReviewRes> call, Throwable t) {

            }
        });

    }
    private void addNetworkData() {
        Retrofit retrofit = NetworkClient.getRetrofitClient(Myreview.this);
        String nick_name =sp.getString("nick_name",null);
        ReviewApi reviewApi = retrofit.create(ReviewApi.class);

        Call<ReviewRes> call = reviewApi.myReview(nick_name,offset,25);
        call.enqueue(new Callback<ReviewRes>() {
            @Override
            public void onResponse(Call<ReviewRes> call, Response<ReviewRes> response) {

                Log.i("AAAA",response.body().getSuccess().toString());

                Log.i("AAAA",response.body().getCnt().toString());

                ArrayList<Rows> arraylist = new ArrayList<>();
                arraylist = response.body().getRows();
                cnt = response.body().getCnt();
                offset = cnt + offset;
                if (cnt < 25){
                    offset = cnt;
                }

                reviewArrayList.addAll(arraylist);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ReviewRes> call, Throwable t) {

            }
        });

    }
    public void deleteReviw(final int position){


        Rows rows = reviewArrayList.get(position);
        String nick_name = rows.getNick_name();
        String review = rows.getReview();
        Float rating = rows.getRating();


        Retrofit retrofit = NetworkClient.getRetrofitClient(Myreview.this);
        Log.i("delete", nick_name+" "+review+" "+rating);
        ReviewApi reviewApi = retrofit.create(ReviewApi.class);

        Call<UserRes> call = reviewApi.deleteMyReview(nick_name,review,rating);
        call.enqueue(new Callback<UserRes>() {
            @Override
            public void onResponse(Call<UserRes> call, Response<UserRes> response) {
                if (response.isSuccessful()){
                    Log.i("delete"," success : "+ nick_name+" "+review+" "+rating);

                    Toast.makeText(Myreview.this,"리뷰가 삭제되었습니다.",Toast.LENGTH_SHORT).show();

                    adapter.notifyDataSetChanged();
                    reviewArrayList.clear();
                    getNetworkData();
                }
            }

            @Override
            public void onFailure(Call<UserRes> call, Throwable t) {

            }
        });

    }

}