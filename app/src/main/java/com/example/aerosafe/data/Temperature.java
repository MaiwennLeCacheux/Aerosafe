package com.example.aerosafe.data;

import com.google.gson.annotations.SerializedName;

import org.w3c.dom.NodeList;

public class Temperature {
    @SerializedName("valid_time")
    public String valid_time;

    @SerializedName("sfc_temp_c")
    public float sfc_temp_c;

    @SerializedName("max_temp_c")
    public String max_temp_c;

    @SerializedName("min_temp_c")
    public String min_temp_c;

    public Temperature(NodeList dataNodeList) {

        valid_time = "";
        sfc_temp_c = 0;
        max_temp_c = "";
        min_temp_c = "";

        for (int i = 0; i < dataNodeList.getLength(); i++) {
            if (dataNodeList.item(i).getNodeName().equals("valid_time"))
                valid_time = dataNodeList.item(i).getTextContent();

            else if (dataNodeList.item(i).getNodeName().equals("sfc_temp_c"))
                sfc_temp_c = Float.parseFloat(dataNodeList.item(i).getTextContent());

            else if (dataNodeList.item(i).getNodeName().equals("max_temp_c"))
                max_temp_c = dataNodeList.item(i).getTextContent();

            else if (dataNodeList.item(i).getNodeName().equals("min_temp_c"))
                min_temp_c = dataNodeList.item(i).getTextContent();
        }
    }
}
