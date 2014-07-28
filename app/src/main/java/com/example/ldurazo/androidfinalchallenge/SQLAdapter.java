package com.example.ldurazo.androidfinalchallenge;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class SQLAdapter extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="MAP_NOTES";
    public final static String TABLE_NOTES = "NOTES";
    public final static String COLUMN_TITLE = "TITLE";
    public final static String COLUMN_CONTENT = "CONTENT";
    public final static String COLUMN_LATITUDE = "LATITUDE";
    public final static String COLUMN_LONGITUDE = "LONGITUDE";

    private final String SQL_CREATE = "CREATE TABLE IF NOT EXISTS " + TABLE_NOTES +
            "(" + COLUMN_TITLE + " TEXT, "
                + COLUMN_LATITUDE + " LONG,"
                + COLUMN_LONGITUDE+ " LONG,"
                + COLUMN_CONTENT + " TEXT) ";
    public SQLAdapter(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }

    public List<Note> selectNotes(){
        String query = "SELECT * FROM "
                +TABLE_NOTES;

        List<Note> noteList = new ArrayList<Note>();
        SQLiteDatabase db = this.getReadableDatabase();

        //dummy data, can be erased
        //db.execSQL("INSERT INTO NOTES (TITLE,CONTENT,LATITUDE,LONGITUDE) VALUES('THIS','THAT',38.44324,-110.45325)");

        Cursor c = db.rawQuery(query, null);
        if(c.moveToFirst()){
            do{
                Note note = new Note();
                note.setTitle(c.getString(0));
                note.setLatitude(c.getString(1));
                note.setLongitude(c.getString(2));
                note.setContent(c.getString(3));
                //Do something Here with values
                noteList.add(note);
            }while(c.moveToNext());
        }
        c.close();
        db.close();
        return noteList;
    }

    public void insertNotes(String title, String content, String latitude, String longitude){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_CONTENT, content);
        values.put(COLUMN_LATITUDE, latitude);
        values.put(COLUMN_LONGITUDE, longitude);

        db.insert(TABLE_NOTES, null, values);
    }


    public void deleteNotes(String title, String content, String latitude, String longitude){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "DELETE FROM NOTES WHERE "+COLUMN_CONTENT+"='"+content+"' AND "+COLUMN_TITLE+"='"+title+"'";
        db.execSQL(query);
    }
}
