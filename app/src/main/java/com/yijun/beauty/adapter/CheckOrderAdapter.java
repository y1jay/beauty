package com.yijun.beauty.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yijun.beauty.R;
import com.yijun.beauty.model.Orders;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class CheckOrderAdapter extends RecyclerView.Adapter<CheckOrderAdapter.ViewHolder>{
    Context context;
    ArrayList<Orders> orderArrayList;

    public CheckOrderAdapter(Context context, ArrayList<Orders> orderArrayList) {
        this.context = context;
        this.orderArrayList = orderArrayList;
    }

    @NonNull
    @Override
    public CheckOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 첫번째 파라미터인, parent로 부터 뷰(화면 : 하나의 셀)를 생성한다.
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.check_order_rows, parent, false); //inflate=만들라는 뜻
        //리턴에, 위에서 생성한 뷰를, 뷰홀더에 담아서 리턴한다.
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckOrderAdapter.ViewHolder holder, int position) {
      Orders orders = orderArrayList.get(position);
        String order_menu = orders.getMenu();
        String order_price = orders.getPrice();
        String order_created_at = orders.getTime();
        int take_out = orders.isTake_out();
        int people_number = orders.getPeople_number();




        if(order_created_at ==null){

            holder.check_order_menu.setText("                 주문하신 음식이 없습니다.");
            holder.check_order_price.setText("");
            holder.check_order_created_at.setText("");
            holder.txt_won.setText("");
            return;
        }

        if(order_price !=null){

            Double total_price = Double.parseDouble(order_price);
            total_price = total_price * 1000;
            DecimalFormat format = new DecimalFormat("###,###");//콤마
            String total = format.format(total_price);
            Log.i("total", total);
            holder.check_order_price.setText(total);



        }else {

            holder.check_order_menu.setText("                 주문하신 음식이 없습니다.");
            holder.check_order_price.setText("");
            holder.check_order_created_at.setText("");
            holder.txt_won.setText("");
            return;
        }




        if(order_created_at == null && order_menu == null && order_price == null){
            Toast.makeText(context,"주문기록이 없습니다",Toast.LENGTH_SHORT).show();
            return;
        }
        if(order_created_at == null || order_menu == null || order_price == null){
            Toast.makeText(context,"주문기록이 없습니다",Toast.LENGTH_SHORT).show();
            return;
        }


        if(take_out == 1){
            holder.txt_take_out.setText("포장");
        }else {
            holder.txt_take_out.setText("매장  "+people_number+"명");
        }








        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        df.setTimeZone(TimeZone.getTimeZone("UTF"));

        try {


            Date date = df.parse(order_created_at);
            df.setTimeZone(TimeZone.getDefault());      // 내 폰의 로컬 타임존으로 바꿔줌.
            String strDate = df.format(date).replace("T", " ");
            holder.check_order_created_at.setText("예약시간  :  "+strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.check_order_menu.setText(order_menu);














    }

    @Override
    public int getItemCount() {
        return orderArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView check_order_menu;
        public TextView check_order_price;
        public TextView check_order_created_at;
        public TextView txt_won;
        public TextView txt_take_out;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            check_order_menu = itemView.findViewById(R.id.check_order_menu);
            check_order_price = itemView.findViewById(R.id.check_order_total);
            check_order_created_at = itemView.findViewById(R.id.check_order_created_at);
            txt_won = itemView.findViewById(R.id.txtwon);
            txt_take_out = itemView.findViewById(R.id.txt_take_out);
        }
    }
}
