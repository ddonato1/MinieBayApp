package com.example.miniebayapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Locale;

public class ShoppingCartPage extends AppCompatActivity {
    Button checkoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart_page);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        checkoutBtn = (Button) findViewById(R.id.confirmBtn);
        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ShoppingCartPage.this, CheckoutPage.class);
                startActivity(i);
            }
        });
    }
}
