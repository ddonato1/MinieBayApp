/**
 * Angel J. Vargas Lopez - S01274152
 * Deyaneira Donato Carrasquillo - S01183053
 * **
 * Bids Offers Page Activity, this activity is to add a bid on the product selected. It will display
 * the user that owns the product, the product ID and the current price of the product. The user most
 * indicate the amount he/she wants to add on the product, as well, enter his/her username.
 **/
package com.example.miniebayapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;

public class BidsOffersPage extends AppCompatActivity {
    //Declare variables
    TextView priceP, ownerP, idP;
    TextView prodID;
    String price, owner, prod_id;
    EditText bid, username;
    Button addbid;
    String priceB, Owner, userN, prodid, Price;

    //This is for debugging
    private String TAG = HttpHandler.class.getSimpleName();
    //Web server's IP address
    private String hostAddress;
    //Shared object though the application
    SharedPreferences prf;
    //Authentication Servlet name
    protected String servletN = "addbid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bids_offers_page);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        priceP = (TextView) findViewById(R.id.itemPrice);
        bid = (EditText) findViewById(R.id.bidPrice);
        ownerP = (TextView) findViewById(R.id.itemOwner);

        username = (EditText) findViewById(R.id.userNameText);
        prodID = (TextView) findViewById(R.id.itemID);
        addbid = (Button) findViewById(R.id.addBidbtn);

        Intent in = getIntent();
        price = in.getStringExtra("Price");
        owner = in.getStringExtra("Owner");
        prod_id = in.getStringExtra("prodid");
        priceP.setText(price);
        ownerP.setText(owner);
        prodID.setText(prod_id);

        addbid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create and start the thread

                new postItems(BidsOffersPage.this).execute();

                Intent i = new Intent(BidsOffersPage.this, HomePage.class);
                startActivity(i);
            }
        });

        // Define the web server's IP address
        hostAddress = "192.168.0.11:8088";
    }

    /***
     *  This class is a thread for sending and process data to the Web server
     */
    private class postItems extends AsyncTask<Void, Void, String> {
        ProgressDialog dialogProg;

        // Context: every transaction in a Android application must be attached to a context
        private Activity activity;

        //Server response
        private String serverResponse;

        private String url;

        /***
         * Special constructor: assigns the context to the thread
         *
         * @param activity: Context
         */
        //@Override
        public postItems(Activity activity) {
            //Define the servlet URL
            url = "http://" + hostAddress +"/"+ servletN;
            this.activity = activity;
        }

        /**
         *  on PreExecute method: runs after the constructor is called and before the thread runs
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialogProg = ProgressDialog.show(activity, "Processing...", "Please wait...");
        }

        @Override
        protected String doInBackground(Void... params) {
            String result = null;

            try {
                //Read GUI inputs
                Price = ((TextView) findViewById(R.id.itemPrice)).getText().toString();
                priceB = ((EditText) findViewById(R.id.bidPrice)).getText().toString();
                userN = ((EditText) findViewById(R.id.userNameText)).getText().toString();
                prodid = ((TextView) findViewById(R.id.itemID)).getText().toString();
                Owner = ((TextView) findViewById(R.id.itemOwner)).getText().toString();


                /*Define a HttpHandler*/
                HttpHandler hconnection = new HttpHandler();

                final JSONObject parmsPost = new JSONObject();

                parmsPost.put("pricebid", priceB);
                parmsPost.put("username", userN);
                parmsPost.put("prodid", prodid);
                parmsPost.put("Owner", Owner);

                /**
                 * Sending the data to database
                 */
                /*perform the authentication process and capture the result in serverResponse variable*/
                serverResponse = hconnection.makeServiceCallPost4(url, priceB,  Owner, prodid, userN);
                //Clean response
                serverResponse=serverResponse.trim();

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            String msgToast;

            //Verify the authentication result
            // not: the user could not be authenticated
            if (!serverResponse.equals("not")) {
                //The product has been authenticated
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(BidsOffersPage.this);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("pricebid", priceB);
                editor.putString("username", userN);
                editor.putString("prodid", prodid);
                editor.putString("Owner", Owner);
                editor.putString("sessionValue", serverResponse);
                //editor.commit();
                editor.apply();
                msgToast= "Bid added successfully!";
            }
            else {
                ///The user could not been authenticated, destroy session variables
                SharedPreferences.Editor editor = prf.edit();
                editor.clear();
                editor.commit();

                //Toast message
                msgToast= "Already exits!";
                Toast.makeText(getApplicationContext(),
                        msgToast,
                        Toast.LENGTH_LONG).show();
            }
        }
    }
}
