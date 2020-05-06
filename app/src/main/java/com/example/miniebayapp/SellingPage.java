package com.example.miniebayapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SellingPage extends AppCompatActivity {
    Button homeBtn;
    Button myEBAYbtn;
    Button notBtn;
    Button saleBtn;

    Button addButton;
    Button editButton;
    Button delButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selling_page);

        homeBtn = (Button) findViewById(R.id.homeButton);
        myEBAYbtn = (Button) findViewById(R.id.userinfButton);
        notBtn = (Button) findViewById(R.id.notifButton);
        saleBtn = (Button) findViewById(R.id.saleButton);

        addButton = (Button) findViewById(R.id.addBtn);
        editButton = (Button) findViewById(R.id.editBtn);
        delButton = (Button) findViewById(R.id.delBtn);

        Intent i = getIntent();

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

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProductObject();
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editProductOjbect();
            }
        });

        delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteMyProductObject();
            }
        });
    }



    /**
     *Methods
     */
    public void homeBtnObject() {
        Intent i = new Intent(SellingPage.this, HomePage.class);
        startActivity(i);
    }

    public void myEBAYBtnObject() {
        Intent i = new Intent(SellingPage.this, MyebayInfo.class);
        startActivity(i);
    }

    public void notiBtnObject() {
        Intent i = new Intent(SellingPage.this, NotificationPage.class);
        startActivity(i);
    }

    public void saleBtnObject() {
        Intent i = new Intent(SellingPage.this, SellingPage.class);
        startActivity(i);
    }

    public void addProductObject() {
        Intent i = new Intent(SellingPage.this, AddProduct.class);
        startActivity(i);
    }

    public void editProductOjbect() {
        Intent i = new Intent(SellingPage.this, EditProduct.class);
        startActivity(i);
    }

    public void deleteMyProductObject() {
        Intent i = new Intent(SellingPage.this, DeleteMyProduct.class);
        startActivity(i);
    }
}
