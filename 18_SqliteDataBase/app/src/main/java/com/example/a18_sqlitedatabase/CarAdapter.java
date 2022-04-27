package com.example.a18_sqlitedatabase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CarAdapter extends BaseAdapter {
    private Context context;
    private int resources;
    private ArrayList<CarModel> data;

    public CarAdapter(Context context, int resources, ArrayList<CarModel> arrayList) {
        this.context = context;
        this.resources = resources;
        this.data = arrayList;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public CarModel getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        if (v ==null)
        {
            v = LayoutInflater.from(context).inflate(resources,null,false);
        }
        TextView car_id = v.findViewById(R.id.tv_car_id);
        TextView car_name = v.findViewById(R.id.tv_car_name);
        TextView car_model = v.findViewById(R.id.tv_car_model);
        TextView car_color = v.findViewById(R.id.tv_car_color);
        TextView distance_for_litre = v.findViewById(R.id.tv_car_dfl);

        int id = getItem(i).getId();
        String name = getItem(i).getCarName();
        String model = getItem(i).getCarModel();
        String color = getItem(i).getCarColor();
        double distanceForLitre = getItem(i).getCarDistanceForLitre();

        car_id.setText(id+"");
        car_name.setText(name);
        car_model.setText(model);
        car_color.setText(color);
        distance_for_litre.setText(distanceForLitre+"");

        return v;
    }
}
