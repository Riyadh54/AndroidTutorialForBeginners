package com.example.a9_implicitintent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    static final int REQUEST_CODE_CAPTURE_IMAGE =1 ;
    Button btn_capture;
    ImageView img_capture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_capture = findViewById(R.id.main_btn_capture);
        img_capture = findViewById(R.id.main_img_capture);

        btn_capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i,REQUEST_CODE_CAPTURE_IMAGE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CAPTURE_IMAGE && resultCode == RESULT_OK){
            Bitmap b = (Bitmap) data.getExtras().get("data");
            img_capture.setImageBitmap(b);
        }else {
            Toast.makeText(this, "there is no picture for display", Toast.LENGTH_SHORT).show();
        }
    }
}