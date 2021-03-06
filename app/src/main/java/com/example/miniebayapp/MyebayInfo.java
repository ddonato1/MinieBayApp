/**
 * Angel J. Vargas Lopez - S01274152
 * Deyaneira Donato Carrasquillo - S01183053
 * **
 * My eBay Info Activity, this activity is located the user info and the logout button.
 **/
package com.example.miniebayapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MyebayInfo extends AppCompatActivity {
    TextView usern;
    TextView firstn;
    TextView lastn;
    TextView address;
    TextView email;

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
    Button logout;

    SharedPreferences perf;
    //This is for debugging
    private String TAG = HttpHandler.class.getSimpleName();

    //Web server's IP address
    private String hostAddress;


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
        logout = (Button) findViewById(R.id.logoutBtn);

        usern = (TextView) findViewById(R.id.usernameView);
        firstn = (TextView) findViewById(R.id.fisrtNameView);
        lastn = (TextView) findViewById(R.id.lastNameView);
        address = (TextView) findViewById(R.id.addressView);
        email = (TextView) findViewById(R.id.emailView);


        // Define the web server's IP address
        hostAddress="192.168.0.11:8088";

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

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutBtnObject();
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

    public void logoutBtnObject() {
        perf = getSharedPreferences("logout",MODE_PRIVATE);
        SharedPreferences.Editor ed = perf.edit();
        ed.clear();
        ed.commit();

        MyebayInfo.this.finishAffinity();

        //Call the MainActivity where is the login activity
        Intent i = new Intent(MyebayInfo.this, MainActivity.class);
        startActivity(i);
    }
}
