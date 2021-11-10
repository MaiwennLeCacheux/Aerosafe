package com.example.aerosafe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aerosafe.data.Airport;

import java.io.Serializable;
import java.util.ArrayList;

public class Informations extends AppCompatActivity {

    ArrayList<Airport> airports = new ArrayList<>();
        private Activity parent = this;

    SwipeListener swipeListener;



    //view pager
    private ViewPager mSlideViewPager;
    private ConstraintLayout mDotLayout;
    private SliderAdapter sliderAdapter;

    // swipe values
    private static final String TAG = "Swipe Position";
    private float x1, x2, y1, y2;
    private static final int MIN_DISTANCE = 150;
    private GestureDetector gestureDetector;

    private int selector = 0;

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

        mSlideViewPager = (ViewPager)findViewById(R.id.slideViewPager);
        mDotLayout = (ConstraintLayout)findViewById(R.id.dotsLayout);

        swipeListener = new SwipeListener(btnMapUp);

        Intent ReIntent = getIntent();

        if (ReIntent != null) {
            if (ReIntent.hasExtra("BUNDLE")) {
                Bundle args = ReIntent.getBundleExtra("BUNDLE");
                airports = (ArrayList<Airport>) args.getSerializable("ARRAYLIST");
                //int nouvellePosition = intent.getIntExtra("position", 0);
               // Log.d("position ", String.valueOf(nouvellePosition));

                /*affichage dynamique du titre
                String message = listTest.get(nouvellePosition);
                TextView titre = (TextView) findViewById(R.id.title);
                titre.setText(message);*/
            }

        }

        sliderAdapter = new SliderAdapter(this, parent, airports);
        mSlideViewPager.setAdapter(sliderAdapter);


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
        /*
        btnRight.setOnClickListener(new View.OnClickListener() { // envoi du tableau d'aeroports
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putSerializable("ARRAYLIST",(Serializable)airports);
                intentNext.putExtra("BUNDLE",args);
                //nouvellePosition = nouvellePosition +1;
              //  Log.d("Nouvelle position ", String.valueOf(nouvellePosition));
               // intentNext.putExtra("position",nouvellePosition);
                startActivity(intentNext);
            }
        });

        btnLeft.setOnClickListener(new View.OnClickListener() { // envoi du tableau d'aeroports
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putSerializable("ARRAYLIST",(Serializable)airports);
                intentPrevious.putExtra("BUNDLE",args);
                //nouvellePosition = nouvellePosition -1;
            //    Log.d("Nouvelle position ", String.valueOf(nouvellePosition));
               // intentPrevious.putExtra("position",nouvellePosition);
                startActivity(intentPrevious);
            }
        });
        */

    }

    //override on touch event
    /*@Override
    public boolean onTouchEvent(MotionEvent event) {

        Intent intentMapUp = new Intent(this, MapUp.class);
        Intent intentNext = new Intent(Informations.this, Informations.class);
        Intent intentPrevious = new Intent(Informations.this, Informations.class);



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

                if (Math.abs(valueY) > MIN_DISTANCE){
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
*/
    private class SwipeListener implements View.OnTouchListener {
        Intent intentMapUp = new Intent(Informations.this, MapUp.class);
        GestureDetector gestureDetector;

        SwipeListener(View view) {
            int threshold = 100;
            int velocity_threshold = 100;

            GestureDetector.SimpleOnGestureListener listener =
                    new GestureDetector.SimpleOnGestureListener(){
                        @Override
                        public boolean onDown(MotionEvent e) {
                            return true;
                        }

                        @Override
                        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                            float xDiff = e2.getX() - e1.getX();
                            float yDiff = e2.getY() - e1.getY();
                            try {
                                if (Math.abs(xDiff) > Math.abs(yDiff)){
                                    if (Math.abs(xDiff)>threshold && Math.abs(velocityX)>velocity_threshold){
                                        if(xDiff>0){
                                        }else {
                                        }
                                    }
                                }else {
                                    if (Math.abs(yDiff)>threshold && Math.abs(velocityY)>velocity_threshold){
                                        if(yDiff>0){

                                        }else {
                                            Bundle args = new Bundle();
                                            args.putSerializable("ARRAYLIST",(Serializable)airports);
                                            intentMapUp.putExtra("BUNDLE",args);
                                            intentMapUp.putExtra("position", mSlideViewPager.getCurrentItem());
                                            startActivity(intentMapUp);
                                        }
                                    }
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            return true;
                        }
                    };

            gestureDetector = new GestureDetector(listener);

            view.setOnTouchListener(this);
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return gestureDetector.onTouchEvent(event);
        }
    }
}