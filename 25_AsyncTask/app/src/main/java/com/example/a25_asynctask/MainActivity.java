package com.example.a25_asynctask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button btn_download;
    private ProgressBar progressBar;
    private TextView tv;
    private MyAsyncTask myAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_download = findViewById(R.id.main_btn);
        progressBar = findViewById(R.id.main_pb);
        tv = findViewById(R.id.main_tv);
        myAsyncTask = new MyAsyncTask();

        btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myAsyncTask.getStatus() == AsyncTask.Status.RUNNING)
                {
                    Toast.makeText(MainActivity.this, "the task is already running", Toast.LENGTH_SHORT).show();

                } else if (myAsyncTask.getStatus() == AsyncTask.Status.FINISHED)
                {
                    myAsyncTask = null;
                    myAsyncTask = new MyAsyncTask();
                    myAsyncTask.execute();
                }else
                {

                    myAsyncTask.execute();
                }

            }
        });



    }



    class MyAsyncTask extends AsyncTask<String,Integer,Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            tv.setVisibility(View.VISIBLE);

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.incrementProgressBy(values[0]);
            tv.setText(progressBar.getProgress()+"%");
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            progressBar.setVisibility(View.INVISIBLE);
            tv.setVisibility(View.INVISIBLE);
            progressBar.setProgress(0);
            tv.setText("");
        }

        @Override
        protected Void doInBackground(String... strings) {
            for (int i =0;i<11;i++)
            {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(10);

            }

            return null;
        }
    }
}