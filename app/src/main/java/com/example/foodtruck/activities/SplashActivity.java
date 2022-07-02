package com.example.foodtruck.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.foodtruck.R;
import com.example.foodtruck.logic.Menu;
import com.example.foodtruck.logic.OrderManager;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        OrderManager.getInstance().readFromDatabase();
        Menu.getInstance().readFromDatabase();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, StationActivity.class));
            }
        }, 4000);
    }
}