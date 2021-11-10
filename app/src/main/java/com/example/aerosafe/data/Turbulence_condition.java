package com.example.aerosafe.data;

import com.google.gson.annotations.SerializedName;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

public class Turbulence_condition {
    @SerializedName("turbulence_intensity")
    public String turbulence_intensity;

    @SerializedName("turbulence_min_alt_ft_agl")
    public int turbulence_min_alt_ft_agl;

    @SerializedName("turbulence_max_alt_ft_agl")
    public int turbulence_max_alt_ft_agl;

    public Turbulence_condition(NamedNodeMap dataNodeList) {

        turbulence_intensity = "";
        turbulence_max_alt_ft_agl = 0;
        turbulence_min_alt_ft_agl = 0;

        for (int i = 0; i < dataNodeList.getLength(); i++) {
            if(!dataNodeList.getNamedItem("turbulence_intensity").equals(null))
                turbulence_intensity = dataNodeList.getNamedItem("turbulence_intensity").getTextContent();

            else if(!dataNodeList.getNamedItem("turbulence_min_alt_ft_agl").equals(null))
                turbulence_min_alt_ft_agl = Integer.parseInt(dataNodeList.getNamedItem("turbulence_min_alt_ft_agl").getTextContent());

            else if(!dataNodeList.getNamedItem("turbulence_max_alt_ft_agl").equals(null))
                turbulence_max_alt_ft_agl = Integer.parseInt(dataNodeList.getNamedItem("turbulence_max_alt_ft_agl").getTextContent());
        }
    }
}
