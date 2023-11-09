package com.example.mobileassignment2;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity{
    String filename = "coordinates.JSON";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DBHandler dbHandler = new DBHandler(this);

        //Adds 50 Entries from coordinates.JSON to the Database
        try {
            InputStream inputStream = this.getAssets().open(filename);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String json = new String(buffer, "UTF-8");


            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                double latitude = jsonObject.getDouble("latitude");
                double longitude = jsonObject.getDouble("longitude");

                // Perform Geocoding to get address
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);

                Address address = addresses.get(0);
                String addressString = address.getAddressLine(0);
                boolean exist = dbHandler.checkExist(addressString);

                //It will add only if the entry does not Exists
                //Prevents program from entering same 50 coords whenever activity is launched
                if (!exist) {
                    dbHandler.addNewAddress(addressString, String.valueOf(latitude), String.valueOf(longitude));
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
}

