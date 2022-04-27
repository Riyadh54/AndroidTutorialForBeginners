package com.example.a18_sqlitedatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.BoringLayout;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    MyDataBase myDataBase;
    LinearLayout ll;
    Button btn_save,btn_restore,btn_delete,btn_update;

    EditText et_carName,et_carModel,et_carColor,et_carDistanceForLitre;
    ListView lv;
    ArrayList<CarModel> arrayList;
    CarAdapter adapter;
    private boolean isAdapter = false;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle(getResources().getText(R.string.app_name));
        //////// inflate Views
        et_carName = findViewById(R.id.car_name);
        et_carModel = findViewById(R.id.car_model);
        et_carColor = findViewById(R.id.car_color);
        et_carDistanceForLitre = findViewById(R.id.car_distance_for_litre);
        lv = findViewById(R.id.main_lv);
        ll = findViewById(R.id.linear_layout);
        btn_save = findViewById(R.id.main_btn_save_data);
        btn_restore = findViewById(R.id.main_btn_restore_data);
        btn_delete = findViewById(R.id.main_btn_delete_data);
        btn_update = findViewById(R.id.main_btn_update_data);
        /////////////////////
        myDataBase = new MyDataBase(MainActivity.this);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        editor = sharedPreferences.edit();



        // Buttons




        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = et_carName.getText().toString();
                String model = et_carModel.getText().toString();
                String color = et_carColor.getText().toString();
                String distance_string = et_carDistanceForLitre.getText().toString();
                if (name.isEmpty() && model.isEmpty() && color.isEmpty() && distance_string.isEmpty())
                {
                    Toast.makeText(MainActivity.this, getResources().getText(R.string.fill_fields), Toast.LENGTH_SHORT).show();
                }else {
                    double distance = Double.parseDouble(distance_string);
                    CarModel carModel = new CarModel(name,model,color,distance);
                    AlertDialog.Builder dialogUpdate = new AlertDialog.Builder(MainActivity.this);
                    dialogUpdate.setTitle(getResources().getText(R.string.update_data));
                    View v =LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_update_layout,null,false);
                    dialogUpdate.setView(v);
                    Spinner spinner = v.findViewById(R.id.dialog_update_sp);
                    EditText et_name = v.findViewById(R.id.dialog_update_et_name);
                    EditText et_model = v.findViewById(R.id.dialog_update_et_model);
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            switch (i){
                                case 0:
                                    et_name.setVisibility(View.GONE);
                                    et_model.setVisibility(View.GONE);
                                    break;
                                case 1:
                                    et_name.setVisibility(View.VISIBLE);
                                    et_model.setVisibility(View.VISIBLE);
                                    break;

                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                    dialogUpdate.setPositiveButton(getResources().getText(R.string.select), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (spinner.getSelectedItemPosition() == 0)
                            {
                                // update All Item
                                myDataBase.updateAllItem(carModel);
                                arrayList = myDataBase.getAllData();
                                if (arrayList.size()== 0){
                                    Toast.makeText(MainActivity.this, getResources().getText(R.string.empty), Toast.LENGTH_SHORT).show();
                                }else {
                                    showData();
                                    isAdapter = true;
                                }
                            }else {
                                // update by name and model
                                String update_name =et_name.getText().toString();
                                String update_model =et_model.getText().toString();
                                CarModel carModel1 = new CarModel(update_name,update_model,null,0);
                                myDataBase.updateItem(carModel,carModel1);
                                arrayList = myDataBase.getAllData();
                                if (arrayList.size()== 0){
                                    Toast.makeText(MainActivity.this, getResources().getText(R.string.empty), Toast.LENGTH_SHORT).show();
                                }else {
                                    showData();
                                    isAdapter = true;
                                }
                            }

                        }
                    });
                    dialogUpdate.setNegativeButton(getResources().getText(R.string.cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });



                    dialogUpdate.create().show();
                }

            }
        });




        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = et_carName.getText().toString();
                String model = et_carModel.getText().toString();
                String color = et_carColor.getText().toString();
                String distance_string = et_carDistanceForLitre.getText().toString();
                if (name.isEmpty() && model.isEmpty() && color.isEmpty() && distance_string.isEmpty())
                {
                    Toast.makeText(MainActivity.this, getResources().getText(R.string.fill_fields), Toast.LENGTH_SHORT).show();
                }else {
                    double distance = Double.parseDouble(distance_string);
                    CarModel carModel = new CarModel(name,model,color,distance);
                    myDataBase.insert_item(carModel);
                    arrayList = myDataBase.getAllData();
                    if (isAdapter){
                        if (arrayList.size()== 0){
                            Toast.makeText(MainActivity.this, getResources().getText(R.string.empty), Toast.LENGTH_SHORT).show();
                        }else {
                            showData();
                        }
                    }


                }


            }
        });
        btn_restore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle(getResources().getText(R.string.restore_data));
                View v = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_search_layout,null,false);
                dialog.setView(v);
                Spinner spinner = v.findViewById(R.id.dialog_search_sp);
                EditText car_name =v.findViewById(R.id.dialog_search_et_name);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        switch (i){
                            case 0:
                                car_name.setVisibility(View.GONE);
                                break;
                            case 1 :
                                car_name.setVisibility(View.VISIBLE);
                                break;

                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                dialog.setPositiveButton(getResources().getText(R.string.select), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (spinner.getSelectedItemPosition() == 0)
                        {
                            arrayList = myDataBase.getAllData();
                            if (arrayList.size()== 0){
                                Toast.makeText(MainActivity.this, getResources().getText(R.string.empty), Toast.LENGTH_SHORT).show();
                            }else {
                                showData();
                                isAdapter = true;
                            }
                        }else {
                            String name = car_name.getText().toString();
                            CarModel carModel =new CarModel(name,null,null,0);
                            arrayList = myDataBase.getDataByCarName(carModel);
                            if (arrayList.size()== 0){
                                Toast.makeText(MainActivity.this, getResources().getText(R.string.empty), Toast.LENGTH_SHORT).show();
                            }else {
                                showData();
                                isAdapter = true;
                            }


                        }

                    }
                });
                dialog.create().show();


            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle(getResources().getText(R.string.delete_data));
                View v = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_layout,null,false);
                dialog.setView(v);
                Spinner spinner = v.findViewById(R.id.dialog_layout_sp);
                EditText car_name =v.findViewById(R.id.dialog_layout_et_name);
                EditText car_model =v.findViewById(R.id.dialog_layout_et_model);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        switch (i){
                            case 0:
                                car_name.setVisibility(View.GONE);
                                car_model.setVisibility(View.GONE);
                                break;
                            case 1 :
                                car_name.setVisibility(View.VISIBLE);
                                car_model.setVisibility(View.VISIBLE);
                                break;

                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                dialog.setPositiveButton(getResources().getText(R.string.select), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (spinner.getSelectedItemPosition() ==0){
                            myDataBase.delete_all();
                            arrayList = myDataBase.getAllData();
                            if (arrayList.size()== 0){
                                Toast.makeText(MainActivity.this, getResources().getText(R.string.empty), Toast.LENGTH_SHORT).show();
                                showData();
                                ll.setVisibility(View.INVISIBLE);
                            }else {
                                showData();

                            }
                        }else {
                            String name =car_name.getText().toString();
                            String model =car_model.getText().toString();
                            if (name.isEmpty() && model.isEmpty())
                            {
                                Toast.makeText(MainActivity.this, getResources().getText(R.string.nothing_delete), Toast.LENGTH_SHORT).show();
                            }else {
                                CarModel carModel =new CarModel(name,model,null,0);
                                myDataBase.delete_item(carModel);
                                arrayList = myDataBase.getAllData();
                                if (arrayList.size()== 0){
                                    Toast.makeText(MainActivity.this, getResources().getText(R.string.empty), Toast.LENGTH_SHORT).show();
                                    showData();
                                    ll.setVisibility(View.INVISIBLE);
                                }else {
                                    showData();
                                }
                            }

                        }

                    }
                });
                dialog.setNegativeButton(getResources().getText(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                dialog.create().show();

            }
        });
        












    }



    private void showData(){
        adapter = new CarAdapter(MainActivity.this,R.layout.items_layout,arrayList);
        lv.setAdapter(adapter);
        ll.setVisibility(View.VISIBLE);
    }
}