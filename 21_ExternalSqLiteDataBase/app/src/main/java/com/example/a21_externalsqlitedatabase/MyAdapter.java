package com.example.a21_externalsqlitedatabase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
    Context context;
    int resource;
    ArrayList<CarModel> data;

    public MyAdapter(Context c, int res, ArrayList<CarModel> arrayList){
        this.context = c;
        this.resource = res;
        this.data = arrayList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(resource,parent,false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CarModel carModel = data.get(position);
        holder.tv_id.setText(carModel.getId()+"");
        holder.tv_name.setText(carModel.getCarName());
        holder.tv_model.setText(carModel.getCarModel());
        holder.tv_color.setText(carModel.getCarColor());
        holder.tv_distance.setText(carModel.getCarDistanceForLitre()+"");

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_id,tv_name,tv_model,tv_color,tv_distance;
        public MyViewHolder(View itemView){
            super(itemView);
            tv_id = itemView.findViewById(R.id.tv_car_id);
            tv_name = itemView.findViewById(R.id.tv_car_name);
            tv_model = itemView.findViewById(R.id.tv_car_model);
            tv_color = itemView.findViewById(R.id.tv_car_color);
            tv_distance = itemView.findViewById(R.id.tv_car_dfl);
        }
    }
}
