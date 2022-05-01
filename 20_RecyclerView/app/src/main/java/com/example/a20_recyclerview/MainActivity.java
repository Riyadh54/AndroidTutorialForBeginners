package com.example.a20_recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<CountriesModel> arrayList;
    int[] myImages = new int[]{R.drawable.algeria,R.drawable.moroco,R.drawable.tunisia,R.drawable.libya,R.drawable.egypt,R.drawable.sudan,R.drawable.saudiarabia,R.drawable.qatar,R.drawable.jordan};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // inflate Views
        recyclerView = findViewById(R.id.main_rv);
        //
        String[] names = getResources().getStringArray(R.array.countries);
        arrayList = new ArrayList<>();
        for (int i =0;i<names.length;i++){
            arrayList.add(new CountriesModel(myImages[i],names[i]));
        }
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(arrayList,MainActivity.this);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(lm);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recyclerAdapter);





    }









}