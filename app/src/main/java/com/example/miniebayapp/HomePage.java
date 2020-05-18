/**
 * Angel J. Vargas Lopez - S01274152
 * Deyaneira Donato Carrasquillo - S01183053
 * **
 * Home Page Activity, this activity is the home page of the application shows all the products that
 * the database have. Have a search bar, where the user can search for any department. As well, below
 * of the page we have several buttons but the important one are the MyeBay and Selling. MyeBay is
 * where the user can find its information and the logout button. Selling is where the user can add
 * any product on his/her account.
 **/
package com.example.miniebayapp;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.app.Activity;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomePage extends AppCompatActivity {
    //Declare Variables
    TextView usern;
    Button homeBtn;
    Button myEBAYbtn;
    Button notBtn;
    Button saleBtn;
    Button cartBtn;
    String[] CATEGORIES = {"AUD2122", "BAE3233", "BEDR2526", "BID4647", "BOY3031", "CHEM3839", "COM2021", "DINR2627", "DOCT4546",
            "FIS4748", "FLO3738", "FRU3536", "GEN4041", "GENQ2223", "GENT3334", "GIR3132", "HAI4445", "KIT2425",
            "LIVR2324", "MAKU4243", "MEN2829", "MOV1920", "MUS1718", "SHO2930", "SKC4344", "SPO3435", "TIR3940",
            "VEG3637", "VGA1819", "VITN4142", "WOM2728"};
    String[] DEPARTMENTS = {"AUT213", "BEA145", "CLO789", "ELEC123", "GAR112", "HOME456", "PET516", "PHA134", "TOY101"};

    //This is for debugging
    private String TAG = HomePage.class.getSimpleName();
    //This is for managing the listview in the activity
    private ListView listv;
    //Web server's IP address
    private String hostAddress;
    //Users adapter
    private UsersAdapter adapter;
    // Item list for storing data from the web server
    private ArrayList<userItem> itemUserList;

    SharedPreferences prf;
    SearchView searchItem;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        homeBtn = (Button) findViewById(R.id.homeButton);
        myEBAYbtn = (Button) findViewById(R.id.userinfButton);
        notBtn = (Button) findViewById(R.id.notifButton);
        saleBtn = (Button) findViewById(R.id.saleButton);
        cartBtn = (Button) findViewById(R.id.shopitemButton);

        SearchManager searchM = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchableInfo searchableInfo = searchM.getSearchableInfo(getComponentName());

        searchItem = (SearchView) findViewById(R.id.searchView);
        searchItem.setSearchableInfo(searchableInfo);
        searchItem.setIconifiedByDefault(false);
        searchItem.setSubmitButtonEnabled(true);

        //Searching for any departments
        searchItem.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Intent in = new Intent(HomePage.this, SearchResultView.class);
                in.putExtra("", s);
                startActivity(in);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String stxt) {
                return false;
            }
        });

        usern = findViewById(R.id.usernameView);
        //Access the local session variables
        prf = getSharedPreferences("user_details",MODE_PRIVATE);
        usern.setText("" +prf.getString("username", null));
        prf.getString("sessionValue", null);

        myEBAYbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myEBAYBtnObject();
            }
        });

        // Define the web server's IP address
        hostAddress="192.168.0.11:8088";
        //Instate the Item list
        itemUserList = new ArrayList<>();
        // Defines the adapter: Receives the context (Current activity) and the Arraylist
        adapter = new UsersAdapter(this, itemUserList);
        // Create a accessor to the ListView in the activity
        listv = findViewById(R.id.itemLists);
        // Create and start the thread
        new GetItems(this).execute();

        //Buttons located in the activity
        homeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                homeBtnObject();
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
            Toast.makeText(HomePage.this, "Items list is downloading", Toast.LENGTH_LONG).show();
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
                            String name = c.getString("Name");
                            String description = c.getString("Description");
                            String price = c.getString("Price");
                            String department = c.getString("deptid");
                            String category = c.getString("cateid");
                            String imageLocation = c.getString("photoURL");
                            String ownerP = c1.getString("Owner");
                            String productID = c.getString("prodid");

                            String path = "cpen410/Mini_eBay/imagesjson_miniebay";

                            //Create URL for each image
                            String imageURL = "http://" + hostAddress + "/" + path +"/"+ imageLocation;
                            //Download the actual image using the imageURL
                            Drawable actualImage= LoadImageFromWebOperations(imageURL);
//                            imag = actualImage;
                            itemUserList.add(new userItem(actualImage, description, price, category, department, name, ownerP, productID));
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
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_items, parent, false);
            }
            // Lookup view for data population
            final TextView itemOwner = (TextView) convertView.findViewById(R.id.itemOwner);
            final TextView itemName = (TextView) convertView.findViewById(R.id.itemName);
            final TextView itemDesc = (TextView) convertView.findViewById(R.id.itemDescription);
            final TextView itemPrice = (TextView) convertView.findViewById(R.id.itemPrice);
            final TextView itemDept = (TextView) convertView.findViewById(R.id.itemDepartment);
            final TextView itemCate = (TextView) convertView.findViewById(R.id.itemCategory);
            final TextView itemId = (TextView) convertView.findViewById(R.id.itemid);
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
                    Intent inT = new Intent(HomePage.this, ProductInfo.class);
                        inT.putExtra("Name", itemName.getText());
                        inT.putExtra("Description", itemDesc.getText());
                        inT.putExtra("Price", itemPrice.getText());
                        inT.putExtra("deptid", itemDept.getText());
                        inT.putExtra("cateid", itemCate.getText());
                        inT.putExtra("Owner", itemOwner.getText());
                        inT.putExtra("prodid", itemId.getText());
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

        /**
         *  Special constructor:
         * @param owner
         * @param name
         * @param description
         * @param price
         * @param department
         * @param category
         * @param image : Item's image
         * @param productid
         */
        public userItem(Drawable image, String description, String price, String category, String department, String name, String owner, String productid) {
            this.owner = owner;
            this.name = name;
            this.description = description;
            this.price = price;
            this.department = department;
            this.category = category;
            this.image = image;
            this.productid = productid;
        }
    }

    /**
     *Methods for the buttons
     */
    public void homeBtnObject() {
        Intent i = new Intent(HomePage.this, HomePage.class);
        startActivity(i);
    }

    public void myEBAYBtnObject() {
        Intent i = new Intent(HomePage.this, MyebayInfo.class);
        startActivity(i);
    }

    public void notiBtnObject() {
        Intent i = new Intent(HomePage.this, NotificationPage.class);
        startActivity(i);
    }

    public void saleBtnObject() {
        Intent i = new Intent(HomePage.this, SellingPage.class);
        startActivity(i);
    }

    public void shoppingcartBtnObject() {
        Intent i = new Intent(HomePage.this, ShoppingCartPage.class);
        startActivity(i);
    }
}
