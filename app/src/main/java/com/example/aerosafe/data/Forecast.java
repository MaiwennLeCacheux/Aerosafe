package com.example.aerosafe.data;

import com.google.gson.annotations.SerializedName;

import org.w3c.dom.NodeList;

import java.util.ArrayList;

public class Forecast {
    @SerializedName("fcst_time_from")
    public String fcst_time_from;

    @SerializedName("fcst_time_to")
    public String fcst_time_to;

    @SerializedName("change_indicator")
    public String change_indicator;

    @SerializedName("time_becoming")
    public String time_becoming;

    @SerializedName("probability")
    public int probability;

    @SerializedName("wind_dir_degrees")
    public short wind_dir_degrees;

    @SerializedName("wind_speed_kt")
    public int wind_speed_kt;

    @SerializedName("wind_gust_kt")
    public int wind_gust_kt;

    @SerializedName("wind_shear_hgt_ft_agl")
    public short wind_shear_hgt_ft_agl;

    @SerializedName("wind_shear_dir_degrees")
    public short wind_shear_dir_degrees;

    @SerializedName("wind_shear_speed_kt")
    public int wind_shear_speed_kt;

    @SerializedName("visibility_statute_mi")
    public float visibility_statute_mi;

    @SerializedName("altim_in_hg")
    public float altim_in_hg;

    @SerializedName("vert_vis_ft")
    public short vert_vis_ft;

    @SerializedName("wx_string")
    public String wx_string;

    @SerializedName("not_decoded")
    public String not_decoded;

    @SerializedName("sky_condition")
    public ArrayList<Sky_condition> sky_condition;

    @SerializedName("turbulence_condition")
    public ArrayList<Turbulence_condition> turbulence_condition;

    @SerializedName("icing_condition")
    public ArrayList<Icing_condition> icing_condition;

    @SerializedName("temperature")
    public ArrayList<Temperature> temperature;

    public Forecast(NodeList dataNodeList) {

        fcst_time_from = "";
        fcst_time_to = "";
        change_indicator = "";
        time_becoming = "";
        probability = 0;
        wind_dir_degrees = 0;
        wind_speed_kt = 0;
        wind_gust_kt = 0;
        wind_shear_dir_degrees = 0;
        wind_shear_hgt_ft_agl = 0;
        wind_shear_speed_kt = 0;
        altim_in_hg = 0;
        visibility_statute_mi = 0;
        vert_vis_ft = 0;
        wx_string = "";
        not_decoded = "";

        sky_condition = new ArrayList<Sky_condition>();
        turbulence_condition = new ArrayList<Turbulence_condition>();
        icing_condition = new ArrayList<Icing_condition>();
        temperature = new ArrayList<Temperature>();

        for (int i = 0; i < dataNodeList.getLength(); i++) {
            if (dataNodeList.item(i).getNodeName().equals("fcst_time_from"))
                fcst_time_from = dataNodeList.item(i).getTextContent();

            else if (dataNodeList.item(i).getNodeName().equals("fcst_time_to"))
                fcst_time_to = dataNodeList.item(i).getTextContent();

            else if (dataNodeList.item(i).getNodeName().equals("change_indicator"))
                change_indicator = dataNodeList.item(i).getTextContent();

            else if (dataNodeList.item(i).getNodeName().equals("time_becoming"))
                time_becoming = dataNodeList.item(i).getTextContent();

            else if (dataNodeList.item(i).getNodeName().equals("probability"))
                probability = Integer.parseInt(dataNodeList.item(i).getTextContent());

            else if (dataNodeList.item(i).getNodeName().equals("wind_dir_degrees"))
                wind_dir_degrees = Short.parseShort(dataNodeList.item(i).getTextContent());

            else if (dataNodeList.item(i).getNodeName().equals("wind_speed_kt"))
                wind_speed_kt = Integer.parseInt(dataNodeList.item(i).getTextContent());

            else if (dataNodeList.item(i).getNodeName().equals("wind_gust_kt"))
                wind_gust_kt = Integer.parseInt(dataNodeList.item(i).getTextContent());

            else if (dataNodeList.item(i).getNodeName().equals("wind_shear_hgt_ft_agl"))
                wind_shear_hgt_ft_agl = Short.parseShort(dataNodeList.item(i).getTextContent());

            else if (dataNodeList.item(i).getNodeName().equals("wind_shear_dir_degrees"))
                wind_shear_dir_degrees = Short.parseShort(dataNodeList.item(i).getTextContent());

            else if (dataNodeList.item(i).getNodeName().equals("wind_shear_speed_kt"))
                wind_shear_speed_kt = Integer.parseInt(dataNodeList.item(i).getTextContent());

            else if (dataNodeList.item(i).getNodeName().equals("visibility_statute_mi"))
                visibility_statute_mi = Float.parseFloat(dataNodeList.item(i).getTextContent());

            else if (dataNodeList.item(i).getNodeName().equals("altim_in_hg"))
                altim_in_hg = Float.parseFloat(dataNodeList.item(i).getTextContent());

            else if (dataNodeList.item(i).getNodeName().equals("vert_vis_ft"))
                vert_vis_ft = Short.parseShort(dataNodeList.item(i).getTextContent());

            else if (dataNodeList.item(i).getNodeName().equals("wx_string"))
                wx_string = dataNodeList.item(i).getTextContent();

            else if (dataNodeList.item(i).getNodeName().equals("not_decoded"))
                not_decoded = dataNodeList.item(i).getTextContent();

            else if (dataNodeList.item(i).getNodeName().equals("sky_condition"))
                sky_condition.add(new Sky_condition(dataNodeList.item(i).getAttributes()));

            else if (dataNodeList.item(i).getNodeName().equals("turbulence_condition"))
                turbulence_condition.add(new Turbulence_condition(dataNodeList.item(i).getAttributes()));

            else if (dataNodeList.item(i).getNodeName().equals("icing_condition"))
                icing_condition.add(new Icing_condition(dataNodeList.item(i).getAttributes()));

            else if (dataNodeList.item(i).getNodeName().equals("temperature"))
                temperature.add(new Temperature(dataNodeList.item(i).getChildNodes()));


        }

    }

}
