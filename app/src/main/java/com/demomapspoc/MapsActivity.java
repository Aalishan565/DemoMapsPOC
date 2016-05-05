package com.demomapspoc;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap mMap;
    private EditText mEtSearch;
    private Button mBtnSearch;
    private Button mBtnType;
    private Button mBtnZoomIn;
    private Button mBtnZoomOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mEtSearch = (EditText) findViewById(R.id.et_location);
        mBtnSearch = (Button) findViewById(R.id.btn_search);
        mBtnType = (Button) findViewById(R.id.btn_map_type);
        mBtnZoomIn = (Button) findViewById(R.id.btn_zoomin);
        mBtnZoomOut = (Button) findViewById(R.id.btn_zoomout);
        mBtnSearch.setOnClickListener(this);
        mBtnType.setOnClickListener(this);
        mBtnZoomIn.setOnClickListener(this);
        mBtnZoomOut.setOnClickListener(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_search:
                String location = mEtSearch.getText().toString();
                if (!NetworkManager.isNetworkAvailable(this)) {
                    Toast.makeText(getApplicationContext(), "Please check network connectivity", Toast.LENGTH_SHORT).show();

                }

               else if (location != null && !location.equals("") && NetworkManager.isNetworkAvailable(this)) {
                    Geocoder geocoder = new Geocoder(this);
                    List<Address> addressesList = null;
                    try {
                        addressesList = geocoder.getFromLocationName(location, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Address address = addressesList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(latLng).title("marker"));
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                } else {

                    Toast.makeText(getApplicationContext(), "Please enter location", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_map_type:
                if (mMap.getMapType() == GoogleMap.MAP_TYPE_NORMAL) {
                    mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                } else {
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                }
                break;
            case R.id.btn_zoomin:
                mMap.animateCamera(CameraUpdateFactory.zoomIn());
                break;
            case R.id.btn_zoomout:
                mMap.animateCamera(CameraUpdateFactory.zoomOut());

                break;

        }


    }
}
