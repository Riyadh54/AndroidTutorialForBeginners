package com.example.a28_roomdatabase.DataBase;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateConverter {

    @TypeConverter
    public static Date toDate(Long millisecond){
        return millisecond==null?null:new Date(millisecond);
    }

    @TypeConverter
    public static Long toLong(Date date){
       return date == null?null: date.getTime();
    }
}
