package com.example.aerosafe;

import static java.lang.String.valueOf;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.Animator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aerosafe.data.Airport;
import com.example.aerosafe.data.Metar;
import com.example.aerosafe.data.Taf;

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
    //position dans la liste
    int position = 0;

    ArrayList<Airport> saveList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.O)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);


        recyclerView = findViewById(R.id.recycler_view_airportList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new AirportListAdapter(null, this));
        Button btnClear = findViewById(R.id.btnClear);
        Button btnAdd = findViewById(R.id.btnAdd);
        Button btnGetInfo = findViewById(R.id.btn_get_information);
        TextView search = findViewById(R.id.search);
        ImageButton btnMap = findViewById(R.id.btnMap);
        Intent intent = new Intent(this, map.class);
        Intent intentInfo = new Intent(this, Informations.class);


        ArrayList<Airport> bonneListe = new ArrayList<Airport>();
        AirportListAdapter mAdapter;


        Intent ReIntent = getIntent(); // R??cup??re le tableau d'aeroports
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


            try { // Parse JSON
                JSONObject object = new JSONObject(readJSON());
                JSONArray array = object.getJSONArray("data");
                for (int i = 0; i < array.length(); i++) {

                    JSONObject jsonObject = array.getJSONObject(i);
                    String icao = jsonObject.getString("icao");
                    String name = jsonObject.getString("name");
                    String country = jsonObject.getString("country");
                    double lat = jsonObject.getDouble("lat");
                    double lon = jsonObject.getDouble("lon");
                    if (icao.equals("")) {
                    } else {
                        Airport airport = new Airport(icao, name, country, lat, lon);
                        airports.add(airport);
                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            btnClear.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if (saveList.size() > 0) {
                        /*
                        new AlertDialog.Builder(view.getContext())
                                .setTitle(getText(R.string.delete_confirm))
                                .setMessage(getText(R.string.delete_empty))
                                .setPositiveButton(getText(R.string.delete_yes), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        mAdapter.clearList(); //effacer la liste d'aeroports recherch??s
                                    }
                                })
                                .setNegativeButton(getText(R.string.delete_no), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // do nothing
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();

                         */

                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        ViewGroup viewGroup = findViewById(android.R.id.content);
                        View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.dialogue_view, viewGroup, false);
                        Button buttonYes =dialogView.findViewById(R.id.buttonYes);
                        Button buttonNo =dialogView.findViewById(R.id.buttonNo);
                        builder.setView(dialogView);
                        final AlertDialog alertDialog = builder.create();
                        buttonYes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mAdapter.clearList(); //effacer la liste d'aeroports recherch??s
                                alertDialog.dismiss();
                            }
                        });
                        buttonNo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alertDialog.dismiss();
                            }
                        });
                        alertDialog.show();

                    }


                }
            });

            btnMap.setOnClickListener(new View.OnClickListener() { // envoi du tableau d'aeroports
                public void onClick(View view) {
                    Bundle args = new Bundle();
                    args.putSerializable("ARRAYLIST",(Serializable)saveList);
                    intent.putExtra("BUNDLE",args);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_out,android.R.anim.fade_in);

                }
            });

            btnAdd.setOnClickListener(new View.OnClickListener() { // ajout
                public void onClick(View view) {
                    boolean find = false;
                    if (!search.getText().toString().equals("")) {
                        for (Airport current : airports) {
                            if (search.getText().toString().toUpperCase().equals(current.icao)) {
                                Log.d(TAG, "onClick: trouve !");
                                Airport newSave = new Airport(current.icao, current.name, current.country, current.lat, current.lon);
                                existOrNot(newSave);
                                mAdapter.addToList(newSave);
                                find = true;

                              }
                        }
                        if (find == false){
                            Toast toast = Toast.makeText(getApplicationContext(), getText(R.string.unkonw_airport), Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                }

            });

            btnGetInfo.setOnClickListener(new View.OnClickListener() { // envoi du tableau d'aeroports
                public void onClick(View view) {
                    if (saveList.size()==0){
                        Toast toast = Toast.makeText(getApplicationContext(), getText(R.string.Add_one), Toast.LENGTH_SHORT);
                        toast.show();
                    }else {
                        Bundle args = new Bundle();
                        args.putSerializable("ARRAYLIST", (Serializable) saveList);
                        intentInfo.putExtra("BUNDLE", args);
                        intentInfo.putExtra("position", position);
                        startActivity(intentInfo);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
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

    public void existOrNot(Airport airport) {
        TextView search = findViewById(R.id.search);
        int i;
        boolean exist = false;
        for (i = 0; i < saveList.size(); i++) {
            if (saveList.get(i).icao.equals(airport.icao)) {
                exist = true;
            }
        }

        if (exist == false) {
            saveList.add(airport);
            search.setText("");
        }
    }


}