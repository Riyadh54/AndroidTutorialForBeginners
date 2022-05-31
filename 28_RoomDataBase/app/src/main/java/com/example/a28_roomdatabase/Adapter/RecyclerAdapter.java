package com.example.a28_roomdatabase.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a28_roomdatabase.DataBase.CarsDetails;
import com.example.a28_roomdatabase.DataBase.CarsPrices;
import com.example.a28_roomdatabase.DataBase.MyViewModel;
import com.example.a28_roomdatabase.Interfaces.DoubleValueListener;
import com.example.a28_roomdatabase.databinding.ItemsLayoutBinding;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    private List<CarsDetails> data;
    private MyViewModel myViewModel;

    public RecyclerAdapter(List<CarsDetails> data,MyViewModel myViewModel) {
        this.data = data;
        this.myViewModel = myViewModel;
    }

    public List<CarsDetails> getData() {
        notifyDataSetChanged();
        return data;
    }

    public void setData(List<CarsDetails> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemsLayoutBinding binding = ItemsLayoutBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CarsDetails carsDetails = data.get(position);
        holder.bindData(carsDetails,myViewModel);


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).getId();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
         ItemsLayoutBinding binding;
         CarsDetails carsDetails;

    public MyViewHolder(@NonNull ItemsLayoutBinding itemView) {
        super(itemView.getRoot());
        binding = itemView;
    }
    void bindData(CarsDetails carsDetails, MyViewModel myViewModel){
        this.carsDetails = carsDetails;
        binding.customItemsTvName.setText(carsDetails.getName());
        binding.customItemsTvModel.setText(carsDetails.getModel());
        myViewModel.getPricesSum(carsDetails.getId(), new DoubleValueListener() {
            @Override
            public void onValueSubmit(double value) {
                binding.customItemsTvPrice.setText(value+"$");
            }
        });
    }

}



}
