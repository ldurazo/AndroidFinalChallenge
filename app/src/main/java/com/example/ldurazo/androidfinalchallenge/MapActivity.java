package com.example.ldurazo.androidfinalchallenge;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.CancelableCallback;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends Activity {
    private static final long TIMEUPDATE = 400;
    private static final long DISTANCEUPDATE = 1000;
    private GoogleMap map;
    private LocationManager locationManager;
    private String provider;
    private Location cLocation;
    private LocationListener myLocationListener = new LocationListener() {

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onLocationChanged(Location location) {
            updateMapLocation(location);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_layout);
        initializeMapFragment();
    }

    private void initializeMapFragment() {
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                .getMap();

    }

    @Override
    protected void onResume() {
        initializeLocationManager();
        getUserCurrentLocation();
        super.onResume();
    }

    private void initializeLocationManager() {
        // Initialize Location Manager
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        // Set Location Manager criteria
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_MEDIUM);
        // Best provider
        provider = locationManager.getBestProvider(criteria, true);
    }

    private void getUserCurrentLocation() {
        if(!checkLocationAviability())
            return;
        // Getting Current Location
        cLocation = locationManager.getLastKnownLocation(provider);
        if (cLocation != null) {
            // We have the user location mite!
            updateMapLocation(cLocation);
        } else {
            // Maybe the user lives in a cave, I don't know...
            // So we must register a listener in order to keep searching for the
            // location
            locationManager.requestLocationUpdates(provider, TIMEUPDATE,
                    DISTANCEUPDATE, myLocationListener);
        }
    }

    private boolean checkLocationAviability() {
        boolean gps_enabled = false, network_enabled = false;
        if (locationManager == null)
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        try {
            gps_enabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }
        try {
            network_enabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }

        if (!network_enabled) {
            showLocationGpsDialog("Location not Available, please allow your device to access your location",
                    "Edit",
                    "Cancel", new Intent(
                            Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            return false;
        } else if (!gps_enabled) {
            showLocationGpsDialog(
                    "GPS is not available, please activate it to improve location accuracy",
                    "Edit",
                    "Cancel",
                    new Intent(
                            android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        }
        return true;
    }

    private void showLocationGpsDialog(String message, String okString,
                                       String cancelString, final Intent intent) {
        Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage(message);
        dialog.setPositiveButton(okString, new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                MapActivity.this.startActivity(intent);
            }
        });

        dialog.setNegativeButton(cancelString,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface paramDialogInterface,
                                        int paramInt) {
                        // TODO Auto-generated method stub

                    }
                });
        dialog.show();
    }

    private void updateMapLocation(Location mLocation) {
        // Getting latitude and longitude of the current location
        Log.w("A", getIntent().getStringExtra("LATITUDE"));
        Log.w("A", getIntent().getStringExtra("LONGITUDE"));
        double latitude = Double.parseDouble(getIntent().getStringExtra("LATITUDE"));
        double longitude = Double.parseDouble(getIntent().getStringExtra("LONGITUDE"));
        // Instance LatLng
        final LatLng latLng = new LatLng(latitude, longitude);
        // move the camera to the current position
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng,
                300);
        map.animateCamera(cameraUpdate, new CancelableCallback() {

            @Override
            public void onFinish() {
                Marker userMarker = map.addMarker(new MarkerOptions().position(latLng)
                        .title(getIntent().getExtras().getString("TITLE")).snippet(getIntent().getExtras().getString("CONTENT")));
                userMarker.showInfoWindow();
            }

            @Override
            public void onCancel() {
                // TODO Auto-generated method stub

            }
        });
    }

    @Override
    protected void onPause() {
        locationManager.removeUpdates(myLocationListener);
        super.onPause();
    }

}
