package com.example.a28_roomdatabase.Activities;

import static com.example.a28_roomdatabase.Activities.AddCarsActivity.ADD_CARS_KEY;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.pm.FeatureInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.a28_roomdatabase.Adapter.RecyclerAdapter;
import com.example.a28_roomdatabase.Adapter.RecyclerAdapter2;
import com.example.a28_roomdatabase.DataBase.CarsDetails;
import com.example.a28_roomdatabase.DataBase.CarsDetailsAndPrices;
import com.example.a28_roomdatabase.DataBase.CarsPrices;
import com.example.a28_roomdatabase.DataBase.MyViewModel;
import com.example.a28_roomdatabase.R;
import com.example.a28_roomdatabase.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    MyViewModel myViewModel;
    RecyclerAdapter adapter;
    RecyclerAdapter2 adapter2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);
        adapter= new RecyclerAdapter(new ArrayList<>(),myViewModel);
       // adapter2= new RecyclerAdapter2(new ArrayList<>());
        binding.mainRv.setAdapter(adapter);
        //binding.mainRv.setAdapter(adapter2);
        binding.mainRv.setHasFixedSize(true);
        binding.mainRv.setLayoutManager(new LinearLayoutManager(this));

        myViewModel.getAllCars().observe(this, new Observer<List<CarsDetails>>() {
            @Override
            public void onChanged(List<CarsDetails> carsDetails) {
                // here method view items into recyclerView
                adapter.setData(carsDetails);
            }
        });

/*
        myViewModel.getAllPricesSum().observe(this, new Observer<List<CarsDetailsAndPrices>>() {
            @Override
            public void onChanged(List<CarsDetailsAndPrices> carsDetailsAndPrices) {
                adapter2.setData(carsDetailsAndPrices);
            }
        });
        */





        ActivityResultLauncher<Intent> arl =  registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null)
                        {
                            CarsDetails carsDetails = (CarsDetails) result.getData().getSerializableExtra(ADD_CARS_KEY);
                            myViewModel.insertCar(carsDetails);
                        }
                    }
                }

        );

        binding.mainFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AddCarsActivity.class);
                arl.launch(intent);
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_add:
                Intent intent = new Intent(MainActivity.this,AddPricesActivity.class);
                startActivity(intent);
                return true;
        }
        return false;
    }
}