package com.example.restaurantguide.jobs;

import android.graphics.Color;
import android.text.Layout;
import android.view.View;
import android.widget.Button;

import com.example.restaurantguide.R;
import com.google.android.material.snackbar.Snackbar;

public class Snack {

    public Button button ;
    public Snack(Button b)
    {
        this.button = b;
    }
    public Snack(){}

    public void showSnackBarError(String msg)
    {

        Snackbar snackbar;
        snackbar = Snackbar.make(button, msg, 6000);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(Color.RED);
        snackbar.show();
    }

    public void showSnackBarSuccess(String msg)
    {

        Snackbar snackbar;
        snackbar = Snackbar.make(button, msg, 6000);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(Color.GREEN);
        snackbar.show();
    }

    public void showError(View layout,String msg)
    {

        Snackbar snackbar;
        snackbar = Snackbar.make(layout, msg, 5000);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(Color.RED);
        snackbar.show();
    }

}
