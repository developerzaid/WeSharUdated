package com.hazyaz.weshare;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class maps extends AppCompatActivity implements OnMapReadyCallback {
    double lat, lon;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Intent i = getIntent();
        String location = i.getStringExtra("location");


        String[] items = location.split(",");

        lat = Double.parseDouble(items[0]);
        lon = Double.parseDouble(items[1]);
        Toast.makeText(this, "s    " + lat + "    " + lon, Toast.LENGTH_LONG).show();


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng lo = new LatLng(lat, lon);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lo, 15));
        // Zoom in, animating the camera.
//        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
//        mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);


        mMap.addMarker(new
                MarkerOptions().position(lo).title("We share Donation Location"));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(lo));
    }
}