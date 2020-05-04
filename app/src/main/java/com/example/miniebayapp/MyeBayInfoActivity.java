package com.example.miniebayapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MyeBayInfoActivity extends AppCompatActivity {
    TextView usern;
    String username;
    Button homeBtn;
    Button myEBAYbtn;
    Button notBtn;
    Button saleBtn;
    Button cartBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myebayinfo);


        homeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                homeBtnObject();
            }
        });

        myEBAYbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myEBAYBtnObject();
            }
        });

        notBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notiBtnObject();
            }
        });

        saleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saleBtnObject();
            }
        });

        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shoppingcartBtnObject();
            }
        });
    }

    public void homeBtnObject() {
        Intent i = new Intent(MyeBayInfoActivity.this, HomePageActivity.class);
        startActivity(i);
    }

    public void myEBAYBtnObject() {
        Intent i = new Intent(MyeBayInfoActivity.this, MyeBayInfoActivity.class);
        startActivity(i);
    }

    public void notiBtnObject() {
        Intent i = new Intent(MyeBayInfoActivity.this, NotificationsAct.class);
        startActivity(i);
    }

    public void saleBtnObject() {
        Intent i = new Intent(MyeBayInfoActivity.this, SellingActivity.class);
        startActivity(i);
    }

    public void shoppingcartBtnObject() {
        Intent i = new Intent(MyeBayInfoActivity.this, ShoppingCartActivity.class);
        startActivity(i);
    }
}
