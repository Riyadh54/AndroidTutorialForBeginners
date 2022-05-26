package com.example.viewpager2.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.viewpager2.Model.ProductModel;
import com.example.viewpager2.databinding.CustomItemsBinding;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    private ArrayList<ProductModel> data ;

    public RecyclerAdapter(ArrayList<ProductModel> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CustomItemsBinding binding = CustomItemsBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ProductModel productModel = data.get(position);
        holder.bindData(productModel);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        CustomItemsBinding binding;
        ProductModel productModel ;

        public MyViewHolder(@NonNull CustomItemsBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        public void bindData(ProductModel productModel){
            this.productModel = productModel;
            binding.customItemsTvName.setText(productModel.getName());
            binding.customItemsTvPrice.setText(String.valueOf(productModel.getPrice())+"$");
            binding.customItemsImg.setImageResource(productModel.getImg());
        }
    }
}
