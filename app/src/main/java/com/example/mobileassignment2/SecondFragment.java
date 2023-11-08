package com.example.mobileassignment2;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mobileassignment2.databinding.FragmentFirstBinding;
import com.example.mobileassignment2.databinding.FragmentSecondBinding;

import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SecondFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    String addressString;
    DBHandler dbHandler;
    Boolean valid = false;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SecondFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SecondFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SecondFragment newInstance(String param1, String param2) {
        SecondFragment fragment = new SecondFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        dbHandler = new DBHandler(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /// Inflate the layout for this fragment
        binding = FragmentSecondBinding.inflate(getLayoutInflater());

        binding.latitudeET.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                try{
                    double newLatitude = Double.parseDouble(binding.latitudeET.getText().toString());
                    double newLongitude = Double.parseDouble(binding.longitudeET.getText().toString());

                    // Perform Geocoding to get address
                    Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
                    List<Address> addresses = geocoder.getFromLocation(newLatitude, newLongitude, 1);

                    Address address = addresses.get(0);
                    addressString = address.getAddressLine(0);

                    binding.genAddress.setText("Address: " + addressString);
                    valid = true;
                } catch (Exception e) {
                    binding.genAddress.setText("Address: Does Not Exist!");
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

        binding.longitudeET.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                try{
                    double newLatitude = Double.parseDouble(binding.latitudeET.getText().toString());
                    double newLongitude = Double.parseDouble(binding.longitudeET.getText().toString());

                    // Perform Geocoding to get address
                    Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
                    List<Address> addresses = geocoder.getFromLocation(newLatitude, newLongitude, 1);

                    Address address = addresses.get(0);
                    addressString = address.getAddressLine(0);

                    binding.genAddress.setText("Address: " + addressString);
                    valid = true;
                } catch (Exception e) {
                    binding.genAddress.setText("Address: Does Not Exist!");
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


        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.saveAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String latitude = binding.latitudeET.getText().toString();
                String longitude = binding.longitudeET.getText().toString();

                if(latitude.isEmpty()){
                    Toast.makeText(getActivity(), "Please enter a Latitude Value", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(longitude.isEmpty()){
                    Toast.makeText(getActivity(), "Please enter a Longitude Value", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!valid){
                    Toast.makeText(getActivity(), "Please Save a Valid Address", Toast.LENGTH_SHORT).show();
                }else{
                    try {
                        // Perform Geocoding to get address
                        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(Double.parseDouble(latitude), Double.parseDouble(longitude), 1);

                        Address address = addresses.get(0);
                        addressString = address.getAddressLine(0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    dbHandler.addNewAddress(addressString, latitude, longitude);
                    NavHostFragment.findNavController(SecondFragment.this).navigate(R.id.action_secondFragment_to_firstFragment);
                }
            }
        });
    }
}