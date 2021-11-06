package com.example.aerosafe;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class MainActivity extends AppCompatActivity {

    private static final String ARRAY_AIRPORT = "state_saved";
    private RecyclerView recyclerView;
    private Activity parent = this;
    private static final String TAG = "interrupt";
    String airport;
    String temp;
    String testRep = "Aeroport introuvable";
    String URL;
    ArrayList<Airport> airports = new ArrayList<>();
    ArrayList<Airport> saveList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.O)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view_airportList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new AirportListAdapter(null, this));
        Button btnClear = findViewById(R.id.btnClear);
        Button btnAdd = findViewById(R.id.btnAdd);
        TextView search = findViewById(R.id.search);
        ImageButton btnMap = findViewById(R.id.btnMap);
        Intent intent = new Intent(this, map.class);


        ArrayList<Airport> bonneListe = new ArrayList<Airport>();
        AirportListAdapter mAdapter;


        Intent ReIntent = getIntent();
        if (ReIntent != null) {
            if (ReIntent.hasExtra("BUNDLE")) {
                Bundle args = ReIntent.getBundleExtra("BUNDLE");
                saveList = (ArrayList<Airport>) args.getSerializable("ARRAYLIST");
                mAdapter = new AirportListAdapter(saveList, parent);
            }else {
                mAdapter = new AirportListAdapter(bonneListe, parent);
            }
        }else {
            mAdapter = new AirportListAdapter(bonneListe, parent);
        }


            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    recyclerView.setAdapter(mAdapter);
                }
            });

            //Log.d(TAG, "Erreur ?");

            try {
                JSONObject object = new JSONObject(readJSON());
                JSONArray array = object.getJSONArray("data");
                for (int i = 0; i < array.length(); i++) {

                    JSONObject jsonObject = array.getJSONObject(i);
                    String icao = jsonObject.getString("icao");
                    String name = jsonObject.getString("name");
                    String country = jsonObject.getString("country");
                    if (icao.equals("")) {
                    } else {
                        Airport airport = new Airport(icao, name, country);
                        airports.add(airport);
                        //Log.d(TAG, "onCreate: " + airport.icao);
                    }

                }
                //Log.d(TAG, "onCreate: " + airport);
            } catch (JSONException e) {
                //Log.d(TAG, "onCreate erreur ?");
                e.printStackTrace();
            }


            btnClear.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    mAdapter.clearList();
                }

            });

            btnMap.setOnClickListener(new View.OnClickListener() {

                public void onClick(View view) {
                    Bundle args = new Bundle();
                    args.putSerializable("ARRAYLIST",(Serializable)saveList);
                    intent.putExtra("BUNDLE",args);
                    startActivity(intent);
                }
            });

            btnAdd.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if (!search.getText().toString().equals("")) {
                        for (Airport current : airports) {
                            if (search.getText().toString().toUpperCase().equals(current.icao)) {
                                Log.d(TAG, "onClick: trouve !");
                                Airport newSave = new Airport(current.icao, current.name, current.country);
                                saveList.add(newSave);
                                mAdapter.addToList(current.icao, current.name, current.country);
                                mAdapter.notifyDataSetChanged();
                            }
                        }

                    }
                }

            });
        }


    public String readJSON() {
        String json = null;
        try {
            // Opening data.json file
            InputStream inputStream = getAssets().open("airports.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            // read values in the byte array
            inputStream.read(buffer);
            inputStream.close();
            // convert byte to string
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return json;
        }
        return json;
    }

    private static Document convertStringToXMLDocument(String xmlString)
    {
        //Parser that produces DOM object trees from XML content
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        //API to obtain DOM Document instance
        DocumentBuilder builder = null;
        try
        {
            //Create DocumentBuilder with default configuration
            builder = factory.newDocumentBuilder();

            //Parse the content to Document object
            Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
            return doc;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }




}