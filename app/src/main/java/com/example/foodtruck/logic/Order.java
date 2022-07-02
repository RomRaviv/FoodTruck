package com.example.foodtruck.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Order {
    private int orderNumber;
    private String customerName;
    private Date date;
    private float totalPrice;
    private Map<MenuItem, Integer> items;
    private boolean paid;
    private Status status;

    public Order() {
    }

    public Order(String customerName, Date date) {
        this.customerName = customerName;
        this.date = date;
        items = new HashMap<>();
        totalPrice = 0;
        paid = false;
        status = Status.InProgress;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public Date getDate() {
        return date;
    }

    public float getTotalPrice() {
        totalPrice = calculatePrice();
        return totalPrice;
    }

    public Map<MenuItem, Integer> getItems() {
        return items;
    }

    public String getStatus() {
        return status.toString();

    }

    public boolean isPaid() {
        return paid;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void addItem(MenuItem item) {
        int quantity = 1;
        Map<MenuItem, Integer> items = getItems();
        ArrayList<MenuItem> sauces = item.getSauces();
        ArrayList<MenuItem> addOns = item.getAddOns();

        if(items.containsKey(item))
            quantity += items.get(item);

        if(sauces != null){
            for (int i = 1; i< sauces.size(); i++) {
                addItem(sauces.get(i));
            }
        }

        if(addOns != null){
            for (MenuItem addOn: addOns) {
                addItem(addOn);
            }
        }

        if(item.getCategory().equals(ItemCategory.ComboMeal)){
            ComboMeal comboMeal = (ComboMeal) item;

            //Burger AddOns and Sauces
            for (MenuItem addOn : comboMeal.getBurger().getAddOns())
                addItem(addOn);
            for (MenuItem sauce : comboMeal.getBurger().getSauces())
                addItem(sauce);

            //Fries AddOn and Sauces
            for (MenuItem addOn : comboMeal.getFries().getAddOns())
                addItem(addOn);
            for (MenuItem sauce : comboMeal.getBurger().getSauces())
                addItem(sauce);
        }

        totalPrice += item.getPrice();
        items.put(item, quantity);
    }

    public void removeItem(MenuItem item) {
        Map<MenuItem, Integer> items = getItems();
        if(items.containsKey(item))
            items.remove(item);
    }

    public void changeItemQuantity(MenuItem item, int quantity) {
        Map<MenuItem, Integer> items = getItems();
        if(!items.containsKey(item))
            return;
        items.put(item, quantity);
    }

    public float calculatePrice(){
        float retVal = 0;
        Map<MenuItem, Integer> items = getItems();

        for (Map.Entry<MenuItem,Integer> entry: items.entrySet()) {
            retVal += (entry.getKey().getPrice() * entry.getValue());
        }

        return retVal;
    }

    public String changeStatus(Status status) {
        if(status.equals(Status.Received)){
            if(!paid)
                return "!! Order is not paid !!";
            else {
                this.status = status;
                return "ok";
            }
        }

        if(status.equals(Status.Done) && !paid){
            this.status = status;
            return "!! Order is not paid !!";
        }

        this.status = status;
        return "ok";



    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return orderNumber == order.orderNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderNumber);
    }
}
