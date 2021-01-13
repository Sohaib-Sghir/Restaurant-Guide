package com.example.restaurantguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.restaurantguide.database.DBHelper;
import com.example.restaurantguide.jobs.Food;
import com.example.restaurantguide.jobs.Restaurant;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class ActivityMenu extends AppCompatActivity {

    private SlidrInterface slider;

    Restaurant t;

    DBHelper dbHelper;
    @BindView(R.id.list_foods)
    ListView list_foods;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);

        slider = Slidr.attach(this);

        dbHelper = new DBHelper(this);

        AdaptaterFoods adap = null;
        Intent intent = getIntent();
        t = (Restaurant) intent.getSerializableExtra("restaurant");

        String title = getString(R.string.activity_menu_restaurant);
        getSupportActionBar().setTitle(title+" "+t.getName());


        adap = new AdaptaterFoods(getApplicationContext(),R.layout.menu_layout,t.getMenu());

        list_foods.setAdapter(adap);
    }

    private void startActivityPopUp(Object foodObj)
    {
        Food food = (Food) foodObj;
        Intent intent = new Intent(this.getApplicationContext(),ActivityPopUpFoodsDescription.class);
        intent.putExtra("foodDescription",food);
        startActivity(intent);
    }

    @OnItemClick(R.id.list_foods)
    public void onListRestaurantsItemClicked(int position)
    {
        int id = dbHelper.getRestaurantIDByName(t.getName());
        startActivityPopUp(dbHelper.getRestaurantListMenu(id).get(position));

    }

    public void lockSlide(View v)
    {
        slider.lock();
    }
    public void unlockSlide(View v)
    {
        slider.unlock();
    }

}