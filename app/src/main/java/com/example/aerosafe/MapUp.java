package com.example.aerosafe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.example.aerosafe.data.Airport;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Serializable;
import java.util.ArrayList;

public class MapUp extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap map;
    ArrayList<Airport> airports = new ArrayList<>();
    private static final String TAG = "Swipe Position";
    private float x1, x2, y1, y2;
    private static final int MIN_DISTANCE = 150;

    SwipeListener2 swipeListener2;

    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_up);
        ImageView image = findViewById(R.id.imageView2);

        swipeListener2 = new SwipeListener2(image);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent ReIntent = getIntent();

        if (ReIntent != null) {
            if (ReIntent.hasExtra("BUNDLE")) {
                Bundle args = ReIntent.getBundleExtra("BUNDLE");
                airports = (ArrayList<Airport>) args.getSerializable("ARRAYLIST");
                pos = ReIntent.getIntExtra("position",0);
            }

        }

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;

        LatLng position = new LatLng(airports.get(pos).lat, airports.get(pos).lon);
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID); // Type de map (vue satellite)
        map.addMarker(new MarkerOptions().position(position).title(airports.get(pos).icao).snippet(airports.get(pos).name + "\n" + airports.get(pos).country));
        map.moveCamera(CameraUpdateFactory.newLatLng(position));

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(airports.get(pos).lat, airports.get(pos).lon))      // Sets the center of the map to location user
                .zoom(13)                   // Sets the zoom - 10 = city
                .bearing(0)                // Sets the orientation of the camera to north
                .tilt(0)                   // Sets the tilt of the camera to 0 degree
                .build();                   // Creates a CameraPosition from the builder
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }



    private class SwipeListener2 implements View.OnTouchListener {


        GestureDetector gestureDetector;

        SwipeListener2(View view) {
            Intent intentBack = new Intent(view.getContext(), Informations.class);
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
                                            Bundle args = new Bundle();
                                            args.putSerializable("ARRAYLIST",(Serializable)airports);
                                            intentBack.putExtra("BUNDLE",args);
                                            startActivity(intentBack);
                                        }else {

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