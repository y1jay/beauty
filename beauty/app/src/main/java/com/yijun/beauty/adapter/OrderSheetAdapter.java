package com.yijun.beauty.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yijun.beauty.R;
import com.yijun.beauty.model.Orders;
import com.yijun.beauty.model.ReservationRes;

import java.util.ArrayList;
import java.util.List;

public class OrderSheetAdapter extends RecyclerView.Adapter<OrderSheetAdapter.ViewHolder>{
    Context context;
    ArrayList<Orders> orderArrayList;

    public OrderSheetAdapter(Context context, ArrayList<Orders> orderArrayList) {
        this.context = context;
        this.orderArrayList = orderArrayList;
    }

    @NonNull
    @Override
    public OrderSheetAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 첫번째 파라미터인, parent로 부터 뷰(화면 : 하나의 셀)를 생성한다.
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_row, parent, false); //inflate=만들라는 뜻
        //리턴에, 위에서 생성한 뷰를, 뷰홀더에 담아서 리턴한다.
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderSheetAdapter.ViewHolder holder, int position) {
        Orders orders = orderArrayList.get(position);
        String order_menu = orders.getMenu();
        String order_price = orders.getPrice();

        holder.order_menu.setText(order_menu);
        holder.order_price.setText(order_price);
    }

    @Override
    public int getItemCount() {
        return orderArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView order_menu;
        public TextView order_price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            order_menu = itemView.findViewById(R.id.order_menu);
            order_price = itemView.findViewById(R.id.order_price);
        }
    }
}
