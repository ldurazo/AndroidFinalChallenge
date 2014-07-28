package com.example.ldurazo.androidfinalchallenge;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.nostra13.universalimageloader.core.ImageLoader;


public class ListFragment extends Fragment {
    private ListView listView;
private SQLAdapter db;
    private Note note;
    @Override
    public void onResume() {
        super.onResume();
        listView.setAdapter(new NoteListAdapter(this.getActivity(), db.selectNotes()));
    }

    @Override
     public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_list_view, null);
         db = new SQLAdapter(this.getActivity());
        ImageView imageView = (ImageView) v.findViewById(R.id.imageView);
        ImageLoader.getInstance().displayImage("http://i.imgur.com/6jr3M0j.png",imageView);
        listView=(ListView) v.findViewById(R.id.home_list);
        listView.setAdapter(new NoteListAdapter(this.getActivity(), db.selectNotes()));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                note =(Note) listView.getItemAtPosition(i);
                Intent intent = new Intent(getActivity(), DetailedNoteActivity.class);
                intent.putExtra("TITLE", note.getTitle());
                intent.putExtra("CONTENT", note.getContent());
                intent.putExtra("LATITUDE", note.getLatitude());
                intent.putExtra("LONGITUDE", note.getLongitude());
                startActivity(intent);
            }
        });
        return v;
    }
}
