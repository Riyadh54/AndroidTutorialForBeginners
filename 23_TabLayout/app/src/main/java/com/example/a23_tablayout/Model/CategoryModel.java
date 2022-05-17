package com.example.a23_tablayout.Model;

public class CategoryModel {
    private int id ;
    private String tabName;

    public CategoryModel(int id, String tabName) {
        this.id = id;
        this.tabName = tabName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }
}
