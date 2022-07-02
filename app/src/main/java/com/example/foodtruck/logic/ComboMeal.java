package com.example.foodtruck.logic;

import java.util.ArrayList;

public class ComboMeal extends MenuItem {
    private MenuItem burger;
    private MenuItem drink;
    private MenuItem fries;


    public ComboMeal() {
    }

    public ComboMeal(ItemCategory category, String name, String description, String freeText, float price, MenuItem burger, MenuItem drink, MenuItem fries) {
        super(category, name, description, freeText, price);
        this.burger = burger;
        this.drink = drink;
        this.fries = fries;
    }

    public MenuItem getBurger() {
        return burger;
    }

    public MenuItem getDrink() {
        return drink;
    }

    public MenuItem getFries() {
        return fries;
    }

    public void setBurger(MenuItem burger) {
        if(burger.getCategory().equals(ItemCategory.Burger))
            this.burger = burger;
    }

    public void setDrink(MenuItem drink) {
        if(burger.getCategory().equals(ItemCategory.Drink))
            this.drink = drink;
    }

    public void setFries(MenuItem fries) {
        if(burger.getCategory().equals(ItemCategory.Fries))
            this.fries = fries;
    }
}
