package com.example.restaurantguide;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.restaurantguide.jobs.Restaurant;

import java.util.ArrayList;

public class Adaptater extends ArrayAdapter<Restaurant> {

    private ArrayList<Restaurant> restos;

    public Adaptater(Context context, int ressource, ArrayList<Restaurant> restos)
    {
        super(context,ressource,restos);
        this.restos = restos;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;
        convertView = inflater.inflate(R.layout.restaurant_layout,parent,false);


        TextView name = (TextView) convertView.findViewById(R.id.restaurant_name);
        name.setText(restos.get(position).getName());
        TextView description = (TextView) convertView.findViewById(R.id.restaurant_description);
        description.setText(restos.get(position).getDescription());
        TextView speciality = (TextView) convertView.findViewById(R.id.restaurant_speciality);
        speciality.setText(restos.get(position).getSpeciality().toString());



        return convertView;
    }
}
