package com.example.a7_areascalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    Button btn_calculate;
    EditText et_n1,et_n2;
    TextView tv_result;
    Spinner spinner;
    double area;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_result = findViewById(R.id.main_tv_result);
        btn_calculate = findViewById(R.id.main_btn_calculate);
        et_n1 = findViewById(R.id.main_et_number1);
        et_n2 = findViewById(R.id.main_et_number2);
        spinner = findViewById(R.id.main_sp_shapes);



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


               switch (i){
                   case 0:
                       et_n2.setVisibility(View.GONE);
                       et_n1.setHint(R.string.main_circle_Radius);
                       et_n1.setText("");
                       tv_result.setText("");
                       break;
                   case 1:
                       // Rectangle
                       et_n2.setVisibility(View.VISIBLE);
                       et_n1.setHint(R.string.main_rectangle_width);
                       et_n2.setHint(R.string.main_rectangle_height);
                       et_n1.setText("");
                       et_n2.setText("");
                       tv_result.setText("");
                       break;
                   case 2:
                       // Triangle
                       et_n2.setVisibility(View.VISIBLE);
                       et_n1.setHint(R.string.main_triangle_Base);
                       et_n2.setHint(R.string.main_triangle_Height);
                       et_n1.setText("");
                       et_n2.setText("");
                       tv_result.setText("");
                       break;
                   case 3:
                       // Square
                       et_n2.setVisibility(View.GONE);
                       et_n1.setHint(R.string.main_Square_Length_side);
                       et_n1.setText("");
                       tv_result.setText("");
                       break;
               }
           }

           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {

           }
       });

       btn_calculate.setOnClickListener(new View.OnClickListener() {
           @SuppressLint("SetTextI18n")
           @Override
           public void onClick(View view) {

                   int i = spinner.getSelectedItemPosition();
                   switch (i){
                       case 0:
                           // Circle

                           if (et_n1.getText().toString().trim().length()>0){
                               double radius =Double.parseDouble(et_n1.getText().toString());
                               area = Math.PI * Math.pow(radius,2);
                               DecimalFormat df = new DecimalFormat("#.00");
                               //df.setMaximumFractionDigits(2);

                               tv_result.setText(""+df.format(area));
                           }else {
                               Toast.makeText(MainActivity.this, "Please fill in the fields", Toast.LENGTH_SHORT).show();
                           }
                           break;
                       case 1:
                           // Rectangle
                           if (et_n1.getText().toString().trim().length()>0 && et_n2.getText().toString().trim().length()>0)
                           {
                               double number1 =Double.parseDouble(et_n1.getText().toString());
                               double number2 =Double.parseDouble(et_n2.getText().toString());
                               area = number1 * number2;
                               tv_result.setText(""+area);
                           }else {
                               Toast.makeText(MainActivity.this, "Please fill in the fields", Toast.LENGTH_SHORT).show();
                           }

                           break;
                       case 2:
                           // Triangle
                           if (et_n1.getText().toString().trim().length()>0 && et_n2.getText().toString().trim().length()>0)
                           {
                               double base =Double.parseDouble(et_n1.getText().toString());
                               double height =Double.parseDouble(et_n2.getText().toString());
                               area = base * height * 0.5 ;
                               tv_result.setText(""+area);
                           }else {
                               Toast.makeText(MainActivity.this, "Please fill in the fields", Toast.LENGTH_SHORT).show();
                           }

                           break;
                       case 3:
                           // Square

                           if (et_n1.getText().toString().trim().length()>0){
                               double lengthSide =Double.parseDouble(et_n1.getText().toString());
                               area = lengthSide * lengthSide;

                               tv_result.setText(""+area);
                           }else {
                               Toast.makeText(MainActivity.this, "Please fill in the fields", Toast.LENGTH_SHORT).show();
                           }
                           break;

                   }



              }
       });

    }
}