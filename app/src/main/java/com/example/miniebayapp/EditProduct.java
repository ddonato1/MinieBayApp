package com.example.miniebayapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class EditProduct extends AppCompatActivity {
    Button nameEdit;
    Button descEdit;
    Button priceEdit;
    Button deptEdit;
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
        deptEdit = (Button) findViewById(R.id.editDeptBtn);
        cateEdit = (Button) findViewById(R.id.editCateBtn);
        photoEdit = (Button) findViewById(R.id.editPhotoBtn);

        itemSel = (TextView) findViewById(R.id.textSelectedProd);
    }
}
