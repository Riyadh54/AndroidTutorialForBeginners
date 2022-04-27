package com.example.a18_sqlitedatabase;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;

import androidx.annotation.Nullable;

import java.io.InputStreamReader;
import java.util.ArrayList;

public class MyDataBase extends SQLiteOpenHelper {
    private final static int VERSION_CODE = 1;
    private final static String MY_DATA_BASE_NAME = "CarsDB.db";
    private final static String TABLE_NAME = "cars";
    private final static String CAR_NAME = "name";
    private final static String CAR_MODEL = "model";
    private final static String CAR_COLOR = "color";
    private final static String CAR_DISTANCE_FOR_LITRE = "distanceForLitre";
    private final Context context;

    public MyDataBase(@Nullable Context context) {
        super(context, MY_DATA_BASE_NAME, null, VERSION_CODE);
        this.context = context;

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_NAME+"( id INTEGER PRIMARY KEY AUTOINCREMENT, "+CAR_NAME+" TEXT NOT NULL ," +CAR_MODEL+" TEXT NOT NULL, "+CAR_COLOR+"  TEXT NOT NULL, "+CAR_DISTANCE_FOR_LITRE+"  REAL )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);

    }

    public boolean insert_item(CarModel carModel){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CAR_NAME,carModel.getCarName());
        contentValues.put(CAR_MODEL,carModel.getCarModel());
        contentValues.put(CAR_COLOR,carModel.getCarColor());
        contentValues.put(CAR_DISTANCE_FOR_LITRE,carModel.getCarDistanceForLitre());

        long result  = db.insert(TABLE_NAME,null,contentValues);

        return result == -1;
    }
    public boolean  delete_all(){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        int result = sqLiteDatabase.delete(TABLE_NAME,null,null);
        return result>0;

    }
    public boolean delete_item(CarModel carModel){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String[] args = new String[]{carModel.getCarName(),carModel.getCarModel()};
        // we can use
        //String[] args = new String[]{"%"+carModel.getCarModel()+"%"};
        //int result = sqLiteDatabase.delete(TABLE_NAME,"model LIKE ?",args);

        int result = sqLiteDatabase.delete(TABLE_NAME,CAR_NAME+"=? and "+CAR_MODEL+"=?",args);
        return result>0;
    }

    public ArrayList<CarModel> getAllData(){
        SQLiteDatabase sqLiteDatabase =getReadableDatabase();
        ArrayList<CarModel> data = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_NAME,null);

        if (cursor !=null && cursor.moveToFirst()){
            do {
                // we can use    int id = cursor.getInt(0);
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String car_name = cursor.getString(cursor.getColumnIndex(CAR_NAME));
                String car_model = cursor.getString(cursor.getColumnIndex(CAR_MODEL));
                String car_color = cursor.getString(cursor.getColumnIndex(CAR_COLOR));
                double car_distance = cursor.getDouble(cursor.getColumnIndex(CAR_DISTANCE_FOR_LITRE));

                CarModel carModel = new CarModel(id,car_name,car_model,car_color,car_distance);
                data.add(carModel);

            }while (cursor.moveToNext());
            cursor.close();
        }

        return data;

    }
    public ArrayList<CarModel> getDataByCarName(CarModel carModel){
        SQLiteDatabase sqLiteDatabase =getReadableDatabase();
        ArrayList<CarModel> data = new ArrayList<>();
        String[] args =new String[]{carModel.getCarName()};
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE name=?",args);

        if (cursor !=null && cursor.moveToFirst()){
            do {
                // we can use    int id = cursor.getInt(0);
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String car_name = cursor.getString(cursor.getColumnIndex(CAR_NAME));
                String car_model = cursor.getString(cursor.getColumnIndex(CAR_MODEL));
                String car_color = cursor.getString(cursor.getColumnIndex(CAR_COLOR));
                double car_distance = cursor.getDouble(cursor.getColumnIndex(CAR_DISTANCE_FOR_LITRE));

                CarModel carModel1 = new CarModel(id,car_name,car_model,car_color,car_distance);
                data.add(carModel1);

            }while (cursor.moveToNext());
            cursor.close();
        }

        return data;

    }

    public boolean updateAllItem(CarModel carModel){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues =new ContentValues();
        contentValues.put(CAR_NAME,carModel.getCarName());
        contentValues.put(CAR_MODEL,carModel.getCarModel());
        contentValues.put(CAR_COLOR,carModel.getCarColor());
        contentValues.put(CAR_DISTANCE_FOR_LITRE,carModel.getCarDistanceForLitre());
        int result = sqLiteDatabase.update(TABLE_NAME,contentValues,null,null);
       return result >0;

    }
    public boolean updateItem(CarModel carModel,CarModel carModel1){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues =new ContentValues();
        contentValues.put(CAR_NAME,carModel.getCarName());
        contentValues.put(CAR_MODEL,carModel.getCarModel());
        contentValues.put(CAR_COLOR,carModel.getCarColor());
        contentValues.put(CAR_DISTANCE_FOR_LITRE,carModel.getCarDistanceForLitre());
        String[] args = new String[]{carModel1.getCarName(),carModel1.getCarModel()};
        int result = sqLiteDatabase.update(TABLE_NAME,contentValues,CAR_NAME+"=? and "+CAR_MODEL+"=?",args);
        return result >0;

    }


    public long dataBase_size(){
        SQLiteDatabase db = getReadableDatabase();
        long result = DatabaseUtils.queryNumEntries(db,TABLE_NAME);
        return  result;
    }

}
