package com.example.foodtruck.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.foodtruck.R;
import com.example.foodtruck.fragments.OrderDetailsFragment;
import com.example.foodtruck.logic.Order;
import com.example.foodtruck.logic.OrderManager;

public class MyOrderActivity extends AppCompatActivity {
    private OrderDetailsFragment orderFragment;
    private Order myOrder;
    private ImageButton btn_my_order_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        btn_my_order_back = findViewById(R.id.btn_my_order_back);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String orderNum = extras.getString("OrderNum");
            myOrder = OrderManager.getInstance().findOrderByNum(orderNum);
        }

        btn_my_order_back.setOnClickListener(view -> {
            finish();
        });

        orderFragment = new OrderDetailsFragment(myOrder);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_my_order , orderFragment)
                .commit();

    }
}