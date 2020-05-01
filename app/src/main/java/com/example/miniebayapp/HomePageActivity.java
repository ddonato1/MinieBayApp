package com.example.miniebayapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HomePageActivity extends AppCompatActivity {
    TextView usern;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        Intent i = getIntent();
        username = i.getStringExtra("userName");

        usern = findViewById(R.id.usernameView);
        usern.setText("" + username);
    }
}
