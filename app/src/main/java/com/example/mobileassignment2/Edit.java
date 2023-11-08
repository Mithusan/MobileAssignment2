package com.example.mobileassignment2;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.Locale;

public class Edit extends AppCompatActivity {
    long id;
    boolean valid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address_edit);

        Intent i = getIntent();
        id = i.getLongExtra("ID",0);
        DBHandler db = new DBHandler(this);
        Location location = db.getLocation(id);

        ImageButton returnBtn = findViewById(R.id.returnBtn);
        Button saveBtn = findViewById(R.id.saveBtn);
        Button deleteBtn = findViewById(R.id.deleteBtn);

        TextView addressOutput = findViewById(R.id.addressOutput);
        EditText latitudeInput = findViewById(R.id.latitudeInput);
        EditText longitudeInput = findViewById(R.id.longitudeInput);

        addressOutput.setText("Address: " + location.getAddress());
        latitudeInput.setText(location.getLatitude());
        longitudeInput.setText(location.getLongitude());

        latitudeInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                try{
                    double newLatitude = Double.parseDouble(latitudeInput.getText().toString());
                    double newLongitude = Double.parseDouble(longitudeInput.getText().toString());

                    // Perform Geocoding to get address
                    Geocoder geocoder = new Geocoder(Edit.this, Locale.getDefault());
                    List<Address> addresses = geocoder.getFromLocation(newLatitude, newLongitude, 1);

                    Address address = addresses.get(0);
                    String addressString = address.getAddressLine(0);

                    addressOutput.setText("Address: " + addressString);
                    valid = true;
                } catch (Exception e) {
                    addressOutput.setText("Address: Does Not Exist!");
                    valid = false;
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        longitudeInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                try{
                    double newLatitude = Double.parseDouble(latitudeInput.getText().toString());
                    double newLongitude = Double.parseDouble(longitudeInput.getText().toString());

                    // Perform Geocoding to get address
                    Geocoder geocoder = new Geocoder(Edit.this, Locale.getDefault());
                    List<Address> addresses = geocoder.getFromLocation(newLatitude, newLongitude, 1);

                    Address address = addresses.get(0);
                    String addressString = address.getAddressLine(0);

                    addressOutput.setText("Address: " + addressString);
                    valid = true;
                } catch (Exception e) {
                    addressOutput.setText("Address: Does Not Exist!");
                    valid = false;
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMain();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteAddress(id);
                goToMain();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!valid){
                    Toast.makeText(Edit.this, "Please Save a Valid Address", Toast.LENGTH_SHORT).show();
                }else {
                    String address = addressOutput.getText().toString();
                    String latitude = latitudeInput.getText().toString();
                    String longitude = longitudeInput.getText().toString();

                    Location editLocation = new Location(address, latitude, longitude);
                    db.editAddress(editLocation);

                    goToMain();
                }
            }
        });

    }

    private void goToMain() {
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }
}
