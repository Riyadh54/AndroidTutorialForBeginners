package com.example.a29_firebase.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerViewAccessibilityDelegate;

import com.example.a29_firebase.Interfaces.onMoreClickListener;
import com.example.a29_firebase.Model.EmployeeModel;
import com.example.a29_firebase.databinding.CustomItemsBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import com.example.a29_firebase.Interfaces.onMoreClickListener;
import com.squareup.picasso.Picasso;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>{
    private ArrayList<EmployeeModel> data;
    private onMoreClickListener onMoreClickListener;
    private Context context;

    public RecyclerAdapter(ArrayList<EmployeeModel> data,onMoreClickListener onMoreClickListener,Context context) {
        this.data = data;
        this.onMoreClickListener = onMoreClickListener;
        this.context = context;
    }

    public ArrayList<EmployeeModel> getData() {
        return data;
    }

    public void setData(ArrayList<EmployeeModel> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CustomItemsBinding binding = CustomItemsBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        EmployeeModel employeeModel = data.get(position);
        if (employeeModel != null)
        {
            holder.bindData(employeeModel);
        }
        holder.binding.customItemsBtnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMoreClickListener.onClickListener(view, employeeModel.getPushId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        CustomItemsBinding binding;
        EmployeeModel _employeeModel;
        public MyViewHolder(@NonNull CustomItemsBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        void bindData(EmployeeModel employeeModel){
            this._employeeModel =employeeModel;
            binding.customItemsTvName.setText(employeeModel.getName());
            binding.customItemsTvEmail.setText("Email :"+employeeModel.getEmail());
            binding.customItemsTvSalary.setText("Salary :"+employeeModel.getSalary()+"$");
            String myFormat="dd/MM/yy";
            SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.FRENCH);
            binding.customItemsTvDateBirth.setText("BirthDay :"+dateFormat.format(toDate(employeeModel.getDateBirth())));
            if (!employeeModel.getImageUri().equals("")){
                Uri uri = Uri.parse(employeeModel.getImageUri());
                Picasso.with(context).load(uri).into(binding.customItemsImage);
            }

        }
    }

    public static Date toDate(Long millisecond){
        return millisecond==null?null:new Date(millisecond);
    }
}
