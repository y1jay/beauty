package com.yijun.beauty.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.yijun.beauty.R;
import com.yijun.beauty.model.Rows;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class MyReviewclerViewAdapter extends RecyclerView.Adapter<MyReviewclerViewAdapter.ViewHolder> {
    Context context;
    ArrayList<Rows> reviewArrayList;

    public MyReviewclerViewAdapter(Context context, ArrayList<Rows> reviewArrayList){
        this.context = context;
        this.reviewArrayList = reviewArrayList;
    }

    @NonNull
    @Override
    public MyReviewclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 첫번째 파라미터인, parent로 부터 뷰(화면 : 하나의 셀)를 생성한다.
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_review_row, parent, false); //inflate=만들라는 뜻
        //리턴에, 위에서 생성한 뷰를, 뷰홀더에 담아서 리턴한다.
        return new MyReviewclerViewAdapter.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MyReviewclerViewAdapter.ViewHolder holder, int position) {
        // 어레이리스트에 저장된 데이터를 화면과 연결 : bind
        Rows rows= reviewArrayList.get(position);
        String review1 = rows.getReview();
        Float rating = rows.getRating();
        String created_at = rows.getCreated_at();


        holder.my_txt_review.setText(review1);
        holder.ratingBar.setRating(rating);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        df.setTimeZone(TimeZone.getTimeZone("UTF"));    // 위의 시간을 utc로 맞추는것.(우리는 이미 서버에서 utc로 맞춰놔서 안해도 되는데 혹시몰라서 해줌)

        try {
            Date date = df.parse(created_at);
            df.setTimeZone(TimeZone.getDefault());      // 내 폰의 로컬 타임존으로 바꿔줌.
            String strDate = df.format(date).replace("T", " ");
            holder.my_txt_created_at.setText("작성일자  :  "+strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return reviewArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView  my_txt_review;
        public TextView my_txt_created_at;
        public RatingBar ratingBar;
        public Button my_btn_delete;



        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            my_txt_review = itemView.findViewById(R.id.txt_review);
            my_txt_created_at = itemView.findViewById(R.id.txt_created_at);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            my_btn_delete = itemView.findViewById(R.id.my_btn_delete);

            my_btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });


        }
    }
}
