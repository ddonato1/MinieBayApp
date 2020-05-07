package com.example.miniebayapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class AddProduct extends AppCompatActivity {
    Button addPhoto;

    EditText nameP;
    EditText desc;
    EditText price;
    EditText dept;
    EditText cate;
    //ImageView photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addPhoto = (Button) findViewById(R.id.addBtn);

        nameP = (EditText) findViewById(R.id.productNameText);
        desc = (EditText) findViewById(R.id.productDescText);
        price = (EditText) findViewById(R.id.priceText);
        dept = (EditText) findViewById(R.id.departmentText);
        cate = (EditText) findViewById(R.id.categoriesText);

       addPhoto.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

           }
       });
    }
}