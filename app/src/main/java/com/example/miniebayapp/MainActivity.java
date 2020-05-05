package com.example.miniebayapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    EditText username;;
    EditText userpass;
    Button btnlogin;
    TextView register;
    CheckBox remembox;
    TextView forgotPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnlogin = (Button) findViewById(R.id.loginButton);
        register = (TextView) findViewById(R.id.registerTxtView);
        forgotPass = (TextView) findViewById(R.id.forgotPassTxtView);

        btnlogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                loginBtnObject();
//                Intent iLOG = new Intent(MainActivity.this, HomePageActivity.class);
//                startActivity(iLOG);
            }
        });

       register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent ireg = new Intent(MainActivity.this, RegisterPage.class);
                startActivity(ireg);
            }
        });

       forgotPass.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent iFP = new Intent(MainActivity.this, ChangePasswordPage.class);
               startActivity(iFP);
           }
       });
    }

    public void loginBtnObject() {
        username = (EditText) findViewById(R.id.userNameText);
        userpass = (EditText) findViewById(R.id.userPassText);

        Intent i = new Intent(MainActivity.this, HomePage.class);
        String userN = username.getText().toString();
//        //String passwrd = userpass.getText().toString();
//
        i.putExtra("userName", userN);
        startActivity(i);
    }

    /**
     * If the user click the check box of "remember me", the user will stay login in the application until
     * he/she decide to logout from the application.
     */
    public void rememberCheckBoxObject(){
        remembox = (CheckBox) findViewById(R.id.rememberCheck);


    }

}
