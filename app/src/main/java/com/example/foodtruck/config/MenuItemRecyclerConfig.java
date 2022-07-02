package com.example.foodtruck.config;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static android.widget.Toast.makeText;
import static androidx.core.content.ContextCompat.getSystemService;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodtruck.R;
import com.example.foodtruck.activities.CategoryActivity;
import com.example.foodtruck.logic.ItemCategory;
import com.example.foodtruck.logic.Menu;
import com.example.foodtruck.logic.MenuItem;
import com.example.foodtruck.logic.Order;
import com.example.foodtruck.logic.OrderManager;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;

public class MenuItemRecyclerConfig {
    private Context context;
    private MenuItemRecyclerConfig.ItemAdapter itemAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, ArrayList<MenuItem> itemNames){
        this.context = context;
        itemAdapter = new ItemAdapter(itemNames);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(itemAdapter);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_item_name;
        private TextView txt_item_description;
        private TextView txt_item_price;
        private RelativeLayout relative_item_card_view;

        public ItemViewHolder(final View view) {
            super(view);
            txt_item_name =  view.findViewById(R.id.txt_item_name);
            txt_item_description =  view.findViewById(R.id.txt_item_description);
            txt_item_price =  view.findViewById(R.id.txt_item_price);
            relative_item_card_view= view.findViewById(R.id.relative_item_card_view);

        }
    }

    public class ItemAdapter extends RecyclerView.Adapter<MenuItemRecyclerConfig.ItemViewHolder>{
        private ArrayList<MenuItem> items;

        public ItemAdapter(ArrayList<MenuItem> items){
            this.items = items;
        }

        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item,parent,false);
            return new ItemViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
            MenuItem item = items.get(position);
            holder.txt_item_name.setText(item.getName());
            holder.txt_item_description.setText(item.getDescription());
            holder.txt_item_price.setText(String.valueOf(item.getPrice()));

            holder.relative_item_card_view.setOnClickListener(view -> {
                onButtonShowPopupWindowClick(holder.relative_item_card_view , item);
            });

        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }

    public void onButtonShowPopupWindowClick(View view, MenuItem item) {
        // inflate the layout of the popup window
        View popupView = LayoutInflater.from(context).inflate(R.layout.add_item_popup, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);

        //logic code
        Order ongoingOrder = OrderManager.getInstance().getOngoingOrder();
        Menu menu = Menu.getInstance();
        MenuItem itemToAdd = new MenuItem(item);
        EditText edt_free_text = popupView.findViewById(R.id.edt_free_text);
        TextView txt_item_name =popupView.findViewById(R.id.txt_item_name);
        TextView txt_total_item_cost =popupView.findViewById(R.id.txt_total_item_cost);
        TextView txt_item_description=popupView.findViewById(R.id. txt_item_description);
        Spinner spinner_sauce = popupView.findViewById(R.id.spinner_sauce);
        Spinner spinner_extra_sauce = popupView.findViewById(R.id.spinner_extra_sauce);
        Spinner spinner_add_ons = popupView.findViewById(R.id.spinner_add_ons);
        LinearLayout linear_add_ons = popupView.findViewById(R.id.linear_add_ons);
        LinearLayout linear_sauce = popupView.findViewById(R.id.linear_sauce);
        LinearLayout linear_extra_sauce = popupView.findViewById(R.id.linear_extra_sauce);
        RelativeLayout relative_add_item_popup = popupView.findViewById(R.id.relative_add_item_popup);
        if(itemToAdd.getCategory().equals(ItemCategory.Drink)){
            relative_add_item_popup.removeView(linear_add_ons);
            relative_add_item_popup.removeView(linear_sauce);
            relative_add_item_popup.removeView(linear_extra_sauce);
        }
        else if(itemToAdd.getCategory().equals(ItemCategory.Dessert)){
            relative_add_item_popup.removeView(linear_add_ons);
        }
        else {
            spinnerBuilder(spinner_add_ons, menu.getAddOnsNames(), itemToAdd, txt_total_item_cost, false);
            spinnerBuilder(spinner_sauce, menu.getSauceNames(), itemToAdd, txt_total_item_cost, true);
            spinnerBuilder(spinner_extra_sauce, menu.getSauceNames(), itemToAdd, txt_total_item_cost, false);
        }

        txt_item_name.setText(item.getName());
        txt_item_description.setText(item.getDescription());


        ExtendedFloatingActionButton fab_add_item_to_order = popupView.findViewById(R.id.fab_add_item_to_order);
        fab_add_item_to_order.setOnClickListener(view1 -> {
            ongoingOrder.addItem(itemToAdd);
            makeText(context, "Item Added!!", Toast.LENGTH_SHORT).show();
            popupWindow.dismiss();
        });

        txt_total_item_cost.setText(txt_total_item_cost.getText().toString() +"  "+  itemToAdd.getTotalPrice());
        itemToAdd.setFreeText(edt_free_text.getText().toString());

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

    private void spinnerBuilder(Spinner spinner, String[] options, MenuItem itemToAdd, TextView txt_total_item_cost, boolean freeSauce){

        // Create the instance of ArrayAdapter
        // having the list of courses
        ArrayAdapter ad
                = new ArrayAdapter(
                context,
                android.R.layout.simple_spinner_item,
                options);

        // set simple layout resource file
        // for each item of spinner
        ad.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);

        // Set the ArrayAdapter (ad) data on the
        // Spinner which binds data to spinner
        spinner.setAdapter(ad);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    MenuItem item = Menu.getInstance().getItemByName(options[i]);
                    if (options[i].equals("None"))
                        txt_total_item_cost.setText(String.valueOf(itemToAdd.getPrice()));

                    if (item != null) {
                        if (ItemCategory.AddOn.equals(item.getCategory())) {
                            ArrayList<MenuItem> addOns = itemToAdd.getAddOns();
                            if (!addOns.contains(item))
                                addOns.add(item);
                            float currentCost = itemToAdd.getPrice() + item.getPrice();
                            txt_total_item_cost.setText(String.valueOf(currentCost));
                            return;
                        }
                        if (ItemCategory.Sauce.equals(item.getCategory())) {
                            if (ItemCategory.Sauce.equals(item.getCategory())) {
                                ArrayList<MenuItem> sauces = itemToAdd.getSauces();
                                if (!sauces.contains(item))
                                    sauces.add(item);
                                float currentCost;
                                if(!freeSauce)
                                    currentCost = itemToAdd.getPrice() + item.getPrice();
                                else
                                    currentCost = itemToAdd.getPrice();

                                txt_total_item_cost.setText(String.valueOf(currentCost));
                                return;
                            }
                        }
                    }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    }



