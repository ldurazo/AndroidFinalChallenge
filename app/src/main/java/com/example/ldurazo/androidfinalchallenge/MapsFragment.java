package com.example.ldurazo.androidfinalchallenge;


import android.app.Fragment;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapsFragment extends Fragment {
    private MapView mapView;
    private View v;
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume(); //without this, map showed but was empty
        setUpMapIfNeeded();
        map.getUiSettings().setMyLocationButtonEnabled(false);
        map.setMyLocationEnabled(true);
        MapsInitializer.initialize(this.getActivity());
        initializeLocationManager();
        getUserCurrentLocation();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.maps_fragment_layout, null);
        mapView = (MapView) v.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);
        return v;
    }



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


    private void initializeLocationManager() {
        // Initialize Location Manager
        locationManager = (LocationManager) getActivity().getSystemService(getActivity().LOCATION_SERVICE);
        // Set Location Manager criteria
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_MEDIUM);
        // Best provider
        provider = locationManager.getBestProvider(criteria, true);
    }

    private void getUserCurrentLocation() {
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

    private void updateMapLocation(Location mLocation) {
                List<Note> notes = new SQLAdapter(getActivity()).selectNotes();
                double lat;
                double lng;
                LatLng latLong;
        for (Note note : notes) {
            lat = Double.parseDouble(note.getLatitude());
            lng = Double.parseDouble(note.getLongitude());
            latLong = new LatLng(lat, lng);
            Marker userMarker = map.addMarker(new MarkerOptions().position(latLong)
                    .title(note.getTitle()).snippet(note.getContent()));
            userMarker.showInfoWindow();
        }
    }

    @Override
    public void onPause() {
        locationManager.removeUpdates(myLocationListener);
        super.onPause();
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (map == null) {
            // Try to obtain the map from the SupportMapFragment.
            map = mapView.getMap();
            // Check if we were successful in obtaining the map.
            if (map != null) {

            }
        }
    }

}



