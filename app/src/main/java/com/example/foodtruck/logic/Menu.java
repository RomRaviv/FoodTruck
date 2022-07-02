package com.example.foodtruck.logic;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class Menu {
    private static Menu instance = null;
    private Map<String, ArrayList<MenuItem>> categories;
    private FirebaseDatabase FT_DB;
    private DatabaseReference myRef;

    private Menu() {
    }

    public static Menu getInstance(){
        if(instance == null){
            instance =  new Menu().setCategories().setDB().setMyRef();
            return instance;
        }
        return instance;
    }

    public Map<String, ArrayList<MenuItem>> getCategories() {
        return categories;
    }

    public Menu setCategories() {
        this.categories = new HashMap<>();
        return this;
    }

    public Menu addCategory(ItemCategory category, ArrayList<MenuItem> items){
        this.categories.put(category.name(), items);
        return this;
    }

    public ArrayList<MenuItem> getCategoryItems(ItemCategory itemCategory){
        return categories.get(itemCategory.toString());
//        for(Map.Entry<String, ArrayList<MenuItem>> entry : categories.entrySet()) {
//            if (entry.getKey().equals(itemCategory.name()))
//                return entry.getValue();
//        }
//        return null;
    }

    public void addItemToMenu(MenuItem item){
        ArrayList<MenuItem> carItems = categories.get(item.getCategory().name());
        if(!carItems.contains(item))
            categories.get(item.getCategory().name()).add(item);
    }

    public boolean removeItemFromMenu(ItemCategory itemCategory,MenuItem item){
        for(Map.Entry<String, ArrayList<MenuItem>> entry : categories.entrySet()) {
            if (entry.getKey().equals(itemCategory.name())){
                return entry.getValue().remove(item);
            }
        }
        return false;
    }

    private Menu setDB() {
        this.FT_DB = FirebaseDatabase.getInstance();
        return this;
    }

    private Menu setMyRef(){
        myRef = FT_DB.getReference(this.getClass().getSimpleName());
        return this;
    }

    public ArrayList<String> getAllCatagoriesNames(){
        ArrayList<String> retVal = new ArrayList<>();
        retVal.addAll(categories.keySet());
        return retVal;
    }

    public String[] getAddOnsNames(){

        ArrayList<MenuItem> addOns =getCategoryItems(ItemCategory.AddOn);
        ArrayList<String> addOnNames = new ArrayList<>();
        addOnNames.add("None");
        if(addOns == null){
            String[] ret = new String[addOnNames.size()];
            ret = addOnNames.toArray(ret);
            return ret;
        }
        for (MenuItem item : addOns) {
            addOnNames.add(item.getName());
        }
        String[] ret = new String[addOnNames.size()];
        ret = addOnNames.toArray(ret);
        return ret;
    }
    public String[] getSauceNames(){
        ArrayList<MenuItem> addOns =getCategoryItems(ItemCategory.Sauce);
        ArrayList<String> sauceNames = new ArrayList<>();
        sauceNames.add("None");
        if(addOns == null){
            String[] ret = new String[sauceNames.size()];
            ret = sauceNames.toArray(ret);
            return ret;
        }
        for (MenuItem item : addOns) {
            sauceNames.add(item.getName());
        }
        String[] ret = new String[sauceNames.size()];
        ret = sauceNames.toArray(ret);
        return ret;
    }

    public MenuItem getItemByName(String name){
        for (ArrayList<MenuItem> list : categories.values()) {
            for (MenuItem item : list){
                if(item.getName().equals(name))
                    return item;
            }
        }
        return null;
    }

    public void writeToDatcbase(){

        myRef.setValue(categories);
    }

    public void readFromDatabase(){
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                categories.clear();
                GenericTypeIndicator<ArrayList<MenuItem>> typeIndicator = new GenericTypeIndicator<ArrayList<MenuItem>>() {};
                for(DataSnapshot node : dataSnapshot.getChildren()){
                    categories.put(node.getKey(), node.getValue(typeIndicator));
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Code
            }
        });
    }
}
