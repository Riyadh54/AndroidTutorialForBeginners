package com.example.a8_explicitintent;

import static com.example.a8_explicitintent.MainActivity.REQUEST_CODE_ACTIVITY_B;
import static com.example.a8_explicitintent.MainActivity.REQUEST_CODE_ACTIVITY_C;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityC extends AppCompatActivity {
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c);
        btn = findViewById(R.id.activityC_btn_move);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ActivityC.this,MainActivity.class);
                i.putExtra("key2","Intent From ActivityC");
                setResult(REQUEST_CODE_ACTIVITY_C,i);
                finish();
            }
        });
    }
}