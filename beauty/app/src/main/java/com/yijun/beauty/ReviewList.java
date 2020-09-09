package com.yijun.beauty;

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

import com.yijun.beauty.adapter.RecyclerViewAdapter;
import com.yijun.beauty.adapter.ReviewclerViewAdapter;
import com.yijun.beauty.api.NetworkClient;
import com.yijun.beauty.api.ReviewApi;
import com.yijun.beauty.model.ReviewRes;
import com.yijun.beauty.model.Rows;
import com.yijun.beauty.url.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ReviewList extends AppCompatActivity {
RecyclerView reviewcyclerView;
    ReviewclerViewAdapter adapter;
    Button btn_set;

    private AlertDialog dialog;
    List<Rows> reviewArrayList = new ArrayList<>();

    TextView txt_nick_name;
    RatingBar ratingbar;
    EditText edit_review;
    Button btn_register;
    Button btn_cancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        btn_set= findViewById(R.id.btn_set);
        reviewcyclerView = findViewById(R.id.reviewcyclerView);
        reviewcyclerView.setHasFixedSize(true);
        reviewcyclerView.setLayoutManager(new LinearLayoutManager(ReviewList.this));
        getNetworkData();
        btn_set.setOnClickListener(new View.OnClickListener() {
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
                // response.body() ==> PostRes 클래스
                Log.i("AAAA",response.body().getSuccess().toString());
                //PostRes.get(0) => List<row>의 첫번째 Item 객체.
                // PostRes.get(0).getPosting()=> 위의 Row 객체에 저장된 Posting 값

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
        btn_register = alertView.findViewById(R.id.btn_set);
        ratingbar = alertView.findViewById(R.id.ratingBar);
        btn_cancel = alertView.findViewById(R.id.btn_cancel);

        ratingbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
            }
        });



        SharedPreferences sp = getSharedPreferences(Utils.PREFERENCES_NAME,MODE_PRIVATE);
        String nick_name =sp.getString("nick_name",null);
        txt_nick_name.setText(nick_name);



        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });


        alert.setView(alertView);

        dialog=alert.create();
//                alert.setCancelable(false);
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

}