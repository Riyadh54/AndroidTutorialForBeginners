package com.example.a28_roomdatabase.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.a28_roomdatabase.DataBase.CarsDetails;
import com.example.a28_roomdatabase.R;

import java.util.List;

public class SpinnerAdapter extends BaseAdapter {

    private List<CarsDetails> data;

    public SpinnerAdapter(List<CarsDetails> data) {
        this.data = data;
    }

    public List<CarsDetails> getData() {
        return data;
    }

    public void setData(List<CarsDetails> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public CarsDetails getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return data.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        if (v == null){
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_spinner_item,null,false);
        }
        TextView textView = v.findViewById(R.id.custom_spinner_tv);
        CarsDetails carsDetails = getItem(i);
        textView.setText(carsDetails.getModel());
        return v;
    }
}
