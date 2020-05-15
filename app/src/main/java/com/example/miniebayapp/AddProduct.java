package com.example.miniebayapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

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

public class AddProduct extends AppCompatActivity {
    Button addPhoto;
    Button addProduct;

    EditText nameP;
    EditText desc;
    EditText price;
    EditText dept;
    EditText cate;
//    CheckBox check0, check1, check2, check3, check4, check5, check6, check7, check8, check9,
//            check10, check11, check12, check13, check14, check15, check16, check17, check18,
//            check19, check20, check21, check22, check23, check24, check25, check26, check27,
//            check28, check29, check30;

    String nameprod, descprod, priceprod, deptprod, categprod;
//    String check_0, check_1, check_2, check_3, check_4, check_5, check_6, check_7, check_8,
//            check_9, check_10, check_11, check_12, check_13, check_14, check_15, check_16,
//            check_17, check_18, check_19, check_20, check_21, check_22, check_23, check_24,
//            check_25, check_26, check_27, check_28, check_29, check_30;

    String image;
    String addphoto;
    String Photo;

    ImageView photo;

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
        dept = (EditText) findViewById(R.id.deptText);
//        check0 = (CheckBox) findViewById(R.id.checkAudio);
//        check1 = (CheckBox) findViewById(R.id.checkBaby);
//        check2 = (CheckBox) findViewById(R.id.checkBedR);
//        check3 = (CheckBox) findViewById(R.id.checkBirds);
//        check4 = (CheckBox) findViewById(R.id.checkBoys);
//        check5 = (CheckBox) findViewById(R.id.checkChems);
//        check6 = (CheckBox) findViewById(R.id.checkComp);
//        check7 = (CheckBox) findViewById(R.id.checkDinR);
//        check8 = (CheckBox) findViewById(R.id.checkDogCat);
//        check9 = (CheckBox) findViewById(R.id.checkFish);
//        check10 = (CheckBox) findViewById(R.id.checkFlowers);
//        check11 = (CheckBox) findViewById(R.id.checkFruits);
//        check12 = (CheckBox) findViewById(R.id.checkGen);
//        check13 = (CheckBox) findViewById(R.id.checkGenEq);
//        check14 = (CheckBox) findViewById(R.id.checkGenToy);
//        check15 = (CheckBox) findViewById(R.id.checkGirls);
//        check16 = (CheckBox) findViewById(R.id.checkHair);
//        check17 = (CheckBox) findViewById(R.id.checkKit);
//        check18 = (CheckBox) findViewById(R.id.checkLivR);
//        check19 = (CheckBox) findViewById(R.id.checkMakeUp);
//        check20 = (CheckBox) findViewById(R.id.checkMen);
//        check21 = (CheckBox) findViewById(R.id.checkMovies);
//        check22 = (CheckBox) findViewById(R.id.checkMusic);
//        check23 = (CheckBox) findViewById(R.id.checkNatVit);
//        check24 = (CheckBox) findViewById(R.id.checkShoes);
//        check25 = (CheckBox) findViewById(R.id.checkSkinC);
//        check26 = (CheckBox) findViewById(R.id.checkSports);
//        check27 = (CheckBox) findViewById(R.id.checkTires);
//        check28 = (CheckBox) findViewById(R.id.checkVeggie);
//        check29 = (CheckBox) findViewById(R.id.checkVideoG);
//        check30 = (CheckBox) findViewById(R.id.checkWomen);

        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FileOutputStream out;
                FileInputStream in;
                image = "/imagesjson_miniebay";
                File filename = new File(getApplicationContext().getFilesDir(), image);
                try {
                    out = new FileOutputStream (new File(filename.getAbsolutePath().toString()), true); // true will be same as Context.MODE_APPEND
                    //out = openFileOutput(String.valueOf(filename), Context.MODE_PRIVATE);
                    //in = get
                    out.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

//        addProduct.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new postItems(AddProduct.this).execute();
//
//                Intent i = new Intent(AddProduct.this, SellingPage.class);
//                startActivity(i);
//            }
//        });

        // Define the web server's IP address
        hostAddress = "192.168.0.11:8088";
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
        protected postItems(Activity activity) {
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
                String owner = "";
                nameprod = ((EditText) findViewById(R.id.productNameText)).getText().toString();
                descprod = ((EditText) findViewById(R.id.productDescText)).getText().toString();
                priceprod = ((EditText) findViewById(R.id.priceText)).getText().toString();
                deptprod = ((EditText) findViewById(R.id.deptText)).getText().toString();
                //Photo = ((ImageView) findViewById(R.id.photoView)).getText().toString();

                //ADD THE DEPARTMENTS AND CATEGORIES!!!
//                check_0 = ((CheckBox) findViewById(R.id.checkAudio)).getText().toString();
//                check_1 = ((CheckBox) findViewById(R.id.checkBaby)).getText().toString();
//                check_2 = ((CheckBox) findViewById(R.id.checkBedR)).getText().toString();
//                check_3 = ((CheckBox) findViewById(R.id.checkBirds)).getText().toString();
//                check_4 = ((CheckBox) findViewById(R.id.checkBoys)).getText().toString();
//                check_5 = ((CheckBox) findViewById(R.id.checkChems)).getText().toString();
//                check_6 = ((CheckBox) findViewById(R.id.checkComp)).getText().toString();
//                check_7 = ((CheckBox) findViewById(R.id.checkDinR)).getText().toString();
//                check_8 = ((CheckBox) findViewById(R.id.checkDogCat)).getText().toString();
//                check_9 = ((CheckBox) findViewById(R.id.checkFish)).getText().toString();
//                check_10 = ((CheckBox) findViewById(R.id.checkFlowers)).getText().toString();
//                check_11 = ((CheckBox) findViewById(R.id.checkFruits)).getText().toString();
//                check_12 = ((CheckBox) findViewById(R.id.checkGen)).getText().toString();
//                check_13 = ((CheckBox) findViewById(R.id.checkGenEq)).getText().toString();
//                check_14 = ((CheckBox) findViewById(R.id.checkGenToy)).getText().toString();
//                check_15 = ((CheckBox) findViewById(R.id.checkGirls)).getText().toString();
//                check_16 = ((CheckBox) findViewById(R.id.checkHair)).getText().toString();
//                check_17 = ((CheckBox) findViewById(R.id.checkKit)).getText().toString();
//                check_18 = ((CheckBox) findViewById(R.id.checkLivR)).getText().toString();
//                check_19 = ((CheckBox) findViewById(R.id.checkMakeUp)).getText().toString();
//                check_20 = ((CheckBox) findViewById(R.id.checkMen)).getText().toString();
//                check_21 = ((CheckBox) findViewById(R.id.checkMovies)).getText().toString();
//                check_22 = ((CheckBox) findViewById(R.id.checkMusic)).getText().toString();
//                check_23 = ((CheckBox) findViewById(R.id.checkNatVit)).getText().toString();
//                check_24 = ((CheckBox) findViewById(R.id.checkShoes)).getText().toString();
//                check_25 = ((CheckBox) findViewById(R.id.checkSkinC)).getText().toString();
//                check_26 = ((CheckBox) findViewById(R.id.checkSports)).getText().toString();
//                check_27 = ((CheckBox) findViewById(R.id.checkTires)).getText().toString();
//                check_28 = ((CheckBox) findViewById(R.id.checkVeggie)).getText().toString();
//                check_29 = ((CheckBox) findViewById(R.id.checkVideoG)).getText().toString();
//                check_30 = ((CheckBox) findViewById(R.id.checkWomen)).getText().toString();


                /*Define a HttpHandler*/
                HttpHandler hconnection = new HttpHandler();

                final JSONObject parmsPost = new JSONObject();

                parmsPost.put("namep", nameprod);
                parmsPost.put("decp", descprod);
                parmsPost.put("pricep", priceprod);
                parmsPost.put("dept", deptprod);
                parmsPost.put("categ", categprod);

                prf = getSharedPreferences("user_details",MODE_PRIVATE);
                owner.compareTo("" +prf.getString("username", null));
                parmsPost.put("owner", owner);

//                if(!(check_0.equals(null) || check_6.equals(null) || check_13.equals(null) ||
//                        check_21.equals(null) || check_22.equals(null) || check_29.equals(null))){
//                    deptid = "ELEN123";
//                    parmsPost.put("deptid", deptid);
//                    if (!check_0.equals(null)) {
//                        categ = "AUD2122";
//                        parmsPost.put("cateid", categ);
//                    } else if (!check_6.equals(null)) {
//                        categ = "COM2021";
//                        parmsPost.put("cateid", categ);
//                    } else if (!check_13.equals(null)) {
//                        categ = "GENQ2223";
//                        parmsPost.put("cateid", categ);
//                    } else if (!check_21.equals(null)) {
//                        categ = "MOV1920";
//                        parmsPost.put("cateid", categ);
//                    } else if (!check_22.equals(null)) {
//                        categ = "MUS1718";
//                        parmsPost.put("cateid", categ);
//                    } else if (!check_29.equals(null)) {
//                        categ = "VGA1819";
//                        parmsPost.put("cateid", categ);
//                    }
//                }
//                else if (!(check_1.equals(null) || check_4.equals(null) || check_15.equals(null) ||
//                        check_20.equals(null) || check_24.equals(null) || check_30.equals(null))) {
//                    deptid = "CLO789";
//                    parmsPost.put("deptid", deptid);
//                    if (!check_1.equals(null)) {
//                        categ = "BAE3233";
//                        parmsPost.put("cateid", categ);
//                    } else if (!check_4.equals(null)) {
//                        categ = "BOY3031";
//                        parmsPost.put("cateid", categ);
//                    } else if (!check_15.equals(null)) {
//                        categ = "GIR3132";
//                        parmsPost.put("cateid", categ);
//                    } else if (!check_20.equals(null)) {
//                        categ = "MEN2829";
//                        parmsPost.put("cateid", categ);
//                    } else if (!check_24.equals(null)) {
//                        categ = "SHO2930";
//                        parmsPost.put("cateid", categ);
//                    } else if (!check_30.equals(null)) {
//                        categ = "WOM2728";
//                        parmsPost.put("cateid", categ);
//                    }
//                }
//                else if (!(check_2.equals(null) || check_7.equals(null) || check_17.equals(null) ||
//                        check_18.equals(null))) {
//                    deptid = "HOME456";
//                    parmsPost.put("deptid", deptid);
//                    if (!check_2.equals(null)) {
//                        categ = "BEDR2526";
//                        parmsPost.put("cateid", categ);
//                    } else if (!check_7.equals(null)) {
//                        categ = "DINR2627";
//                        parmsPost.put("cateid", categ);
//                    } else if (!check_17.equals(null)) {
//                        categ = "KIT2425";
//                        parmsPost.put("cateid", categ);
//                    } else if (!check_18.equals(null)) {
//                        categ = "LIVR2324";
//                        parmsPost.put("cateid", categ);
//                    }
//                }
//                else if (!(check_3.equals(null) || check_8.equals(null) || check_9.equals(null))) {
//                    deptid = "PETS516";
//                    parmsPost.put("deptid", deptid);
//                    if (!check_3.equals(null)) {
//                        categ = "BID4647";
//                        parmsPost.put("cateid", categ);
//                    } else if (!check_8.equals(null)) {
//                        categ = "DOCT4546";
//                        parmsPost.put("cateid", categ);
//                    } else if (!check_9.equals(null)) {
//                        categ = "FIS4748";
//                        parmsPost.put("cateid", categ);
//                    }
//                }
//                else if (!(check_5.equals(null) || check_12.equals(null) || check_27.equals(null))) {
//                    deptid = "AUT213";
//                    parmsPost.put("deptid", deptid);
//                    if (!check_5.equals(null)) {
//                        categ = "CHEM3839";
//                        parmsPost.put("cateid", categ);
//                    } else if (!check_12.equals(null)) {
//                        categ = "GEN4041";
//                        parmsPost.put("cateid", categ);
//                    } else if (!check_27.equals(null)) {
//                        categ = "TIR3940";
//                        parmsPost.put("cateid", categ);
//                    }
//                }
//                else if (!(check_10.equals(null) || check_11.equals(null) || check_28.equals(null))) {
//                    deptid = "GAR112";
//                    parmsPost.put("deptid", deptid);
//                    if (!check_10.equals(null)) {
//                        categ = "FLO3738";
//                        parmsPost.put("cateid", categ);
//                    } else if (!check_11.equals(null)) {
//                        categ = "FRU3536";
//                        parmsPost.put("cateid", categ);
//                    } else if (!check_28.equals(null)) {
//                        categ = "VEG3637";
//                        parmsPost.put("cateid", categ);
//                    }
//                }
//                else if (!(check_14.equals(null) || check_26.equals(null))) {
//                    deptid = "TOY101";
//                    parmsPost.put("deptid", deptid);
//                    if (!check_14.equals(null)) {
//                        categ = "GENT3334";
//                        parmsPost.put("cateid", categ);
//                    } else if (!check_26.equals(null)) {
//                        categ = "SPO3435";
//                        parmsPost.put("cateid", categ);
//                    }
//                }
//                else if (!(check_16.equals(null) || check_19.equals(null) || check_25.equals(null))) {
//                    deptid = "BEA145";
//                    parmsPost.put("deptid", deptid);
//                    if (!check_16.equals(null)) {
//                        categ = "HAI4445";
//                        parmsPost.put("cateid", categ);
//                    } else if (!check_19.equals(null)) {
//                        categ = "MAKU4243";
//                        parmsPost.put("cateid", categ);
//                    } else if (!check_25.equals(null)) {
//                        categ = "SKC4344";
//                        parmsPost.put("cateid", categ);
//                    }
//                }
//                else if (!(check_23.equals(null))) {
//                    deptid = "PHA134";
//                    parmsPost.put("deptid", deptid);
//                    if (!check_23.equals(null)) {
//                        categ = "VITN4142";
//                        parmsPost.put("cateid", categ);
//                    }
//                }
                /*//parmsPost.put("cateid", categ);
                //String imageLocation = c.getString("photoURL");
                //Create URL for each image
                //String imageURL = "http://" + hostAddress + "/" + imageLocation;
                Download the actual image using the imageURL
                //Drawable actualImage= LoadImageFromWebOperations(imageURL);*/
                /**
                 * Add the photo to Database
                 */
                //Bitmap bitmap;

                addPhoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FileOutputStream out;
                        FileInputStream in;
                        image = "imagesjson_miniebay";
                        File filename = new File(getApplicationContext().getFilesDir(), image);
                        try {
                            out = openFileOutput(String.valueOf(filename), Context.MODE_PRIVATE);
                            //in = get
                            out.close();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                addphoto = photo.toString();
                parmsPost.put("image", addphoto);
                //EDIT!!!
                /**
                 * Sending the data to database
                 */
                /*perform the authentication process and capture the result in serverResponse variable*/
                serverResponse = hconnection.makeServiceCallPost3(url, nameprod, descprod, priceprod, deptprod, categprod, owner);
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
                editor.putString("categ", categprod);
                //editor.putString("lname", image);
                //String categid = "";
                //editor.putString("categ", categid);
                //String deptid = "";
                //editor.putString("deptid", deptid);
                editor.putString("sessionValue", serverResponse);
                //editor.commit();
                editor.apply();
                msgToast= "User added successfully!";
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
