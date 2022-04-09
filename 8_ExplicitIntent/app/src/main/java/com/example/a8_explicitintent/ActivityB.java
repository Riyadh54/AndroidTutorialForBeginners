package com.example.a8_explicitintent;

import static com.example.a8_explicitintent.MainActivity.REQUEST_CODE_ACTIVITY_B;
import static com.example.a8_explicitintent.MainActivity.REQUEST_CODE_ACTIVITY_C;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityB extends AppCompatActivity {
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
        btn = findViewById(R.id.activityB_btn_move);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ActivityB.this,MainActivity.class);
                i.putExtra("key1","Intent From ActivityB");
                setResult(REQUEST_CODE_ACTIVITY_B,i);
                finish();
            }
        });
    }
}