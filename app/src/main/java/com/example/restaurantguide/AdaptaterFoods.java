package com.example.restaurantguide;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.restaurantguide.jobs.Food;
import com.example.restaurantguide.jobs.Restaurant;

import java.util.ArrayList;

public class AdaptaterFoods extends ArrayAdapter<Food> {

    private ArrayList<Food> foods;

    public AdaptaterFoods(Context context, int ressource, ArrayList<Food> foods)
    {
        super(context,ressource,foods);
        this.foods = foods;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;
        convertView = inflater.inflate(R.layout.menu_layout,parent,false);


        TextView name = (TextView) convertView.findViewById(R.id.food_name);
        name.setText(foods.get(position).getName());
        TextView price = (TextView) convertView.findViewById(R.id.food_price);
        double cost = foods.get(position).getPrice();
        price.setText(cost+" dhs");



        return convertView;
    }
}
