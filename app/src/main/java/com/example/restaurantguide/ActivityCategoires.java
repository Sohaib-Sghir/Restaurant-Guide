package com.example.restaurantguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityCategoires extends AppCompatActivity {

    private SlidrInterface slider;
    RadioButton radioButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoires);
        ButterKnife.bind(this);

        String title = getString(R.string.activity_category);
        getSupportActionBar().setTitle(title);

        // make the slides effect between the activities
        slider = Slidr.attach(this);
    }

    @BindView(R.id.Choice_radioGroup)
    RadioGroup radioGroup;

    @BindView(R.id.SearchButton)
    Button search_button;

    @OnClick(R.id.SearchButton)
    protected void onClickOnSearch() {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        if (selectedId != -1)
        {
            radioButton = (RadioButton) findViewById(selectedId);
            startActivityListResto();
        }
    }

    public void startActivityListResto()
    {
        Intent intent = new Intent(this,ActivityListRestaurants.class);
        intent.putExtra("category",radioButton.getText().toString());
        startActivity(intent);
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