package com.example.restaurantguide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.restaurantguide.database.DBHelper;
import com.example.restaurantguide.jobs.Restaurant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import butterknife.OnTextChanged;

import com.example.restaurantguide.jobs.PreferenceUtils;

public class ActivityListRestaurants extends AppCompatActivity {


    PreferenceUtils preferenceUtils;
    DBHelper dbHelper;
    Adaptater adap = null;

    @BindView(R.id.list_restaurants)
    ListView list_restaurants;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_restaurants);
        ButterKnife.bind(this);

        String title = getString(R.string.activity_list_restaurant);
        getSupportActionBar().setTitle(title);
        dbHelper = new DBHelper(this);


        //working with DBHelper sqlite


        Intent intent = getIntent();
        String category = intent.getStringExtra("category");
        if(category==null || category.equals("All"))
        {
            adap = new Adaptater(getApplicationContext(),R.layout.restaurant_layout,dbHelper.getRestaurants_list());
        }
        else
        {
            adap = new Adaptater(getApplicationContext(),R.layout.restaurant_layout,dbHelper.getRestaurants_list_filtered(category));
        }

        list_restaurants.setAdapter(adap);
    }

    @OnTextChanged(R.id.search_restaurant)
    void onTextChanged(CharSequence text)
    {
        String textSearched = text.toString();
        list_restaurants.setAdapter(null);
        adap = new Adaptater(getApplicationContext(),R.layout.restaurant_layout,dbHelper.getRestaurants_list_searched(textSearched));
        list_restaurants.setAdapter(adap);
    }

    public void startActivityRestaurantDescription(Restaurant r)
    {
        Intent intent = new Intent(this, ActivityRestaurantDescription.class);
        intent.putExtra("restaurant", r);
        this.startActivity(intent);
    }

    public void startActivityCategories()
    {
        Intent intent = new Intent(this, ActivityCategoires.class);
        this.startActivity(intent);
    }

    public void startHelpActivity()
    {
        Intent intent = new Intent(this, ActivityHelp.class);
        this.startActivity(intent);
    }

    public void returnToMainActivity()
    {
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
    }


    @OnItemClick(R.id.list_restaurants)
    public void onListRestaurantsItemClicked(int position)
    {
        startActivityRestaurantDescription(dbHelper.getRestaurants_list().get(position));
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_menu,menu);
        return true;

    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case (R.id.category):
                startActivityCategories();
                return true;
            case (R.id.close_session):

                preferenceUtils.clearData(this);
                returnToMainActivity();
                return true;
            case (R.id.help):
                startHelpActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }


}