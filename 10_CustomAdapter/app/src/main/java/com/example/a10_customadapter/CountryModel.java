package com.example.a10_customadapter;

public class CountryModel {
    private String countryName;
    private int countryImg;

    public CountryModel(String countryName,int countryImg){
        this.countryName = countryName;
        this.countryImg = countryImg;
    }

    public String getCountryName(){
        return countryName;
    }
    public void setCountryName(String countryName1){
        this.countryName = countryName1;
    }

    public int getCountryImg() {
        return countryImg;
    }

    public void setCountryImg(int countryImg) {
        this.countryImg = countryImg;
    }
}
