package com.example.foodtruck.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.foodtruck.R;
import com.example.foodtruck.config.CategoriesRecyclerConfig;
import com.example.foodtruck.config.MenuItemRecyclerConfig;
import com.example.foodtruck.logic.ItemCategory;
import com.example.foodtruck.logic.Menu;
import com.example.foodtruck.logic.OrderManager;

public class CategoryActivity extends AppCompatActivity {
    private Menu menu = Menu.getInstance();
    private String currentCategory;
    private TextView txt_category_title;
    private RecyclerView recycler_current_orders;
    private Button btn_category_my_order;
    private ImageButton btn_category_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        findViews();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentCategory = extras.getString("Category");
            txt_category_title.setText(currentCategory);
        }
        recycler_current_orders = findViewById(R.id.recycler_current_orders);

        btn_category_my_order.setOnClickListener(view -> {
            String orderNum = String.valueOf(OrderManager.getInstance().getOngoingOrder().getOrderNumber());
            Intent i = new Intent(this, MyOrderActivity.class);
            i.putExtra("OrderNum", orderNum);
            startActivity(i);
            finish();
        });
        btn_category_back.setOnClickListener(view -> {
            finish();
        });
        new MenuItemRecyclerConfig().setConfig(recycler_current_orders, CategoryActivity.this, menu.getCategoryItems(ItemCategory.valueOf(currentCategory)));

    }

    private void findViews() {
        txt_category_title = findViewById(R.id.txt_category_title);
        recycler_current_orders = findViewById(R.id.recycler_current_orders);
        btn_category_my_order = findViewById(R.id.btn_category_my_order);
        btn_category_back = findViewById(R.id.btn_category_back);
    }
}