package com.example.a10_customadapter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lv;
    ArrayList<CountryModel> arrayList;
    MyAdapter adapter;

    int[] img = {R.drawable.algeria,R.drawable.moroco,R.drawable.tunisia,R.drawable.libya,R.drawable.egypt,R.drawable.sudan,R.drawable.saudiarabia,R.drawable.qatar,R.drawable.jordan};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = findViewById(R.id.main_lv_country_items);

        arrayList = new ArrayList<>();

        String[] name = getResources().getStringArray(R.array.countries);



        for (int i =0;i<name.length;i++){
            arrayList.add(new CountryModel(name[i],img[i]));
        }

        adapter = new MyAdapter(MainActivity.this,R.layout.items_layout,arrayList);
        lv.setAdapter(adapter);
    }
}