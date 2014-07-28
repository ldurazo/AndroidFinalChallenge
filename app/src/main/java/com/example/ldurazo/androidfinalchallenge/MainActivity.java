package com.example.ldurazo.androidfinalchallenge;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends Activity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private FragmentManager fragmentManager;
    private ListFragment listFragment=new ListFragment();
    private MapsFragment mapsFragment = new MapsFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        // Set the adapter for the list view
        fragmentManager = getFragmentManager();
        mDrawerList.setAdapter(new DrawerAdapter(MainActivity.this));
        fragmentManager.beginTransaction().replace(R.id.content_frame, listFragment).commit();
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int index, long arg3) {
                switch (index){
                    case 0:
                fragmentManager.beginTransaction().replace(R.id.content_frame, listFragment).commit();
                        break;
                    case 1:
                        fragmentManager.beginTransaction().replace(R.id.content_frame, mapsFragment).commit();
                        break;
                }
                mDrawerLayout.closeDrawers();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.add_note) {
            Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
