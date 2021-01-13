package com.example.restaurantguide;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.restaurantguide.jobs.Notification;
import com.example.restaurantguide.jobs.Restaurant;
import com.example.restaurantguide.jobs.Snack;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.stream.LongStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityRestaurantDescription extends FragmentActivity implements OnMapReadyCallback {

    Snack snack;

    private SlidrInterface slider;

    Notification notification;
    GoogleMap map;
    Intent intent;
    Restaurant t;

    FusedLocationProviderClient client;
    SupportMapFragment mapFragment;


    LatLng myPosition;
    LatLng RestaurantPosition;

    MarkerOptions myPositionMarker;
    MarkerOptions restaurantMarker;


    @BindView(R.id.name)
    TextView name;

    String current_position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_description);
        ButterKnife.bind(this);
        notification = new Notification(this);

        // make the slides effect between the activities
        slider = Slidr.attach(this);


        snack = new Snack();
        current_position = getString(R.string.current_location_button);

        intent = getIntent();
        t = (Restaurant) intent.getSerializableExtra("restaurant");
        name.setText(t.getName());

        client = LocationServices.getFusedLocationProviderClient(this);

        //get device height
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        //set map height
        ViewGroup.LayoutParams params = mapFragment.getView().getLayoutParams();
        params.height = height / 3;
        mapFragment.getView().setLayoutParams(params);

        mapFragment.getMapAsync(this);
    }

    protected boolean checkIfLocationAndInternetActivated() {
        View parentLayout = findViewById(android.R.id.content);
        String errorInternet = getString(R.string.error_internet);
        String errorGps = getString(R.string.error_gps);
        boolean internet = true;
        boolean gps = true;

        try {
            //gps verification
            int off = Settings.Secure.getInt(getContentResolver(), Settings.Secure.LOCATION_MODE);
            if(off==0)gps=false;
            //internet verification
            ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity.getActiveNetworkInfo() == null) internet = false;

        } catch (Exception e) {
            e.printStackTrace();
        }
        if(!internet)snack.showError(parentLayout,errorInternet);
        if(!gps)snack.showError(parentLayout,errorGps);
        if(!internet && !gps)snack.showError(parentLayout,errorInternet+" & "+errorGps);

        return (internet && gps);
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkIfLocationAndInternetActivated();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;
        RestaurantPosition = new LatLng(t.getLocationV(), t.getLocationV1());
        restaurantMarker = new MarkerOptions().position(RestaurantPosition).title(t.getName());
        map.addMarker(restaurantMarker);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(RestaurantPosition, 5));
    }

    @OnClick(R.id.button_call)
    protected void onClickOnCallButton() {
        Uri phoneNumber = Uri.parse("tel:" + t.getPhone_number());
        Intent call = new Intent(Intent.ACTION_DIAL, phoneNumber);
        startActivity(call);
    }


    @OnClick(R.id.button_get_current_Position)
    protected void onClickOnGetCurrentPositionButton() {
        if(!checkIfLocationAndInternetActivated())return;
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        checkIfLocationAndInternetActivated();

        map.setMyLocationEnabled(true);

        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {

                    mapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            map.clear();
                            map.addMarker(restaurantMarker);
                            myPosition = new LatLng(location.getLatitude(), location.getLongitude());
                            myPositionMarker = new MarkerOptions().position(myPosition).title(current_position);
                            map.addMarker(myPositionMarker);
                            map.animateCamera(CameraUpdateFactory.newLatLngZoom(myPosition, 10));
                        }
                    });


                } else {
                    String title = getString(R.string.warning_title);
                    String message = getString(R.string.error_gps);
                    notification.showGetLocationWarning(title, message);
                }

            }
        });
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 44)
        {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                //when permission granted do
                getCurrentLocation();
            }
        }
        else if(requestCode == 55)
        {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                //when permission granted do
                createItineraireLine();
            }
        }
    }


    @OnClick(R.id.button_itineraire)
    protected void onClickOnItineraireButton()
    {
        if(!checkIfLocationAndInternetActivated())return;

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            createItineraireLine();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 55);
        }


    }

    protected void createItineraireLine()
    {
        final PolylineOptions polylines = new PolylineOptions();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        map.setMyLocationEnabled(true);

        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {


                if(location!=null)
                {

                    mapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            map.clear();
                            myPosition = new LatLng(location.getLatitude(),location.getLongitude());
                            myPositionMarker = new MarkerOptions().position(myPosition).title(current_position);

                            polylines.color(Color.BLUE);
                            polylines.add(myPosition);
                            polylines.add(RestaurantPosition);

                            //On met à jour la carte
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition,10));
                            map.addMarker(myPositionMarker);
                            map.addPolyline(polylines);
                            map.addMarker(restaurantMarker);
                        }
                    });


                }

            }
        });
    }


    @OnClick(R.id.menu_button)
    protected void onClickOnMenuButton()
    {
        Intent intent = new Intent(this,ActivityMenu.class);
        intent.putExtra("restaurant",t);
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