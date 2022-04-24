package com.example.a17_externalstorageprivately;

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
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class MainActivity extends AppCompatActivity {
    Button btn_save,btn_restore;
    EditText et_name,et_email,et_password;
    TextView tv;
    public static final String FILE_NAME = "users";
    public static final int REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE_CODE = 123;
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

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
        {
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE_CODE);

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
                    if (isCheckAvailableExternalStorage()){
                        createFile();
                        writeData(createFile(),user_name,user_email,user_password);
                    }
                }

            }
        });
        btn_restore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restoreData(createFile(),tv);

            }
        });


    }






    private File createFile(){


        File file = new File(getExternalFilesDir("myFolder"),FILE_NAME);
        if (!file.exists()){
            try {
                file.createNewFile();
                Toast.makeText(this, "File Created"+file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    private boolean isCheckAvailableExternalStorage(){
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)){
            return true;
        }
        return false;
    }

    private void writeData(File f,String name,String email,String password){
        try {
            FileOutputStream fos = new FileOutputStream(f,false);
            PrintWriter pw = new PrintWriter(fos);
            pw.println("userName: " +name+"email: "+email+"password: "+password);
            pw.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void restoreData(File f,TextView textView){
        try {
            FileInputStream fis = new FileInputStream(f);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String allText ="";
            String temp="";
            while ((temp = br.readLine()) != null)
            {
                allText += temp;
            }
            int lastIndexUserName =allText.lastIndexOf("email") ;
            String userName = allText.substring(10,lastIndexUserName);
            // get User Email
            int startIndexUserEmail = allText.indexOf("email");
            int endIndexUserEmail =allText.lastIndexOf("password");
            String userEmail= allText.substring(startIndexUserEmail+7,endIndexUserEmail);
            // get user Password
            int startIndexUserPassword = allText.indexOf("password");
            String userPassword= allText.substring(startIndexUserPassword+10);
            br.close();
            isr.close();
            fis.close();
            textView.setText(getResources().getString(R.string.user_name)+": "+userName+"\n\n"+getResources().getString(R.string.user_email)+": "+userEmail+"\n\n"+getResources().getString(R.string.user_password)+": "+userPassword);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE_CODE)
        {

            for (int i =0;i<permissions.length;i++)
            {
                String per =permissions[i];
                if (grantResults[i] == PackageManager.PERMISSION_DENIED)
                {
                    boolean isShowRational = ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,per);
                    if (!isShowRational)
                    {
                        // when user Clicked Never Ask Again
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle(getResources().getText(R.string.app_permission));
                        builder.setMessage(getResources().getText(R.string.permission_message));
                        builder.setPositiveButton(getResources().getText(R.string.open_settings), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent();
                                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package",getPackageName(),null);
                                intent.setData(uri);
                                startActivityForResult(intent,REQUEST_PERMISSION_SETTING);
                            }
                        });
                        builder.create().show();

                    }else
                    {
                        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE_CODE);
                    }
                }else
                {
                    Toast.makeText(this, "Permission Granted"+per, Toast.LENGTH_SHORT).show();
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
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE_CODE);
            }
        }
    }
}