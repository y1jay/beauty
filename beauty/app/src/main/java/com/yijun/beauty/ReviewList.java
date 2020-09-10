package com.yijun.beauty;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.yijun.beauty.adapter.RecyclerViewAdapter;
import com.yijun.beauty.adapter.ReviewclerViewAdapter;
import com.yijun.beauty.api.NetworkClient;
import com.yijun.beauty.api.ReviewApi;
import com.yijun.beauty.api.UserApi;
import com.yijun.beauty.model.Review;
import com.yijun.beauty.model.ReviewRes;
import com.yijun.beauty.model.Rows;
import com.yijun.beauty.model.UserReq;
import com.yijun.beauty.model.UserRes;
import com.yijun.beauty.url.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Body;

public class ReviewList extends AppCompatActivity {
    RecyclerView reviewcyclerView;
    ReviewclerViewAdapter adapter;
    Button set_review;
    SharedPreferences sp;
    private AlertDialog dialog;
    List<Rows> reviewArrayList = new ArrayList<>();

    TextView txt_nick_name;
    RatingBar ratingbar;
    EditText edit_review;
    Button btn_cancel;
    Button btn_set;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        set_review= findViewById(R.id.set_review);
        reviewcyclerView = findViewById(R.id.reviewcyclerView);
        reviewcyclerView.setHasFixedSize(true);
        reviewcyclerView.setLayoutManager(new LinearLayoutManager(ReviewList.this));
        sp = getSharedPreferences(Utils.PREFERENCES_NAME,MODE_PRIVATE);

        getNetworkData();
        set_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPopupDialog();
            }
        });


    }
    private void getNetworkData() {
        Retrofit retrofit = NetworkClient.getRetrofitClient(ReviewList.this);

        ReviewApi reviewApi = retrofit.create(ReviewApi.class);

        Call<ReviewRes> call = reviewApi.selectReview(0,25);
        call.enqueue(new Callback<ReviewRes>() {
            @Override
            public void onResponse(Call<ReviewRes> call, Response<ReviewRes> response) {

                Log.i("AAAA",response.body().getSuccess().toString());

                Log.i("AAAA",response.body().getCnt().toString());

                reviewArrayList = response.body().getRows();

                adapter = new ReviewclerViewAdapter(ReviewList.this, reviewArrayList);
                reviewcyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ReviewRes> call, Throwable t) {

            }
        });

    }
    public void createPopupDialog(){
        AlertDialog.Builder alert = new AlertDialog.Builder(ReviewList.this);
        View alertView = getLayoutInflater().inflate(R.layout.review,null);
        txt_nick_name = alertView.findViewById(R.id.txt_nick_name);
        edit_review = alertView.findViewById(R.id.edit_review);
        btn_set = alertView.findViewById(R.id.btn_set);
        ratingbar = alertView.findViewById(R.id.ratingBar);
        btn_cancel = alertView.findViewById(R.id.btn_cancel);

        String nick_name =sp.getString("nick_name",null);
        txt_nick_name.setText(nick_name);

        btn_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String review = edit_review.getText().toString().trim();

                ratingbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating,
                                                boolean fromUser) {
                    }
                });

                Float rating = ratingbar.getRating();

                if (review.isEmpty()){
                    Toast.makeText(ReviewList.this,
                            "리뷰를 입력해주세요",Toast.LENGTH_SHORT).show();
                }

//                Review review1 = new Review(nick_name1,review,rating);
                Rows rows = new Rows(nick_name,review,rating);

                Retrofit retrofit = NetworkClient.getRetrofitClient(ReviewList.this);
                ReviewApi reviewApi = retrofit.create(ReviewApi.class);

                Call<UserRes> call = reviewApi.createReview(rows);

                call.enqueue(new Callback<UserRes>() {
                    @Override
                    public void onResponse(Call<UserRes> call, Response<UserRes> response) {
                        // 상태코드가 200 인지 확인
                        if (response.isSuccessful()){
                            Log.i("AAAAA","? : "+response.body().toString());
                            Toast.makeText(ReviewList.this,"리뷰가 작성되었습니다"
                                    ,Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                            adapter.notifyDataSetChanged();
                        } else if (response.isSuccessful()==false){

                        }

                    }

                    @Override
                    public void onFailure(Call<UserRes> call, Throwable t) {
                        Log.i("AAAAA",""+t.toString());
                    }
                });
//                reviewArrayList.clear();
//                adapter.notifyDataSetChanged();

            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });


        alert.setView(alertView);

        dialog=alert.create();
        dialog.setCancelable(false);
        dialog.show();
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }
//    @Override
//    protected void onResume() {
//        super.onResume();
//        adapter = new ReviewclerViewAdapter(ReviewList.this, reviewArrayList);
//        reviewcyclerView.setAdapter(adapter);
////        DatabaseHandler db = new DatabaseHandler(MoveRecord.this);
////        moveRecordArrayList = db.getAllRecord();
////        // 어댑터를 연결해야지 화면에 표시가 됨.
////        recyclerViewAdapter = new RecyclerViewAdapter(MoveRecord.this, moveRecordArrayList);
////        recyclerView.setAdapter(recyclerViewAdapter);
//    }

}