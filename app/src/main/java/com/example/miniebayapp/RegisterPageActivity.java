package com.example.miniebayapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.net.PasswordAuthentication;

public class RegisterPageActivity extends AppCompatActivity {
    Button btnconfirm;
    EditText fname;
    EditText lname;
    EditText uname;
    EditText address;
    EditText e_mail;
    EditText passwrd;
    EditText passwrdconf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerpage);

        fname = findViewById(R.id.firstnameText);
        lname = findViewById(R.id.lastnameText);
        address = findViewById(R.id.addressText);
        e_mail = findViewById(R.id.emailTxt);
        uname = findViewById(R.id.usernameText);
        passwrd = findViewById(R.id.userpasswdText);
        passwrdconf = findViewById(R.id.userconfpasswdText);
        btnconfirm = findViewById(R.id.registerButton);

        btnconfirm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent iconf = new Intent(RegisterPageActivity.this, MainActivity.class);
                startActivity(iconf);
            }
        });
    }
}
