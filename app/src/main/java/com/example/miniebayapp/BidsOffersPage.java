package com.example.miniebayapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;

public class BidsOffersPage extends AppCompatActivity {
    TextView priceP, ownerP, idP;
    TextView prodID;
    String price, owner, prod_id;
    EditText bid, username;
    Button addbid;
    String priceB, Owner, userN, prodid, Price;

    //This is for debugging
    private String TAG = HttpHandler.class.getSimpleName();
    //This is for managing the listview in the activity
    private ListView listv;
    //Web server's IP address
    private String hostAddress;
    //Shared object though the application
    SharedPreferences prf;
    //Authentication Servlet name
    protected String servletN = "addbid";

//    //Users adapter
//    private UsersAdapter adapter;
//    // Item list for storing data from the web server
//    private ArrayList<userItem> itemUserList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bids_offers_page);

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
        //Instate the Item list
        //itemUserList = new ArrayList<>();
        // Defines the adapter: Receives the context (Current activity) and the Arraylist
        //adapter = new UsersAdapter(this, itemUserList);
        // Create a accessor to the ListView in the activity
        listv = findViewById(R.id.itemLists);

    }

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
//                String url = "http://"+hostAddress+"/getdatabasejson1";
//                // Download data from the web server using JSON;
//                String jsonStr = hconnection.makeServiceCall(url);
//                // Log download's results
//                Log.e(TAG, "Response from url: " + jsonStr);
//
//                //The JSON data must contain an array of JSON objects
//                if (jsonStr != null) {
//                    try {
//                        //Define a JSON object from the received data
//                        JSONObject jsonObj = new JSONObject(jsonStr);
//
//                        // Getting JSON Array node
//                        JSONArray items = jsonObj.getJSONArray("PRODUCT");
//                        JSONArray items1 = jsonObj.getJSONArray("INFORMATION");
//
//                        // looping through All Items
//                        for (int i = 0; i < items.length(); i++) {
//                            JSONObject c = items.getJSONObject(i);
//                            JSONObject c1 = items1.getJSONObject(i);
//                            String price = c.getString("Price");
//                            String pID = c.getString("prodid");
//                            String ownerP = c1.getString("Owner");
//
//
//                            itemUserList.add(new userItem(pID, price, ownerP));
//                        }
//                    } //Log any problem with received data
//                    catch (final JSONException e) {
//                        Log.e(TAG, "Json parsing error: " + e.getMessage());
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(getApplicationContext(),
//                                        "Json parsing error: " + e.getMessage(),
//                                        Toast.LENGTH_LONG).show(); }
//                        });
//                    }
//                } else {
//                    Log.e(TAG, "Couldn't get json from server.");
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//
//                            Toast.makeText(getApplicationContext(),
//                                    "Couldn't get json from server. Check LogCat for possible errors!",
//                                    Toast.LENGTH_LONG).show();
//                        }
//                    });
//                }

                final JSONObject parmsPost = new JSONObject();

                parmsPost.put("pricebid", priceB);
                parmsPost.put("username", userN);
                parmsPost.put("prodid", prodid);
                parmsPost.put("Owner", Owner);


                //EDIT!!!
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
                //Update local session variables
//                SharedPreferences.Editor editor = prf.edit();
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
                //Define the next activity
//                Intent i = new Intent(RegisterPage.this, MainActivity.class);
//                //call the DetailsActivity
//                startActivity(i);
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

//    /***
//     *  This class is a thread for receiving and process data from the Web server
//     */
//    class GetItems extends AsyncTask<Void, Void, Void> {
//        // Context: every transaction in a Android application must be attached to a context
//        private Activity activity;
//
//        private Drawable actualBaseImage;
//
//        /***
//         * Special constructor: assigns the context to the thread
//         *
//         * @param activity: Context
//         */
//        //@Override
//        protected GetItems(Activity activity) {
//            this.activity = activity;
//        }
//
//        /**
//         *  on PreExecute method: runs after the constructor is called and before the thread runs
//         */
//        protected void onPreExecute() {
//            super.onPreExecute();
//            Toast.makeText(BidsOffersPage.this, "Items list is downloading", Toast.LENGTH_LONG).show();
//        }
//
//        /***
//         *  Main thread
//         * @param arg0
//         * @return
//         */
//        protected Void doInBackground(Void... arg0) {
//            //Create a HttpHandler object
//            HttpHandler sh = new HttpHandler();
//
//            // Making a request to url and getting response
//            String url = "http://"+hostAddress+"/getDataBaseJson";
//
//
//            // Download data from the web server using JSON;
//            String jsonStr = sh.makeServiceCall(url);
//
//            // Log download's results
//            Log.e(TAG, "Response from url: " + jsonStr);
//
//            //The JSON data must contain an array of JSON objects
//            if (jsonStr != null) {
//                try {
//                    //Define a JSON object from the received data
//                    JSONObject jsonObj = new JSONObject(jsonStr);
//
//                    // Getting JSON Array node
//                    JSONArray items = jsonObj.getJSONArray("PRODUCT");
//                    JSONArray items1 = jsonObj.getJSONArray("INFORMATION");
//
//                    // looping through All Items
//                    for (int i = 0; i < items.length(); i++) {
//                        JSONObject c = items.getJSONObject(i);
//                        JSONObject c1 = items1.getJSONObject(i);
//                        String price = c.getString("Price");
//                        String productID = c.getString("prodid");
//                        String ownerP = c1.getString("Owner");
//
//
//                        itemUserList.add(new userItem(productID, price, ownerP));
//                    }
//                } //Log any problem with received data
//                catch (final JSONException e) {
//                    Log.e(TAG, "Json parsing error: " + e.getMessage());
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(getApplicationContext(),
//                                    "Json parsing error: " + e.getMessage(),
//                                    Toast.LENGTH_LONG).show(); }
//                    });
//                }
//            } else {
//                Log.e(TAG, "Couldn't get json from server.");
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        Toast.makeText(getApplicationContext(),
//                                "Couldn't get json from server. Check LogCat for possible errors!",
//                                Toast.LENGTH_LONG).show();
//                    }
//                });
//            }
//            return null;
//        }
//
//        /***
//         *  This method runs after thread completion
//         *  Set up the List view using the ArrayAdapter
//         *
//         * @param result
//         */
//        protected void onPostExecute (Void result){
//            super.onPostExecute(result);
//            listv.setAdapter(adapter);
//        }
//
//        /***
//         *  This method downloads a image from a web server using an URL
//         * @param url: Image URL
//         * @return  d: android.graphics.drawable.Drawable;
//         * */
//        public Drawable LoadImageFromWebOperations(String url) {
//            try {
//                //Request the image to the web server
//                InputStream is = (InputStream) new URL(url).getContent();
//
//                //Generates an android.graphics.drawable.Drawable object
//                Drawable d = Drawable.createFromStream(is, "src name");
//
//                return d; }
//            catch (Exception e) {
//                return null;
//            }
//        }
//    }
//
//    /**
//     * This class defines a ArrayAdapter for the ListView manipulation
//     */
//    public class UsersAdapter extends ArrayAdapter<userItem> {
//
//        /**
//         *  Constructor:
//         * @param context: Activity
//         * @param users: ArrayList for storing Items list
//         */
//        public UsersAdapter(Context context, ArrayList<userItem> users) {
//            super(context, 0, users);
//        }
//
//        /***
//         *  This method generates a view for manipulating the item list
//         *  This method is called from the ListView.
//         *
//         * @param position: Item's position in the ArrayList
//         * @param convertView:
//         * @param parent
//         * @return
//         */
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            // Get the data item for this position
//            userItem user = getItem(position);
//            // Check if an existing view is being reused, otherwise inflate the view
//            if (convertView == null) {
//                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_items3, parent, false);
//            }
//            // Lookup view for data population
//            final TextView itemOwner = (TextView) convertView.findViewById(R.id.itemOwner);
//            final TextView itemId = (TextView) convertView.findViewById(R.id.itemid);
//            final TextView itemPrice = (TextView) convertView.findViewById(R.id.itemPrice);
//
//            // Populate the data into the template view using the data object
//            itemOwner.setText(user.owner);
//            itemId.setText(user.productid);
//            itemPrice.setText(user.price);
//
//
//            // Return the completed view to render on screen
//            convertView.setTag(position);
////
////            //Create Listener to detect a click
////            convertView.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View view) {
////                    int position = (Integer) view.getTag();
////
////                    // Show data of the clicked item
////                    Toast.makeText(getApplicationContext(),
////                            "You have selected " + itemUserList.get(position).name,
////                            Toast.LENGTH_LONG).show();
////                    // Do what you want here...
////                    Intent inT = new Intent(BidsOffersPage.this, BidsOffersPage.class);
////                    inT.putExtra("Description", itemDesc.getText());
////                    inT.putExtra("Price", itemPrice.getText());
////                    inT.putExtra("Owner", itemOwner.getText());
////                    startActivity(inT);
////                }
////            });
//            return convertView;
//        }
//    }
//
//    /**
//     *  This class generates a Data structure for manipulating each Item in the application
//     */
//    public class userItem implements Serializable {
//        public String owner;
//        // Item's list
//        public String productid;
//        // Item's price
//        public String price;
//
//        /**
//         *  Special constructor:
//         * @param owner
//         * @param productid
//         * @param price
//         */
//        public userItem(String productid, String price, String owner) {
//            this.owner = owner;
//            this.productid = productid;
//            this.price = price;
//
//        }
//    }
}
