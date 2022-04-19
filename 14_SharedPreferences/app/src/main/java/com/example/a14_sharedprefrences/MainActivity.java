package com.example.a14_sharedprefrences;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    Button btn_save,btn_restore;
    EditText et_name,et_email,et_password;
    TextView tv;
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_save = findViewById(R.id.main_btn_save_data);
        btn_restore = findViewById(R.id.main_btn_restore_data);
        et_name = findViewById(R.id.user_name);
        et_email = findViewById(R.id.user_email);
        et_password = findViewById(R.id.user_password);



        tv = findViewById(R.id.main_tv);
        // sp = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        //sp = getPreferences(MODE_PRIVATE);
        // sp = getSharedPreferences("fileName",MODE_PRIVATE);

        sp = getSharedPreferences("fileName",MODE_PRIVATE);
        editor = sp.edit();

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = et_name.getText().toString();
                String email = et_email.getText().toString();
                String date = et_password.getText().toString();
                if (!name.isEmpty() && !email.isEmpty() && !date.isEmpty()){
                    editor.putString("username",name);
                    editor.putString("email",email);
                    editor.putString("password",date);
                    editor.apply();
                }else {
                    Toast.makeText(MainActivity.this, "the fields is empty", Toast.LENGTH_SHORT).show();
                }


            }
        });

        btn_restore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = sp.getString("username",null);
                String email = sp.getString("email",null);
                String password = sp.getString("password",null);
                if (name ==null && email == null && password == null){
                    Toast.makeText(MainActivity.this, "No Data in Shared Preferences", Toast.LENGTH_SHORT).show();
                }else {
                    tv.setText(getResources().getText(R.string.user_name)+": "+name+ "\n\n"+getResources().getText(R.string.user_email)+": "+email+ "\n\n"+ getResources().getText(R.string.user_password)+": "+password);
                }

            }
        });







    }

}