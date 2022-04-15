package com.example.a10_customadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.prefs.BackingStoreException;

public class MyAdapter extends BaseAdapter {
    private Context c;
    private int resource;
    private ArrayList<CountryModel> data;

    public MyAdapter(Context c,int resource,ArrayList<CountryModel> data){
        this.c = c;
        this.resource = resource;
        this.data = data;

    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public CountryModel getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = view;
        if (v == null){
            v = LayoutInflater.from(c).inflate(resource,null,false);
        }

        TextView tv_name = v.findViewById(R.id.country_name);
        ImageView img_country = v.findViewById(R.id.country_flag);

        String name = getItem(i).getCountryName();
        int img = getItem(i).getCountryImg();

        tv_name.setText(name);
        img_country.setImageResource(img);


        return v;
    }
}
