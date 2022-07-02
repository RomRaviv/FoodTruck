package com.example.foodtruck.config;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.ViewGroup;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.foodtruck.R;
import com.example.foodtruck.logic.MenuItem;
import com.example.foodtruck.logic.Order;

import java.util.Map;

public class LayoutOrderBuilder {
    private final int TEXT_SIZE = 18;
    public LayoutOrderBuilder(){
    }

    public void buildLayoutOrder(View view, Context context, Order order){
        int itemNumber = 1;

        TextView txt_order_date = view.findViewById(R.id.txt_order_date);
        TextView txt_order_num = view.findViewById(R.id.txt_order_num);
        TextView txt_customer_name =view.findViewById(R.id.txt_customer_name);
        TextView txt_order_total_price =view.findViewById(R.id.txt_order_total_price);

        LinearLayout linear_order_grid =view.findViewById(R.id.linear_order_grid);

        //fill in the order details
        txt_order_date.setText(order.getDate().toString());
        txt_order_num.setText("" + order.getOrderNumber());
        txt_customer_name.setText(order.getCustomerName());
        txt_order_total_price.setText(String.valueOf(order.getTotalPrice()));


        //build row for each item.
        for(Map.Entry<MenuItem,Integer> entry: order.getItems().entrySet()) {
            MenuItem item = entry.getKey();
            int quantity = entry.getValue();
            LinearLayout layout = new LinearLayout(context);

            //initialize linear layout
            LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            linearLayoutParams.setMargins(8,8,8,8);
            layout.setLayoutParams(linearLayoutParams);
            layout.setOrientation(LinearLayout.HORIZONTAL);


            //children of layout LinearLayout
            TableLayout.LayoutParams txtViewParams = new TableLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);

            TextView txt_item_number = new TextView(context);
            TextView txt_item = new TextView(context);
            TextView txt_quantity = new TextView(context);
            TextView txt_item_price = new TextView(context);
            TextView txt_total = new TextView(context);

            //width and height
            txt_item_number.setLayoutParams(txtViewParams);
            txt_item.setLayoutParams(txtViewParams);
            txt_quantity.setLayoutParams(txtViewParams);
            txt_item_price.setLayoutParams(txtViewParams);
            txt_total.setLayoutParams(txtViewParams);


            //set padding
            txt_item_number.setPadding(5,5,5,5);
            txt_item.setPadding(5,5,5,5);
            txt_quantity.setPadding(5,5,5,5);
            txt_item_price.setPadding(5,5,5,5);
            txt_total.setPadding(5,5,5,5);

            //set color
            txt_item_number.setTextColor(Color.parseColor("#FF000000"));
            txt_item.setTextColor(Color.parseColor("#FF000000"));
            txt_quantity.setTextColor(Color.parseColor("#FF000000"));
            txt_item_price.setTextColor(Color.parseColor("#FF000000"));

            txt_total.setTextColor(Color.parseColor("#FF000000"));

            //set style
            txt_item_number.setTypeface(Typeface.DEFAULT_BOLD);
            txt_item.setTypeface(Typeface.DEFAULT_BOLD);
            txt_quantity.setTypeface(Typeface.DEFAULT_BOLD);
            txt_item_price.setTypeface(Typeface.DEFAULT_BOLD);
            txt_total.setTypeface(Typeface.DEFAULT_BOLD);

            //set text size
            txt_item_number.setTextSize(TEXT_SIZE);
            txt_item.setTextSize(TEXT_SIZE);
            txt_quantity.setTextSize(TEXT_SIZE);
            txt_item_price.setTextSize(TEXT_SIZE);
            txt_total.setTextSize(TEXT_SIZE);

            //set text
            txt_item_number.setText("" + itemNumber);
            txt_item.setText(item.toString());
            txt_quantity.setText("" + quantity);
            txt_item_price.setText("" + item.getPrice());
            txt_total.setText("" + (item.getPrice()* quantity));


            layout.addView(txt_item_number);
            layout.addView(txt_item);
            layout.addView(txt_quantity);
            layout.addView(txt_item_price);
            layout.addView(txt_total);

            linear_order_grid.addView(layout);

            itemNumber++;
        }
    }

}
