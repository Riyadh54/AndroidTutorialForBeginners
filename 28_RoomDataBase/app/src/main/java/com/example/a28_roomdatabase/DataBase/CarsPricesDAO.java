package com.example.a28_roomdatabase.DataBase;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;
import androidx.room.Update;

import java.util.Date;
import java.util.List;

@Dao
@TypeConverters({DateConverter.class})
public interface CarsPricesDAO {

    @Insert
    void insertPrice(CarsPrices carsPrices);
    @Update
    void updatePrice(CarsPrices carsPrices);
    @Delete
    void deletePrice(CarsPrices carsPrices);
    @Query("select * from cars_prices where carId=:carId order by date desc")
    LiveData<List<CarsPrices>> getPriceByCarName(Long carId);
    @Query("select * from cars_prices where carId=:carId AND date>=:from AND date<=:to order by date desc")
    LiveData<List<CarsPrices>> getCarPrices(long carId, Date from,Date to);
    @Query("select * from cars_prices where date>=:from AND date<=:to order by date desc")
    LiveData<List<CarsPrices>> getCarPrices(Date from,Date to);
    @Query("select sum(price) from cars_prices where carId=:carId")
    double getPricesSum(long carId);
    // get Cars And Prices
    // Merge Tables
    @Query("select sum(cars_prices.price) as _carPrice,cars_table.model as _carName from cars_prices INNER JOIN cars_table ON cars_prices.carId=cars_table._ID group by name")
    LiveData<List<CarsDetailsAndPrices>> getAllPricesSum();

}
