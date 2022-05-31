package com.example.a28_roomdatabase.DataBase;

public class CarsDetailsAndPrices {

    private String _carName;
    private double _carPrice;

    public CarsDetailsAndPrices(String _carName, double _carPrice) {
        this._carName = _carName;
        this._carPrice = _carPrice;
    }

    public CarsDetailsAndPrices() {
    }


    public String get_carName() {
        return _carName;
    }

    public void set_carName(String _carName) {
        this._carName = _carName;
    }

    public double get_carPrice() {
        return _carPrice;
    }

    public void set_carPrice(double _carPrice) {
        this._carPrice = _carPrice;
    }
}
