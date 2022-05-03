package com.example.a21_externalsqlitedatabase;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    LinearLayout ll;
    Button btn_save,btn_restore,btn_delete,btn_update;
    RecyclerView rv;
    private boolean isAdapter = false;


    EditText et_carName,et_carModel,et_carColor,et_carDistanceForLitre;
    DataBaseAccess db;
    ArrayList<CarModel> arrayList;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //////// inflate Views
        et_carName = findViewById(R.id.car_name);
        et_carModel = findViewById(R.id.car_model);
        et_carColor = findViewById(R.id.car_color);
        et_carDistanceForLitre = findViewById(R.id.car_distance_for_litre);
        ll = findViewById(R.id.linear_layout);
        btn_save = findViewById(R.id.main_btn_save_data);
        btn_restore = findViewById(R.id.main_btn_restore_data);
        btn_delete = findViewById(R.id.main_btn_delete_data);
        btn_update = findViewById(R.id.main_btn_update_data);
        rv = findViewById(R.id.main_rv);
        /////////////////////

        db = DataBaseAccess.getInstance(MainActivity.this);
        arrayList = new ArrayList<>();



        // Buttons
        btn_restore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restoreItems();
            }});
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              updateItems();
            }});
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveItems();
            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteItems();
            }
        });


    }

    // Methods
    private void restoreItems(){
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
                    db.openDataBase();
                    arrayList = db.getAllData();
                    db.closeDataBase();
                    if (arrayList.size()== 0){
                        Toast.makeText(MainActivity.this, getResources().getText(R.string.empty), Toast.LENGTH_SHORT).show();
                    }else {
                        showData();
                        isAdapter = true;
                    }
                }else {
                    String name = car_name.getText().toString();
                    CarModel carModel =new CarModel(name,null,null,0);
                    db.openDataBase();
                    arrayList = db.getDataByCarName(carModel);
                    db.closeDataBase();
                    if (arrayList.size()== 0){
                        Toast.makeText(MainActivity.this, getResources().getText(R.string.there_is_no), Toast.LENGTH_SHORT).show();
                    }else {
                        showData();
                        isAdapter = true;
                    }


                }

            }
        });
        dialog.create().show();


    }

    private void updateItems(){
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
                        db.openDataBase();
                        db.updateAllItem(carModel);
                        arrayList = db.getAllData();
                        db.closeDataBase();
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
                        db.openDataBase();
                        db.updateItem(carModel,carModel1);
                        arrayList = db.getAllData();
                        db.closeDataBase();
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

    private void saveItems(){
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
            db.openDataBase();
            db.insert_item(carModel);
            arrayList = db.getAllData();
            db.closeDataBase();
            if (isAdapter){
                if (arrayList.size()== 0){
                    Toast.makeText(MainActivity.this, getResources().getText(R.string.empty), Toast.LENGTH_SHORT).show();
                }else {
                    showData();
                }
            }


        }
    }


    private void deleteItems(){
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
                    db.openDataBase();
                    db.delete_all();
                    arrayList = db.getAllData();
                    db.closeDataBase();
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
                        db.openDataBase();
                        db.delete_item(carModel);
                        arrayList = db.getAllData();
                        db.closeDataBase();
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

    private void showData(){
        adapter = new MyAdapter(this,R.layout.items_layout,arrayList);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rv.setLayoutManager(lm);
        rv.setHasFixedSize(true);
        rv.setAdapter(adapter);
    }
}