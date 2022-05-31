package com.example.a28_roomdatabase.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.a28_roomdatabase.DataBase.CarsDetails;
import com.example.a28_roomdatabase.R;
import com.example.a28_roomdatabase.databinding.ActivityAddCarsBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddCarsActivity extends AppCompatActivity {
    ActivityAddCarsBinding binding;
    final Calendar myCalendar= Calendar.getInstance();
    public static final String ADD_CARS_KEY = "carsKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddCarsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        updateLabel();

        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();

            }
        };
        binding.addCarsEtBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddCarsActivity.this,date,myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        binding.addCarsBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.addCarsEtName.getText().toString();
                String model = binding.addCarsEtModel.getText().toString();
                String carId = binding.addCarsEtId.getText().toString();
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(model) || TextUtils.isEmpty(carId)){
                    Toast.makeText(AddCarsActivity.this, "Please fill the fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                long id = Long.parseLong(carId);
                CarsDetails carsDetails = new CarsDetails(id,name,model,myCalendar.getTime());
                Intent intent = new Intent();
                intent.putExtra(ADD_CARS_KEY,carsDetails);
                setResult(RESULT_OK,intent);
                finish();
            }
        });



    }

    private void updateLabel(){
        String myFormat="dd/MM/yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.FRENCH);
        binding.addCarsEtBirthday.setText(dateFormat.format(myCalendar.getTime()));
    }
}