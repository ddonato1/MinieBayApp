package com.example.miniebayapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class DeleteMyProduct extends AppCompatActivity {
    Button delProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_my_product);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        delProduct = (Button) findViewById(R.id.deleteProBtn);
    }
}
