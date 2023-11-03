package com.example.mobileassignment2;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private double[] latitudes;
    private double[] longitudes;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context = getApplicationContext();

        // Read the JSON file and populate arrays
        readJSONFile("coordinates.json");

        Button btnShow = (Button) findViewById(R.id.button);
        btnShow.setOnClickListener(MainActivity.this);
    }

    private void readJSONFile(String fileName) {
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String json = new String(buffer, "UTF-8");

            // Use Gson to parse JSON into a list of Coordinate objects
            Type listType = new TypeToken<List<Coordinate>>(){}.getType();
            List<Coordinate> coordinates = new Gson().fromJson(json, listType);

            // Separate latitude and longitude values into two arrays
            latitudes = new double[coordinates.size()];
            longitudes = new double[coordinates.size()];
            for (int i = 0; i < coordinates.size(); i++) {
                latitudes[i] = coordinates.get(i).getLatitude();
                longitudes[i] = coordinates.get(i).getLongitude();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        String test = "Coordinate (" + latitudes[0] + ", " + longitudes[1];
        Toast.makeText(getApplicationContext(), test, Toast.LENGTH_SHORT).show();
    }
}

class Coordinate {
    private double latitude;
    private double longitude;

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}