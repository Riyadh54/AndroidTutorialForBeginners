package com.example.a28_roomdatabase.DataBase;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "cars_table")
@TypeConverters({DateConverter.class})
public class CarsDetails implements Serializable{
    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "_ID")
    private long id;
    @NonNull
    private String name;
    @NonNull
    private String model;
    @NonNull
    private Date date;

    public CarsDetails(){
    }

    public CarsDetails(long id, @NonNull String name, @NonNull String model,@NonNull Date date) {
        this.id = id;
        this.name = name;
        this.model = model;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getModel() {
        return model;
    }

    public void setModel(@NonNull String model) {
        this.model = model;
    }







    @NonNull
    public Date getDate() {
        return date;
    }

    public void setDate(@NonNull Date date) {
        this.date = date;
    }
}
