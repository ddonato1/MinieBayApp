/**
 * Angel J. Vargas Lopez - S01274152
 * Deyaneira Donato Carrasquillo - S01183053
 * **
 * Search Result View Activity, this activity is where the result of the search item. It will be
 * showing all the products by the department that the user search for. Display the product information
 * and allowing the user to select the product that hi/she desire.
 **/
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
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;

public class SearchResultView extends AppCompatActivity {
    String l;
    ListView list;

    //This is for debugging
    private String TAG = SearchResultView.class.getSimpleName();
    //This is for managing the listview in the activity
    private ListView listv;
    //Web server's IP address
    private String hostAddress;
    //Users adapter
    private UsersAdapter adapter;
    // Item list for storing data from the web server
    private ArrayList<userItem> itemUserList;
    //Users adapter
    private UsersAdapter1 adapter1;
    // Item list for storing data from the web server
    private ArrayList<userItem1> itemUserList1;
    //Users adapter
    private UsersAdapter2 adapter2;
    // Item list for storing data from the web server
    private ArrayList<userItem2> itemUserList2;
    //Users adapter
    private UsersAdapter3 adapter3;
    // Item list for storing data from the web server
    private ArrayList<userItem3> itemUserList3;
    //Users adapter
    private UsersAdapter4 adapter4;
    // Item list for storing data from the web server
    private ArrayList<userItem4> itemUserList4;
    //Users adapter
    private UsersAdapter5 adapter5;
    // Item list for storing data from the web server
    private ArrayList<userItem5> itemUserList5;
    //Users adapter
    private UsersAdapter6 adapter6;
    // Item list for storing data from the web server
    private ArrayList<userItem6> itemUserList6;
    //Users adapter
    private UsersAdapter7 adapter7;
    // Item list for storing data from the web server
    private ArrayList<userItem7> itemUserList7;
    //Users adapter
    private UsersAdapter8 adapter8;
    // Item list for storing data from the web server
    private ArrayList<userItem8> itemUserList8;
    TextView testing;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result_view);
        list = (ListView) findViewById(R.id.viewSearchList);
        testing = (TextView) findViewById(R.id.test);
        Intent in = this.getIntent();
        l = in.getStringExtra("");
        testing.setText(l);
        list.setFilterText(l);

        hostAddress="192.168.0.11:8088";
        //Instate the Item list
        itemUserList = new ArrayList<>();
        // Defines the adapter: Receives the context (Current activity) and the Arraylist
        adapter = new UsersAdapter(this, itemUserList);
        //Instate the Item list
        itemUserList1 = new ArrayList<>();
        // Defines the adapter: Receives the context (Current activity) and the Arraylist
        adapter1 = new UsersAdapter1(this, itemUserList1);
        //Instate the Item list
        itemUserList2 = new ArrayList<>();
        // Defines the adapter: Receives the context (Current activity) and the Arraylist
        adapter2 = new UsersAdapter2(this, itemUserList2);
        //Instate the Item list
        itemUserList3 = new ArrayList<>();
        // Defines the adapter: Receives the context (Current activity) and the Arraylist
        adapter3 = new UsersAdapter3(this, itemUserList3);
        //Instate the Item list
        itemUserList4 = new ArrayList<>();
        // Defines the adapter: Receives the context (Current activity) and the Arraylist
        adapter4 = new UsersAdapter4(this, itemUserList4);
        //Instate the Item list
        itemUserList5 = new ArrayList<>();
        // Defines the adapter: Receives the context (Current activity) and the Arraylist
        adapter5 = new UsersAdapter5(this, itemUserList5);
        //Instate the Item list
        itemUserList6 = new ArrayList<>();
        // Defines the adapter: Receives the context (Current activity) and the Arraylist
        adapter6 = new UsersAdapter6(this, itemUserList6);
        //Instate the Item list
        itemUserList7 = new ArrayList<>();
        // Defines the adapter: Receives the context (Current activity) and the Arraylist
        adapter7 = new UsersAdapter7(this, itemUserList7);
        //Instate the Item list
        itemUserList8 = new ArrayList<>();
        // Defines the adapter: Receives the context (Current activity) and the Arraylist
        adapter8 = new UsersAdapter8(this, itemUserList8);

        // Create a accessor to the ListView in the activity
        listv = findViewById(R.id.viewSearchList);
        // Create and start the thread
        if(l.equals("Autos")){
            new GetItems(this).execute();
        }
        else if(l.equals("Electronics")){
            new GetItems1(this).execute();
        }
        else if(l.equals("Beauty")){
            new GetItems2(this).execute();
        }
        else if(l.equals("Pets")){
            new GetItems3(this).execute();
        }
        else if(l.equals("Home")){
            new GetItems4(this).execute();
        }
        else if(l.equals("Clothes")){
            new GetItems5(this).execute();
        }
        else if(l.equals("Toys")){
            new GetItems6(this).execute();
        }
        else if(l.equals("Garden")){
            new GetItems7(this).execute();
        }
        else if(l.equals("Pharmacy")){
            new GetItems8(this).execute();
        }
        else{
            Toast.makeText(this,"Sorry there is no item!", Toast.LENGTH_LONG).show();
        }
    }

    /***
     *  This class is a thread for receiving and process data from the Web server
     */
    class GetItems extends AsyncTask<Void, Void, Void> {
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
            Toast.makeText(SearchResultView.this, "Items list is downloading", Toast.LENGTH_LONG).show();
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
            String url = "http://"+hostAddress+"/getdatabasejson1";


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
                    JSONArray items2 = jsonObj.getJSONArray("DEPARTMENT");

                    // looping through All Items
                    for (int i = 0; i < items.length(); i++) {
                        JSONObject c = items.getJSONObject(i);
                        JSONObject c1 = items1.getJSONObject(i);
                        JSONObject c2 = items2.getJSONObject(i);
                        String name = c.getString("Name");
                        String description = c.getString("Description");
                        String price = c.getString("Price");
                        String department = c.getString("deptid");
                        String category = c.getString("cateid");
                        String imageLocation = c.getString("photoURL");
                        String ownerP = c1.getString("Owner");
                        String productID = c.getString("prodid");
                        String named = c2.getString("Name");

                        String path = "cpen410/Mini_eBay/imagesjson_miniebay";

                        //Create URL for each image
                        String imageURL = "http://" + hostAddress + "/" + path +"/"+ imageLocation;
                        //Download the actual image using the imageURL
                        Drawable actualImage= LoadImageFromWebOperations(imageURL);
                        itemUserList.add(new userItem(actualImage, description, price, category, department, name, ownerP, productID, named));
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
            listv.setAdapter(adapter);
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
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_items3, parent, false);
            }
            // Lookup view for data population
            final TextView itemOwner = (TextView) convertView.findViewById(R.id.itemOwner);
            final TextView itemName = (TextView) convertView.findViewById(R.id.itemName);
            final TextView itemDesc = (TextView) convertView.findViewById(R.id.itemDescription);
            final TextView itemPrice = (TextView) convertView.findViewById(R.id.itemPrice);
            final TextView itemDept = (TextView) convertView.findViewById(R.id.itemDepartment);
            final TextView itemCate = (TextView) convertView.findViewById(R.id.itemCategory);
            final TextView itemId = (TextView) convertView.findViewById(R.id.itemDepatName);
            final TextView itemNameDept = (TextView) convertView.findViewById(R.id.itemid);
            final ImageView itemImage = (ImageView) convertView.findViewById(R.id.imageView);

            // Populate the data into the template view using the data object
            itemOwner.setText(user.owner);
            itemName.setText(user.name);
            itemDesc.setText(user.description);
            itemPrice.setText(user.price);
            itemDept.setText(user.department);
            itemCate.setText(user.category);
            itemImage.setImageDrawable(user.image);
            itemId.setText(user.productid);
            itemNameDept.setText(user.named);


            // Return the completed view to render on screen
            convertView.setTag(position);

            //Create Listener to detect a click
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = (Integer) view.getTag();

                    // Show data of the clicked item
                    Toast.makeText(getApplicationContext(),
                            "You have selected " + itemUserList.get(position).name,
                            Toast.LENGTH_LONG).show();
                    // Do what you want here...


                    //String urlImagen = getResources().getString(itemImage[position]);
