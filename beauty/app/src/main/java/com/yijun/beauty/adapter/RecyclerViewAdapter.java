package com.yijun.beauty.adapter;

import android.content.Context;
import android.content.SharedPreferences;
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

import com.yijun.beauty.R;
import com.yijun.beauty.model.Review;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import static android.content.Context.MODE_PRIVATE;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    Context context;
    ArrayList<Review> reviewArrayList;

    public RecyclerViewAdapter(Context context, ArrayList<Review> reviewArrayList){
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
        Review review= reviewArrayList.get(position);
        String nick_name = review.getNick_name();
        String review1 = review.getReview();
        int rating = review.getRating();
        String created_at = review.getCreated_at();


        holder.txt_nick_name.setText(nick_name);
        holder.txt_review.setText(review1);
        holder.ratingBar.setNumStars(rating);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        df.setTimeZone(TimeZone.getTimeZone("UTF"));    // 위의 시간을 utc로 맞추는것.(우리는 이미 서버에서 utc로 맞춰놔서 안해도 되는데 혹시몰라서 해줌)

        try {
            Date date = df.parse(created_at);
            df.setTimeZone(TimeZone.getDefault());      // 내 폰의 로컬 타임존으로 바꿔줌.
            String strDate = df.format(date).replace("T", " ");
           holder.txt_created_at.setText("작성일자  :  "+strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return reviewArrayList.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txt_nick_name;
        public TextView txt_review;
        public TextView txt_created_at;
        public RatingBar ratingBar;
        public CardView cardView;



        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            txt_nick_name = itemView.findViewById(R.id.txt_nick_name);
            txt_review = itemView.findViewById(R.id.txt_review);
            txt_created_at = itemView.findViewById(R.id.txt_created_at);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            cardView = itemView.findViewById(R.id.cardView);



        }
    }
}
