package com.example.a26_notifications;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.res.ResourcesCompat;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    Button btn_show;
    private final String CHANNEL_ID = "myChanelId";
    private final String CHANNEL_NAME = "myChanel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_show = findViewById(R.id.main_btn);

        btn_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create Channel if android version >= 26 oreo
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                {
                    NotificationChannel channel = new NotificationChannel(CHANNEL_ID,CHANNEL_NAME,NotificationManager.IMPORTANCE_DEFAULT);
                    channel.setDescription("My Description");
                    NotificationManager nm = getSystemService(NotificationManager.class);
                    nm.createNotificationChannel(channel);
                }
                // Build Notification
                Intent intent = new Intent(getBaseContext(),MainActivity.class);
                PendingIntent pi = PendingIntent.getActivity(getBaseContext(),12,intent,0);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this,CHANNEL_ID);
                builder.setContentIntent(pi);
                builder.setPriority(NotificationCompat.PRIORITY_HIGH);
                builder.setContentTitle("MyTitle");
                builder.setContentText("Content Text");
                builder.setSmallIcon(R.drawable.ic_baseline_notifications_24);
                //builder.setStyle(new NotificationCompat.BigTextStyle().bigText("My Big Text"));
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.smoke_bg);
                builder.setLargeIcon(bitmap);
                builder.addAction(R.drawable.ic_baseline_open,"Action",pi);

                // Publish Notification
                NotificationManagerCompat nmc = NotificationManagerCompat.from(MainActivity.this);
                nmc.notify(13,builder.build());

            }
        });


    }
}