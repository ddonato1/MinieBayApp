package com.example.miniebayapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    EditText username;;
    EditText userpass;
    Button btnlogin;
    TextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.userNameText);
        userpass = findViewById(R.id.userPassText);
        btnlogin = findViewById(R.id.loginButton);
        register = findViewById(R.id.registerTxtView);

        btnlogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                loginBtnObject();
            }
        });

       register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent ireg = new Intent(MainActivity.this, RegisterPageActivity.class);
                startActivity(ireg);
            }
        });
    }

    public void loginBtnObject() {
        Intent i = new Intent(MainActivity.this, HomePageActivity.class);

        String userN = username.getText().toString();
        String passwrd = userpass.getText().toString();

        i.putExtra("userName", userN);
        startActivity(i);
    }

}
