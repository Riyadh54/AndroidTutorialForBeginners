package com.example.a28_roomdatabase.DataBase;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.a28_roomdatabase.Interfaces.DoubleValueListener;

import java.util.Date;
import java.util.List;

public class MyViewModel extends AndroidViewModel {
    Repository repository;
    public MyViewModel(@NonNull Application application) {
        super(application);
        repository= new Repository(application);
    }


    // Cars Details Methods

    public void insertCar(CarsDetails... carsDetails){
        repository.insertCar(carsDetails);
    }

    public void updateCar(CarsDetails... carsDetails){
        repository.updateCar(carsDetails);
    }

    public void deleteCar(CarsDetails... carsDetails){
        repository.deleteCar(carsDetails);
    }

    public void deleteCarByModel(String model){
        repository.deleteCarByModel(model);
    }

    public LiveData<List<CarsDetails>> getAllCars(){
        return repository.getAllCars();
    }

    public LiveData<List<CarsDetails>> getCarsByName(String name){
        return repository.getCarsByName(name);
    }

    public LiveData<List<CarsDetails>> getCarsByModel(String model){
        return repository.getCarsByModel(model);
    }
    //Cars Prices Methods


    public void insertPrice(CarsPrices carsPrices){
        repository.insertPrice(carsPrices);
    }

    public void updatePrice(CarsPrices carsPrices){
        repository.updatePrice(carsPrices);
    }

    public void deletePrice(CarsPrices carsPrices){
        repository.deletePrice(carsPrices);
    }

    public LiveData<List<CarsPrices>> getPriceByCarName(Long carId){
        return repository.getPriceByCarName(carId);
    }

    public LiveData<List<CarsPrices>> getCarPrices(long carId, Date from, Date to){
        return repository.getCarPrices(carId,from,to);
    }

    public LiveData<List<CarsPrices>> getCarPrices(Date from,Date to){
        return repository.getCarPrices(from,to);
    }

    public void getPricesSum(long carId, DoubleValueListener listener){
        repository.getPricesSum(carId,listener);
    }
    public LiveData<List<CarsDetailsAndPrices>> getAllPricesSum(){
        return repository.getAllPricesSum();
    };

}
