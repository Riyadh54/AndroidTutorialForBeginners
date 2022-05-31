package com.example.a28_roomdatabase.DataBase;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.a28_roomdatabase.Interfaces.DoubleValueListener;

import java.util.Date;
import java.util.List;

public class Repository {
    CarsDetailsDAO carsDetailsDAO;
    CarsPricesDAO carsPricesDAO;

    public Repository(Application app){
        MyRoomDataBase db = MyRoomDataBase.getDatabase(app);
         carsDetailsDAO = db.carsDetailsDAO();
         carsPricesDAO = db.carsPricesDAO();

    }

    // Cars Details Methods

    public void insertCar(CarsDetails... carsDetails){
        MyRoomDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                carsDetailsDAO.insertCar(carsDetails);
            }
        });
    }

    public void updateCar(CarsDetails... carsDetails){
        MyRoomDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                carsDetailsDAO.updateCar(carsDetails);
            }
        });

    }

    public void deleteCar(CarsDetails... carsDetails){
        MyRoomDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                carsDetailsDAO.deleteCar(carsDetails);
            }
        });


    }

    public void deleteCarByModel(String model){
        MyRoomDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                carsDetailsDAO.deleteCarByModel(model);
            }
        });

    }

    public LiveData<List<CarsDetails>> getAllCars(){
        return carsDetailsDAO.getAllCars();

    }

    public LiveData<List<CarsDetails>> getCarsByName(String name){
        return carsDetailsDAO.getCarsByName(name);
    }

    public LiveData<List<CarsDetails>> getCarsByModel(String model){
        return carsDetailsDAO.getCarsByModel(model);
    }
    //Cars Prices Methods


    public void insertPrice(CarsPrices carsPrices){
        MyRoomDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                carsPricesDAO.insertPrice(carsPrices);
            }
        });
    }

    public void updatePrice(CarsPrices carsPrices){
        MyRoomDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                carsPricesDAO.updatePrice(carsPrices);
            }
        });
    }

    public void deletePrice(CarsPrices carsPrices){
        MyRoomDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                carsPricesDAO.deletePrice(carsPrices);
            }
        });
    }

   public LiveData<List<CarsPrices>> getPriceByCarName(Long carId){
        return carsPricesDAO.getPriceByCarName(carId);
    }

    public LiveData<List<CarsPrices>> getCarPrices(long carId, Date from, Date to){
        return carsPricesDAO.getCarPrices(carId,from,to);
    }

   public LiveData<List<CarsPrices>> getCarPrices(Date from,Date to){
       return carsPricesDAO.getCarPrices(from, to);
    }

    void getPricesSum(long carId, DoubleValueListener listener){
        MyRoomDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                double value = carsPricesDAO.getPricesSum(carId);
                listener.onValueSubmit(value);
            }
        });
    }


    public LiveData<List<CarsDetailsAndPrices>> getAllPricesSum(){
        return carsPricesDAO.getAllPricesSum();
    };



}
