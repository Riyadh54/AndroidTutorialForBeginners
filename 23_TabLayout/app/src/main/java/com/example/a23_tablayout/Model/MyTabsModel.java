package com.example.a23_tablayout.Model;

import androidx.fragment.app.Fragment;

public class MyTabsModel {
    private Fragment fragment;
    private CategoryModel categoryModel;

    public MyTabsModel(Fragment fragment, CategoryModel categoryModel) {
        this.fragment = fragment;
        this.categoryModel = categoryModel;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public CategoryModel getCategoryModel() {
        return categoryModel;
    }

    public void setCategoryModel(CategoryModel categoryModel) {
        this.categoryModel = categoryModel;
    }
}
