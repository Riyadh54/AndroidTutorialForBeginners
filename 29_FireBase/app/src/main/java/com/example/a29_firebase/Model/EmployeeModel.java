package com.example.a29_firebase.Model;

import java.util.Date;

public class EmployeeModel {
    private String pushId ;
    private String name;
    private String email;
    private String imageUri;
    private double salary;
    private long dateBirth;

    public EmployeeModel() {
    }


    public EmployeeModel(String pushId, String name, String email, String imageUri, double salary, long dateBirth) {
        this.pushId = pushId;
        this.name = name;
        this.email = email;
        this.imageUri = imageUri;
        this.salary = salary;
        this.dateBirth = dateBirth;
    }

    public EmployeeModel(String name, String email, String imageUri, double salary, long dateBirth) {
        this.name = name;
        this.email = email;
        this.imageUri = imageUri;
        this.salary = salary;
        this.dateBirth = dateBirth;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public long getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(long dateBirth) {
        this.dateBirth = dateBirth;
    }
}
