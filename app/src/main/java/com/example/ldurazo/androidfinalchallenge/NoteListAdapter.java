package com.example.ldurazo.androidfinalchallenge;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

//THIS CLASS MAY BE NO LONGER USED
public class NoteListAdapter extends BaseAdapter{
private List<Note> noteList;
private Context mContext;

	public NoteListAdapter(Context context, List<Note> results) {
		noteList = results;
		mContext = context;
	}

	@Override
	public int getCount() {
		return noteList.size();
	}

	@Override
	public Object getItem(int position) {
		return noteList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.notes_row,null);
            holder = new ViewHolder();
            holder.titleView =(TextView) convertView.findViewById(R.id.note_title);
            holder.contentView = (TextView) convertView.findViewById(R.id.note_content);
            holder.latitudeView = (TextView) convertView.findViewById(R.id.note_latitude);
            holder.longitudeView = (TextView) convertView.findViewById(R.id.note_longitude);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
		holder.titleView.setText(noteList.get(position).getTitle());
		holder.contentView.setText(noteList.get(position).getContent());
        holder.latitudeView.setText(noteList.get(position).getLatitude());
        holder.longitudeView.setText(noteList.get(position).getLongitude());
		return convertView;
	}

    public class ViewHolder {
        public TextView titleView;
        public TextView contentView;
        public TextView latitudeView;
        public TextView longitudeView;
    }
}
