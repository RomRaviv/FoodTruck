package com.example.foodtruck.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.foodtruck.R;
import com.example.foodtruck.logic.Menu;
import com.example.foodtruck.logic.Order;
import com.example.foodtruck.logic.OrderManager;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.Date;

public class StationActivity extends AppCompatActivity {
    private ExtendedFloatingActionButton fab_station_menu;
    private ExtendedFloatingActionButton fab_station_register;
    private String customerName;
    private OrderManager orderManager;
    private Menu menu;
    private  boolean letsGo = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station);
        orderManager = OrderManager.getInstance();
        menu = Menu.getInstance();
        fab_station_register = findViewById(R.id.fab_station_cash_register);
        fab_station_register.setOnClickListener(view -> {
            startActivity(new Intent(StationActivity.this, OrdersActivity.class));
        });


        fab_station_menu = findViewById(R.id.fab_station_menu);
        fab_station_menu.setOnClickListener(view -> {
            onButtonShowPopupWindowClick(fab_station_menu);
        });
    }

    public void menuClicked(){
        if(letsGo) {
            Order order = new Order(customerName, new Date());
            orderManager.addOrderToCurrent(order);
            startActivity(new Intent(StationActivity.this, MenuActivity.class));
        }

    }

    public void onButtonShowPopupWindowClick(View view) {
        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.new_order_popup, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
        EditText edt_customer_name = popupView.findViewById(R.id.edt_customer_name);
        ExtendedFloatingActionButton fab_lets_go = popupView.findViewById(R.id.fab_lets_go);
        fab_lets_go.setOnClickListener(view1 -> {
            if(edt_customer_name.getText().toString().equals(""))
                Toast.makeText(this, "Must enter a customer name!", Toast.LENGTH_SHORT).show();
            else {
                customerName = edt_customer_name.getText().toString();
                letsGo = true;
                menuClicked();
                popupWindow.dismiss();
            }
        });

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }
}