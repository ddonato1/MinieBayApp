/**
 * Angel J. Vargas Lopez - S01274152
 * Deyaneira Donato Carrasquillo - S01183053
 * **
 * Register Activity, this activity is in charge to register any user on the database. Creating an
 * authenticated account username and password for him/her.
 **/
package com.example.miniebayapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;


public class RegisterPage extends AppCompatActivity {
    //private ProgressDialog loadingBar;

    Intent i;

    Button btnreg;

    EditText fname;
    EditText lname;
    EditText uname;
    EditText phoneNum;
    EditText address;
    EditText e_mail;
    EditText passwrd;

    String firstname;
    String lastname;
    String phonenum;
    String add;
    String email;
    String userN;
    String passwd;

    //This is for debugging
    private String TAG = HttpHandler.class.getSimpleName();
    //This is for managing the listview in the activity
    private ListView listv;
    //Web server's IP address
    private String hostAddress;
    //Shared object though the application
    SharedPreferences prf;
    //Authentication Servlet name
    protected String servletN = "register";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        btnreg = (Button) findViewById(R.id.registerButton);

        uname = (EditText) findViewById(R.id.usernameText);
        passwrd = (EditText) findViewById(R.id.userPassText);
        lname = (EditText) findViewById(R.id.lastnameText);
        fname = (EditText) findViewById(R.id.firstnameText);
        phoneNum = (EditText) findViewById(R.id.telText);
        e_mail = (EditText) findViewById(R.id.emailTxt);
        address = (EditText) findViewById(R.id.addressText);

        Intent i = getIntent();

        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new postItems(RegisterPage.this).execute();

                Intent i = new Intent(RegisterPage.this, MainActivity.class);
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
            //String userName, passWord, firstName, lastName, userTel, Email, Address;
                userN = ((EditText) findViewById(R.id.usernameText)).getText().toString();
                passwd = ((EditText) findViewById(R.id.userpasswdText)).getText().toString();
                firstname = ((EditText) findViewById(R.id.firstnameText)).getText().toString();
                lastname = ((EditText) findViewById(R.id.lastnameText)).getText().toString();
                phonenum = ((EditText) findViewById(R.id.telText)).getText().toString();
                email = ((EditText) findViewById(R.id.emailTxt)).getText().toString();
                add = ((EditText) findViewById(R.id.addressText)).getText().toString();

                    //Define a HttpHandler
                    HttpHandler hconnection = new HttpHandler();

                    JSONObject parmsPost = new JSONObject();

                    parmsPost.put("user", userN);
                    parmsPost.put("pass", passwd);
                    parmsPost.put("fname", firstname);
                    parmsPost.put("lname", lastname);
                    parmsPost.put("tel", phonenum);
                    parmsPost.put("e_mail", email);
                    parmsPost.put("add", add);

                    /*perform the authentication process and capture the result in serverResponse variable*/
                    serverResponse = hconnection.makeServiceCallPost2(url, userN, passwd, firstname, lastname, phonenum, email, add);
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
                //The user has been authenticated
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(RegisterPage.this);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("user", userN);
                editor.putString("pass", passwd);
                editor.putString("fname", firstname);
                editor.putString("lname", lastname);
                editor.putString("tel", phonenum);
                editor.putString("e_mail", email);
                editor.putString("add", add);
                editor.putString("sessionValue", serverResponse);
                //editor.commit();
                editor.apply();
                msgToast= "User added successfully!";
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
