package com.example.a28_roomdatabase.DataBase;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;
import androidx.room.Update;

import java.util.List;

@Dao
@TypeConverters({DateConverter.class})
public interface CarsDetailsDAO {

    @Insert
    void insertCar(CarsDetails... carsDetails);
    @Update
    void updateCar(CarsDetails... carsDetails);
    @Delete
    void deleteCar(CarsDetails... carsDetails);
    @Query("delete from cars_table where model=:model")
    void deleteCarByModel(String model);
    @Query("select * from cars_table order by name asc")
    LiveData<List<CarsDetails>> getAllCars();
    @Query("select * from cars_table where name=:name")
    LiveData<List<CarsDetails>> getCarsByName(String name);
    @Query("select * from cars_table where model like '%'||:model||'%' ")
    LiveData<List<CarsDetails>> getCarsByModel(String model);

}
