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

import org.json.JSONArray;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    ArrayList<String> latitude = new ArrayList<>();
    ArrayList<String> longitude = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = (Button) findViewById(R.id.button);

        List<location> locations = coordinateParser.readLocationsFromJson(this, "coordinates.JSON");

        // Access latitude and longitude values from the list of Location objects
        for (location location : locations) {
            latitude.add(Double.toString(location.getLatitude()));
            longitude.add(Double.toString(location.getLongitude()));
        }

    }

    @Override
    public void onClick(View v) {

    }
}

