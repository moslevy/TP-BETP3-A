package com.example.api_rest_call;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AutoAdapter extends ArrayAdapter {
    public AutoAdapter(Context context, ArrayList autos) {
        super(context, 0, autos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Auto auto = (Auto) getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.auto_row, parent, false);
        }
        // Lookup view for data population
        TextView marca = (TextView) convertView.findViewById(R.id.marca);
        TextView modelo = (TextView) convertView.findViewById(R.id.modelo);
        // Populate the data into the template view using the data object
        marca.setText(auto.getMarca());
        modelo.setText(auto.getModelo());
        // Return the completed view to render on screen
        return convertView;
    }
}

