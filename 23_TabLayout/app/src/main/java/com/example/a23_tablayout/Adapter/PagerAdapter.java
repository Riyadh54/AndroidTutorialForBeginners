package com.example.a23_tablayout.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.a23_tablayout.Model.MyTabsModel;

import java.util.ArrayList;

public class PagerAdapter extends FragmentStatePagerAdapter {
   private ArrayList<MyTabsModel> data ;

    public PagerAdapter(@NonNull FragmentManager fm,ArrayList<MyTabsModel> data){
        super(fm);
        this.data = data;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return data.get(position).getFragment();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return data.get(position).getCategoryModel().getTabName();
    }

    @Override
    public int getCount() {
        return data.size();
    }
}
