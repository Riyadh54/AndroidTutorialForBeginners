package com.example.a28_roomdatabase.DataBase;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

@Entity(tableName = "cars_prices",foreignKeys = {@ForeignKey(entity = CarsDetails.class,parentColumns = {"_ID"},
        childColumns = {"carId"},onUpdate = ForeignKey.CASCADE,onDelete = ForeignKey.CASCADE)})
@TypeConverters({DateConverter.class})
public class CarsPrices {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    @NonNull
    private double price;
    @NonNull
    private Date date;
    @NonNull
    private long carId;

    public CarsPrices(){
    }

    public CarsPrices(int id, double price, @NonNull Date date, long carId) {
        this.id = id;
        this.price = price;
        this.date = date;
        this.carId = carId;
    }

    public CarsPrices(double price, @NonNull Date date, long carId) {
        this.price = price;
        this.date = date;
        this.carId = carId;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @NonNull
    public Date getDate() {
        return date;
    }

    public void setDate(@NonNull Date date) {
        this.date = date;
    }

    public long getCarId() {
        return carId;
    }

    public void setCarId(long carId) {
        this.carId = carId;
    }
}
