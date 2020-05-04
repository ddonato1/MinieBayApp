package com.example.miniebayapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class RegisterPage extends AppCompatActivity {
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
        setContentView(R.layout.activity_register_page);

        Intent i = getIntent();

        uname = findViewById(R.id.usernameText);
        passwrd = findViewById(R.id.userPassText);
        passwrdconf = findViewById(R.id.userconfpasswdText);
        lname = findViewById(R.id.lastnameText);
        fname = findViewById(R.id.firstnameText);
        address = findViewById(R.id.addressText);

        btnconfirm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //sendUserDataObject();
                Intent iconf = new Intent(RegisterPage.this, MainActivity.class);
                startActivity(iconf);
            }
        });

        //    public void sendUserDataObject() {
//        Intent i = new Intent(RegisterPageActivity.this, MyeBayInfoActivity.class);
//
//        String userN = uname.getText().toString();
//        String firstname = fname.getText().toString();
//        String lastname = lname.getText().toString();
//        String dob = datebirth.getText().toString();
//        String add = address.getText().toString();
//        String email = e_mail.getText().toString();
//
//        i.putExtra("username", userN);
//        i.putExtra("first name", firstname);
//        i.putExtra("last name", lastname);
//        i.putExtra("birth", dob);
//        i.putExtra("address", add);
//        i.putExtra("email", email);
//        startActivity(i);
//    }
    }
}
