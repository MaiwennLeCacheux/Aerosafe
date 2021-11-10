package com.example.aerosafe.data;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

public class Sky_condition {
    @SerializedName("sky_cover")
    public String sky_cover;

    @SerializedName("cloud_base_ft_agl")
    public int cloud_base_ft_agl;

    @SerializedName("cloud_type")
    public String cloud_type;

    public Sky_condition(NamedNodeMap dataNodeList) {
        sky_cover = "";
        cloud_base_ft_agl = 0;
        cloud_type = "";

        for (int i = 0; i < dataNodeList.getLength(); i++) {
            if(dataNodeList.item(i).getNodeName().equals("sky_cover"))
                sky_cover = dataNodeList.item(i).getTextContent();

            else if(dataNodeList.item(i).getNodeName().equals("cloud_base_ft_agl"))
                cloud_base_ft_agl = Integer.parseInt(dataNodeList.item(i).getTextContent());

            else if(dataNodeList.item(i).getNodeName().equals("cloud_type"))
                cloud_type = dataNodeList.item(i).getTextContent();
        }
    }
}
