package com.example.aerosafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class Informations extends AppCompatActivity implements GestureDetector.OnGestureListener {

    ArrayList<Airport> airports = new ArrayList<>();
    ArrayList<String> listTest = new ArrayList<>();
    private static final String TAG = "Swipe Position";
    private float x1, x2, y1, y2;
    private static final int MIN_DISTANCE = 150;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informations);

        ImageButton btnHome = findViewById(R.id.btnHome);
        ImageButton btnMap = findViewById(R.id.btnMap);
        ImageButton btnMapUp = findViewById(R.id.btnMapUp);
        ImageButton btnRight = findViewById(R.id.btnRight);
        ImageButton btnLeft = findViewById(R.id.btnLeft);
        Intent intent = new Intent(this, MainActivity.class);
        Intent intentMap = new Intent(this, map.class);

        listTest.add("Test1");
        listTest.add("Test2");
        listTest.add("Test3");

        //initialize gestureDetector
        this.gestureDetector = new GestureDetector(Informations.this, this);


        Intent ReIntent = getIntent();

        if (ReIntent != null) {
            if (ReIntent.hasExtra("BUNDLE")) {
                Bundle args = ReIntent.getBundleExtra("BUNDLE");
                airports = (ArrayList<Airport>) args.getSerializable("ARRAYLIST");
                Log.d("liste ?", airports.get(0).icao);
            }

        }

        btnHome.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putSerializable("ARRAYLIST",(Serializable)airports);
                intent.putExtra("BUNDLE",args);
                startActivity(intent);
            }
        });

       /* btnMapUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putSerializable("ARRAYLIST",(Serializable)airports);
                intentMapUp.putExtra("BUNDLE",args);
                startActivity(intentMapUp);
            }
        }); */

        btnMap.setOnClickListener(new View.OnClickListener() { // envoi du tableau d'aeroports
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putSerializable("ARRAYLIST",(Serializable)airports);
                intentMap.putExtra("BUNDLE",args);
                startActivity(intentMap);
            }
        });


    }

    //override on touch event
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        Intent intentMapUp = new Intent(this, MapUp.class);
        gestureDetector.onTouchEvent(event);

        switch(event.getAction()){
            //starting to swipe time gesture
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                y1 = event.getY();
                break;

            //ending time swipe gesture
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                y2 = event.getY();

                //getting value for horizontal swipe
                float valueX = x2 - x1;
                //getting value for vertical swipe
                float valueY = y2 - y1;

                if (Math.abs(valueX) > MIN_DISTANCE){
                    //detect left to right swipe
                    if(x2>x1){
                        Toast.makeText(this, "Right is swipe", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Right Swipe");
                    }

                    else{
                        //detect right to left swipe
                        Toast.makeText(this, "Left is swipe", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Left Swipe");
                    }

                }
                else if (Math.abs(valueY) > MIN_DISTANCE){
                    //detect top to bottom swipe
                    if(y2<y1){

                        //detect bottom to top swipe
                        //Toast.makeText(this, "Top is swipe", Toast.LENGTH_SHORT).show();
                        //Log.d(TAG, "Top Swipe");
                        Bundle args = new Bundle();
                        args.putSerializable("ARRAYLIST",(Serializable)airports);
                        intentMapUp.putExtra("BUNDLE",args);
                        startActivity(intentMapUp);
                    }
                }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }
}