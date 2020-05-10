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
    Button logout;

    SharedPreferences perf;
      //This is for debugging
    private String TAG = HttpHandler.class.getSimpleName();
//    //This is for managing the listview in the activity
//    private ListView listv;
    //Web server's IP address
    private String hostAddress;
//    //Users adapter
//    private UsersAdapter adapter;
//    // Item list for storing data from the web server
//    private ArrayList<userItem> itemUserList;

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

//        Intent i = getIntent();
//        username = i.getStringExtra("userName");
//        firstname = i.getStringExtra("first name");
//        lastname = i.getStringExtra("last name");

        usern = (TextView) findViewById(R.id.usernameView);
//        usern.setText("" + username);
        firstn = (TextView) findViewById(R.id.fisrtNameView);
//        firstn.setText("" + firstname);
        lastn = (TextView) findViewById(R.id.lastNameView);
//        lastn.setText("" + lastname);
        address = (TextView) findViewById(R.id.addressView);
        email = (TextView) findViewById(R.id.emailView);

        //Access the local session variables
//        prf = getSharedPreferences("user_inf",MODE_PRIVATE);

        //Display on the screen
//        usern.setText("" + prf.getString("username", null));
//        firstn.setText("" + prf.getString("first name", null));
//        lastn.setText("" + prf.getString("last name", null));
//        address.setText("" + prf.getString("address", null));
//        email.setText("" + prf.getString("email", null));

            // Define the web server's IP address
            hostAddress="192.168.0.11:8088";
//
//        //Instate the Item list
//        itemUserList = new ArrayList<>();
//
//        // Defines the adapter: Receives the context (Current activity) and the Arraylist
//        adapter = new UsersAdapter(this, itemUserList);
//
//        // Create a accessor to the ListView in the activity
//        listv = findViewById(R.id.itemLists);
//
//        // Create and start the thread
//        new GetItems(this).execute();

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

    /**
     * EDIT!!!!!
     */
    public void logoutBtnObject() {
        perf = getSharedPreferences("logout",MODE_PRIVATE);
//        perf.getString("sessionValues",null);
        SharedPreferences.Editor ed = perf.edit();
        ed.clear();
        ed.commit();

        MyebayInfo.this.finishAffinity();

        //Call the MainActivity where is the login activity
        Intent i = new Intent(MyebayInfo.this, MainActivity.class);
        startActivity(i);
    }
}
