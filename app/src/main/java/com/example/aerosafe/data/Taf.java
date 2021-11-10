package com.example.aerosafe.data;

import com.google.gson.annotations.SerializedName;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
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

        raw_text = "";
        station_id = "";
        issue_time = "";
        bulletin_time = "";
        valid_time_from = "";
        valid_time_to = "";
        remarks = "";
        latitude = 0;
        longitude = 0;
        elevation_m = 0;

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

                    forecast = new ArrayList<Forecast>();


                    for (int i = 0; i < dataNodeList.getLength(); i++) {
                        if (dataNodeList.item(i).getNodeName().equals("raw_text"))
                            raw_text = dataNodeList.item(i).getTextContent();

                        else if (dataNodeList.item(i).getNodeName().equals("station_id"))
                            station_id = dataNodeList.item(i).getTextContent();

                        else if (dataNodeList.item(i).getNodeName().equals("issue_time"))
                            issue_time = dataNodeList.item(i).getTextContent();

                        else if (dataNodeList.item(i).getNodeName().equals("bulletin_time"))
                            bulletin_time = dataNodeList.item(i).getTextContent();

                        else if (dataNodeList.item(i).getNodeName().equals("valid_time_to"))
                            valid_time_to = dataNodeList.item(i).getTextContent();

                        else if (dataNodeList.item(i).getNodeName().equals("remarks"))
                            remarks = dataNodeList.item(i).getTextContent();

                        else if (dataNodeList.item(i).getNodeName().equals("latitude"))
                            latitude = Float.parseFloat(dataNodeList.item(i).getTextContent());

                        else if (dataNodeList.item(i).getNodeName().equals("longitude"))
                            longitude = Float.parseFloat(dataNodeList.item(i).getTextContent());

                        else if (dataNodeList.item(i).getNodeName().equals("elevation_m"))
                            elevation_m = Float.parseFloat(dataNodeList.item(i).getTextContent());

                        else if (dataNodeList.item(i).getNodeName().equals("forecast"))
                            forecast.add(new Forecast(dataNodeList.item(i).getChildNodes()));


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