//                    itemImage.setDrawingCacheEnabled(true);
//                    Bitmap bitmap = itemImage.getDrawingCache();
                    Intent inT = new Intent(SearchResultView.this, ProductInfo.class);
                    inT.putExtra("Name", itemName.getText());
                    inT.putExtra("Description", itemDesc.getText());
                    inT.putExtra("Price", itemPrice.getText());
                    inT.putExtra("deptid", itemDept.getText());
                    inT.putExtra("cateid", itemCate.getText());
                    inT.putExtra("Owner", itemOwner.getText());
                    inT.putExtra("prodid", itemId.getText());
                    //inT.putExtra("Name", itemNameDept.getText());
//                        inT.putExtra("photoURL", itemImage.get);
                    startActivity(inT);
                }
            });
            return convertView;
        }
    }

    /**
     *  This class generates a Data structure for manipulating each Item in the application
     */
    public class userItem implements Serializable {
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
        public String productid;
        public String named;

        /**
         *  Special constructor:
         * @param image : Item's image
         * @param description
         * @param price
         * @param category
         * @param department
         * @param name
         * @param owner
         * @param productid
         * @param named
         */
        public userItem(Drawable image, String description, String price, String category, String department, String name, String owner, String productid, String named) {
            this.owner = owner;
            this.name = name;
            this.description = description;
            this.price = price;
            this.department = department;
            this.category = category;
            this.image = image;
            this.productid = productid;
            this.named = named;
        }
    }

    /***
     *  This class is a thread for receiving and process data from the Web server
     */
    class GetItems1 extends AsyncTask<Void, Void, Void> {
        // Context: every transaction in a Android application must be attached to a context
        private Activity activity;

        private Drawable actualBaseImage;

        /***
         * Special constructor: assigns the context to the thread
         *
         * @param activity: Context
         */
        //@Override
        protected GetItems1(Activity activity) {
            this.activity = activity;
        }

        /**
         *  on PreExecute method: runs after the constructor is called and before the thread runs
         */
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(SearchResultView.this, "Items list is downloading", Toast.LENGTH_LONG).show();
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
            String url = "http://"+hostAddress+"/getdatabasejson2";


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
                    JSONArray items2 = jsonObj.getJSONArray("DEPARTMENT");
                    // looping through All Items
                    for (int i = 0; i < items.length(); i++) {
                        JSONObject c = items.getJSONObject(i);
                        JSONObject c1 = items1.getJSONObject(i);
                        JSONObject c2 = items2.getJSONObject(i);
                        String name = c.getString("Name");
                        String description = c.getString("Description");
                        String price = c.getString("Price");
                        String department = c.getString("deptid");
                        String category = c.getString("cateid");
                        String imageLocation = c.getString("photoURL");
                        String ownerP = c1.getString("Owner");
                        String productID = c.getString("prodid");
                        String named = c2.getString("Name");

                        String path = "cpen410/Mini_eBay/imagesjson_miniebay";

                        //Create URL for each image
                        String imageURL = "http://" + hostAddress + "/" + path +"/"+ imageLocation;
                        //Download the actual image using the imageURL
                        Drawable actualImage= LoadImageFromWebOperations(imageURL);
                        itemUserList.add(new userItem(actualImage, description, price, category, department, name, ownerP, productID, named));
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
    public class UsersAdapter1 extends ArrayAdapter<userItem1> {

        /**
         *  Constructor:
         * @param context: Activity
         * @param users: ArrayList for storing Items list
         */
        public UsersAdapter1(Context context, ArrayList<userItem1> users) {
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
            userItem1 user = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_items3, parent, false);
            }
            // Lookup view for data population
            final TextView itemOwner = (TextView) convertView.findViewById(R.id.itemOwner);
            final TextView itemName = (TextView) convertView.findViewById(R.id.itemName);
            final TextView itemDesc = (TextView) convertView.findViewById(R.id.itemDescription);
            final TextView itemPrice = (TextView) convertView.findViewById(R.id.itemPrice);
            final TextView itemDept = (TextView) convertView.findViewById(R.id.itemDepartment);
            final TextView itemCate = (TextView) convertView.findViewById(R.id.itemCategory);
            final TextView itemId = (TextView) convertView.findViewById(R.id.itemDepatName);
            final TextView itemNameDept = (TextView) convertView.findViewById(R.id.itemid);
            final ImageView itemImage = (ImageView) convertView.findViewById(R.id.imageView);

            // Populate the data into the template view using the data object
            itemOwner.setText(user.owner);
            itemName.setText(user.name);
            itemDesc.setText(user.description);
            itemPrice.setText(user.price);
            itemDept.setText(user.department);
            itemCate.setText(user.category);
            itemImage.setImageDrawable(user.image);
            itemId.setText(user.productid);
            itemNameDept.setText(user.named);


            // Return the completed view to render on screen
            convertView.setTag(position);

            //Create Listener to detect a click
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = (Integer) view.getTag();

                    // Show data of the clicked item
                    Toast.makeText(getApplicationContext(),
                            "You have selected " + itemUserList1.get(position).name,
                            Toast.LENGTH_LONG).show();
                    // Do what you want here...


                    //String urlImagen = getResources().getString(itemImage[position]);
//                    itemImage.setDrawingCacheEnabled(true);
//                    Bitmap bitmap = itemImage.getDrawingCache();
                    Intent inT = new Intent(SearchResultView.this, ProductInfo.class);
                    inT.putExtra("Name", itemName.getText());
                    inT.putExtra("Description", itemDesc.getText());
                    inT.putExtra("Price", itemPrice.getText());
                    inT.putExtra("deptid", itemDept.getText());
                    inT.putExtra("cateid", itemCate.getText());
                    inT.putExtra("Owner", itemOwner.getText());
                    inT.putExtra("prodid", itemId.getText());
                    //inT.putExtra("Name", itemNameDept.getText());
//                        inT.putExtra("photoURL", itemImage.get);
                    startActivity(inT);
                }
            });
            return convertView;
        }
    }

    /**
     *  This class generates a Data structure for manipulating each Item in the application
     */
    public class userItem1 implements Serializable {
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
        public String productid;
        public String named;

        /**
         *  Special constructor:
         * @param image : Item's image
         * @param description
         * @param price
         * @param category
         * @param department
         * @param name
         * @param owner
         * @param productid
         * @param named
         */
        public userItem1(Drawable image, String description, String price, String category, String department, String name, String owner, String productid, String named) {
            this.owner = owner;
            this.name = name;
            this.description = description;
            this.price = price;
            this.department = department;
            this.category = category;
            this.image = image;
            this.productid = productid;
            this.named = named;
        }
    }

    /***
     *  This class is a thread for receiving and process data from the Web server
     */
    class GetItems2 extends AsyncTask<Void, Void, Void> {
        // Context: every transaction in a Android application must be attached to a context
        private Activity activity;

        private Drawable actualBaseImage;

        /***
         * Special constructor: assigns the context to the thread
         *
         * @param activity: Context
         */
        //@Override
        protected GetItems2(Activity activity) {
            this.activity = activity;
        }

        /**
         *  on PreExecute method: runs after the constructor is called and before the thread runs
         */
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(SearchResultView.this, "Items list is downloading", Toast.LENGTH_LONG).show();
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
            String url = "http://"+hostAddress+"/getdatabasejson3";


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
                    JSONArray items2 = jsonObj.getJSONArray("DEPARTMENT");
                    // looping through All Items
                    for (int i = 0; i < items.length(); i++) {
                        JSONObject c = items.getJSONObject(i);
                        JSONObject c1 = items1.getJSONObject(i);
                        JSONObject c2 = items2.getJSONObject(i);
                        String name = c.getString("Name");
                        String description = c.getString("Description");
                        String price = c.getString("Price");
                        String department = c.getString("deptid");
                        String category = c.getString("cateid");
                        String imageLocation = c.getString("photoURL");
                        String ownerP = c1.getString("Owner");
                        String productID = c.getString("prodid");
                        String named = c2.getString("Name");

                        if(list.equals("A") || list.equals("a")){
                            list.setFilterText(named);
                            //name;
                        }

                        String path = "cpen410/Mini_eBay/imagesjson_miniebay";

                        //Create URL for each image
                        String imageURL = "http://" + hostAddress + "/" + path +"/"+ imageLocation;
                        //Download the actual image using the imageURL
                        Drawable actualImage= LoadImageFromWebOperations(imageURL);
//                            imag = actualImage;
                        itemUserList.add(new userItem(actualImage, description, price, category, department, name, ownerP, productID, named));
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
            listv.setAdapter(adapter2);
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
    public class UsersAdapter2 extends ArrayAdapter<userItem2> {

        /**
         *  Constructor:
         * @param context: Activity
         * @param users: ArrayList for storing Items list
         */
        public UsersAdapter2(Context context, ArrayList<userItem2> users) {
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
            userItem2 user = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_items3, parent, false);
            }
            // Lookup view for data population
            final TextView itemOwner = (TextView) convertView.findViewById(R.id.itemOwner);
            final TextView itemName = (TextView) convertView.findViewById(R.id.itemName);
            final TextView itemDesc = (TextView) convertView.findViewById(R.id.itemDescription);
            final TextView itemPrice = (TextView) convertView.findViewById(R.id.itemPrice);
            final TextView itemDept = (TextView) convertView.findViewById(R.id.itemDepartment);
            final TextView itemCate = (TextView) convertView.findViewById(R.id.itemCategory);
            final TextView itemId = (TextView) convertView.findViewById(R.id.itemDepatName);
            final TextView itemNameDept = (TextView) convertView.findViewById(R.id.itemid);
            final ImageView itemImage = (ImageView) convertView.findViewById(R.id.imageView);

            // Populate the data into the template view using the data object
            itemOwner.setText(user.owner);
            itemName.setText(user.name);
            itemDesc.setText(user.description);
            itemPrice.setText(user.price);
            itemDept.setText(user.department);
            itemCate.setText(user.category);
            itemImage.setImageDrawable(user.image);
            itemId.setText(user.productid);
            itemNameDept.setText(user.named);


            // Return the completed view to render on screen
            convertView.setTag(position);

            //Create Listener to detect a click
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = (Integer) view.getTag();

                    // Show data of the clicked item
                    Toast.makeText(getApplicationContext(),
                            "You have selected " + itemUserList2.get(position).name,
                            Toast.LENGTH_LONG).show();
                    // Do what you want here...


                    //String urlImagen = getResources().getString(itemImage[position]);
//                    itemImage.setDrawingCacheEnabled(true);
//                    Bitmap bitmap = itemImage.getDrawingCache();
                    Intent inT = new Intent(SearchResultView.this, ProductInfo.class);
                    inT.putExtra("Name", itemName.getText());
                    inT.putExtra("Description", itemDesc.getText());
                    inT.putExtra("Price", itemPrice.getText());
                    inT.putExtra("deptid", itemDept.getText());
                    inT.putExtra("cateid", itemCate.getText());
                    inT.putExtra("Owner", itemOwner.getText());
                    inT.putExtra("prodid", itemId.getText());
                    //inT.putExtra("Name", itemNameDept.getText());
//                        inT.putExtra("photoURL", itemImage.get);
                    startActivity(inT);
                }
            });
            return convertView;
        }
    }

    /**
     *  This class generates a Data structure for manipulating each Item in the application
     */
    public class userItem2 implements Serializable {
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
        public String productid;
        public String named;

        /**
         *  Special constructor:
         * @param image : Item's image
         * @param description
         * @param price
         * @param category
         * @param department
         * @param name
         * @param owner
         * @param productid
         * @param named
         */
        public userItem2(Drawable image, String description, String price, String category, String department, String name, String owner, String productid, String named) {
            this.owner = owner;
            this.name = name;
            this.description = description;
            this.price = price;
            this.department = department;
            this.category = category;
            this.image = image;
            this.productid = productid;
            this.named = named;
        }
    }

    /***
     *  This class is a thread for receiving and process data from the Web server
     */
    class GetItems3 extends AsyncTask<Void, Void, Void> {
        // Context: every transaction in a Android application must be attached to a context
        private Activity activity;

        private Drawable actualBaseImage;

        /***
         * Special constructor: assigns the context to the thread
         *
         * @param activity: Context
         */
        //@Override
        protected GetItems3(Activity activity) {
            this.activity = activity;
        }

        /**
         *  on PreExecute method: runs after the constructor is called and before the thread runs
         */
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(SearchResultView.this, "Items list is downloading", Toast.LENGTH_LONG).show();
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
            String url = "http://"+hostAddress+"/getdatabasejson4";

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
                    JSONArray items2 = jsonObj.getJSONArray("DEPARTMENT");
                    // looping through All Items
                    for (int i = 0; i < items.length(); i++) {
                        JSONObject c = items.getJSONObject(i);
                        JSONObject c1 = items1.getJSONObject(i);
                        JSONObject c2 = items2.getJSONObject(i);
                        String name = c.getString("Name");
                        String description = c.getString("Description");
                        String price = c.getString("Price");
                        String department = c.getString("deptid");
                        String category = c.getString("cateid");
                        String imageLocation = c.getString("photoURL");
                        String ownerP = c1.getString("Owner");
                        String productID = c.getString("prodid");
                        String named = c2.getString("Name");

                        if(list.equals("A") || list.equals("a")){
                            list.setFilterText(named);
                            //name;
                        }

                        String path = "cpen410/Mini_eBay/imagesjson_miniebay";

                        //Create URL for each image
                        String imageURL = "http://" + hostAddress + "/" + path +"/"+ imageLocation;
                        //Download the actual image using the imageURL
                        Drawable actualImage= LoadImageFromWebOperations(imageURL);
//                            imag = actualImage;
                        itemUserList.add(new userItem(actualImage, description, price, category, department, name, ownerP, productID, named));
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
            listv.setAdapter(adapter3);
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
    public class UsersAdapter3 extends ArrayAdapter<userItem3> {

        /**
         *  Constructor:
         * @param context: Activity
         * @param users: ArrayList for storing Items list
         */
        public UsersAdapter3(Context context, ArrayList<userItem3> users) {
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
            userItem3 user = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_items3, parent, false);
            }
            // Lookup view for data population
            final TextView itemOwner = (TextView) convertView.findViewById(R.id.itemOwner);
            final TextView itemName = (TextView) convertView.findViewById(R.id.itemName);
            final TextView itemDesc = (TextView) convertView.findViewById(R.id.itemDescription);
            final TextView itemPrice = (TextView) convertView.findViewById(R.id.itemPrice);
            final TextView itemDept = (TextView) convertView.findViewById(R.id.itemDepartment);
            final TextView itemCate = (TextView) convertView.findViewById(R.id.itemCategory);
            final TextView itemId = (TextView) convertView.findViewById(R.id.itemDepatName);
            final TextView itemNameDept = (TextView) convertView.findViewById(R.id.itemid);
            final ImageView itemImage = (ImageView) convertView.findViewById(R.id.imageView);

            // Populate the data into the template view using the data object
            itemOwner.setText(user.owner);
            itemName.setText(user.name);
            itemDesc.setText(user.description);
            itemPrice.setText(user.price);
            itemDept.setText(user.department);
            itemCate.setText(user.category);
            itemImage.setImageDrawable(user.image);
            itemId.setText(user.productid);
            itemNameDept.setText(user.named);


            // Return the completed view to render on screen
            convertView.setTag(position);

            //Create Listener to detect a click
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = (Integer) view.getTag();

                    // Show data of the clicked item
                    Toast.makeText(getApplicationContext(),
                            "You have selected " + itemUserList3.get(position).name,
                            Toast.LENGTH_LONG).show();
                    // Do what you want here...


                    //String urlImagen = getResources().getString(itemImage[position]);
//                    itemImage.setDrawingCacheEnabled(true);
//                    Bitmap bitmap = itemImage.getDrawingCache();
                    Intent inT = new Intent(SearchResultView.this, ProductInfo.class);
                    inT.putExtra("Name", itemName.getText());
                    inT.putExtra("Description", itemDesc.getText());
                    inT.putExtra("Price", itemPrice.getText());
                    inT.putExtra("deptid", itemDept.getText());
                    inT.putExtra("cateid", itemCate.getText());
                    inT.putExtra("Owner", itemOwner.getText());
                    inT.putExtra("prodid", itemId.getText());
                    //inT.putExtra("Name", itemNameDept.getText());
//                        inT.putExtra("photoURL", itemImage.get);
                    startActivity(inT);
                }
            });
            return convertView;
        }
    }

    /**
     *  This class generates a Data structure for manipulating each Item in the application
     */
    public class userItem3 implements Serializable {
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
        public String productid;
        public String named;

        /**
         *  Special constructor:
         * @param image : Item's image
         * @param description
         * @param price
         * @param category
         * @param department
         * @param name
         * @param owner
         * @param productid
         * @param named
         */
        public userItem3(Drawable image, String description, String price, String category, String department, String name, String owner, String productid, String named) {
            this.owner = owner;
            this.name = name;
            this.description = description;
            this.price = price;
            this.department = department;
            this.category = category;
            this.image = image;
            this.productid = productid;
            this.named = named;
        }
    }

    /***
     *  This class is a thread for receiving and process data from the Web server
     */
    class GetItems4 extends AsyncTask<Void, Void, Void> {
        // Context: every transaction in a Android application must be attached to a context
        private Activity activity;

        private Drawable actualBaseImage;

        /***
         * Special constructor: assigns the context to the thread
         *
         * @param activity: Context
         */
        //@Override
        protected GetItems4(Activity activity) {
            this.activity = activity;
        }

        /**
         *  on PreExecute method: runs after the constructor is called and before the thread runs
         */
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(SearchResultView.this, "Items list is downloading", Toast.LENGTH_LONG).show();
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
            String url = "http://"+hostAddress+"/getdatabasejson5";


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
                    JSONArray items2 = jsonObj.getJSONArray("DEPARTMENT");
                    // looping through All Items
                    for (int i = 0; i < items.length(); i++) {
                        JSONObject c = items.getJSONObject(i);
                        JSONObject c1 = items1.getJSONObject(i);
                        JSONObject c2 = items2.getJSONObject(i);
                        String name = c.getString("Name");
                        String description = c.getString("Description");
                        String price = c.getString("Price");
                        String department = c.getString("deptid");
                        String category = c.getString("cateid");
                        String imageLocation = c.getString("photoURL");
                        String ownerP = c1.getString("Owner");
                        String productID = c.getString("prodid");
                        String named = c2.getString("Name");

                        if(list.equals("A") || list.equals("a")){
                            list.setFilterText(named);
                            //name;
                        }

                        String path = "cpen410/Mini_eBay/imagesjson_miniebay";

                        //Create URL for each image
                        String imageURL = "http://" + hostAddress + "/" + path +"/"+ imageLocation;
                        //Download the actual image using the imageURL
                        Drawable actualImage= LoadImageFromWebOperations(imageURL);
//                            imag = actualImage;
                        itemUserList.add(new userItem(actualImage, description, price, category, department, name, ownerP, productID, named));
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
            listv.setAdapter(adapter4);
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
    public class UsersAdapter4 extends ArrayAdapter<userItem4> {

        /**
         *  Constructor:
         * @param context: Activity
         * @param users: ArrayList for storing Items list
         */
        public UsersAdapter4(Context context, ArrayList<userItem4> users) {
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
            userItem4 user = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_items3, parent, false);
            }
            // Lookup view for data population
            final TextView itemOwner = (TextView) convertView.findViewById(R.id.itemOwner);
            final TextView itemName = (TextView) convertView.findViewById(R.id.itemName);
            final TextView itemDesc = (TextView) convertView.findViewById(R.id.itemDescription);
            final TextView itemPrice = (TextView) convertView.findViewById(R.id.itemPrice);
            final TextView itemDept = (TextView) convertView.findViewById(R.id.itemDepartment);
            final TextView itemCate = (TextView) convertView.findViewById(R.id.itemCategory);
            final TextView itemId = (TextView) convertView.findViewById(R.id.itemDepatName);
            final TextView itemNameDept = (TextView) convertView.findViewById(R.id.itemid);
            final ImageView itemImage = (ImageView) convertView.findViewById(R.id.imageView);

            // Populate the data into the template view using the data object
            itemOwner.setText(user.owner);
            itemName.setText(user.name);
            itemDesc.setText(user.description);
            itemPrice.setText(user.price);
            itemDept.setText(user.department);
            itemCate.setText(user.category);
            itemImage.setImageDrawable(user.image);
            itemId.setText(user.productid);
            itemNameDept.setText(user.named);


            // Return the completed view to render on screen
            convertView.setTag(position);

            //Create Listener to detect a click
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = (Integer) view.getTag();

                    // Show data of the clicked item
                    Toast.makeText(getApplicationContext(),
                            "You have selected " + itemUserList4.get(position).name,
                            Toast.LENGTH_LONG).show();
                    // Do what you want here...


                    //String urlImagen = getResources().getString(itemImage[position]);
//                    itemImage.setDrawingCacheEnabled(true);
//                    Bitmap bitmap = itemImage.getDrawingCache();
                    Intent inT = new Intent(SearchResultView.this, ProductInfo.class);
                    inT.putExtra("Name", itemName.getText());
                    inT.putExtra("Description", itemDesc.getText());
                    inT.putExtra("Price", itemPrice.getText());
                    inT.putExtra("deptid", itemDept.getText());
                    inT.putExtra("cateid", itemCate.getText());
                    inT.putExtra("Owner", itemOwner.getText());
                    inT.putExtra("prodid", itemId.getText());
                    //inT.putExtra("Name", itemNameDept.getText());
//                        inT.putExtra("photoURL", itemImage.get);
                    startActivity(inT);
                }
            });
            return convertView;
        }
    }

    /**
     *  This class generates a Data structure for manipulating each Item in the application
     */
    public class userItem4 implements Serializable {
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
        public String productid;
        public String named;

        /**
         *  Special constructor:
         * @param image : Item's image
         * @param description
         * @param price
         * @param category
         * @param department
         * @param name
         * @param owner
         * @param productid
         * @param named
         */
        public userItem4(Drawable image, String description, String price, String category, String department, String name, String owner, String productid, String named) {
            this.owner = owner;
            this.name = name;
            this.description = description;
            this.price = price;
            this.department = department;
            this.category = category;
            this.image = image;
            this.productid = productid;
            this.named = named;
        }
    }

    /***
     *  This class is a thread for receiving and process data from the Web server
     */
    class GetItems5 extends AsyncTask<Void, Void, Void> {
        // Context: every transaction in a Android application must be attached to a context
        private Activity activity;

        private Drawable actualBaseImage;

        /***
         * Special constructor: assigns the context to the thread
         *
         * @param activity: Context
         */
        //@Override
        protected GetItems5(Activity activity) {
            this.activity = activity;
        }

        /**
         *  on PreExecute method: runs after the constructor is called and before the thread runs
         */
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(SearchResultView.this, "Items list is downloading", Toast.LENGTH_LONG).show();
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
            String url = "http://"+hostAddress+"/getdatabasejson6";


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
                    JSONArray items2 = jsonObj.getJSONArray("DEPARTMENT");
                    // looping through All Items
                    for (int i = 0; i < items.length(); i++) {
                        JSONObject c = items.getJSONObject(i);
                        JSONObject c1 = items1.getJSONObject(i);
                        JSONObject c2 = items2.getJSONObject(i);
                        String name = c.getString("Name");
                        String description = c.getString("Description");
                        String price = c.getString("Price");
                        String department = c.getString("deptid");
                        String category = c.getString("cateid");
                        String imageLocation = c.getString("photoURL");
                        String ownerP = c1.getString("Owner");
                        String productID = c.getString("prodid");
                        String named = c2.getString("Name");

                        if(list.equals("A") || list.equals("a")){
                            list.setFilterText(named);
                            //name;
                        }

                        String path = "cpen410/Mini_eBay/imagesjson_miniebay";

                        //Create URL for each image
                        String imageURL = "http://" + hostAddress + "/" + path +"/"+ imageLocation;
                        //Download the actual image using the imageURL
                        Drawable actualImage= LoadImageFromWebOperations(imageURL);
//                            imag = actualImage;
                        itemUserList.add(new userItem(actualImage, description, price, category, department, name, ownerP, productID, named));
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
            listv.setAdapter(adapter2);
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
    public class UsersAdapter5 extends ArrayAdapter<userItem5> {

        /**
         *  Constructor:
         * @param context: Activity
         * @param users: ArrayList for storing Items list
         */
        public UsersAdapter5(Context context, ArrayList<userItem5> users) {
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
            userItem5 user = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_items3, parent, false);
            }
            // Lookup view for data population
            final TextView itemOwner = (TextView) convertView.findViewById(R.id.itemOwner);
            final TextView itemName = (TextView) convertView.findViewById(R.id.itemName);
            final TextView itemDesc = (TextView) convertView.findViewById(R.id.itemDescription);
            final TextView itemPrice = (TextView) convertView.findViewById(R.id.itemPrice);
            final TextView itemDept = (TextView) convertView.findViewById(R.id.itemDepartment);
            final TextView itemCate = (TextView) convertView.findViewById(R.id.itemCategory);
            final TextView itemId = (TextView) convertView.findViewById(R.id.itemDepatName);
            final TextView itemNameDept = (TextView) convertView.findViewById(R.id.itemid);
            final ImageView itemImage = (ImageView) convertView.findViewById(R.id.imageView);

            // Populate the data into the template view using the data object
            itemOwner.setText(user.owner);
            itemName.setText(user.name);
            itemDesc.setText(user.description);
            itemPrice.setText(user.price);
            itemDept.setText(user.department);
            itemCate.setText(user.category);
            itemImage.setImageDrawable(user.image);
            itemId.setText(user.productid);
            itemNameDept.setText(user.named);


            // Return the completed view to render on screen
            convertView.setTag(position);

            //Create Listener to detect a click
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = (Integer) view.getTag();

                    // Show data of the clicked item
                    Toast.makeText(getApplicationContext(),
                            "You have selected " + itemUserList5.get(position).name,
                            Toast.LENGTH_LONG).show();
                    // Do what you want here...


                    //String urlImagen = getResources().getString(itemImage[position]);
//                    itemImage.setDrawingCacheEnabled(true);
//                    Bitmap bitmap = itemImage.getDrawingCache();
                    Intent inT = new Intent(SearchResultView.this, ProductInfo.class);
                    inT.putExtra("Name", itemName.getText());
                    inT.putExtra("Description", itemDesc.getText());
                    inT.putExtra("Price", itemPrice.getText());
                    inT.putExtra("deptid", itemDept.getText());
                    inT.putExtra("cateid", itemCate.getText());
                    inT.putExtra("Owner", itemOwner.getText());
                    inT.putExtra("prodid", itemId.getText());
                    //inT.putExtra("Name", itemNameDept.getText());
//                        inT.putExtra("photoURL", itemImage.get);
                    startActivity(inT);
                }
            });
            return convertView;
        }
    }

    /**
     *  This class generates a Data structure for manipulating each Item in the application
     */
    public class userItem5 implements Serializable {
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
        public String productid;
        public String named;

        /**
         *  Special constructor:
         * @param image : Item's image
         * @param description
         * @param price
         * @param category
         * @param department
         * @param name
         * @param owner
         * @param productid
         * @param named
         */
        public userItem5(Drawable image, String description, String price, String category, String department, String name, String owner, String productid, String named) {
            this.owner = owner;
            this.name = name;
            this.description = description;
            this.price = price;
            this.department = department;
            this.category = category;
            this.image = image;
            this.productid = productid;
            this.named = named;
        }
    }

    /***
     *  This class is a thread for receiving and process data from the Web server
     */
    class GetItems6 extends AsyncTask<Void, Void, Void> {
        // Context: every transaction in a Android application must be attached to a context
        private Activity activity;

        private Drawable actualBaseImage;

        /***
         * Special constructor: assigns the context to the thread
         *
         * @param activity: Context
         */
        //@Override
        protected GetItems6(Activity activity) {
            this.activity = activity;
        }

        /**
         *  on PreExecute method: runs after the constructor is called and before the thread runs
         */
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(SearchResultView.this, "Items list is downloading", Toast.LENGTH_LONG).show();
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
            String url = "http://"+hostAddress+"/getdatabasejson7";


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
                    JSONArray items2 = jsonObj.getJSONArray("DEPARTMENT");
                    // looping through All Items
                    for (int i = 0; i < items.length(); i++) {
                        JSONObject c = items.getJSONObject(i);
                        JSONObject c1 = items1.getJSONObject(i);
                        JSONObject c2 = items2.getJSONObject(i);
                        String name = c.getString("Name");
                        String description = c.getString("Description");
                        String price = c.getString("Price");
                        String department = c.getString("deptid");
                        String category = c.getString("cateid");
                        String imageLocation = c.getString("photoURL");
                        String ownerP = c1.getString("Owner");
                        String productID = c.getString("prodid");
                        String named = c2.getString("Name");

                        if(list.equals("A") || list.equals("a")){
                            list.setFilterText(named);
                            //name;
                        }

                        String path = "cpen410/Mini_eBay/imagesjson_miniebay";

                        //Create URL for each image
                        String imageURL = "http://" + hostAddress + "/" + path +"/"+ imageLocation;
                        //Download the actual image using the imageURL
                        Drawable actualImage= LoadImageFromWebOperations(imageURL);
//                            imag = actualImage;
                        itemUserList.add(new userItem(actualImage, description, price, category, department, name, ownerP, productID, named));
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
            listv.setAdapter(adapter6);
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
    public class UsersAdapter6 extends ArrayAdapter<userItem6> {

        /**
         *  Constructor:
         * @param context: Activity
         * @param users: ArrayList for storing Items list
         */
        public UsersAdapter6(Context context, ArrayList<userItem6> users) {
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
            userItem6 user = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_items3, parent, false);
            }
            // Lookup view for data population
            final TextView itemOwner = (TextView) convertView.findViewById(R.id.itemOwner);
            final TextView itemName = (TextView) convertView.findViewById(R.id.itemName);
            final TextView itemDesc = (TextView) convertView.findViewById(R.id.itemDescription);
            final TextView itemPrice = (TextView) convertView.findViewById(R.id.itemPrice);
            final TextView itemDept = (TextView) convertView.findViewById(R.id.itemDepartment);
            final TextView itemCate = (TextView) convertView.findViewById(R.id.itemCategory);
            final TextView itemId = (TextView) convertView.findViewById(R.id.itemDepatName);
            final TextView itemNameDept = (TextView) convertView.findViewById(R.id.itemid);
            final ImageView itemImage = (ImageView) convertView.findViewById(R.id.imageView);

            // Populate the data into the template view using the data object
            itemOwner.setText(user.owner);
            itemName.setText(user.name);
            itemDesc.setText(user.description);
            itemPrice.setText(user.price);
            itemDept.setText(user.department);
            itemCate.setText(user.category);
            itemImage.setImageDrawable(user.image);
            itemId.setText(user.productid);
            itemNameDept.setText(user.named);


            // Return the completed view to render on screen
            convertView.setTag(position);

            //Create Listener to detect a click
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = (Integer) view.getTag();

                    // Show data of the clicked item
                    Toast.makeText(getApplicationContext(),
                            "You have selected " + itemUserList6.get(position).name,
                            Toast.LENGTH_LONG).show();
                    // Do what you want here...


                    //String urlImagen = getResources().getString(itemImage[position]);
//                    itemImage.setDrawingCacheEnabled(true);
//                    Bitmap bitmap = itemImage.getDrawingCache();
                    Intent inT = new Intent(SearchResultView.this, ProductInfo.class);
                    inT.putExtra("Name", itemName.getText());
                    inT.putExtra("Description", itemDesc.getText());
                    inT.putExtra("Price", itemPrice.getText());
                    inT.putExtra("deptid", itemDept.getText());
                    inT.putExtra("cateid", itemCate.getText());
                    inT.putExtra("Owner", itemOwner.getText());
                    inT.putExtra("prodid", itemId.getText());
                    //inT.putExtra("Name", itemNameDept.getText());
//                        inT.putExtra("photoURL", itemImage.get);
                    startActivity(inT);
                }
            });
            return convertView;
        }
    }

    /**
     *  This class generates a Data structure for manipulating each Item in the application
     */
    public class userItem6 implements Serializable {
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
        public String productid;
        public String named;

        /**
         *  Special constructor:
         * @param image : Item's image
         * @param description
         * @param price
         * @param category
         * @param department
         * @param name
         * @param owner
         * @param productid
         * @param named
         */
        public userItem6(Drawable image, String description, String price, String category, String department, String name, String owner, String productid, String named) {
            this.owner = owner;
            this.name = name;
            this.description = description;
            this.price = price;
            this.department = department;
            this.category = category;
            this.image = image;
            this.productid = productid;
            this.named = named;
        }
    }

    /***
     *  This class is a thread for receiving and process data from the Web server
     */
    class GetItems7 extends AsyncTask<Void, Void, Void> {
        // Context: every transaction in a Android application must be attached to a context
        private Activity activity;

        private Drawable actualBaseImage;

        /***
         * Special constructor: assigns the context to the thread
         *
         * @param activity: Context
         */
        //@Override
        protected GetItems7(Activity activity) {
            this.activity = activity;
        }

        /**
         *  on PreExecute method: runs after the constructor is called and before the thread runs
         */
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(SearchResultView.this, "Items list is downloading", Toast.LENGTH_LONG).show();
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
            String url = "http://"+hostAddress+"/getdatabasejson8";


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
                    JSONArray items2 = jsonObj.getJSONArray("DEPARTMENT");
                    // looping through All Items
                    for (int i = 0; i < items.length(); i++) {
                        JSONObject c = items.getJSONObject(i);
                        JSONObject c1 = items1.getJSONObject(i);
                        JSONObject c2 = items2.getJSONObject(i);
                        String name = c.getString("Name");
                        String description = c.getString("Description");
                        String price = c.getString("Price");
                        String department = c.getString("deptid");
                        String category = c.getString("cateid");
                        String imageLocation = c.getString("photoURL");
                        String ownerP = c1.getString("Owner");
                        String productID = c.getString("prodid");
                        String named = c2.getString("Name");

                        if(list.equals("A") || list.equals("a")){
                            list.setFilterText(named);
                            //name;
                        }

                        String path = "cpen410/Mini_eBay/imagesjson_miniebay";

                        //Create URL for each image
                        String imageURL = "http://" + hostAddress + "/" + path +"/"+ imageLocation;
                        //Download the actual image using the imageURL
                        Drawable actualImage= LoadImageFromWebOperations(imageURL);
//                            imag = actualImage;
                        itemUserList.add(new userItem(actualImage, description, price, category, department, name, ownerP, productID, named));
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
            listv.setAdapter(adapter7);
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
    public class UsersAdapter7 extends ArrayAdapter<userItem7> {

        /**
         *  Constructor:
         * @param context: Activity
         * @param users: ArrayList for storing Items list
         */
        public UsersAdapter7(Context context, ArrayList<userItem7> users) {
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
            userItem7 user = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_items3, parent, false);
            }
            // Lookup view for data population
            final TextView itemOwner = (TextView) convertView.findViewById(R.id.itemOwner);
            final TextView itemName = (TextView) convertView.findViewById(R.id.itemName);
            final TextView itemDesc = (TextView) convertView.findViewById(R.id.itemDescription);
            final TextView itemPrice = (TextView) convertView.findViewById(R.id.itemPrice);
            final TextView itemDept = (TextView) convertView.findViewById(R.id.itemDepartment);
            final TextView itemCate = (TextView) convertView.findViewById(R.id.itemCategory);
            final TextView itemId = (TextView) convertView.findViewById(R.id.itemDepatName);
            final TextView itemNameDept = (TextView) convertView.findViewById(R.id.itemid);
            final ImageView itemImage = (ImageView) convertView.findViewById(R.id.imageView);

            // Populate the data into the template view using the data object
            itemOwner.setText(user.owner);
            itemName.setText(user.name);
            itemDesc.setText(user.description);
            itemPrice.setText(user.price);
            itemDept.setText(user.department);
            itemCate.setText(user.category);
            itemImage.setImageDrawable(user.image);
            itemId.setText(user.productid);
            itemNameDept.setText(user.named);


            // Return the completed view to render on screen
            convertView.setTag(position);

            //Create Listener to detect a click
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = (Integer) view.getTag();

                    // Show data of the clicked item
                    Toast.makeText(getApplicationContext(),
                            "You have selected " + itemUserList7.get(position).name,
                            Toast.LENGTH_LONG).show();
                    // Do what you want here...


                    //String urlImagen = getResources().getString(itemImage[position]);
//                    itemImage.setDrawingCacheEnabled(true);
//                    Bitmap bitmap = itemImage.getDrawingCache();
                    Intent inT = new Intent(SearchResultView.this, ProductInfo.class);
                    inT.putExtra("Name", itemName.getText());
                    inT.putExtra("Description", itemDesc.getText());
                    inT.putExtra("Price", itemPrice.getText());
                    inT.putExtra("deptid", itemDept.getText());
                    inT.putExtra("cateid", itemCate.getText());
                    inT.putExtra("Owner", itemOwner.getText());
                    inT.putExtra("prodid", itemId.getText());
                    //inT.putExtra("Name", itemNameDept.getText());
//                        inT.putExtra("photoURL", itemImage.get);
                    startActivity(inT);
                }
            });
            return convertView;
        }
    }

    /**
     *  This class generates a Data structure for manipulating each Item in the application
     */
    public class userItem7 implements Serializable {
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
        public String productid;
        public String named;

        /**
         *  Special constructor:
         * @param image : Item's image
         * @param description
         * @param price
         * @param category
         * @param department
         * @param name
         * @param owner
         * @param productid
         * @param named
         */
        public userItem7(Drawable image, String description, String price, String category, String department, String name, String owner, String productid, String named) {
            this.owner = owner;
            this.name = name;
            this.description = description;
            this.price = price;
            this.department = department;
            this.category = category;
            this.image = image;
            this.productid = productid;
            this.named = named;
        }
    }

    /***
     *  This class is a thread for receiving and process data from the Web server
     */
    class GetItems8 extends AsyncTask<Void, Void, Void> {
        // Context: every transaction in a Android application must be attached to a context
        private Activity activity;

        private Drawable actualBaseImage;

        /***
         * Special constructor: assigns the context to the thread
         *
         * @param activity: Context
         */
        //@Override
        protected GetItems8(Activity activity) {
            this.activity = activity;
        }

        /**
         *  on PreExecute method: runs after the constructor is called and before the thread runs
         */
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(SearchResultView.this, "Items list is downloading", Toast.LENGTH_LONG).show();
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
            String url = "http://"+hostAddress+"/getdatabasejson9";


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
                    JSONArray items2 = jsonObj.getJSONArray("DEPARTMENT");
                    // looping through All Items
                    for (int i = 0; i < items.length(); i++) {
                        JSONObject c = items.getJSONObject(i);
                        JSONObject c1 = items1.getJSONObject(i);
                        JSONObject c2 = items2.getJSONObject(i);
                        String name = c.getString("Name");
                        String description = c.getString("Description");
                        String price = c.getString("Price");
                        String department = c.getString("deptid");
                        String category = c.getString("cateid");
                        String imageLocation = c.getString("photoURL");
                        String ownerP = c1.getString("Owner");
                        String productID = c.getString("prodid");
                        String named = c2.getString("Name");

                        if(list.equals("A") || list.equals("a")){
                            list.setFilterText(named);
                            //name;
                        }

                        String path = "cpen410/Mini_eBay/imagesjson_miniebay";

                        //Create URL for each image
                        String imageURL = "http://" + hostAddress + "/" + path +"/"+ imageLocation;
                        //Download the actual image using the imageURL
                        Drawable actualImage= LoadImageFromWebOperations(imageURL);
//                            imag = actualImage;
                        itemUserList.add(new userItem(actualImage, description, price, category, department, name, ownerP, productID, named));
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
            listv.setAdapter(adapter8);
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
    public class UsersAdapter8 extends ArrayAdapter<userItem8> {

        /**
         *  Constructor:
         * @param context: Activity
         * @param users: ArrayList for storing Items list
         */
        public UsersAdapter8(Context context, ArrayList<userItem8> users) {
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
            userItem8 user = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_items3, parent, false);
            }
            // Lookup view for data population
            final TextView itemOwner = (TextView) convertView.findViewById(R.id.itemOwner);
            final TextView itemName = (TextView) convertView.findViewById(R.id.itemName);
            final TextView itemDesc = (TextView) convertView.findViewById(R.id.itemDescription);
            final TextView itemPrice = (TextView) convertView.findViewById(R.id.itemPrice);
            final TextView itemDept = (TextView) convertView.findViewById(R.id.itemDepartment);
            final TextView itemCate = (TextView) convertView.findViewById(R.id.itemCategory);
            final TextView itemId = (TextView) convertView.findViewById(R.id.itemDepatName);
            final TextView itemNameDept = (TextView) convertView.findViewById(R.id.itemid);
            final ImageView itemImage = (ImageView) convertView.findViewById(R.id.imageView);

            // Populate the data into the template view using the data object
            itemOwner.setText(user.owner);
            itemName.setText(user.name);
            itemDesc.setText(user.description);
            itemPrice.setText(user.price);
            itemDept.setText(user.department);
            itemCate.setText(user.category);
            itemImage.setImageDrawable(user.image);
            itemId.setText(user.productid);
            itemNameDept.setText(user.named);


            // Return the completed view to render on screen
            convertView.setTag(position);

            //Create Listener to detect a click
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = (Integer) view.getTag();

                    // Show data of the clicked item
                    Toast.makeText(getApplicationContext(),
                            "You have selected " + itemUserList8.get(position).name,
                            Toast.LENGTH_LONG).show();
                    // Do what you want here...


                    //String urlImagen = getResources().getString(itemImage[position]);
//                    itemImage.setDrawingCacheEnabled(true);
//                    Bitmap bitmap = itemImage.getDrawingCache();
                    Intent inT = new Intent(SearchResultView.this, ProductInfo.class);
                    inT.putExtra("Name", itemName.getText());
                    inT.putExtra("Description", itemDesc.getText());
                    inT.putExtra("Price", itemPrice.getText());
                    inT.putExtra("deptid", itemDept.getText());
                    inT.putExtra("cateid", itemCate.getText());
                    inT.putExtra("Owner", itemOwner.getText());
                    inT.putExtra("prodid", itemId.getText());
                    //inT.putExtra("Name", itemNameDept.getText());
//                        inT.putExtra("photoURL", itemImage.get);
                    startActivity(inT);
                }
            });
            return convertView;
        }
    }

    /**
     *  This class generates a Data structure for manipulating each Item in the application
     */
    public class userItem8 implements Serializable {
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
        public String productid;
        public String named;

        /**
         *  Special constructor:
         * @param image : Item's image
         * @param description
         * @param price
         * @param category
         * @param department
         * @param name
         * @param owner
         * @param productid
         * @param named
         */
        public userItem8(Drawable image, String description, String price, String category, String department, String name, String owner, String productid, String named) {
            this.owner = owner;
            this.name = name;
            this.description = description;
            this.price = price;
            this.department = department;
            this.category = category;
            this.image = image;
            this.productid = productid;
            this.named = named;
        }
    }
}