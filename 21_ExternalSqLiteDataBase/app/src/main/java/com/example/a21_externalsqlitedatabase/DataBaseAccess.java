package com.example.a21_externalsqlitedatabase;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DataBaseAccess {

    private SQLiteDatabase dataBase;
    private SQLiteOpenHelper openHelper;
    private static DataBaseAccess instance;

    private DataBaseAccess(Context context){
        this.openHelper = new MyDataBase(context);
    }
    public static DataBaseAccess getInstance(Context c){
        if (instance == null){
            instance = new DataBaseAccess(c);
        }
        return instance;
    }

    public void openDataBase(){
        this.dataBase = this.openHelper.getWritableDatabase();
    }
    public void closeDataBase(){
        if (this.dataBase != null)
        {
            this.dataBase.close();
        }
    }





    public boolean insert_item(CarModel carModel){
        ContentValues contentValues = new ContentValues();
        contentValues.put(MyDataBase.CAR_NAME,carModel.getCarName());
        contentValues.put(MyDataBase.CAR_MODEL,carModel.getCarModel());
        contentValues.put(MyDataBase.CAR_COLOR,carModel.getCarColor());
        contentValues.put(MyDataBase.CAR_DISTANCE_FOR_LITRE,carModel.getCarDistanceForLitre());

        long result  = dataBase.insert(MyDataBase.TABLE_NAME,null,contentValues);

        return result == -1;
    }
    public boolean  delete_all(){
        int result = dataBase.delete(MyDataBase.TABLE_NAME,null,null);
        return result>0;

    }
    public boolean delete_item(CarModel carModel){
        String[] args = new String[]{carModel.getCarName(),carModel.getCarModel()};
        // we can use
        //String[] args = new String[]{"%"+carModel.getCarModel()+"%"};
        //int result = sqLiteDatabase.delete(TABLE_NAME,"model LIKE ?",args);

        int result = dataBase.delete(MyDataBase.TABLE_NAME,MyDataBase.CAR_NAME+"=? and "+MyDataBase.CAR_MODEL+"=?",args);
        return result>0;
    }

    public ArrayList<CarModel> getAllData(){
        ArrayList<CarModel> data = new ArrayList<>();
        Cursor cursor = dataBase.rawQuery("SELECT * FROM "+MyDataBase.TABLE_NAME,null);

        if (cursor !=null && cursor.moveToFirst()){
            do {
                // we can use    int id = cursor.getInt(0);
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String car_name = cursor.getString(cursor.getColumnIndex(MyDataBase.CAR_NAME));
                String car_model = cursor.getString(cursor.getColumnIndex(MyDataBase.CAR_MODEL));
                String car_color = cursor.getString(cursor.getColumnIndex(MyDataBase.CAR_COLOR));
                double car_distance = cursor.getDouble(cursor.getColumnIndex(MyDataBase.CAR_DISTANCE_FOR_LITRE));

                CarModel carModel = new CarModel(id,car_name,car_model,car_color,car_distance);
                data.add(carModel);

            }while (cursor.moveToNext());
            cursor.close();
        }

        return data;

    }
    public ArrayList<CarModel> getDataByCarName(CarModel carModel){
        ArrayList<CarModel> data = new ArrayList<>();
        String[] args =new String[]{carModel.getCarName()};
        Cursor cursor = dataBase.rawQuery("SELECT * FROM "+MyDataBase.TABLE_NAME+" WHERE name=?",args);

        if (cursor !=null && cursor.moveToFirst()){
            do {
                // we can use    int id = cursor.getInt(0);
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String car_name = cursor.getString(cursor.getColumnIndex(MyDataBase.CAR_NAME));
                String car_model = cursor.getString(cursor.getColumnIndex(MyDataBase.CAR_MODEL));
                String car_color = cursor.getString(cursor.getColumnIndex(MyDataBase.CAR_COLOR));
                double car_distance = cursor.getDouble(cursor.getColumnIndex(MyDataBase.CAR_DISTANCE_FOR_LITRE));

                CarModel carModel1 = new CarModel(id,car_name,car_model,car_color,car_distance);
                data.add(carModel1);

            }while (cursor.moveToNext());
            cursor.close();
        }

        return data;

    }

    public boolean updateAllItem(CarModel carModel){
        ContentValues contentValues =new ContentValues();
        contentValues.put(MyDataBase.CAR_NAME,carModel.getCarName());
        contentValues.put(MyDataBase.CAR_MODEL,carModel.getCarModel());
        contentValues.put(MyDataBase.CAR_COLOR,carModel.getCarColor());
        contentValues.put(MyDataBase.CAR_DISTANCE_FOR_LITRE,carModel.getCarDistanceForLitre());
        int result = dataBase.update(MyDataBase.TABLE_NAME,contentValues,null,null);
        return result >0;

    }
    public boolean updateItem(CarModel carModel,CarModel carModel1){
        ContentValues contentValues =new ContentValues();
        contentValues.put(MyDataBase.CAR_NAME,carModel.getCarName());
        contentValues.put(MyDataBase.CAR_MODEL,carModel.getCarModel());
        contentValues.put(MyDataBase.CAR_COLOR,carModel.getCarColor());
        contentValues.put(MyDataBase.CAR_DISTANCE_FOR_LITRE,carModel.getCarDistanceForLitre());
        String[] args = new String[]{carModel1.getCarName(),carModel1.getCarModel()};
        int result = dataBase.update(MyDataBase.TABLE_NAME,contentValues,MyDataBase.CAR_NAME+"=? and "+MyDataBase.CAR_MODEL+"=?",args);
        return result >0;

    }


    public long dataBase_size(){
        long result = DatabaseUtils.queryNumEntries(dataBase,MyDataBase.TABLE_NAME);
        return  result;
    }

}
