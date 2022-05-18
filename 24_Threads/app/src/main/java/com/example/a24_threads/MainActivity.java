package com.example.a24_threads;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btn_download;
    ProgressBar progressBar;
    TextView tv;
    Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_download = findViewById(R.id.main_btn);
        progressBar = findViewById(R.id.main_pb);
        tv = findViewById(R.id.main_tv);

        thread = new Thread();

        btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (thread.isAlive()){
                    Toast.makeText(MainActivity.this, "Thread is Alive", Toast.LENGTH_SHORT).show();
                }else {
                    tv.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                    thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            for (int i =0;i<11;i++)
                            {
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressBar.incrementProgressBy(10);
                                        tv.setText(progressBar.getProgress() +"%");
                                    }
                                });
                            }

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    tv.setVisibility(View.INVISIBLE);
                                    progressBar.setProgress(0);
                                    tv.setText("");
                                }
                            });

                        }
                    });
                    thread.start();
                }

            }
        });

    }
}