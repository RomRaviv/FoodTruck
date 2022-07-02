package com.example.foodtruck.logic;

import androidx.appcompat.view.menu.MenuItemImpl;

import java.util.ArrayList;
import java.util.Objects;

public class MenuItem {
    private String name;
    private String description;
    private String freeText;
    private float price;
    private ArrayList<MenuItem> sauces;
    private ArrayList<MenuItem> addOns;
    private ItemCategory category;
    private String imgURL;


    public MenuItem() { }

    public MenuItem(ItemCategory category, String name, String description, String freeText, float price, ArrayList<MenuItem> sauces, ArrayList<MenuItem> addOns) {
        this.name = name;
        this.description = description;
        this.freeText = freeText;
        this.price = price;
        this.sauces = sauces;
        this.addOns = addOns;
        this.category = category;
    }

    public MenuItem(ItemCategory category, String name, String description, String freeText, float price) {
        this.name = name;
        this.description = description;
        this.freeText = freeText;
        this.price = price;
        this.category = category;
    }

    public MenuItem(MenuItem other){
        this.name = other.getName();
        this.description = other.getDescription();
        this.freeText = other.getFreeText();

        this.price = other.getPrice();
        if(other.getSauces() != null)
            this.sauces = (ArrayList<MenuItem>) other.getSauces().clone();
        if(other.addOns != null)
            this.addOns = (ArrayList<MenuItem>) other.getAddOns().clone() ;
        this.category = other.getCategory();

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFreeText(String freeText) {
        this.freeText = freeText;
    }

    public void setSauces(ArrayList<MenuItem> sauces) {
        this.sauces = sauces;
    }

    public void setAddOns(ArrayList<MenuItem> addOns) {
        this.addOns = addOns;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setCategory(ItemCategory category) {
        this.category = category;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getImgURL() {
        return imgURL;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ItemCategory getCategory() {
        return category;
    }

    public float getPrice() {
        return price;
    }

    public String getFreeText() {
        return freeText;
    }

    public ArrayList<MenuItem> getSauces() {
        if(category.equals(ItemCategory.AddOn) || category.equals(ItemCategory.Sauce))
            return null;
        if(sauces == null)
            sauces = new ArrayList<>();
        return sauces;
    }

    public ArrayList<MenuItem> getAddOns() {
        if(category.equals(ItemCategory.AddOn) || category.equals(ItemCategory.Sauce))
            return null;
        if(addOns == null)
            addOns = new ArrayList<>();
        return addOns;
    }

    public float getTotalPrice(){
        float retVal = price;
        if(sauces!= null)
            for(int i = 1; i < sauces.size() ; i++){
                retVal += sauces.get(i).getPrice();
            }
        if(addOns != null)
            for(int i = 0; i < addOns.size() ; i++){
                retVal += addOns.get(i).getPrice();
            }
        return retVal;
    }

    public void addSauce(MenuItem sauce) {
        this.sauces.add(sauce);
    }

    public void addAddOns(MenuItem addOn) {
        this.addOns.add(addOn);
    }

    public void removeSauce(MenuItem sauce) {
        try {
            this.sauces.remove(sauces.indexOf(sauce));
        }catch (Exception ex){}
    }

    public void removeAddOns(MenuItem addOn) {
        try {
            this.addOns.remove(addOns.indexOf(addOn));
        }catch (Exception ex){}
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MenuItem)) return false;
        MenuItem menuItem = (MenuItem) o;
        return price == menuItem.price && name.equals(menuItem.name) && description.equals(menuItem.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, price);
    }

    @Override
    public String toString() {
        String retVal = "";

        StringBuilder st = new StringBuilder();
        st.append("~" + this.name);
        if(addOns != null) {
            for (MenuItem addOn : addOns)
                st.append("\n\t\t~" + addOn.getName());
        }

        if(sauces != null) {
            for (MenuItem sauce : sauces)
                st.append("\n\t\t~" + sauce.getName());
        }
        if(!freeText.isEmpty())
            st.append("\n\t\t~" + this.freeText);
        retVal = st.toString();

        return retVal;
    }

}
