package com.yijun.beauty.adapter;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.yijun.beauty.AfterLogin;
import com.yijun.beauty.MainActivity;
import com.yijun.beauty.R;
import com.yijun.beauty.ReviewList;
import com.yijun.beauty.model.Review;
import com.yijun.beauty.model.Rows;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import static android.content.Context.MODE_PRIVATE;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Context context;
    List<Rows> reviewArrayList;

    public RecyclerViewAdapter(Context context, List<Rows> reviewArrayList) {
        this.context = context;
        this.reviewArrayList = reviewArrayList;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 첫번째 파라미터인, parent로 부터 뷰(화면 : 하나의 셀)를 생성한다.
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_row, parent, false); //inflate=만들라는 뜻
        //리턴에, 위에서 생성한 뷰를, 뷰홀더에 담아서 리턴한다.
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        // 어레이리스트에 저장된 데이터를 화면과 연결 : bind
        Rows rows = reviewArrayList.get(position);
        String nick_name = rows.getNick_name();
        String review1 = rows.getReview();
        Float rating = rows.getRating();
        String created_at = rows.getCreated_at();


        holder.txt_nick_name.setText(nick_name);
        holder.txt_review.setText(review1);
        holder.ratingbar.setRating(rating);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        df.setTimeZone(TimeZone.getTimeZone("UTF"));    // 위의 시간을 utc로 맞추는것.(우리는 이미 서버에서 utc로 맞춰놔서 안해도 되는데 혹시몰라서 해줌)

        try {
            Date date = df.parse(created_at);
            df.setTimeZone(TimeZone.getDefault());      // 내 폰의 로컬 타임존으로 바꿔줌.
            String strDate = df.format(date).replace("T", " ");
            holder.txt_created_at.setText("작성일자  :  " + strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return reviewArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_nick_name;
        public TextView txt_review;
        public TextView txt_created_at;
        public RatingBar ratingbar;
        public CardView cardView;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            txt_nick_name = itemView.findViewById(R.id.txt_nick_name);
            txt_review = itemView.findViewById(R.id.txt_review);
            txt_created_at = itemView.findViewById(R.id.txt_created_at);
            ratingbar = itemView.findViewById(R.id.ratingBar);
            cardView = itemView.findViewById(R.id.cardView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, ReviewList.class);
                    CheckTypesTask task = new CheckTypesTask();
                    task.execute();
                    context.startActivity(i);
                }
            });
            txt_review.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, ReviewList.class);
                    CheckTypesTask task = new CheckTypesTask();
                    task.execute();
                    context.startActivity(i);
                }
            });
            txt_nick_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, ReviewList.class);
                    CheckTypesTask task = new CheckTypesTask();
                    task.execute();
                    context.startActivity(i);
                }
            });
            txt_created_at.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, ReviewList.class);
                    CheckTypesTask task = new CheckTypesTask();
                    task.execute();
                    context.startActivity(i);
                }
            });
            ratingbar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, ReviewList.class);
                    CheckTypesTask task = new CheckTypesTask();
                    task.execute();
                    context.startActivity(i);
                }
            });
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, ReviewList.class);

                    CheckTypesTask task = new CheckTypesTask();
                    task.execute();

                    context.startActivity(i);
                }
            });
        }

    }

    private  class CheckTypesTask extends AsyncTask<Void, Integer, Boolean> {
        ProgressDialog asyncDialog = new ProgressDialog(context);

        @Override
        protected void onPreExecute(){
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("로딩중..");
            asyncDialog.show();
            asyncDialog.setCancelable(false);
            super.onPreExecute();
        }
        @Override
        protected Boolean doInBackground(Void... strings){

            for(int i = 0; i<10000; i++){
                publishProgress(i);


            }
            return true;

        }

        @Override
        protected void onPostExecute(Boolean s){

            asyncDialog.dismiss();
            super.onPostExecute(s);
        }


        @Override
        protected void onCancelled(Boolean s){
            super.onCancelled(s);
        }

    }
}
