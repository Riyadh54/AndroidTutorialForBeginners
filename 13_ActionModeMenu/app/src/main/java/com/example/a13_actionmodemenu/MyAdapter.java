package com.example.a13_actionmodemenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {
    private ArrayList<String> data;
    private int resources;
    private Context c;

    public MyAdapter(ArrayList<String> data, int resources, Context c) {
        this.data = data;
        this.resources = resources;
        this.c = c;
    }

    public void renameItem(String name,int pos){
        data.set(pos,name);
        notifyDataSetChanged();
    }
    public void deleteItem(int pos){
        data.remove(pos);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public String getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v =view;
        if (v == null){
            v = LayoutInflater.from(c).inflate(resources,null,false);
        }
        TextView tv = v.findViewById(R.id.tv_country_name);
        String name = getItem(i);
        tv.setText(name);


        return v;
    }
}
