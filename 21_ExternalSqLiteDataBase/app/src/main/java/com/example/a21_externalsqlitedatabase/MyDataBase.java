package com.example.a21_externalsqlitedatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class MyDataBase extends SQLiteAssetHelper {
    public static final  int VERSION_CODE = 1;
    public  static final  String MY_DATA_BASE_NAME = "CarsDB.db";
    public  static final  String TABLE_NAME = "cars";
    public static final  String CAR_NAME = "name";
    public static final  String CAR_MODEL = "model";
    public static final  String CAR_COLOR = "color";
    public static final  String CAR_DISTANCE_FOR_LITRE = "distanceForLitre";



    public MyDataBase(Context context) {
        super(context, MY_DATA_BASE_NAME, null, VERSION_CODE);

    }



}
