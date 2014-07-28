package com.example.ldurazo.androidfinalchallenge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class DetailedNoteActivity extends Activity {

    private EditText mTitleText;
    private EditText mContentText;
    private TextView latTextView;
    private TextView lngTextView;
    private SQLAdapter db;
    private String oldTitle;
    private String oldText;
    private String oldLat;
    private String oldLong;
    private Note note;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.delete_note) {
            db.deleteNotes(note.getTitle(), note.getContent(), note.getLatitude(), note.getLongitude());
            finish();
            return true;
        }
        if (id == R.id.save_note) {
            db.deleteNotes(note.getTitle(), note.getContent(), note.getLatitude(), note.getLongitude());
            db.insertNotes(mTitleText.getText().toString(), mContentText.getText().toString(), latTextView.getText().toString(), lngTextView.getText().toString());
            finish();
            return true;
        }
        if (id == R.id.cancel_edit) {
            finish();
            return true;
        }
        if (id == R.id.map_view) {
            Intent intent = new Intent(DetailedNoteActivity.this, MapActivity.class);
            intent.putExtra("TITLE", note.getTitle());
            intent.putExtra("CONTENT", note.getContent());
            intent.putExtra("LATITUDE", note.getLatitude());
            intent.putExtra("LONGITUDE", note.getLongitude());
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_note_activity);
        db = new SQLAdapter(DetailedNoteActivity.this);
        mContentText = (EditText) findViewById(R.id.contentText1);
        mTitleText = (EditText) findViewById(R.id.titleText1);
        latTextView = (TextView) findViewById(R.id.latTextView1);
        lngTextView = (TextView) findViewById(R.id.lngTextView1);
        note = new Note();
        note.setContent(getIntent().getExtras().getString("CONTENT"));
        note.setTitle(getIntent().getExtras().getString("TITLE"));
        note.setLatitude(getIntent().getExtras().getString("LATITUDE"));
        note.setLongitude(getIntent().getExtras().getString("LONGITUDE"));
        latTextView.setText(note.getLatitude());
        lngTextView.setText(note.getLongitude());
    }
}
