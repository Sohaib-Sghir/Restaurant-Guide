package com.example.restaurantguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restaurantguide.database.DBHelper;
import com.example.restaurantguide.jobs.Snack;
import com.google.android.material.snackbar.Snackbar;

import java.util.regex.Pattern;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityRegister extends AppCompatActivity {

    @BindView(R.id.editTextName)
    TextView editTextName;
    @BindView(R.id.editTextEmailAddress)
    TextView editTextEmailAddress;
    @BindView(R.id.editTextPassword)
    TextView editTextPassword;

    Snack snack ;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        dbHelper = new DBHelper(this);
        snack = new Snack((Button)findViewById(R.id.register_button));

        String title = getString(R.string.register_msg);
        getSupportActionBar().setTitle(title);
    }

    @OnClick(R.id.sing_in)
    public void onClickOnSignIn()
    {
        startMainActivity();
    }

    protected void startMainActivity()
    {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.register_button)
    public void onClickOnRegisterButton()
    {
        if(saveDataInDataBase())
        {
            startMainActivity();
            //dbHelper.seeData();

        }

    }


    protected boolean saveDataInDataBase()
    {
        String name = editTextName.getText().toString();
        String email = editTextEmailAddress.getText().toString();
        String password = editTextPassword.getText().toString();

        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

        Pattern pattern = Pattern.compile(regex);

        if(name.isEmpty() || email.isEmpty() || password.isEmpty())
        {
            String message = getString(R.string.empty_fields);
            snack.showSnackBarError(message);
            return false;
        }
        else if(!pattern.matcher(email).matches())
        {
            String message = getString(R.string.invalid_email);
            snack.showSnackBarError(message);
            return false;
        }
        else if(dbHelper.checkIfEmailExists(email))
        {
            String message = getString(R.string.email_already_exists);
            snack.showSnackBarError(message);
        }
        else if(!dbHelper.checkIfEmailExists(email))
        {
            dbHelper.setUserData(name,email,password);
            return true;
        }

        return false;

    }


}