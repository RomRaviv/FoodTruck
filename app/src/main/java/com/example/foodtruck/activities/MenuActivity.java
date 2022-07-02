package com.example.foodtruck.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.foodtruck.R;
import com.example.foodtruck.config.CategoriesRecyclerConfig;
import com.example.foodtruck.logic.Menu;
import com.example.foodtruck.logic.OrderManager;

public class MenuActivity extends AppCompatActivity {
    private RecyclerView recycler_menu_categories;
    private ImageButton btn_menu_back;
    private Button btn_my_order;
    private Button btn_done;
    private OrderManager orderManager;
    private Menu menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        orderManager = OrderManager.getInstance();
        menu = Menu.getInstance();
        recycler_menu_categories = findViewById(R.id.recycler_menu_categories);
        btn_menu_back = findViewById(R.id.btn_menu_back);
        btn_my_order = findViewById(R.id.btn_my_order);
        btn_done = findViewById(R.id.btn_done);

        btn_done.setOnClickListener(view -> {
            Toast.makeText(this, "Order Received!! ", Toast.LENGTH_SHORT).show();
            OrderManager.getInstance().writeToDatabase();
            finish();
        });

        btn_my_order.setOnClickListener(view -> {
            String orderNum = String.valueOf(OrderManager.getInstance().getOngoingOrder().getOrderNumber());
            Intent i = new Intent(this, MyOrderActivity.class);
            i.putExtra("OrderNum",orderNum);
            startActivity(i);
        });

        btn_menu_back.setOnClickListener(view -> {
            finish();
        });

        new CategoriesRecyclerConfig().setConfig(recycler_menu_categories, MenuActivity.this, menu.getAllCatagoriesNames());


    }
}