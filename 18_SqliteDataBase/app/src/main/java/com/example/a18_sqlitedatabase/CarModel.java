package com.example.a18_sqlitedatabase;

import android.app.Application;

public class CarModel {
    private int id;
    private String carName;
    private String carModel;
    private String carColor;
    private double carDistanceForLitre;

    public CarModel(int id, String carName, String carModel, String carColor, double carDistanceForLitre) {
        this.id = id;
        this.carName = carName;
        this.carModel = carModel;
        this.carColor = carColor;
        this.carDistanceForLitre = carDistanceForLitre;
    }

    public CarModel(String carName, String carModel, String carColor, double carDistanceForLitre) {
        this.carName = carName;
        this.carModel = carModel;
        this.carColor = carColor;
        this.carDistanceForLitre = carDistanceForLitre;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public double getCarDistanceForLitre() {
        return carDistanceForLitre;
    }

    public void setCarDistanceForLitre(double carDistanceForLitre) {
        this.carDistanceForLitre = carDistanceForLitre;
    }
}
