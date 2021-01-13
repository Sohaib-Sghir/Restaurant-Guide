package com.example.restaurantguide.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.util.Log;
import android.widget.Toast;

import com.example.restaurantguide.jobs.Food;
import com.example.restaurantguide.jobs.Restaurant;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private final String foodDescription ="Lorem ipsum dolor sit amet, consectetur adipiscing elit,"+
                                          " sed do eiusmod tempor incididunt ut labore et dolore magna"+
                                          " aliqua. Ut enim ad minim veniam, quis nostrud exercitation"+
                                          " ullamco laboris nisi ut aliquip ex ea commodo consequat."+
                                          " cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat";

    public ArrayList<Restaurant> getRestaurants_list()
    {
        String sqlQuery = "select * from "+TABLE_RESTAURANT_NAME;
        return insertingIntoRestaurantList(sqlQuery);
    }
    public ArrayList<Restaurant> getRestaurants_list_filtered(String category)
    {
        String sqlQuery = "select * from "+TABLE_RESTAURANT_NAME+" where speciality='"+category+"'";
        return  insertingIntoRestaurantList(sqlQuery);
    }

    public ArrayList<Restaurant> getRestaurants_list_searched(String searchedText)
    {
        String sqlQuery = "select * from "+TABLE_RESTAURANT_NAME+" where name LIKE '%"+searchedText+"%'";
        return  insertingIntoRestaurantList(sqlQuery);
    }



    protected static final String DATA_BASE_NAME = "restaurant_guide.db";
    protected static final String TABLE_RESTAURANT_NAME = "restaurants";
    protected static final String COL1_TABLE_RESTAURANT = "ID";
    protected static final String COL2_TABLE_RESTAURANT = "NAME";
    protected static final String COL3_TABLE_RESTAURANT = "SPECIALITY";
    protected static final String COL4_TABLE_RESTAURANT = "DESCRIPTION";
    protected static final String COL5_TABLE_RESTAURANT = "PHONE_NUMBER";
    protected static final String COL6_TABLE_RESTAURANT = "LATITUDE";
    protected static final String COL7_TABLE_RESTAURANT = "LONG_LATITUDE";


    protected static final String TABLE_MENU_NAME = "menu";
    protected static final String COL1_TABLE_MENU = "ID";
    protected static final String COL2_TABLE_MENU = "ID_RESTAURANT";
    protected static final String COL3_TABLE_MENU = "FOOD_NAME";
    protected static final String COL4_TABLE_MENU = "FOOD_DESCRIPTION";
    protected static final String COL5_TABLE_MENU = "FOOD_PRICE";


    protected static final String TABLE_USER_NAME = "users";
    protected static final String COL1_TABLE_USER = "ID";
    protected static final String COL2_TABLE_USER = "NAME";
    protected static final String COL3_TABLE_USER = "EMAIL";
    protected static final String COL4_TABLE_USER = "PASSWORD";




    public DBHelper(Context context)
    {
        super(context,DATA_BASE_NAME,null,2);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_RESTAURANT_NAME+" ("+COL1_TABLE_RESTAURANT+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                            COL2_TABLE_RESTAURANT+" TEXT," +
                                                            COL3_TABLE_RESTAURANT+" TEXT," +
                                                            COL4_TABLE_RESTAURANT+" TEXT," +
                                                            COL5_TABLE_RESTAURANT+" TEXT," +
                                                            COL6_TABLE_RESTAURANT+" DOUBLE," +
                                                            COL7_TABLE_RESTAURANT+" DOUBLE)");

        db.execSQL("create table "+TABLE_MENU_NAME+" ("+COL1_TABLE_MENU+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                    COL2_TABLE_MENU+" INTEGER,"+
                                                    COL3_TABLE_MENU+" TEXT," +
                                                    COL4_TABLE_MENU+" TEXT(255)," +
                                                    COL5_TABLE_MENU+" DOUBLE)");
                                                    //"FOREIGN KEY (ID_RESTAURANT) REFERENCES "+TABLE_RESTAURANT_NAME+"(ID) )");

        db.execSQL("create table "+TABLE_USER_NAME+" ("+COL1_TABLE_USER+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL2_TABLE_USER+" TEXT,"+
                COL3_TABLE_USER+" TEXT," +
                COL4_TABLE_USER+" TEXT)");

        insert(db);


    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_RESTAURANT_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_MENU_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_USER_NAME);
        onCreate(db);
    }



    public void insert(SQLiteDatabase db)
    {
        insertingDataIntoDataBase(db,"Le Plaza","Sea Food","the best sea food restaurant ever","+212808522191",
                34.258550,-6.583737,
                "Shark","Sole","Catfish",
                100,211,444);

        insertingDataIntoDataBase(db,"Vinesia","Pizza","the best Pizza restaurant ever","+212537375602",
                34.26048758417828, -6.581893263970363,
                "Pizza Margarita","Pizza Thon","Pizza Vinesia",
                100,211,444);

        insertingDataIntoDataBase(db,"Fire Place","Pizza","the best Pizza restaurant ever","+212537327944",
                34.266349,-6.590159,
                "Pizza Poulet","Pizza V.H","Pizza Fire Place",
                100,211,444);

        insertingDataIntoDataBase(db,"Eat & Meet","Egyptien","the best Egyptien food restaurant ever","+212643473267",
                34.269597447994755, -6.591098173856291,
                "Hilala","Kunafa","ROZE",
                100,211,444);

        insertingDataIntoDataBase(db,"Ifly","Tajine","the best Tajine restaurent ever","+212664444675",
                34.265621881769036, -6.587593816185223,
                "Tajine Poissons","Tajine Viande","Tajine Poulet",
                100,211,444);
    }


    public void insertingDataIntoDataBase(SQLiteDatabase db,String nameRestaurant,String speciality,String description,String phone,double locationV,double locationV1,
                                          String name1,String name2,String name3,
                                          double price1, double price2, double price3)
    {

        db.execSQL("insert into "+TABLE_RESTAURANT_NAME+" ("+COL2_TABLE_RESTAURANT+","+COL3_TABLE_RESTAURANT+","
                +COL4_TABLE_RESTAURANT+","+COL5_TABLE_RESTAURANT+","
                +COL6_TABLE_RESTAURANT+","+COL7_TABLE_RESTAURANT+") values ('"+nameRestaurant+"','"+speciality+"','"+description+"','"+phone+"','"+locationV+"','"+locationV1+"')");

        int restaurantID;
        restaurantID = getRestaurantIDByName(db,nameRestaurant);

        db.execSQL("insert into "+TABLE_MENU_NAME+" ("+COL2_TABLE_MENU+","+COL3_TABLE_MENU+","
                +COL4_TABLE_MENU+","+COL5_TABLE_MENU+") values ("+restaurantID+",'"+name1+"','"+foodDescription+"',"+price1+")");
        db.execSQL("insert into "+TABLE_MENU_NAME+" ("+COL2_TABLE_MENU+","+COL3_TABLE_MENU+","
                +COL4_TABLE_MENU+","+COL5_TABLE_MENU+") values ("+restaurantID+",'"+name2+"','"+foodDescription+"',"+price2+")");
        db.execSQL("insert into "+TABLE_MENU_NAME+" ("+COL2_TABLE_MENU+","+COL3_TABLE_MENU+","
                +COL4_TABLE_MENU+","+COL5_TABLE_MENU+") values ("+restaurantID+",'"+name3+"','"+foodDescription+"',"+price3+")");



    }




    public ArrayList insertingIntoRestaurantList(String sqlQuery)
    {
        ArrayList<Restaurant>restaurants_list = new ArrayList<Restaurant>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( sqlQuery, null );
        res.moveToFirst();
        while(res.isAfterLast() == false) {
            int id = res.getInt(0);
            String name = res.getString(1);
            String speciality = res.getString(2);
            String description = res.getString(3);
            String phone_number = res.getString(4);
            double latitude = res.getDouble(5);
            double long_latitude = res.getDouble(6);
            Restaurant r = new Restaurant(name,speciality,description,phone_number,getRestaurantListMenu(id),latitude,long_latitude);
            restaurants_list.add(r);
            res.moveToNext();
        }

        return restaurants_list;
    }


    public int getRestaurantIDByName(SQLiteDatabase db,String name_restaurant)
    {
        int id_restaurant=0;
        Cursor res = db.rawQuery( "select id from "+TABLE_RESTAURANT_NAME +" where "+COL2_TABLE_RESTAURANT+"='"+name_restaurant+"'", null );
        res.moveToFirst();
        while(res.isAfterLast() == false) {
            id_restaurant = res.getInt(0);
            res.moveToNext();
        }
        return id_restaurant;
    }


    public int getRestaurantIDByName(String name_restaurant)
    {
        int id_restaurant=0;
        SQLiteDatabase db = getReadableDatabase();
        Cursor res = db.rawQuery( "select id from "+TABLE_RESTAURANT_NAME +" where "+COL2_TABLE_RESTAURANT+"='"+name_restaurant+"'", null );
        res.moveToFirst();
        while(res.isAfterLast() == false) {
            id_restaurant = res.getInt(0);
            res.moveToNext();
        }
        return id_restaurant;
    }

    public ArrayList getRestaurantListMenu(int id_restaurant)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Food> menu = new ArrayList<Food>();

        Cursor res = db.rawQuery( "select * from "+TABLE_MENU_NAME +" where ID_RESTAURANT="+id_restaurant, null );
        res.moveToFirst();
        while(res.isAfterLast() == false) {
            String food_name = res.getString(2);
            String food_description = res.getString(3);
            double food_price = res.getDouble(4);

            Food f = new Food(food_name,food_description,food_price);
            menu.add(f);
            res.moveToNext();
        }
        return menu;
    }

    public boolean checkIfLoginIsGood(String email,String password)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor res = db.rawQuery("select * from " + TABLE_USER_NAME + " where " + COL3_TABLE_USER + "='" + email +
                    "' and " + COL4_TABLE_USER + "='" + password + "'", null);
            if (res.getCount() == 1) {
                return true;
            }
        }
        catch (Exception e){ }

        return false;
    }

    public boolean checkIfEmailExists(String email)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery("select * from " + TABLE_USER_NAME + " where " + COL3_TABLE_USER + "='" + email + "'", null);
        if (res.getCount() ==1) {
            return true;
        }
        else
            return false;

    }

    public boolean setUserData(String name,String email,String password)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = -1;

        ContentValues userContentValues = new ContentValues();
        userContentValues.put(COL2_TABLE_USER,name);
        userContentValues.put(COL3_TABLE_USER,email);
        userContentValues.put(COL4_TABLE_USER,password);

        result = db.insert(TABLE_USER_NAME,null,userContentValues);

        if(result==-1)return false;
        else return true;
    }

    public String getUsernameByEmail(String email)
    {
        SQLiteDatabase db = getReadableDatabase();
        String name="";
        Cursor res = db.rawQuery( "select * from "+TABLE_USER_NAME +" where "+COL3_TABLE_USER+"='"+email+"'", null );
        res.moveToFirst();
        while(res.isAfterLast() == false) {
            name = res.getString(1);
            res.moveToNext();
        }
        return name;

    }







}
