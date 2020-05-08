package com.example.miniebayapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EditProduct extends AppCompatActivity {
    Button nameEdit;
    Button descEdit;
    Button priceEdit;
    Button cateEdit;
    Button photoEdit;

    TextView itemSel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nameEdit = (Button) findViewById(R.id.editNameBtn);
        descEdit = (Button) findViewById(R.id.editDescBtn);
        priceEdit = (Button) findViewById(R.id.editPriceBtn);
        cateEdit = (Button) findViewById(R.id.editCateBtn);
        photoEdit = (Button) findViewById(R.id.editPhotoBtn);

        itemSel = (TextView) findViewById(R.id.textSelectedProd);

        nameEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EditProduct.this, EditProductName.class);
                startActivity(i);
            }
        });

        descEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EditProduct.this, EditProductDescription.class);
                startActivity(i);
            }
        });

        priceEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EditProduct.this, EditProductPrice.class);
                startActivity(i);
            }
        });

        cateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EditProduct.this, EditProductCategory.class);
                startActivity(i);
            }
        });

        photoEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EditProduct.this, EditProductPhoto.class);
                startActivity(i);
            }
        });
    }
}
