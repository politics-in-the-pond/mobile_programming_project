package com.example.mpteam.data;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarkerModel implements Serializable {
    String title;
    double lng; //longitude
    double lat; //langitude

    MarkerModel(String title, Location location)
    {
        this.lng = location.getLongitude();
        this.lat = location.getLatitude();
        this.title = title;
    }

    MarkerModel(String title, LatLng latLng){
        this.lng = latLng.longitude;
        this.lat = latLng.latitude;
        this.title = title;
    }

}
