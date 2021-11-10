package com.example.aerosafe;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.viewpager.widget.PagerAdapter;

import com.example.aerosafe.data.Airport;
import com.example.aerosafe.data.Metar;
import com.example.aerosafe.data.Taf;
import com.victor.loading.rotate.RotateLoading;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;
    private ArrayList<Airport> listAirports;
    private final Activity parent;
    //Arrays
    public ArrayList<String> slide_titles;
    public ArrayList<String> location;
    public ArrayList<Metar> metarList;
    public ArrayList<Taf> tafList;





    public SliderAdapter(Context context, Activity parent, ArrayList<Airport> airports){
        this.parent = parent;
        this.context = context;
        this.listAirports = airports;

        slide_titles = new ArrayList<String>();
        location = new ArrayList<String>();
        metarList = new ArrayList<Metar>();
        tafList = new ArrayList<Taf>();

        for(int i =0;i<listAirports.size();i++){
            slide_titles.add(listAirports.get(i).icao);
            location.add(listAirports.get(i).name + "\n" + listAirports.get(i).country);
            Metar metar = new Metar(listAirports.get(i).icao);
            Taf taf = new Taf(listAirports.get(i).icao);

            try {
                Thread.sleep(500);
                metarList.add(metar);
                tafList.add(taf);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }





    @Override
    public int getCount() {
        return slide_titles.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (ConstraintLayout) object;
    }

    public Object instantiateItem(ViewGroup container, int position){
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);


        TextView slideTitle = (TextView) view.findViewById(R.id.title);
        TextView slideLocation = (TextView) view.findViewById(R.id.location);
        TextView slideTitleMetar = (TextView) view.findViewById(R.id.titre_metar);
        TextView metarData = (TextView) view.findViewById(R.id.metar_data);
        TextView titreTaf1 = (TextView) view.findViewById(R.id.titre_taf1);
        TextView tafData1 = (TextView) view.findViewById(R.id.taf_data1);
        TextView titreTaf2 = (TextView) view.findViewById(R.id.titre_taf2);
        TextView tafData2 = (TextView) view.findViewById(R.id.taf_data2);
        TextView titreTaf3 = (TextView) view.findViewById(R.id.titre_taf3);
        TextView tafData3 = (TextView) view.findViewById(R.id.taf_data3);
        TextView titreTaf4 = (TextView) view.findViewById(R.id.titre_taf4);
        TextView tafData4 = (TextView) view.findViewById(R.id.taf_data4);
        TextView titreTaf5 = (TextView) view.findViewById(R.id.titre_taf5);
        TextView tafData5 = (TextView) view.findViewById(R.id.taf_data5);

        String data = "";
        String dataTaf1 = "";
        String dataTaf2 = "";
        String dataTaf3 = "";
        String dataTaf4 = "";
        String dataTaf5 = "";
        String titleTaf1 = "";
        String titleTaf2 = "";
        String titleTaf3 = "";
        String titleTaf4 = "";
        String titleTaf5 = "";
        String meters;

        data = this.context.getText(R.string.info_flight_category) + " " + metarList.get(position).flight_category + "\n";

        if(metarList.get(position).sky_condition.size() > 0) {
            for(int i = 0;i<metarList.get(position).sky_condition.size();i++){
                meters = "";
                if(metarList.get(position).sky_condition.get(i).cloud_base_ft_agl != 0)
                    meters = " " + this.context.getText(R.string.at) + " " + Math.ceil(metarList.get(position).sky_condition.get(i).cloud_base_ft_agl * 0.3048) + " meters";
                data = data + this.context.getText(R.string.info_clouds) + " " + metarList.get(position).sky_condition.get(i).sky_cover + meters +  "\n";
            }
        }

            data = data + this.context.getText(R.string.info_visibility) + " " + Math.ceil(metarList.get(position).visibility_statute_mi * 1.60934)+ " km\n";
            data = data + this.context.getText(R.string.info_wind) + " " + metarList.get(position).wind_dir_degrees + "° " + this.context.getText(R.string.at) + " " + metarList.get(position).wind_speed_kt  + " kt\n";
            data = data + this.context.getText(R.string.info_temperature) + " " + metarList.get(position).temp_c + " °C\n";
            data = data + this.context.getText(R.string.info_dew_point) + " " + metarList.get(position).dewpoint_c + " °C\n";
            data = data + this.context.getText(R.string.info_pressure) + " " + Math.ceil(metarList.get(position).altim_in_hg * 33.864) + " millibars\n";

        titreTaf1.setVisibility(view.GONE);
        tafData1.setVisibility(view.GONE);
        titreTaf2.setVisibility(view.GONE);
        tafData2.setVisibility(view.GONE);
        titreTaf3.setVisibility(view.GONE);
        tafData3.setVisibility(view.GONE);
        titreTaf4.setVisibility(view.GONE);
        tafData4.setVisibility(view.GONE);
        titreTaf5.setVisibility(view.GONE);
        tafData5.setVisibility(view.GONE);

        if(tafList.get(position).forecast.size()>0) {
            for (int i = 0; i < tafList.get(position).forecast.size(); i++) {
                meters = "";
                if (i == 0) {
                    titreTaf1.setVisibility(view.VISIBLE);
                    tafData1.setVisibility(view.VISIBLE);

                    titleTaf1 = context.getString(R.string.info_from) + " " + tafList.get(position).forecast.get(i).fcst_time_from + "\n" + this.context.getString(R.string.info_to) + " " + tafList.get(position).forecast.get(i).fcst_time_to;


                    if (tafList.get(position).forecast.get(i).sky_condition.size() > 0) {
                        for (int j = 0; j < tafList.get(position).forecast.get(i).sky_condition.size(); j++) {
                            if (tafList.get(position).forecast.get(i).sky_condition.get(j).cloud_base_ft_agl != 0) {
                                meters = " " + this.context.getText(R.string.at) + " " + Math.ceil(tafList.get(position).forecast.get(i).sky_condition.get(j).cloud_base_ft_agl * 0.3048) + " meters";
                            }
                            dataTaf1 = dataTaf1 + this.context.getText(R.string.info_clouds) + " " + tafList.get(position).forecast.get(i).sky_condition.get(j).sky_cover + meters + "\n";
                        }
                    }

                    if (tafList.get(position).forecast.get(i).visibility_statute_mi != 0) {
                        dataTaf1 = dataTaf1 + this.context.getText(R.string.info_visibility) + " " + Math.ceil(tafList.get(position).forecast.get(i).visibility_statute_mi* 1.60934) + " km\n";
                    }
                    if (tafList.get(position).forecast.get(i).wind_speed_kt != 0) {
                        dataTaf1 = dataTaf1 + this.context.getText(R.string.info_wind) + " " + tafList.get(position).forecast.get(i).wind_dir_degrees + "° " + this.context.getText(R.string.at) + " " + tafList.get(position).forecast.get(i).wind_speed_kt + " kt\n";
                    }
                    if (tafList.get(position).forecast.get(i).probability != 0) {
                        dataTaf1 = dataTaf1 + this.context.getText(R.string.info_from_proba) + " " + tafList.get(position).forecast.get(i).probability + " %\n";
                    }

                }

                if (i == 1) {
                    titreTaf2.setVisibility(view.VISIBLE);
                    tafData2.setVisibility(view.VISIBLE);

                    titleTaf2 = context.getString(R.string.info_from) + " " + tafList.get(position).forecast.get(i).fcst_time_from + "\n" + this.context.getString(R.string.info_to) + " " + tafList.get(position).forecast.get(i).fcst_time_to;

                    if (tafList.get(position).forecast.get(i).sky_condition.size() > 0) {
                        for (int j = 0; j < tafList.get(position).forecast.get(i).sky_condition.size(); j++) {
                            if (tafList.get(position).forecast.get(i).sky_condition.get(j).cloud_base_ft_agl != 0) {
                                meters = " " + this.context.getText(R.string.at) + " " + Math.ceil(tafList.get(position).forecast.get(i).sky_condition.get(j).cloud_base_ft_agl * 0.3048) + " meters";
                            }
                            dataTaf2 = dataTaf2 + this.context.getText(R.string.info_clouds) + " " + tafList.get(position).forecast.get(i).sky_condition.get(j).sky_cover + meters + "\n";
                        }
                    }
                    if (tafList.get(position).forecast.get(i).visibility_statute_mi != 0) {
                        dataTaf2 = dataTaf2 + this.context.getText(R.string.info_visibility) + " " + tafList.get(position).forecast.get(i).visibility_statute_mi + " miles\n";
                    }
                    if (tafList.get(position).forecast.get(i).wind_speed_kt != 0) {
                        dataTaf2 = dataTaf2 + this.context.getText(R.string.info_wind) + " " + tafList.get(position).forecast.get(i).wind_dir_degrees + "° " + this.context.getText(R.string.at) + " " + tafList.get(position).forecast.get(i).wind_speed_kt + " kt\n";
                    }
                    if (tafList.get(position).forecast.get(i).probability != 0) {
                        dataTaf2 = dataTaf2 + this.context.getText(R.string.info_from_proba) + " " + tafList.get(position).forecast.get(i).probability + " %\n";
                    }

                }
                if (i == 2) {
                    titreTaf3.setVisibility(view.VISIBLE);
                    tafData3.setVisibility(view.VISIBLE);

                    titleTaf3 = context.getString(R.string.info_from) + " " + tafList.get(position).forecast.get(i).fcst_time_from + "\n" + this.context.getString(R.string.info_to) + " " + tafList.get(position).forecast.get(i).fcst_time_to;

                    if (tafList.get(position).forecast.get(i).sky_condition.size() > 0) {
                        for (int j = 0; j < tafList.get(position).forecast.get(i).sky_condition.size(); j++) {
                            if (tafList.get(position).forecast.get(i).sky_condition.get(j).cloud_base_ft_agl != 0) {
                                meters = " " + this.context.getText(R.string.at) + " " + Math.ceil(tafList.get(position).forecast.get(i).sky_condition.get(j).cloud_base_ft_agl * 0.3048) + " meters";
                            }
                            dataTaf3 = dataTaf3 + this.context.getText(R.string.info_clouds) + " " + tafList.get(position).forecast.get(i).sky_condition.get(j).sky_cover + meters + "\n";
                        }
                    }
                    if (tafList.get(position).forecast.get(i).visibility_statute_mi != 0) {
                        dataTaf3 = dataTaf3 + this.context.getText(R.string.info_visibility) + " " + tafList.get(position).forecast.get(i).visibility_statute_mi + " miles\n";
                    }
                    if (tafList.get(position).forecast.get(i).wind_speed_kt != 0) {
                        dataTaf3 = dataTaf3 + this.context.getText(R.string.info_wind) + " " + tafList.get(position).forecast.get(i).wind_dir_degrees + "° " + this.context.getText(R.string.at) + " " + tafList.get(position).forecast.get(i).wind_speed_kt + " kt\n";
                    }
                    if (tafList.get(position).forecast.get(i).probability != 0) {
                        dataTaf3 = dataTaf3 + this.context.getText(R.string.info_from_proba) + " " + tafList.get(position).forecast.get(i).probability + " %\n";
                    }

                }
                if (i == 3) {
                    titreTaf4.setVisibility(view.VISIBLE);
                    tafData4.setVisibility(view.VISIBLE);

                    titleTaf4 = context.getString(R.string.info_from) + " " + tafList.get(position).forecast.get(i).fcst_time_from + "\n" + this.context.getString(R.string.info_to) + " " + tafList.get(position).forecast.get(i).fcst_time_to;

                    if (tafList.get(position).forecast.get(i).sky_condition.size() > 0) {
                        for (int j = 0; j < tafList.get(position).forecast.get(i).sky_condition.size(); j++) {
                            if (tafList.get(position).forecast.get(i).sky_condition.get(j).cloud_base_ft_agl != 0) {
                                meters = " " + this.context.getText(R.string.at) + " " + Math.ceil(tafList.get(position).forecast.get(i).sky_condition.get(j).cloud_base_ft_agl * 0.3048) + " meters";
                            }
                            dataTaf4 = dataTaf4 + this.context.getText(R.string.info_clouds) + " " + tafList.get(position).forecast.get(i).sky_condition.get(j).sky_cover + meters + "\n";
                        }
                    }
                    if (tafList.get(position).forecast.get(i).visibility_statute_mi != 0) {
                        dataTaf4 = dataTaf4 + this.context.getText(R.string.info_visibility) + " " + tafList.get(position).forecast.get(i).visibility_statute_mi + " miles\n";
                    }
                    if (tafList.get(position).forecast.get(i).wind_speed_kt != 0) {
                        dataTaf4 = dataTaf4 + this.context.getText(R.string.info_wind) + " " + tafList.get(position).forecast.get(i).wind_dir_degrees + "° " + this.context.getText(R.string.at) + " " + tafList.get(position).forecast.get(i).wind_speed_kt + " kt\n";
                    }
                    if (tafList.get(position).forecast.get(i).probability != 0) {
                        dataTaf4 = dataTaf4 + this.context.getText(R.string.info_from_proba) + " " + tafList.get(position).forecast.get(i).probability + " %\n";
                    }

                }
                if (i == 4) {
                    titreTaf5.setVisibility(view.VISIBLE);
                    tafData5.setVisibility(view.VISIBLE);

                    titleTaf5 = context.getString(R.string.info_from) + " " + tafList.get(position).forecast.get(i).fcst_time_from + "\n" + this.context.getString(R.string.info_to) + " " + tafList.get(position).forecast.get(i).fcst_time_to;

                    if (tafList.get(position).forecast.get(i).sky_condition.size() > 0) {
                        for (int j = 0; j < tafList.get(position).forecast.get(i).sky_condition.size(); j++) {
                            if (tafList.get(position).forecast.get(i).sky_condition.get(j).cloud_base_ft_agl != 0) {
                                meters = " " + this.context.getText(R.string.at) + " " + Math.ceil(tafList.get(position).forecast.get(i).sky_condition.get(j).cloud_base_ft_agl * 0.3048) + " meters";
                            }
                            dataTaf5 = dataTaf5 + this.context.getText(R.string.info_clouds) + " " + tafList.get(position).forecast.get(i).sky_condition.get(j).sky_cover + meters + "\n";
                        }
                    }
                    if (tafList.get(position).forecast.get(i).visibility_statute_mi != 0) {
                        dataTaf5 = dataTaf5 + this.context.getText(R.string.info_visibility) + " " + tafList.get(position).forecast.get(i).visibility_statute_mi + " miles\n";
                    }
                    if (tafList.get(position).forecast.get(i).wind_speed_kt != 0) {
                        dataTaf5 = dataTaf5 + this.context.getText(R.string.info_wind) + " " + tafList.get(position).forecast.get(i).wind_dir_degrees + "° " + this.context.getText(R.string.at) + " " + tafList.get(position).forecast.get(i).wind_speed_kt + " kt\n";
                    }
                    if (tafList.get(position).forecast.get(i).probability != 0) {
                        dataTaf5 = dataTaf5 + this.context.getText(R.string.info_from_proba) + " " + tafList.get(position).forecast.get(i).probability + " %\n";
                    }

                }

            }
        }


        slideTitle.setText(slide_titles.get(position));
        slideLocation.setText(location.get(position));
        slideTitleMetar.setText(this.context.getString(R.string.info_weather_conditions) + "\n" + metarList.get(position).observation_time);
        metarData.setText(data);
        titreTaf1.setText(titleTaf1);
        tafData1.setText(dataTaf1);
        titreTaf2.setText(titleTaf2);
        tafData2.setText(dataTaf2);
        titreTaf3.setText(titleTaf3);
        tafData3.setText(dataTaf3);
        titreTaf4.setText(titleTaf4);
        tafData4.setText(dataTaf4);
        titreTaf5.setText(titleTaf5);
        tafData5.setText(dataTaf5);
        container.addView(view);
        return view;
    }

    //peut-être à mettre en comm
    public void destroyItem (ViewGroup container, int position, Object object){
        container.removeView((ConstraintLayout)object);
    }


}
