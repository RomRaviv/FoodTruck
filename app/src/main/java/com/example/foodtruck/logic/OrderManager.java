package com.example.foodtruck.logic;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderManager {
    private Gson gson = new Gson();
    private static OrderManager instance;
    private Map<String ,String> activeOrders;
    private int activeOrdersNum;
    private int currentOrderNum;
    private Map<String , String> history;
    private Order ongoingOrder;
    private FirebaseDatabase FT_DB;
    private DatabaseReference myRef;

    private OrderManager() {
    }

    public static  OrderManager getInstance(){
        if (instance == null) {
            instance = new OrderManager()
                    .setOrders(new HashMap<>())
                    .setHistory(new HashMap<>())
                    .setCurrentOrderNum(1)
                    .setActiveOrdersNum(0)
                    .setDB()
                    .setMyRef();
            return instance;
        }

        return instance;
    }


    public int getCurrentOrderNum() {
        return currentOrderNum;
    }

    private OrderManager setCurrentOrderNum(int currentOrderNum) {
        this.currentOrderNum = currentOrderNum;
        return this;
    }

    public Map<String, Order> getHistory() {
        Map<String, Order> retVal = new HashMap<>();
        for(Map.Entry<String , String> entry : history.entrySet()){
            Order order = gson.fromJson(entry.getValue(), Order.class);
            retVal.put(entry.getKey(), order);
        }
        return retVal;
    }

    public Map<String, Order> getActiveOrders() {
        Map<String, Order> retVal = new HashMap<>();
        for(Map.Entry<String , String> entry : activeOrders.entrySet()){
            Order order = gson.fromJson(entry.getValue(), Order.class);
            retVal.put(entry.getKey(), order);
        }
        return retVal;
    }

    public int getActiveOrdersNum() {
        return activeOrdersNum;
    }

    private OrderManager setHistory(Map<String, Order> history) {
            this.history = new HashMap<>();
        return this;
    }
    private OrderManager setOrders(Map<String, Order> orders) {
        this.activeOrders = new HashMap<>();
        return this;
    }

    private OrderManager setDB() {
        this.FT_DB = FirebaseDatabase.getInstance();
        return this;
    }

    private OrderManager setMyRef(){
        myRef = FT_DB.getReference(this.getClass().getSimpleName());
        return this;
    }

    private OrderManager setActiveOrdersNum(int num) {
        this.activeOrdersNum = num;
        return this;
    }

    public Order getOngoingOrder() {
        return ongoingOrder;
    }

    private void setOngoingOrder(Order ongoingOrder) {
        this.ongoingOrder = ongoingOrder;
    }

    public void increaseCurrentOrderNum(){
        currentOrderNum++;
    }
    public void increaseActiveOrderNum() {
        activeOrdersNum++;
    }
    public void decreaseActiveOrderNum() {
        activeOrdersNum--;
    }

    public boolean addOrderToCurrent(Order order){
        order.setOrderNumber(getCurrentOrderNum());
        increaseCurrentOrderNum();
        increaseActiveOrderNum();
        if(activeOrders.keySet().contains(order.getOrderNumber()))
            return false;
        activeOrders.put(""+ order.getOrderNumber(),gson.toJson(order));
        setOngoingOrder(order);
        return true;
    }



    public Order findOrderByNum(String orderNum){
        try {
            if (history.keySet().contains(orderNum))
                return gson.fromJson(history.get(orderNum), Order.class);

            if (activeOrders.keySet().contains(orderNum))
                return gson.fromJson(activeOrders.get(orderNum), Order.class);
        }catch (Exception ex){}
        return null;
    }

    public boolean addOrderToHistory(Order order){
        if(history.keySet().contains(order.getOrderNumber()))
            return false;
        history.put("" +order.getOrderNumber(),gson.toJson(order));
        return true;
    }

    public void writeToDatabase(){
        if(activeOrders != null)
            myRef.child("activeOrders").setValue(activeOrders);
        if(history != null)
            myRef.child("history").setValue(history);
        myRef.child("activeOrdersNum").setValue(activeOrdersNum);
        myRef.child("currentOrderNum").setValue(currentOrderNum);

    }

    public void readFromDatabase(){
        myRef.child("activeOrdersNum").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue() !=null )
                    activeOrdersNum = snapshot.getValue(Integer.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        myRef.child("currentOrdersNum").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue() !=null )
                    currentOrderNum = snapshot.getValue(Integer.class);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        myRef.child("history").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue() !=null) {
                    history.clear();
                    for (DataSnapshot node : snapshot.getChildren()) {
                        history.put(node.getKey(), node.getValue(String.class));
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        myRef.child("orders").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue() !=null) {
                    activeOrders.clear();
                    for (DataSnapshot node : snapshot.getChildren()) {
                        activeOrders.put(node.getKey(), node.getValue(String.class));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}
