package com.example.foodtruck.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.foodtruck.R;
import com.example.foodtruck.config.OrdersRecyclerConfig;
import com.example.foodtruck.interfaces.OrderClickCallBack;
import com.example.foodtruck.logic.Order;
import com.example.foodtruck.logic.OrderManager;

import java.util.ArrayList;

public class OrdersActivity extends AppCompatActivity implements OrderClickCallBack {
    private ImageButton btn_orders_back;
    private RecyclerView recycler_current_orders;
    private Button btn_pay;
    private Order clickedOrder;
    private OrdersRecyclerConfig orc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        recycler_current_orders = findViewById(R.id.recycler_current_orders);
        btn_orders_back= findViewById(R.id.btn_orders_back);
        btn_pay= findViewById(R.id.btn_pay);
        
        btn_orders_back.setOnClickListener(view -> {finish();});
        btn_pay.setOnClickListener(view -> {
            if(clickedOrder != null){
                clickedOrder.setPaid(true);
                Toast.makeText(this, "Paid ! ", Toast.LENGTH_SHORT).show();
                orc.closeRecyclerView();
                orc.setConfig(recycler_current_orders, this, OrdersActivity.this, new ArrayList<>(OrderManager.getInstance().getActiveOrders().values()), this);
            }
            else
                Toast.makeText(this, "Select an order first ! ", Toast.LENGTH_SHORT).show();
        });


        OrderManager om = OrderManager.getInstance();
        orc = new OrdersRecyclerConfig();
        orc.setConfig(recycler_current_orders, this, OrdersActivity.this, new ArrayList<>(OrderManager.getInstance().getActiveOrders().values()), this);
        

    }

    @Override
    public void OrderClicked(Order order) {
        this.clickedOrder = order;

    }
}