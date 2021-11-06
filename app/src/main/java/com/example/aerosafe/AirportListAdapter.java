package com.example.aerosafe;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AirportListAdapter extends RecyclerView.Adapter<AirportListAdapter.ViewHolder> {

    private final ArrayList<Airport> listAirports;
    private final Activity parent;
    int pos;

    public AirportListAdapter(ArrayList<Airport> airports, Activity parent) {
        this.parent = parent;
        this.listAirports = airports;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_material_airportlist, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;



    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Airport airport = listAirports.get(position);

        holder.aeroport.setText(airport.icao);
        holder.txtName.setText(airport.name+", " + airport.country);

        holder.btnDel.setOnClickListener(view -> {
            listAirports.remove(airport);
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return (listAirports==null)?0:listAirports.size();
    }

    public void addToList(Airport airport){
        int i;
        boolean exist = false;
        for (i=0; i<listAirports.size();i++){
            if(listAirports.get(i).icao.equals(airport.icao)){
                exist = true;
            }
        }
        Log.d("existe ?", String.valueOf(exist));

        if(exist == false) {
            listAirports.add(airport);
            notifyDataSetChanged();
        }
    }

    public void clearList(){
        listAirports.clear();
        notifyDataSetChanged();
    }

    public ArrayList getList(){
        return listAirports;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView aeroport;
        private final TextView txtName;
        private final ImageButton btnDel;


        public ViewHolder(View view) {
            super(view);

            aeroport = view.findViewById(R.id.aeroport);
            btnDel = (ImageButton) view.findViewById(R.id.btnDel);
            txtName = view.findViewById(R.id.txtName);


        }
    }

    }
