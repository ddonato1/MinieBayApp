package com.example.miniebayapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MyeBayInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.);

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
