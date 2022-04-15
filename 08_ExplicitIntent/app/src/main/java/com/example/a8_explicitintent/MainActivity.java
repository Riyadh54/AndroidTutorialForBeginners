package com.example.a8_explicitintent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button btnB,btnC;
    TextView tv;
     static final int REQUEST_CODE_ACTIVITY_B = 1;
     static final int REQUEST_CODE_ACTIVITY_C = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnB = findViewById(R.id.activityA_btn_moveB);
        btnC = findViewById(R.id.activityA_btn_moveC);
        tv = findViewById(R.id.activityA_tv_nameIntent);

        btnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,ActivityB.class);
                startActivityForResult(i,REQUEST_CODE_ACTIVITY_B);

            }
        });
        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,ActivityC.class);
                startActivityForResult(i,REQUEST_CODE_ACTIVITY_C);

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ACTIVITY_B){
            String s = data.getStringExtra("key1");
            tv.setText(s);
        }else {
            String s = data.getStringExtra("key2");
            tv.setText(s);
        }
    }
}