package com.example.restaurantguide;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.example.restaurantguide.database.DBHelper;
import com.example.restaurantguide.jobs.Notification;
import com.example.restaurantguide.jobs.PreferenceUtils;
import com.example.restaurantguide.jobs.Snack;

public class MainActivity extends AppCompatActivity {

    Notification notification;


    @BindView(R.id.editTextEmailAddress)
    TextView email;
    @BindView(R.id.editTextPassword)
    TextView password;
    @BindView(R.id.checkBox)
    CheckBox saveMyData;

    Snack snack ;

    PreferenceUtils preferenceUtils;
    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        dbHelper = new DBHelper(this);
        notification = new Notification(this);

        snack = new Snack((Button)findViewById(R.id.login_button));
        preferenceUtils = new PreferenceUtils();

        String title = getString(R.string.authentification_msg);
        getSupportActionBar().setTitle(title);

        String preferenceUtilsEmail = preferenceUtils.getEmail(this.getApplicationContext());

        Log.i("sett", "onCreate: "+preferenceUtilsEmail);
        if(preferenceUtilsEmail!=null)
        {
            if(!preferenceUtilsEmail.equals("") || !preferenceUtilsEmail.isEmpty())
            {
                startListRestaurantActivity();
            }
        }


    }

    @OnClick(R.id.sing_up)
    public void onClickOnSignUp()
    {
        Intent intent = new Intent(this,ActivityRegister.class);
        startActivity(intent);
    }





    @OnClick(R.id.login_button)
    protected void onClickOnLoginButton()
    {
        if(saveMyData.isChecked())
            saveInSharedPrefs();


    }

    public void saveInSharedPrefs()
    {
        String emailEditText = email.getText().toString();
        String passwordEditText = password.getText().toString();

        if(dbHelper.checkIfLoginIsGood(emailEditText,passwordEditText))
        {
            String username = dbHelper.getUsernameByEmail(email.getText().toString());
            preferenceUtils.saveData(this,emailEditText,passwordEditText);
            startListRestaurantActivity();
            String title = getString(R.string.successLogin_title);
            String message = getString(R.string.successLogin_message);
            notification.showSuccessAuth(title+" "+username,message);

        }
        else if( emailEditText.isEmpty() || passwordEditText.isEmpty())
        {
            String message = getString(R.string.empty_fields);
            snack.showSnackBarError(message);
        }
        else if(!dbHelper.checkIfEmailExists(emailEditText))
        {
            String message = getString(R.string.email_not_found);
            snack.showSnackBarError(message);
        }

        else
        {
            String message = getString(R.string.password_incorrect);
            snack.showSnackBarError(message);
        }



    }

    public void startListRestaurantActivity()
    {
        Intent intent = new Intent(this, ActivityListRestaurants.class);
        this.startActivity(intent);
    }



}