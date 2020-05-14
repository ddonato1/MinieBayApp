package com.example.miniebayapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class ProductInfo extends AppCompatActivity {

    //This is for debugging
    private String TAG = HomePage.class.getSimpleName();
    //This is for managing the listview in the activity
    private ListView listv;
    //Web server's IP address
    private String hostAddress;
    //Users adapter
    private UsersAdapter adapter1;
    // Item list for storing data from the web server
    private ArrayList<userItem> itemUserList;

    String item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_info);

        Intent i = getIntent();
        item = i.getStringExtra("Item Selected");

        // Define the web server's IP address
        hostAddress="192.168.0.11:8088";
        //Instate the Item list
        itemUserList = new ArrayList<>();
        // Defines the adapter: Receives the context (Current activity) and the Arraylist
        //adapter = new UsersAdapter(ProductInfo.this, itemUserList);
        adapter1 = new UsersAdapter(this, itemUserList);
        // Create a accessor to the ListView in the activity
        listv = findViewById(R.id.itemLists);
        // Create and start the thread
        new GetItems(this).execute();
    }

    /***
     *  This class is a thread for receiving and process data from the Web server
     */
    private class GetItems extends AsyncTask<Void, Void, Void> {
        // Context: every transaction in a Android application must be attached to a context
        private Activity activity;

        private Drawable actualBaseImage;

        /***
         * Special constructor: assigns the context to the thread
         *
         * @param activity: Context
         */
        //@Override
        protected GetItems(Activity activity) {
            this.activity = activity;
        }

        /**
         *  on PreExecute method: runs after the constructor is called and before the thread runs
         */
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(ProductInfo.this, "Item downloading", Toast.LENGTH_LONG).show();
        }

        /***
         *  Main thread
         * @param arg0
         * @return
         */
        protected Void doInBackground(Void... arg0) {
            //Create a HttpHandler object
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String url = "http://"+hostAddress+"/getDataBaseJson";


            // Download data from the web server using JSON;
            String jsonStr = sh.makeServiceCall(url);

            // Log download's results
            Log.e(TAG, "Response from url: " + jsonStr);

            //The JSON data must contain an array of JSON objects
            if (jsonStr != null) {
                try {
                    //Define a JSON object from the received data
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray items = jsonObj.getJSONArray("PRODUCT");
                    JSONArray items1 = jsonObj.getJSONArray("INFORMATION");

                    // looping through All Items
                    for (int i = 0; i < items.length(); i++) {
                        JSONObject c = items.getJSONObject(i);
                        JSONObject c1 = items1.getJSONObject(i);
                        String ownerP = c1.getString("Owner");
                        String name = c.getString("Name");
                        String description = c.getString("Description");
                        String price = c.getString("Price");
                        String department = c.getString("deptid");
                        String category = c.getString("cateid");
                        String imageLocation = c.getString("photoURL");

                        //Create URL for each image
                        String imageURL = "http://" + hostAddress + "/" + imageLocation;
                        //Download the actual image using the imageURL
                        Drawable actualImage= LoadImageFromWebOperations(imageURL);

                        itemUserList.add(new userItem(actualImage, description, price, category, department, name, ownerP));


//                        String name = c.getString("Name");
//                        String description = c.getString("Description");
//                        String price = c.getString("Price");
//                        String department = c.getString("deptid");
//                        String category = c.getString("cateid");
//                        String imageLocation = c.getString("photoURL");
//                        String departmentID = c.getString("deptid");
//                        String namedtp = c.getString("Name");
//                        String imageLocation = c.getString("image");

//                        //Create URL for each image
//                        String imageURL = "http://" + hostAddress + "/" + imageLocation;
//                        //Download the actual image using the imageURL
//                        Drawable actualImage= LoadImageFromWebOperations(imageURL);

                        // Create an userItem object and add it to the items' list
//                        itemUserList.add(new userItem(ownerP, name, description, department, category, price, actualImage));
//                        itemUserList.add(new userItem(name, description, department, category, price, actualImage));
//                        itemUserList.add(new userItem(departmentID, namedtp, actualImage));
                    }
                } //Log any problem with received data
                catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show(); }
                    });
                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }

        /***
         *  This method runs after thread completion
         *  Set up the List view using the ArrayAdapter
         *
         * @param result
         */
        protected void onPostExecute (Void result){
            super.onPostExecute(result);
            listv.setAdapter(adapter1);
        }

        /***
         *  This method downloads a image from a web server using an URL
         * @param url: Image URL
         * @return  d: android.graphics.drawable.Drawable;
         * */
        public Drawable LoadImageFromWebOperations(String url) {
            try {
                //Request the image to the web server
                InputStream is = (InputStream) new URL(url).getContent();

                //Generates an android.graphics.drawable.Drawable object
                Drawable d = Drawable.createFromStream(is, "src name");

                return d; }
            catch (Exception e) {
                return null;
            }
        }
    }

    /**
     * This class defines a ArrayAdapter for the ListView manipulation
     */
    public class UsersAdapter extends ArrayAdapter<userItem> {

        /**
         *  Constructor:
         * @param context: Activity
         * @param users: ArrayList for storing Items list
         */
        public UsersAdapter(Context context, ArrayList<userItem> users) {
            super(context, 0, users);
        }

        /***
         *  This method generates a view for manipulating the item list
         *  This method is called from the ListView.
         *
         * @param position: Item's position in the ArrayList
         * @param convertView:
         * @param parent
         * @return
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            userItem user = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_items2, parent, false);
            }
            // Lookup view for data population
            TextView itemOwner = (TextView) convertView.findViewById(R.id.itemOwner);
            TextView itemName = (TextView) convertView.findViewById(R.id.itemName);
            TextView itemDesc = (TextView) convertView.findViewById(R.id.itemDescription);
            TextView itemPrice = (TextView) convertView.findViewById(R.id.itemPrice);
            TextView itemDept = (TextView) convertView.findViewById(R.id.itemDepartment);
            TextView itemCate = (TextView) convertView.findViewById(R.id.itemCategory);
            ImageView itemImage = (ImageView) convertView.findViewById(R.id.imageView);

            // Populate the data into the template view using the data object
            itemOwner.setText(user.owner);
            itemName.setText(user.name);
            itemDesc.setText(user.description);
            itemPrice.setText(user.price);
            itemDept.setText(user.department);
            itemCate.setText(user.category);
            itemImage.setImageDrawable(user.image);

            // Return the completed view to render on screen
            convertView.setTag(position);

            //Create Listener to detect a click
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = (Integer) view.getTag();

                    // Show data of the clicked item
//                    Toast.makeText(getApplicationContext(),
//                            "You have selected " + itemUserList.get(position).name,
//                            Toast.LENGTH_LONG).show();
                    // Do what you want here...
                    //Intent inT = new Intent(HomePage.this, ProductInfo.class);
                    //startActivity(inT);
                }
            });
            return convertView;
        }
    }

    /**
     *  This class generates a Data structure for manipulating each Item in the application
     */
    public class userItem {
        public String owner;
        // Item's list
        public String name;
        // Item's description
        public String description;
        // Item's price
        public String price;
        // Item's department
        public String department;
        // Item's category
        public String category;
        // Item's image
        public Drawable image;

        /**
         *  Special constructor:
         * @param owner
         * @param name
         * @param description
         * @param price
         * @param department
         * @param category
         * @param image : Item's image
         */
        public userItem(Drawable image, String description, String price, String category, String department, String name, String owner) {
            this.owner = owner;
            this.name = name;
            this.description = description;
            this.price = price;
            this.department = department;
            this.category = category;
            this.image = image;
        }
    }
}
