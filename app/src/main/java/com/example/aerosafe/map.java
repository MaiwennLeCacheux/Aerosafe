package com.example.aerosafe;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.aerosafe.data.Airport;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.Serializable;
import java.util.ArrayList;

public class map extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener , GoogleMap.OnMapClickListener{

    GoogleMap map; // map
    ArrayList<Airport> airports = new ArrayList<>();

    // Flag for GPS status
    boolean isGPSEnabled = false;

    // Flag for network status
    boolean isNetworkEnabled = false;

    // Flag for GPS status
    boolean canGetLocation = false;

    Location location; // Location
    double latitude; // Latitude
    double longitude; // Longitude

    private static final int COLOR_RED_ARGB = R.color.white ;


    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

    // Declaring a Location Manager
    protected LocationManager locationManager;

    boolean next = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        ImageButton btnHome = findViewById(R.id.btnHome2);
        ImageButton btnMap = findViewById(R.id.btnMap2);
        Intent intent = new Intent(this, MainActivity.class);
        Intent ReIntent = getIntent();

        if (ReIntent != null) { // récupération du tableau d'aeroport
            if (ReIntent.hasExtra("BUNDLE")) {
                Bundle args = ReIntent.getBundleExtra("BUNDLE");
                airports = (ArrayList<Airport>) args.getSerializable("ARRAYLIST");
            }

        }

        getLocation();

        if(location != null){
            latitude = location.getLatitude();
        }
        if(location != null){
            longitude = location.getLongitude();
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager() // initialise la map
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        btnHome.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putSerializable("ARRAYLIST", (Serializable) airports);
                intent.putExtra("BUNDLE", args);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_out,android.R.anim.fade_in);

            }
        });

        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                        .zoom(5)                   // Sets the zoom - 10 = city
                        .bearing(0)                // Sets the orientation of the camera to north
                        .tilt(0)                   // Sets the tilt of the camera to 0 degree
                        .build();                   // Creates a CameraPosition from the builder
                map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });




    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) { //Mise en place de la carte
        map = googleMap;
        map.setOnMapClickListener(this);
        String txtPos = getString(R.string.user_position);

        LatLng posUser = new LatLng(latitude, longitude); // Position du marker
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID); // Type de map (vue satellite)
        Marker marker = map.addMarker(new MarkerOptions().position(posUser).title(txtPos)); // Ajout du marker
        marker.setTag(0);
        map.moveCamera(CameraUpdateFactory.newLatLng(posUser)); // Centre la caméra sur le marker

        if(airports.size()>1) {
            for (int i = 1; i < airports.size(); i++) {
                Polyline polyline = map.addPolyline(new PolylineOptions()
                        .clickable(true)
                        .color(Color.rgb(203, 67, 53))
                        .add(
                                new LatLng(airports.get(i - 1).lat, airports.get(i - 1).lon),
                                new LatLng(airports.get(i).lat, airports.get(i).lon)));


                polyline.setJointType(JointType.ROUND);
            }

            for (int i = 0; i < airports.size(); i++) {
                map.addMarker(new MarkerOptions().position(new LatLng(airports.get(i).lat, airports.get(i).lon)).title(airports.get(i).icao).snippet(airports.get(i).name));
                Log.d("error", String.valueOf(airports.get(i).lat));
            }

        }

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                .zoom(5)                   // Sets the zoom - 10 = city
                .bearing(0)                // Sets the orientation of the camera to north
                .tilt(0)                   // Sets the tilt of the camera to 0 degree
                .build();                   // Creates a CameraPosition from the builder
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


    }


    @Override
    public void onMapClick(LatLng latLng) {

    }


    @Override
    public boolean onMarkerClick(final Marker marker) {

        // Retrieve the data from the marker.
        Integer clickCount = (Integer) marker.getTag();

        // Check if a click count was set, then display the click count.
        if (clickCount != null) {
            clickCount = clickCount + 1;
            marker.setTag(clickCount);
            Toast.makeText(this,
                    marker.getTitle() +
                            " has been clicked " + clickCount + " times.",
                    Toast.LENGTH_SHORT).show();
        }

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }


    public Location getLocation() { // Get the location of the user
        try {
            locationManager = (LocationManager) this
                    .getSystemService(LOCATION_SERVICE);

            // Getting GPS status
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // Getting network status
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // No network provider is enabled
            } else {
                this.canGetLocation = true;
                if (isNetworkEnabled) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES,
                                (android.location.LocationListener) this);
                    }

                    Log.d("Network", "Network");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
                // If GPS enabled, get latitude/longitude using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, (android.location.LocationListener) this);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return location;
    }


}