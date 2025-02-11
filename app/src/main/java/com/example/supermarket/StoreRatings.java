package com.example.supermarket;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class StoreRatings extends AppCompatActivity {
    private String name;
    private String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_store_ratings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.storeR), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RatingBar liquorRateBar = findViewById(R.id.liquorRateBar);
        RatingBar produceRateBar = findViewById(R.id.produceRateBar);
        RatingBar meatsRateBar = findViewById(R.id.meatRateBar);
        RatingBar cheeseRateBar = findViewById(R.id.cheeseRateBar);
        RatingBar easeOfCheckOutRateBar = findViewById(R.id.checkOutRateBar);

        TextView tvAvgRating = findViewById(R.id.tvAvgRating);

        RatingBar.OnRatingBarChangeListener ratingBarChangeListener = new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                calcAvgRating(tvAvgRating);
            }
        };
        liquorRateBar.setOnRatingBarChangeListener(ratingBarChangeListener);
        produceRateBar.setOnRatingBarChangeListener(ratingBarChangeListener);
        meatsRateBar.setOnRatingBarChangeListener(ratingBarChangeListener);
        cheeseRateBar.setOnRatingBarChangeListener(ratingBarChangeListener);
        easeOfCheckOutRateBar .setOnRatingBarChangeListener(ratingBarChangeListener);

        calcAvgRating(tvAvgRating);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        address = intent.getStringExtra("address");

        loadPreferences();
        backToMenuButton();

        findViewById(R.id.saveBtn).setOnClickListener(l -> savePreferences());


    }
    private void calcAvgRating(TextView tvAvgRating){
        RatingBar liquorRateBar = findViewById(R.id.liquorRateBar);
        RatingBar produceRateBar = findViewById(R.id.produceRateBar);
        RatingBar meatsRateBar = findViewById(R.id.meatRateBar);
        RatingBar cheeseRateBar = findViewById(R.id.cheeseRateBar);
        RatingBar easeOfCheckOutRateBar = findViewById(R.id.checkOutRateBar);

        double AvgRating = liquorRateBar.getRating()+ produceRateBar.getRating()+
                meatsRateBar.getRating()+ cheeseRateBar.getRating()+
                easeOfCheckOutRateBar.getRating()/5;

        tvAvgRating.setText("Avg rating:"+ String.valueOf(AvgRating));

    }
    public void savePreferences(){
        TextView output = findViewById(R.id.tvAvgRating);
        SharedPreferences prefs = getSharedPreferences(this.name, MODE_PRIVATE);

        prefs.edit().putString("name",name).apply();
        prefs.edit().putString("address",address).apply();

        RatingBar liquorRateBar = findViewById(R.id.liquorRateBar);
        RatingBar produceRateBar = findViewById(R.id.produceRateBar);
        RatingBar meatsRateBar = findViewById(R.id.meatRateBar);
        RatingBar cheeseRateBar = findViewById(R.id.cheeseRateBar);
        RatingBar easeOfCheckOutRateBar = findViewById(R.id.checkOutRateBar);

        float liquorRate = liquorRateBar.getRating();
        float produceRate = produceRateBar.getRating();
        float meatRate = meatsRateBar.getRating();
        float cheeseRate = cheeseRateBar.getRating();
        float easeOfCheckOutRate = easeOfCheckOutRateBar.getRating();

        prefs.edit().putFloat("liquor",liquorRate).apply();
        prefs.edit().putFloat("produce",produceRate).apply();
        prefs.edit().putFloat("meats",meatRate).apply();
        prefs.edit().putFloat("liquor",cheeseRate).apply();
        prefs.edit().putFloat("easeOfCheckOut",easeOfCheckOutRate).apply();

        double avg = (liquorRate+produceRate+meatRate+cheeseRate+easeOfCheckOutRate)/5;
        prefs.edit().putFloat("avg", (float) avg).apply();

        output.setText(String.format(Locale.getDefault(), "%.2f", avg));



    }
    public void loadPreferences(){

        TextView output = findViewById(R.id.tvAvgRating);
        SharedPreferences prefs = getSharedPreferences(this.name, MODE_PRIVATE);

        RatingBar liquorRateBar = findViewById(R.id.liquorRateBar);
        RatingBar produceRateBar = findViewById(R.id.produceRateBar);
        RatingBar meatsRateBar = findViewById(R.id.meatRateBar);
        RatingBar cheeseRateBar = findViewById(R.id.cheeseRateBar);
        RatingBar easeOfCheckOutRateBar = findViewById(R.id.checkOutRateBar);

        liquorRateBar.setRating(prefs.getFloat("liquor",0));
        produceRateBar.setRating(prefs.getFloat("produce", 0));
        meatsRateBar.setRating(prefs.getFloat("meats",0));
        cheeseRateBar.setRating(prefs.getFloat("cheese",0));
        easeOfCheckOutRateBar.setRating(prefs.getFloat("easeOfCheckOut",0));

        float avg = prefs.getFloat("avg", 0);
        output.setText(String.format(Locale.getDefault(), "%.2f", avg));

    }
    private void backToMenuButton(){
        findViewById(R.id.backToMenuBtn).setOnClickListener(
                l -> {
                    Intent intent = new Intent(StoreRatings.this, MainActivity.class);
                    startActivity(intent);
                }
        );
    }

}