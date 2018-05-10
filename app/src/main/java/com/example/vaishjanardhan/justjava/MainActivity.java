package com.example.vaishjanardhan.justjava;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    private Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+5:30"));
    private RelativeLayout mOpenLayout;
    private RelativeLayout mClosedLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mOpenLayout = findViewById(R.id.open_layout);
        mClosedLayout = findViewById(R.id.closed_layout);

        //Finding the current time:
        //accepting orders only from 8 am to 10 pm
        int currentHour = cal.get(Calendar.HOUR_OF_DAY);
        Log.v("MainActivity", "Current Hour: " + currentHour);
        if (currentHour >= 8 && currentHour <= 22) {
            showOpened();
        }

    }


    /**
     * makes the welcome screen visible
     * if time is between 8 am and 10 pm
     */
    public void showOpened() {
        mOpenLayout.setVisibility(View.VISIBLE);
        mClosedLayout.setVisibility(View.GONE);
    }


    public void openOrderActivity(View view) {
        Intent intent = new Intent(MainActivity.this, OrderActivity.class);
        startActivity(intent);
    }
}