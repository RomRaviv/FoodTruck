package com.example.foodtruck.config;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodtruck.R;
import com.example.foodtruck.activities.CategoryActivity;
import com.example.foodtruck.fragments.OrderDetailsFragment;
import com.example.foodtruck.interfaces.OrderClickCallBack;
import com.example.foodtruck.logic.Order;

import java.util.ArrayList;

public class OrdersRecyclerConfig {

    private Context context;
    private Activity activity;
    private OrderAdapter orderAdapter;
    private OrderClickCallBack clickCallBack;
    private RecyclerView recyclerView;

    public void setConfig(RecyclerView recyclerView, Context context,Activity activity, ArrayList<Order> orders, OrderClickCallBack clickCallBack){
        this.activity = activity;
        this.context = context;
        this.clickCallBack = clickCallBack;
        this.recyclerView= recyclerView;
        orderAdapter = new OrderAdapter(orders);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        this.recyclerView.setLayoutManager(layoutManager);
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());
        this.recyclerView.setAdapter(orderAdapter);
    }
    public void closeRecyclerView(){
        recyclerView.clearFocus();
    }

    public class OrdersViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_order_num;
        private TextView txt_order_name;
        private TextView txt_order_status;
        private RelativeLayout relative_order_card_view;
        private ImageView img_paid;

        public OrdersViewHolder(final View view) {
            super(view);
            txt_order_num =  view.findViewById(R.id.txt_order_num);
            txt_order_name =  view.findViewById(R.id.txt_order_name);
            txt_order_status =  view.findViewById(R.id.txt_order_status);
            relative_order_card_view= view.findViewById(R.id.relative_order_card_view);
            img_paid= view.findViewById(R.id.img_paid);

        }
    }

    public class OrderAdapter extends RecyclerView.Adapter<OrdersViewHolder>{
        private ArrayList<Order> orders;

        public OrderAdapter(ArrayList<Order> orders){
            this.orders = orders;
        }

        @NonNull
        @Override
        public OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_order,parent,false);
            return new OrdersViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull OrdersViewHolder holder, int position) {
            Order order = orders.get(position);
            holder.txt_order_name.setText(order.getCustomerName());
            holder.txt_order_num.setText("" + order.getOrderNumber());
            holder.txt_order_status.setText(order.getStatus());
            if(order.isPaid())
                holder.img_paid.setVisibility(View.VISIBLE);
            else
                holder.img_paid.setVisibility(View.INVISIBLE);

            holder.relative_order_card_view.setOnClickListener(view -> {
                OrderDetailsFragment orderFragment = new OrderDetailsFragment(order);
                ((FragmentActivity)activity).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_current_orders , orderFragment)
                        .commit();
            });
            clickCallBack.OrderClicked(order);

        }

        @Override
        public int getItemCount() {
            return orders.size();
        }
    }
}
