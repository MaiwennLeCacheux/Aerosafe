package com.example.aerosafe.data;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Metar {
    private boolean containData;

    @SerializedName("raw_text")
    public String raw_text;

    @SerializedName("station_id")
    public String station_id;

    @SerializedName("observation_time")
    public String observation_time;

    @SerializedName("latitude")
    public float latitude;

    @SerializedName("longitude")
    public float longitude;

    @SerializedName("temp_c")
    public float temp_c;

    @SerializedName("dewpoint_c")
    public float dewpoint_c;

    @SerializedName("wind_dir_degrees")
    public int wind_dir_degrees;

    @SerializedName("wind_speed_kt")
    public int wind_speed_kt;

    @SerializedName("wind_gust_kt")
    public int wind_gust_kt;

    @SerializedName("visibility_statute_mi")
    public float visibility_statute_mi;

    @SerializedName("altim_in_hg")
    public float altim_in_hg;

    @SerializedName("sea_level_pressure_mb")
    public float sea_level_pressure_mb;

    @SerializedName("quality_control_flags")
    public String quality_control_flags;

    @SerializedName("wx_string")
    public String wx_string;

    @SerializedName("sky_condition")
    public ArrayList<Sky_condition> sky_condition;

    @SerializedName("flight_category")
    public String flight_category;

    @SerializedName("three_hr_pressure_tendency_mb")
    public float three_hr_pressure_tendency_mb;

    @SerializedName("maxT_c")
    public float maxT_c;

    @SerializedName("minT_c")
    public float minT_c;

    @SerializedName("maxT24hr_c")
    public float maxT24hr_c;

    @SerializedName("minT24hr_c")
    public float minT24hr_c;

    @SerializedName("precip_in")
    public float precip_in;

    @SerializedName("pcp3hr_in")
    public float pcp3hr_in;

    @SerializedName("pcp6hr_in")
    public float pcp6hr_in;

    @SerializedName("pcp24hr_in")
    public float pcp24hr_in;

    @SerializedName("snow_in")
    public float snow_in;

    @SerializedName("vert_vis_ft")
    public int vert_vis_ft;

    @SerializedName("metar_type")
    public String metar_type;

    @SerializedName("elevation_m")
    public float elevation_m;

    public Metar(String icao) {

        raw_text = "";
        station_id = "";
        observation_time = "";
        latitude = 0;
        longitude = 0;
        temp_c = 0;
        dewpoint_c = 0;
        wind_dir_degrees = 0;
        wind_gust_kt = 0;
        wind_speed_kt = 0;
        visibility_statute_mi = 0;
        altim_in_hg = 0;
        sea_level_pressure_mb = 0;
        flight_category = "";
        quality_control_flags = "";
        three_hr_pressure_tendency_mb = 0;
        maxT_c = 0;
        minT_c = 0;
        maxT24hr_c = 0;
        minT24hr_c = 0;
        precip_in = 0;
        pcp3hr_in = 0;
        pcp6hr_in = 0;
        pcp24hr_in = 0;
        snow_in = 0;
        vert_vis_ft = 0;
        metar_type = "";
        elevation_m = 0;


        sky_condition = new ArrayList<Sky_condition>();


        OkHttpClient okHttpClient = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://aviationweather.gov/adds/dataserver_current/httpparam?dataSource=metars&requestType=retrieve&format=xml&stationString="+icao.toUpperCase()+"&hoursBeforeNow=4&mostRecent=true")
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(!response.isSuccessful()){
                    throw new IOException("Unexpected code "+ response);
                }
                final String responseData = response.body().string();

                Document doc = convertStringToXMLDocument(responseData);

                if(doc.getChildNodes().item(0).getChildNodes().item(13).getAttributes().item(0).getTextContent().equals("0")){
                    containData = false;
                }
                else {
                    containData = true;



                NodeList dataNodeList = doc.getChildNodes().item(0).getChildNodes().item(13).getChildNodes().item(1).getChildNodes();



                    for (int i = 0; i < dataNodeList.getLength(); i++) {
                        if (dataNodeList.item(i).getNodeName().equals("raw_text"))
                            raw_text = dataNodeList.item(i).getTextContent();

                        else if (dataNodeList.item(i).getNodeName().equals("station_id"))
                            station_id = dataNodeList.item(i).getTextContent();

                        else if (dataNodeList.item(i).getNodeName().equals("observation_time"))
                            observation_time = dataNodeList.item(i).getTextContent();

                        else if (dataNodeList.item(i).getNodeName().equals("latitude"))
                            latitude = Float.parseFloat(dataNodeList.item(i).getTextContent());

                        else if (dataNodeList.item(i).getNodeName().equals("longitude"))
                            longitude = Float.parseFloat(dataNodeList.item(i).getTextContent());

                        else if (dataNodeList.item(i).getNodeName().equals("temp_c"))
                            temp_c = Float.parseFloat(dataNodeList.item(i).getTextContent());

                        else if (dataNodeList.item(i).getNodeName().equals("dewpoint_c"))
                            dewpoint_c = Float.parseFloat(dataNodeList.item(i).getTextContent());

                        else if (dataNodeList.item(i).getNodeName().equals("wind_dir_degrees"))
                            wind_dir_degrees = Integer.parseInt(dataNodeList.item(i).getTextContent());

                        else if (dataNodeList.item(i).getNodeName().equals("wind_speed_kt"))
                            wind_speed_kt = Integer.parseInt(dataNodeList.item(i).getTextContent());

                        else if (dataNodeList.item(i).getNodeName().equals("wind_gust_kt"))
                            wind_gust_kt = Integer.parseInt(dataNodeList.item(i).getTextContent());

                        else if (dataNodeList.item(i).getNodeName().equals("visibility_statute_mi"))
                            visibility_statute_mi = Float.parseFloat(dataNodeList.item(i).getTextContent());

                        else if (dataNodeList.item(i).getNodeName().equals("altim_in_hg"))
                            altim_in_hg = Float.parseFloat(dataNodeList.item(i).getTextContent());

                        else if (dataNodeList.item(i).getNodeName().equals("sea_level_pressure_mb"))
                            sea_level_pressure_mb = Float.parseFloat(dataNodeList.item(i).getTextContent());

                        else if (dataNodeList.item(i).getNodeName().equals("quality_control_flags"))
                            quality_control_flags = dataNodeList.item(i).getTextContent();

                        else if (dataNodeList.item(i).getNodeName().equals("wx_string"))
                            wx_string = dataNodeList.item(i).getTextContent();

                        else if (dataNodeList.item(i).getNodeName().equals("sky_condition"))
                            sky_condition.add(new Sky_condition(dataNodeList.item(i).getAttributes()));

                        else if (dataNodeList.item(i).getNodeName().equals("flight_category"))
                            flight_category = dataNodeList.item(i).getTextContent();

                        else if (dataNodeList.item(i).getNodeName().equals("three_hr_pressure_tendency_mb"))
                            three_hr_pressure_tendency_mb = Float.parseFloat(dataNodeList.item(i).getTextContent());

                        else if (dataNodeList.item(i).getNodeName().equals("maxT_c"))
                            maxT_c = Float.parseFloat(dataNodeList.item(i).getTextContent());

                        else if (dataNodeList.item(i).getNodeName().equals("minT_c"))
                            minT_c = Float.parseFloat(dataNodeList.item(i).getTextContent());

                        else if (dataNodeList.item(i).getNodeName().equals("maxT24hr_c"))
                            maxT24hr_c = Float.parseFloat(dataNodeList.item(i).getTextContent());

                        else if (dataNodeList.item(i).getNodeName().equals("minT24hr_c"))
                            minT24hr_c = Float.parseFloat(dataNodeList.item(i).getTextContent());

                        else if (dataNodeList.item(i).getNodeName().equals("precip_in"))
                            precip_in = Float.parseFloat(dataNodeList.item(i).getTextContent());

                        else if (dataNodeList.item(i).getNodeName().equals("pcp3hr_in"))
                            pcp3hr_in = Float.parseFloat(dataNodeList.item(i).getTextContent());

                        else if (dataNodeList.item(i).getNodeName().equals("pcp6hr_in"))
                            pcp6hr_in = Float.parseFloat(dataNodeList.item(i).getTextContent());

                        else if (dataNodeList.item(i).getNodeName().equals("pcp24hr_in"))
                            pcp24hr_in = Float.parseFloat(dataNodeList.item(i).getTextContent());

                        else if (dataNodeList.item(i).getNodeName().equals("snow_in"))
                            snow_in = Float.parseFloat(dataNodeList.item(i).getTextContent());

                        else if (dataNodeList.item(i).getNodeName().equals("vert_vis_ft"))
                            vert_vis_ft = Integer.parseInt(dataNodeList.item(i).getTextContent());

                        else if (dataNodeList.item(i).getNodeName().equals("metar_type"))
                            metar_type = dataNodeList.item(i).getTextContent();

                        else if (dataNodeList.item(i).getNodeName().equals("elevation_m"))
                            elevation_m = Float.parseFloat(dataNodeList.item(i).getTextContent());
                    }
                }
            }
        });
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
