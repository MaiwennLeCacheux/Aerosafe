package com.example.aerosafe.data;

import com.google.gson.annotations.SerializedName;

public class SkyCondition {
    @SerializedName("sky_cover")
    public String sky_cover;

    @SerializedName("cloud_base_ft_agl")
    public int cloud_base_ft_agl;

    public SkyCondition(String sky_cover, int cloud_base_ft_agl) {
        this.sky_cover = sky_cover;
        this.cloud_base_ft_agl = cloud_base_ft_agl;
    }
}
