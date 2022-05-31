package com.example.a28_roomdatabase.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a28_roomdatabase.DataBase.CarsDetails;
import com.example.a28_roomdatabase.DataBase.CarsDetailsAndPrices;
import com.example.a28_roomdatabase.DataBase.CarsPrices;
import com.example.a28_roomdatabase.DataBase.MyViewModel;
import com.example.a28_roomdatabase.Interfaces.DoubleValueListener;
import com.example.a28_roomdatabase.databinding.ItemsLayoutBinding;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter2 extends RecyclerView.Adapter<RecyclerAdapter2.MyViewHolder> {
    private List<CarsDetailsAndPrices> data;

    public RecyclerAdapter2(List<CarsDetailsAndPrices> data) {
        this.data = data;
    }

    public List<CarsDetailsAndPrices> getData() {
        notifyDataSetChanged();
        return data;
    }

    public void setData(List<CarsDetailsAndPrices> data) {
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
        CarsDetailsAndPrices carsDetails = data.get(position);
        holder.bindData(carsDetails);


    }

    @Override
    public int getItemCount() {
        return data.size();
    }



    class MyViewHolder extends RecyclerView.ViewHolder {
        ItemsLayoutBinding binding;
        CarsDetailsAndPrices carsDetails;

        public MyViewHolder(@NonNull ItemsLayoutBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
        void bindData(CarsDetailsAndPrices carsDetails){
            this.carsDetails = carsDetails;
            binding.customItemsTvName.setText(carsDetails.get_carName());
            binding.customItemsTvPrice.setText(carsDetails.get_carPrice()+"$");

        }

    }



}
