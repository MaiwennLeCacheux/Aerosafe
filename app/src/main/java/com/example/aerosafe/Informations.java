package com.example.aerosafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import java.io.Serializable;
import java.util.ArrayList;

public class Informations extends AppCompatActivity {

    ArrayList<Airport> airports = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informations);

        ImageButton btnHome = findViewById(R.id.btnHome2);
        Intent intent = new Intent(this, MainActivity.class);

        Intent ReIntent = getIntent();

        /*if (ReIntent != null) {
            if (ReIntent.hasExtra("BUNDLE")) {
                Bundle args = ReIntent.getBundleExtra("BUNDLE");
                airports = (ArrayList<Airport>) args.getSerializable("ARRAYLIST");
                Log.d("liste ?", airports.get(0).icao);
            }

        }*/

        btnHome.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putSerializable("ARRAYLIST",(Serializable)airports);
                intent.putExtra("BUNDLE",args);
                startActivity(intent);
            }
        });
    }
}