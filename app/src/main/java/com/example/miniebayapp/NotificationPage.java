package com.example.miniebayapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.os.Bundle;

public class NotificationPage extends AppCompatActivity {
    NotificationCompat.Builder noti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_page);

//        noti = new NotificationCompat.Builder(this);
//        noti.setAutoCancel(true);
    }
}
