package com.yijun.beauty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
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
    ArrayList<Rows> reviewArrayList = new ArrayList<>();

    Button set_review;
    SharedPreferences sp;

    private AlertDialog dialog;

    TextView txt_nick_name;
    RatingBar ratingbar;
    EditText edit_review;
    Button btn_cancel;
    Button btn_set;

    int offset = 0;
    int cnt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        reviewcyclerView = findViewById(R.id.reviewcyclerView);
        reviewcyclerView.setHasFixedSize(true);
        reviewcyclerView.setLayoutManager(new LinearLayoutManager(ReviewList.this));

        reviewcyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                        Toast.makeText(ReviewList.this, "모든 리뷰를 표시했습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        set_review= findViewById(R.id.set_review);
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
                cnt = response.body().getCnt();
                offset = cnt + offset;

                adapter = new ReviewclerViewAdapter(ReviewList.this, reviewArrayList);
                reviewcyclerView.setAdapter(adapter);

            }
            @Override
            public void onFailure(Call<ReviewRes> call, Throwable t) {

            }
        });

    }
    private void addNetworkData() {
        Retrofit retrofit = NetworkClient.getRetrofitClient(ReviewList.this);

        ReviewApi reviewApi = retrofit.create(ReviewApi.class);

        Call<ReviewRes> call = reviewApi.selectReview(offset,25);
        call.enqueue(new Callback<ReviewRes>() {
            @Override
            public void onResponse(Call<ReviewRes> call, Response<ReviewRes> response) {

                Log.i("AAAA",response.body().getSuccess().toString());

                Log.i("AAAA",response.body().getCnt().toString());

                List<Rows> arraylist = new ArrayList<>();
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

                Rows rows = new Rows(review,rating);

                Retrofit retrofit = NetworkClient.getRetrofitClient(ReviewList.this);
                ReviewApi reviewApi = retrofit.create(ReviewApi.class);

                Call<UserRes> call = reviewApi.createReview(nick_name,rows);

                call.enqueue(new Callback<UserRes>() {
                    @Override
                    public void onResponse(Call<UserRes> call, Response<UserRes> response) {
                        // 상태코드가 200 인지 확인
                        if (response.isSuccessful()){
                            Log.i("AAAAA","? : "+response.body().toString());
                            Toast.makeText(ReviewList.this,"리뷰가 작성되었습니다"
                                    ,Toast.LENGTH_SHORT).show();

                            reviewArrayList.clear();
                            getNetworkData();
                            adapter.notifyDataSetChanged();
                            dialog.cancel();

                        } else if (response.isSuccessful()==false){

                        }

                    }

                    @Override
                    public void onFailure(Call<UserRes> call, Throwable t) {
                        Log.i("AAAAA",""+t.toString());
                    }
                });
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
//        arraylist.addAll(reviewArrayList);
//        adapter.notifyDataSetChanged();
//
////        DatabaseHandler db = new DatabaseHandler(MoveRecord.this);
////        moveRecordArrayList = db.getAllRecord();
////        // 어댑터를 연결해야지 화면에 표시가 됨.
////        recyclerViewAdapter = new RecyclerViewAdapter(MoveRecord.this, moveRecordArrayList);
////        recyclerView.setAdapter(recyclerViewAdapter);
//    }


}