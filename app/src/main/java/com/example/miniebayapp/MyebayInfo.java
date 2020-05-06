package com.example.miniebayapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MyebayInfo extends AppCompatActivity {
    TextView usern;
    TextView firstn;
    TextView lastn;
    String username;
    String firstname;
    String lastname;

    //main buttons of the app
    Button homeBtn;
    Button myEBAYbtn;
    Button notBtn;
    Button saleBtn;
    Button cartBtn;

    //changing to another activity variables
    Button orders;
    Button purchases;
    Button help;
    Button payment;
    Button bidsO;
    Button settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myebay_info);

        homeBtn = (Button) findViewById(R.id.homeButton);
        myEBAYbtn = (Button) findViewById(R.id.userinfButton);
        notBtn = (Button) findViewById(R.id.notifButton);
        saleBtn = (Button) findViewById(R.id.saleButton);
        cartBtn = (Button) findViewById(R.id.shopitemButton);

        orders = (Button) findViewById(R.id.yourOrdersBtn);
        bidsO = (Button) findViewById(R.id.bidsBtn);
        purchases = (Button) findViewById(R.id.purchasesBtn);
        payment = (Button) findViewById(R.id.paymentBtn);
        help = (Button) findViewById(R.id.helpBtn);
        settings = (Button) findViewById(R.id.settingsBtn);

        Intent i = getIntent();
        username = i.getStringExtra("userName");
        firstname = i.getStringExtra("first name");
        lastname = i.getStringExtra("last name");

        usern = findViewById(R.id.usernameView);
        usern.setText("" + username);
        firstn = findViewById(R.id.fisrtNameView);
        firstn.setText("" + firstname);
        lastn = findViewById(R.id.lastNameView);
        lastn.setText("" + lastname);

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

        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myOrdersBtnObject();
            }
        });

        bidsO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bidsandoffersBtnObject();
            }
        });

        purchases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                purchasesBtnObject();
            }
        });

        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paymentBtnObject();
            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helpBtnObject();
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingsBtnObject();
            }
        });
    }

    public void homeBtnObject() {
        Intent i = new Intent(MyebayInfo.this, HomePage.class);
        startActivity(i);
    }

    public void myEBAYBtnObject() {
        Intent i = new Intent(MyebayInfo.this, MyebayInfo.class);
        startActivity(i);
    }

    public void notiBtnObject() {
        Intent i = new Intent(MyebayInfo.this, NotificationPage.class);
        startActivity(i);
    }

    public void saleBtnObject() {
        Intent i = new Intent(MyebayInfo.this, SellingPage.class);
        startActivity(i);
    }

    public void shoppingcartBtnObject() {
        Intent i = new Intent(MyebayInfo.this, ShoppingCartPage.class);
        startActivity(i);
    }

    public void myOrdersBtnObject() {
        Intent i = new Intent(MyebayInfo.this, MyOrdersPage.class);
        startActivity(i);
    }

    public void bidsandoffersBtnObject() {
        Intent i = new Intent(MyebayInfo.this, BidsOffersPage.class);
        startActivity(i);
    }

    public void purchasesBtnObject() {
        Intent i = new Intent(MyebayInfo.this, PurchasesPage.class);
        startActivity(i);
    }

    public void paymentBtnObject() {
        Intent i = new Intent(MyebayInfo.this, PaymentMethodPage.class);
        startActivity(i);
    }

    public void helpBtnObject() {
        Intent i = new Intent(MyebayInfo.this, HelpPage.class);
        startActivity(i);
    }

    public void settingsBtnObject() {
        Intent i = new Intent(MyebayInfo.this, SettingsPage.class);
        startActivity(i);
    }
}
