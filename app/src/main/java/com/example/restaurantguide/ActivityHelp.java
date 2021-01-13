package com.example.restaurantguide;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

public class ActivityHelp extends AppCompatActivity {

    private SlidrInterface slider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        String title = getString(R.string.help_title);
        getSupportActionBar().setTitle(title);

        // make the slides effect between the activities
        slider = Slidr.attach(this);
    }


    //slide effect
    public void lockSlide(View v)
    {
        slider.lock();
    }
    public void unlockSlide(View v)
    {
        slider.unlock();
    }
}