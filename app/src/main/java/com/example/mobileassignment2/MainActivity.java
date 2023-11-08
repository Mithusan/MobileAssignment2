package com.example.mobileassignment2;

import android.location.Geocoder;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity{
    //ArrayList<location> coordinatesList = new ArrayList<>();
    //List<Address> addresses;
    String filename = "coordinates.JSON";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DBHandler db = new DBHandler(this);

        try {
            InputStream inputStream = this.getAssets().open(filename);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String json = new String(buffer, "UTF-8");
            String address;

            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                double latitude = jsonObject.getDouble("latitude");
                double longitude = jsonObject.getDouble("longitude");

                //location coordinates = new location(latitude, longitude);
                //coordinatesList.add(coordinates);

                // Perform Geocoding to get address
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                address = geocoder.getFromLocation(latitude, longitude, 1).toString();

                db.addNewAddress(address, String.valueOf(latitude), String.valueOf(longitude));
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
}

