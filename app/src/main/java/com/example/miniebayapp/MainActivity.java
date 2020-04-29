package com.example.miniebayapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    EditText username;;
    EditText userpass;
    Button btnlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.userNameText);
        userpass = findViewById(R.id.userPassText);
        btnlogin = findViewById(R.id.loginButton);

       // btnlogin.setOnClickListener(){
            //Intent i = new Intent(MainActivity.this, welcomePage.class);
            //startActivities(i);
       // };


        btnlogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                loginBtnObject();
            }
        });
    }

    public void loginBtnObject() {
        Intent i = new Intent(MainActivity.this, HomePageActivity.class);

        String userN = username.getText().toString();
        String passwrd = userpass.getText().toString();

        startActivity(i);
    }

}
