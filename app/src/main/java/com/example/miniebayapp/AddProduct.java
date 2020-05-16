package com.example.miniebayapp;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.acl.Owner;
import java.util.HashMap;

public class AddProduct extends AppCompatActivity {
    private final String CARPET = "imagejson_miniebay/";
    private final String IMAGE_RUT = CARPET + "myPhotos";
    private static final int COD_SELECTION = 10;
    private static final int COD_PHOTO = 20;
    Button addPhoto;
    Button addProduct;

    EditText nameP;
    EditText desc;
    EditText price;
    MultiAutoCompleteTextView dept;
    EditText owner;
    MultiAutoCompleteTextView categ;

    String[] CATEGORIES = {"AUD2122", "BAE3233", "BEDR2526", "BID4647", "BOY3031", "CHEM3839", "COM2021", "DINR2627", "DOCT4546",
                    "FIS4748", "FLO3738", "FRU3536", "GEN4041", "GENQ2223", "GENT3334", "GIR3132", "HAI4445", "KIT2425",
                    "LIVR2324", "MAKU4243", "MEN2829", "MOV1920", "MUS1718", "SHO2930", "SKC4344", "SPO3435", "TIR3940",
                    "VEG3637", "VGA1819", "VITN4142", "WOM2728"};
    String[] DEPARTMENTS = {"AUT213", "BEA145", "CLO789", "ELEC123", "GAR112", "HOME456", "PET516", "PHA134", "TOY101"};

    String image;
    String addphoto;
    String Photo;
    String Owner;
    String nameprod, descprod, priceprod, deptprod, categprod;

    Bitmap bm;

    ImageView photo;
    ProgressDialog prog;
    //This is for debugging
    private String TAG = HttpHandler.class.getSimpleName();
    //This is for managing the listview in the activity
    private ListView listv;
    //Web server's IP address
    private String hostAddress;
    //Shared object though the application
    SharedPreferences prf;
    //Authentication Servlet name
    protected String servletN = "addproduct";

    private static final int PERMISION_REQUEST = 1;
    private static final int IMAGE_REQUEST = 2;

    TextView USERN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        photo = (ImageView) findViewById(R.id.photoView);

        addPhoto = (Button) findViewById(R.id.addPhotoBtn);
        addProduct = (Button) findViewById(R.id.addProductBtn);

        nameP = (EditText) findViewById(R.id.productNameText);
        desc = (EditText) findViewById(R.id.productDescText);
        price = (EditText) findViewById(R.id.priceText);
        dept = (MultiAutoCompleteTextView) findViewById(R.id.deptText);
        categ = (MultiAutoCompleteTextView) findViewById(R.id.categText);

        ArrayAdapter<String> adpt = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, DEPARTMENTS);
        dept.setAdapter(adpt);
        dept.setThreshold(1);
        dept.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        ArrayAdapter<String> adp = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, CATEGORIES);
        categ.setAdapter(adp);
        categ.setThreshold(1);
        categ.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        owner = (EditText) findViewById(R.id.productOwner);

        RequestQueue request = Volley.newRequestQueue(getContext());

        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPhotoOptions();
            }
        });

        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new postItems(AddProduct.this).execute();

                Intent i = new Intent(AddProduct.this, SellingPage.class);
                startActivity(i);
            }
        });

        // Define the web server's IP address
        hostAddress = "192.168.0.11:8088";
    }

    private Context getContext() {
        return getContext();
    }

    private void addPhotoOptions(){
        final CharSequence[] options = {"Take photo", "Choose from Gallery", "Cancel"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Choose an option");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(options[i].equals("Take photo")){
                    //method of camera
                    Toast.makeText(getContext(), "Load camera", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(options[i].equals("Choose from Gallery")){
                        //method gallery
                        Intent inT = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        inT.setType("image/");
                        startActivityForResult(inT.createChooser(inT, "Choose"), COD_SELECTION);
                    }
                    else{
                        dialogInterface.dismiss();
                    }
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode){
            case COD_SELECTION:
                Uri mypath = data.getData();
                photo.setImageURI(mypath);
                break;
        }
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
                //String categ = "", deptid = "";
                //String owner = "";

                nameprod = ((EditText) findViewById(R.id.productNameText)).getText().toString();
                descprod = ((EditText) findViewById(R.id.productDescText)).getText().toString();
                priceprod = ((EditText) findViewById(R.id.priceText)).getText().toString();
                deptprod = ((MultiAutoCompleteTextView) findViewById(R.id.deptText)).getText().toString();
                categprod = ((MultiAutoCompleteTextView) findViewById(R.id.categText)).getText().toString();
                Owner = ((EditText) findViewById(R.id.productOwner)).getText().toString();
                //Photo = ((ImageView) findViewById(R.id.photoView)).getText().toString();


                /*Define a HttpHandler*/
                HttpHandler hconnection = new HttpHandler();

                final JSONObject parmsPost = new JSONObject();

                parmsPost.put("namep", nameprod);
                parmsPost.put("decp", descprod);
                parmsPost.put("pricep", priceprod);
                parmsPost.put("dept", deptprod);
                parmsPost.put("cateid", categprod);
                parmsPost.put("owner", Owner);

                /*//parmsPost.put("cateid", categ);
                //String imageLocation = c.getString("photoURL");
                //Create URL for each image
                //String imageURL = "http://" + hostAddress + "/" + imageLocation;
                Download the actual image using the imageURL
                //Drawable actualImage= LoadImageFromWebOperations(imageURL);*/
                /**
                 * Add the photo to Database
                 */
                addPhoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


//                        FileOutputStream out;
//                        FileInputStream in;
//                        image = "imagesjson_miniebay";
//                        File filename = new File(getApplicationContext().getFilesDir(), image);
//                        try {
//                            out = openFileOutput(String.valueOf(filename), Context.MODE_PRIVATE);
//                            //in = get
//                            out.close();
//                        } catch (FileNotFoundException e) {
//                            e.printStackTrace();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
                    }
                });
//                addphoto = photo.toString();
                parmsPost.put("photoURL", addphoto);

                //EDIT!!!
                /**
                 * Sending the data to database
                 */
                /*perform the authentication process and capture the result in serverResponse variable*/
                serverResponse = hconnection.makeServiceCallPost3(url, nameprod, descprod, priceprod, addphoto, deptprod, categprod, Owner);
                //Clean response
                serverResponse=serverResponse.trim();

            } catch (JSONException e) {
              e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//            dialogProg.dismiss();
//            String rlt = result;
//            Toast.makeText(activity,rlt,Toast.LENGTH_LONG);

            String msgToast;

            //Verify the authentication result
            // not: the user could not be authenticated
            if (!serverResponse.equals("not")) {
                //The product has been authenticated
                //Update local session variables
//                SharedPreferences.Editor editor = prf.edit();
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(AddProduct.this);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                //editor.putString(key, name);

                //EDIT!!!
                editor.putString("namep", nameprod);
                editor.putString("decp", descprod);
                editor.putString("pricep", priceprod);
                editor.putString("dept", deptprod);
                editor.putString("cateid", categprod);
                editor.putString("photoURL", addphoto);
                editor.putString("owner", Owner);
                editor.putString("sessionValue", serverResponse);
                //editor.commit();
                editor.apply();
                msgToast= "Product added successfully!";
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


}
