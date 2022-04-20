package com.example.a15_internalstorage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

public class MainActivity extends AppCompatActivity {
    Button btn_save,btn_restore;
    EditText et_name,et_email,et_password;
    TextView tv;
    public static final String FILE_NAME = "users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // inflate Views
        btn_save = findViewById(R.id.main_btn_save_data);
        btn_restore = findViewById(R.id.main_btn_restore_data);
        et_name = findViewById(R.id.user_name);
        et_email = findViewById(R.id.user_email);
        et_password = findViewById(R.id.user_password);
        tv = findViewById(R.id.main_tv);
        //


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_name = et_name.getText().toString();
                String user_email = et_email.getText().toString();
                String user_password = et_password.getText().toString();

                if (user_name.isEmpty() && user_email.isEmpty() && user_password.isEmpty())
                {
                    Toast.makeText(MainActivity.this, "Pleas fill the fields", Toast.LENGTH_SHORT).show();
                }else{
                    try {
                        File f = new File(getFilesDir(),FILE_NAME);
                        if (!f.exists()){
                            f.createNewFile();
                        }
                        FileOutputStream fos = new FileOutputStream(f,false);
                        PrintWriter pw = new PrintWriter(fos);
                        pw.println("userName: " +user_name+"email: "+user_email+"password: "+user_password);
                        pw.close();
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }



            }
        });

        btn_restore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    FileInputStream fis = openFileInput(FILE_NAME);
                    InputStreamReader isr = new InputStreamReader(fis);
                    BufferedReader br = new BufferedReader(isr);
                    String temp ="";
                    String allText ="";
                    while ((temp = br.readLine()) != null){
                        allText += temp;
                    }
                    // get user Name
                    int lastIndexUserName =allText.lastIndexOf("email") ;
                    String userName = allText.substring(10,lastIndexUserName);
                    // get User Email
                    int startIndexUserEmail = allText.indexOf("email");
                    int endIndexUserEmail =allText.lastIndexOf("password");
                    String userEmail= allText.substring(startIndexUserEmail+7,endIndexUserEmail);
                    // get user Password
                    int startIndexUserPassword = allText.indexOf("password");
                    String userPassword= allText.substring(startIndexUserPassword+10);

                    tv.setText(getResources().getString(R.string.user_name)+": "+userName+"\n\n"+getResources().getString(R.string.user_email)+": "+userEmail+"\n\n"+getResources().getString(R.string.user_password)+": "+userPassword);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });

    }
}