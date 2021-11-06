package com.example.aerosafe;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Airport implements Parcelable {
        @SerializedName("icao")
        public String icao;

        @SerializedName("name")
        public String name;

        @SerializedName("country")
        public String country;

    public Airport(String icao, String country, String name) {
        this.icao = icao;
        this.name = name;
        this.country = country;
    }


    protected Airport(Parcel in) {
        icao = in.readString();
        name = in.readString();
        country = in.readString();
    }

    public static final Creator<Airport> CREATOR = new Creator<Airport>() {
        @Override
        public Airport createFromParcel(Parcel in) {
            return new Airport(in);
        }

        @Override
        public Airport[] newArray(int size) {
            return new Airport[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(icao);
        dest.writeString(name);
        dest.writeString(country);
    }
}
