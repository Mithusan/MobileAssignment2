package com.example.mobileassignment2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
    private Context context;
    private List<Location> locations;

    Adapter(Context context,List<Location> locations){
        this.context = context;
        this.locations = locations;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.custom_list_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String address = locations.get(position).getAddress();
        String latitude = "Latitude: " + locations.get(position).getLatitude();
        String longitude = "Longitude: " + locations.get(position).getLongitude();
        long id = locations.get(position).getId();

        holder.addressOutput.setText(address);
        holder.latitudeOutput.setText(latitude);
        holder.longitudeOutput.setText(longitude);
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView addressOutput;
        TextView latitudeOutput;
        TextView longitudeOutput;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            addressOutput = itemView.findViewById(R.id.nAddress);
            latitudeOutput = itemView.findViewById(R.id.nLatitude);
            longitudeOutput = itemView.findViewById(R.id.nLongitude);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), Edit.class);
                    intent.putExtra("ID", locations.get(getAdapterPosition()).getId());
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}
