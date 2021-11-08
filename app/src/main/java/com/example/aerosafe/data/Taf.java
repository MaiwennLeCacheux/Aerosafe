package com.example.aerosafe.data;

import com.google.gson.annotations.SerializedName;

import org.w3c.dom.Document;
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

public class Taf {
    private boolean containData;

    @SerializedName("raw_text")
    public String raw_text;

    @SerializedName("station_id")
    public String station_id;

    @SerializedName("issue_time")
    public String issue_time;

    @SerializedName("bulletin_time")
    public String bulletin_time;

    @SerializedName("valid_time_from")
    public String valid_time_from;

    @SerializedName("valid_time_to")
    public String valid_time_to;

    @SerializedName("remarks")
    public String remarks;

    @SerializedName("latitude")
    public float latitude;

    @SerializedName("longitude")
    public float longitude;

    @SerializedName("elevation_m")
    public float elevation_m;

    @SerializedName("forecast")
    public ArrayList<Forecast> forecast;

    public Taf(String icao) {

        OkHttpClient okHttpClient = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://aviationweather.gov/adds/dataserver_current/httpparam?dataSource=tafs&requestType=retrieve&format=xml&stationString="+icao.toUpperCase()+"&hoursBeforeNow=4&mostRecent=true")
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

                    forecast = new ArrayList<>();

                    for (int i = 0; i < dataNodeList.getLength(); i++) {
                        if (dataNodeList.item(i).getNodeName().equals("raw_text"))
                            raw_text = dataNodeList.item(i).getTextContent();

                        else if (dataNodeList.item(i).getNodeName().equals("station_id"))
                            station_id = dataNodeList.item(i).getTextContent();


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

class Forecast {
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
}

class Icing_condition {
    @SerializedName("icing_intensity")
    public String icing_intensity;

    @SerializedName("icing_min_alt_ft_agl")
    public int icing_min_alt_ft_agl;

    @SerializedName("icing_max_alt_ft_agl")
    public int icing_max_alt_ft_agl;
}

class Temperature {
    @SerializedName("valid_time")
    public String valid_time;

    @SerializedName("sfc_temp_c")
    public float sfc_temp_c;

    @SerializedName("max_temp_c")
    public String max_temp_c;

    @SerializedName("min_temp_c")
    public String min_temp_c;
}

class Turbulence_condition {
    @SerializedName("turbulence_intensity")
    public String turbulence_intensity;

    @SerializedName("turbulence_min_alt_ft_agl")
    public int turbulence_min_alt_ft_agl;

    @SerializedName("turbulence_max_alt_ft_agl")
    public int turbulence_max_alt_ft_agl;
}

class Sky_condition {
    @SerializedName("sky_cover")
    public String sky_cover;

    @SerializedName("cloud_base_ft_agl")
    public int cloud_base_ft_agl;

    @SerializedName("cloud_type")
    public String cloud_type;
}

