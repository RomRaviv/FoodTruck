package com.example.foodtruck.config;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodtruck.R;
import com.example.foodtruck.activities.CategoryActivity;
import com.example.foodtruck.activities.MenuActivity;

import java.util.ArrayList;

public class CategoriesRecyclerConfig {
    private Context context;
    private CategoryAdapter categoryAdapter;

    public void setConfig(RecyclerView recyclerView, Context context,ArrayList<String> categoriesNames){
        this.context = context;
        categoriesNames.remove("AddOn");
        categoryAdapter = new CategoryAdapter(categoriesNames);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(categoryAdapter);
    }

    public class CategoriesViewHolder extends RecyclerView.ViewHolder {
        private TextView categoryTitle;
        private RelativeLayout relative_category_card_view;
        private ImageView img_category;
        public CategoriesViewHolder(final View view) {
            super(view);
            categoryTitle =  view.findViewById(R.id.txt_category_name);
            relative_category_card_view= view.findViewById(R.id.relative_category_card_view);
            img_category= view.findViewById(R.id.img_category);

        }
    }

    public class CategoryAdapter extends RecyclerView.Adapter<CategoriesViewHolder>{
        private ArrayList<String> categoriesNames;

        public CategoryAdapter(ArrayList<String> categoriesNames){
            this.categoriesNames = categoriesNames;
        }

        @NonNull
        @Override
        public CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_menu_category,parent,false);
            return new CategoriesViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull CategoriesViewHolder holder, int position) {
            String categoryName = categoriesNames.get(position);
            holder.categoryTitle.setText(categoryName);
            switch (categoryName){
                case "Burger":
                    holder.img_category.setImageResource(R.drawable.ic_burger);
                    break;
                case "Fries":
                    holder.img_category.setImageResource(R.drawable.ic_fries);
                    break;
                case "Sauce":
                    holder.img_category.setImageResource(R.drawable.ic_sauce);
                    break;
                case "Drink":
                    holder.img_category.setImageResource(R.drawable.ic_drink);
                    break;
                case "Dessert":
                    holder.img_category.setImageResource(R.drawable.ic_dessert);
                    break;
                case "AddOn":
                    holder.img_category.setImageResource(R.drawable.ic_addon);
                    break;

            }
            holder.relative_category_card_view.setOnClickListener(view -> {
                String value=categoriesNames.get(position);
                Intent i = new Intent(context, CategoryActivity.class);
                i.putExtra("Category",value);

                context.startActivity(i);
            });

        }

        @Override
        public int getItemCount() {
            return categoriesNames.size();
        }
    }
}
