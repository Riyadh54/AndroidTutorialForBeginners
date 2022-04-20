package com.example.a16_externalstorage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
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
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class MainActivity extends AppCompatActivity {

    Button btn_save,btn_restore;
    EditText et_name,et_email,et_password;
    TextView tv;
    public static final String FILE_NAME = "users";
    public static final int PERMISSION_REQUEST_CODE = 123;
    public static final int REQUEST_PERMISSION_SETTING =12;

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
        //// user Permission

        if (ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
        {
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_REQUEST_CODE);

        }


        ///////////////// Buttons
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
                    //public External Storage
                    if (isExternalStorageAvailable())
                    {
                        File f =new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),FILE_NAME);
                        if (!f.exists())
                        {
                            try {
                                f.createNewFile();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }else {
                            f.delete();
                        }
                        try {
                            FileOutputStream fos = new FileOutputStream(f,false);
                            PrintWriter pw = new PrintWriter(fos);
                            pw.println("userName: " +user_name+"email: "+user_email+"password: "+user_password);
                            pw.close();
                            fos.close();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }


            }}
        });

        btn_restore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    File f =new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),FILE_NAME);

                    FileInputStream fis = new FileInputStream(f);
                    InputStreamReader isr = new InputStreamReader(fis);
                    BufferedReader br =new BufferedReader(isr);

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

    private boolean isExternalStorageAvailable(){
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED))
        {
            return true;
        }else
        {
            Toast.makeText(MainActivity.this, "External Storage not Available", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE)
        {
            for (int i =0;i<permissions.length;i++)
            {
                String perm = permissions[i];
                if (grantResults[i] == PackageManager.PERMISSION_DENIED)
                {
                    boolean isShowRational = ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,perm);
                    if (!isShowRational)
                    {
                        // when user Clicked Never Ask Again
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                        alertDialog.setTitle(getResources().getText(R.string.app_permission));
                        alertDialog.setMessage(getResources().getText(R.string.permission_message));
                        alertDialog.setPositiveButton(getResources().getText(R.string.open_settings), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package",getPackageName(),null);
                                intent.setData(uri);
                                startActivityForResult(intent,REQUEST_PERMISSION_SETTING);
                            }
                        }).create().show();
                    }else {
                        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_REQUEST_CODE);
                    }
                }
                else
                {
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PERMISSION_SETTING)
        {
            if (ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }
            else
            {
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_REQUEST_CODE);
            }
        }
    }


}