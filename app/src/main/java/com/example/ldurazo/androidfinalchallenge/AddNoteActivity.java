package com.example.ldurazo.androidfinalchallenge;

import android.app.Activity;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class AddNoteActivity extends Activity {
private EditText mTitleText;
private EditText mContentText;
private TextView latTextView;
private TextView lngTextView;
private SQLAdapter db;

    private LocationManager locationManager;
    private String provider;
    private Location cLocation;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.add_note_confirm) {
            db.insertNotes(mTitleText.getText().toString(), mContentText.getText().toString(), latTextView.getText().toString(), lngTextView.getText().toString());
            finish();
            return true;
        }
        if (id == R.id.cancel_add_note) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_note_activity);
        db = new SQLAdapter(AddNoteActivity.this);
        mContentText = (EditText) findViewById(R.id.contentText);
        mTitleText = (EditText) findViewById(R.id.titleText);
        latTextView = (TextView) findViewById(R.id.latTextView);
        lngTextView = (TextView) findViewById(R.id.lngTextView);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        // Set Location Manager criteria
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_MEDIUM);
        // Best provider
        provider = locationManager.getBestProvider(criteria, true);
        cLocation = locationManager.getLastKnownLocation(provider);
        if (cLocation != null) {
            // We have the user location mite!
            latTextView.setText(String.valueOf(cLocation.getLatitude()));
            lngTextView.setText(String.valueOf(cLocation.getLongitude()));
        }
    }
}
