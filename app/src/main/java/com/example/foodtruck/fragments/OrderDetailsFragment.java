package com.example.foodtruck.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodtruck.R;
import com.example.foodtruck.config.LayoutOrderBuilder;
import com.example.foodtruck.logic.Order;


public class OrderDetailsFragment extends Fragment {
    private Order orderToShow;

    public OrderDetailsFragment(Order orderToShow) {
        this.orderToShow = orderToShow;

    }

    @Override
    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);

        LayoutOrderBuilder lob = new LayoutOrderBuilder();
        lob.buildLayoutOrder(view, this.getContext(), orderToShow);

        return view;
    }
}