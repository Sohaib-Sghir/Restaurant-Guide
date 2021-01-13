package com.example.restaurantguide.jobs;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class PreferenceUtils {


    public static final String Email = "emailKey";
    public static final String Password = "passwordKey";
    public PreferenceUtils(){ }

    public static boolean saveData(Context context,String email,String password)
    {

        SharedPreferences sharedpreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putString(Email, email);
        editor.putString(Password, password);
        editor.apply();
        return true;
    }

    public static boolean clearData(Context context)
    {

        SharedPreferences sharedpreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putString(Email, "");
        editor.putString(Password, "");
        editor.apply();
        return true;
    }

    public static String getEmail(Context context)
    {
        SharedPreferences sharedpreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedpreferences.getString(Email,null);
    }

    public static String getPassword(Context context)
    {
        SharedPreferences sharedpreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedpreferences.getString(Password,null);
    }
}
