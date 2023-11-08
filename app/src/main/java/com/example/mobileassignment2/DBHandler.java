package com.example.mobileassignment2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "location_database";
    private static final int DATABASE_VERSION = 1;

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static final String TABLE_NAME = "locations";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_ADDRESS + " TEXT, "
                + COLUMN_LATITUDE + " TEXT, "
                + COLUMN_LONGITUDE + " TEXT " +
                ")";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addNewAddress(String address, String latitude, String longitude){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_ADDRESS, address);
        values.put(COLUMN_LATITUDE, latitude);
        values.put(COLUMN_LONGITUDE, longitude);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }
    public List<Location> getLocations(){
        List<Location> locations = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME +" ORDER BY "+ COLUMN_ID +" DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        if(cursor.moveToFirst()){
            do{
                Location location = new Location();
                location.setId(Long.parseLong(cursor.getString(0)));
                location.setAddress(cursor.getString(1));
                location.setLatitude(cursor.getString(2));
                location.setLongitude(cursor.getString(3));

                locations.add(location);
            }while (cursor.moveToNext());
        }
        return locations;
    }
    public Location getLocation(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] query = new String[] {COLUMN_ID,COLUMN_ADDRESS,COLUMN_LATITUDE,COLUMN_LONGITUDE};
        Cursor cursor=  db.query(TABLE_NAME,query,COLUMN_ID+"=?",new String[]{String.valueOf(id)},null,null,null,null);
        if(cursor != null)
            cursor.moveToFirst();

        return new Location(
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3));
    }

    void deleteAddress(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,COLUMN_ID+"=?",new String[]{String.valueOf(id)});
        db.close();
    }

    public void editAddress(Location location){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put(COLUMN_ADDRESS, location.getAddress());
        c.put(COLUMN_LATITUDE, location.getLatitude());
        c.put(COLUMN_LONGITUDE, location.getLongitude());

        db.update(TABLE_NAME,c,COLUMN_ID+"=?",new String[]{String.valueOf(location.getId())});
    }

}
