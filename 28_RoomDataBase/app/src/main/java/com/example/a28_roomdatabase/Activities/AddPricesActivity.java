package com.example.a28_roomdatabase.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.a28_roomdatabase.Adapter.SpinnerAdapter;
import com.example.a28_roomdatabase.DataBase.CarsDetails;
import com.example.a28_roomdatabase.DataBase.CarsPrices;
import com.example.a28_roomdatabase.DataBase.MyViewModel;
import com.example.a28_roomdatabase.R;
import com.example.a28_roomdatabase.databinding.ActivityAddPricesBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

public class AddPricesActivity extends AppCompatActivity {
     ActivityAddPricesBinding binding;
     final Calendar myCalendar= Calendar.getInstance();
     SpinnerAdapter spinnerAdapter;
     MyViewModel myViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddPricesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        updateLabel();

        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);
        spinnerAdapter = new SpinnerAdapter(new ArrayList<>());
        binding.addPriceSpinner.setAdapter(spinnerAdapter);
        myViewModel.getAllCars().observe(this, new Observer<List<CarsDetails>>() {
            @Override
            public void onChanged(List<CarsDetails> carsDetails) {
               spinnerAdapter.setData(carsDetails);
            }
        });




        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();

            }
        };
        binding.addPriceEtBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddPricesActivity.this,date,myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        binding.addPriceBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _price = binding.addPriceEtPrice.getText().toString();
                long carId = binding.addPriceSpinner.getSelectedItemId();
                if (TextUtils.isEmpty(_price)){
                    Toast.makeText(AddPricesActivity.this, "Please Fill the Fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                double price = Double.parseDouble(_price);
                CarsPrices carsPrices = new CarsPrices(price,myCalendar.getTime(),carId);
                myViewModel.insertPrice(carsPrices);
                finish();

            }
        });

    }

    private void updateLabel(){
        String myFormat="dd/MM/yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.FRENCH);
        binding.addPriceEtBirthday.setText(dateFormat.format(myCalendar.getTime()));
    }
}