package com.example.restaurantguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import com.example.restaurantguide.R;
import com.example.restaurantguide.jobs.Food;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityPopUpFoodsDescription extends AppCompatActivity {

    @BindView(R.id.food_description_title)
    TextView foodDescriptionTitle;
    @BindView(R.id.food_description)
    TextView foodDescription;
    Food food;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_foods_description);
        ButterKnife.bind(this);


        String title = getString(R.string.food_description_title);
        foodDescriptionTitle.setText(title);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.6));

    }

    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        if (intent!=null)
        {
            food = (Food) intent.getSerializableExtra("foodDescription");
            foodDescription.setText(food.getDescription());

        }

    }


}