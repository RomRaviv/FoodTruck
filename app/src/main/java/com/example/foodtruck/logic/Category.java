package com.example.foodtruck.logic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Category {
    private ItemCategory type;
    private ArrayList<MenuItem> items;

    public Category() {
    }

    public Category(ItemCategory type) {
        this.type = type;
        this.items = new ArrayList<>();
    }

    public ItemCategory getType() {
        return type;
    }

    public ArrayList<MenuItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<MenuItem> items) {
        this.items = items;
    }

    public Category addItem(MenuItem item){
        if(item.getCategory().equals(type) && !items.contains(item))
            items.add(item);
        return this;
    }

    public boolean removeItem(MenuItem item){
        return items.remove(item);
    }

    public MenuItem getItemByName(String itemName) {
        for (MenuItem item : items) {
            if (item.getName().equalsIgnoreCase(itemName))
                return item;
        }
        return null;
    }




}
