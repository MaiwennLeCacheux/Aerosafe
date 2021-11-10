package com.example.aerosafe.data;

import com.google.gson.annotations.SerializedName;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

public class Icing_condition {
    @SerializedName("icing_intensity")
    public String icing_intensity;

    @SerializedName("icing_min_alt_ft_agl")
    public int icing_min_alt_ft_agl;

    @SerializedName("icing_max_alt_ft_agl")
    public int icing_max_alt_ft_agl;

    public Icing_condition(NamedNodeMap dataNodeList) {

        icing_intensity = "";
        icing_max_alt_ft_agl = 0;
        icing_min_alt_ft_agl = 0;

        for (int i = 0; i < dataNodeList.getLength(); i++) {
            if(!dataNodeList.getNamedItem("icing_intensity").equals(null))
                icing_intensity = dataNodeList.getNamedItem("icing_intensity").getTextContent();

            else if(!dataNodeList.getNamedItem("icing_min_alt_ft_agl").equals(null))
                icing_min_alt_ft_agl = Integer.parseInt(dataNodeList.getNamedItem("icing_min_alt_ft_agl").getTextContent());

            else if(!dataNodeList.getNamedItem("icing_max_alt_ft_agl").equals(null))
                icing_max_alt_ft_agl = Integer.parseInt(dataNodeList.getNamedItem("icing_max_alt_ft_agl").getTextContent());
        }
    }

}
