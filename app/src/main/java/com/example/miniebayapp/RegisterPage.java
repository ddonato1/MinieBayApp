package com.example.miniebayapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

//Se referencian las Clases necesarias para la conexión con el Servidor MySQL //CHANGE TO ENGLISH
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class RegisterPage extends AppCompatActivity {
    //private ProgressDialog loadingBar;

    Intent i;

    Button btnreg;
    Button btnlogin;

    EditText fname;
    EditText lname;
    EditText uname;
    EditText phoneNum;
    EditText address;
    EditText e_mail;
    EditText passwrd;
    EditText passwrdconf;

    String firstname;
    String lastname;
    String phonenum;
    String add;
    String email;
    String userN;
    String passwd;
    String confpasswd;

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
    //Users adapter
//    private DBAdapter databaseAdapter;
//    // Item list for storing data from the web server
//    private ArrayList<userItem> itemUserList;

    //Authentication Servlet name
    protected String servletName = "sessionServlet2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        btnreg = (Button) findViewById(R.id.registerButton);
        btnlogin = (Button) findViewById(R.id.loginBtn);

        //loadingBar = new ProgressDialog(this);

        uname = (EditText) findViewById(R.id.usernameText);
        passwrd = (EditText) findViewById(R.id.userPassText);
        passwrdconf = (EditText) findViewById(R.id.userconfpasswdText);
        lname = (EditText) findViewById(R.id.lastnameText);
        fname = (EditText) findViewById(R.id.firstnameText);
        address = (EditText) findViewById(R.id.addressText);
        e_mail = (EditText) findViewById(R.id.emailTxt);
        phoneNum = (EditText) findViewById(R.id.telText);

        Intent i = getIntent();

        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sendUserDataObject();
//                firstname = fname.getText().toString();
//                lastname = lname.getText().toString();
//                phonenum = phoneNum.getText().toString();
//                add = address.getText().toString();
//                email = e_mail.getText().toString();
//                userN = uname.getText().toString();
//                passwd = passwrd.getText().toString();
//                confpasswd = passwrdconf.getText().toString();
//
//                if (confpasswd.equals(passwd)) {
//
//                }

                // prf.put("username", userN);
                new postItems(RegisterPage.this).execute();
            }
        });

//        pref = this.getSharedPreferences("user_inf",MODE_PRIVATE);
//        i = new Intent(RegisterPage.this, RegisterPage.class);
//
//        //Verify for session variables
//        if(pref.contains("first name") && pref.contains("last name") && pref.contains("username") &&
//                pref.contains("telephone") && pref.contains("address") && pref.contains("email") &&
//                pref.contains("password") && pref.contains("sessionValue")) {
//            //The user credentials already exists
//            firstname = pref.getString("first name", null);
//            lastname = pref.getString("last name", null);
//            phonenum = pref.getString("telephone", null);
//            add = pref.getString("address", null);
//            email = pref.getString("email", null);
//            userN = pref.getString("username", null);
//            passwd = pref.getString("password", null);
//            confpasswd = pref.getString("password", null);
//
//            //Authenticate credentials
//            new GetItems(RegisterPage.this).execute();
//            Toast.makeText(RegisterPage.this, "Already have an account...", Toast.LENGTH_SHORT).show();
//        }
//        else {
//            //The user has not been authenticated
//            btnreg.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    firstname = fname.getText().toString();
//                    lastname = lname.getText().toString();
//                    phonenum = phoneNum.getText().toString();
//                    add = address.getText().toString();
//                    email = e_mail.getText().toString();
//                    userN = uname.getText().toString();
//                    passwd = passwrd.getText().toString();
//
//                    //Authenticate the user via webservices
//                    new GetItems(RegisterPage.this).execute();
//                }
//            });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegisterPage.this, MainActivity.class);
                startActivity(i);
            }
        });

        // Define the web server's IP address
        hostAddress = "192.168.0.11:8088";

//        //Instate the Item list
//        itemUserList = new ArrayList<>();
//
//        // Defines the adapter: Receives the context (Current activity) and the Arraylist
//        adapter = new UsersAdapter(this, itemUserList);

        /**
         * Create a accessor to the ListView in the activity
         */
        listv = findViewById(R.id.itemLists);

        // Create and start the thread
        //new GetItems(this).execute();
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

//            try {
                //Read GUI inputs
            String userName, passWord, firstName, lastName, Address, userTel, Email, confPass;
            userName = ((EditText) findViewById(R.id.usernameText)).getText().toString();
            passWord = ((EditText) findViewById(R.id.userpasswdText)).getText().toString();
            firstName = ((EditText) findViewById(R.id.firstnameText)).getText().toString();
            lastName = ((EditText) findViewById(R.id.lastnameText)).getText().toString();
            Address = ((EditText) findViewById(R.id.addressText)).getText().toString();
            userTel = ((EditText) findViewById(R.id.telText)).getText().toString();
            Email = ((EditText) findViewById(R.id.emailTxt)).getText().toString();
            confPass = ((EditText) findViewById(R.id.userconfpasswdText)).getText().toString();

//                firstname = fname.getText().toString();
//                lastname = lname.getText().toString();
//                phonenum = phoneNum.getText().toString();
//                add = address.getText().toString();
//                email = e_mail.getText().toString();
//                userN = uname.getText().toString();
//                passwd = passwrd.getText().toString();
//                confpasswd = passwrdconf.getText().toString();
                if (confPass.equals(passWord)) {
                    //Define a HttpHandler
                    HttpHandler hconnection = new HttpHandler();

//                    JSONObject parmsPost = new JSONObject();
//
//                    parmsPost.put("user", userN);
//                    parmsPost.put("pass", passwd);
//                    parmsPost.put("fname", firstname);
//                    parmsPost.put("lname", lastname);
//                    parmsPost.put("add", add);
//                    parmsPost.put("tel", phonenum);
//                    parmsPost.put("e_mail", email);

                    /*perform the authentication process and capture the result in serverResponse variable*/
                    serverResponse = hconnection.makeServiceCallPost2(url, userName, passWord, firstName, lastName, Address, userTel, Email);

                    //Clean response
                    serverResponse=serverResponse.trim();
                    //Toast.makeText()
                }
                else {
                    Toast.makeText(RegisterPage.this, "The password does not match!" + url, Toast.LENGTH_LONG).show();
                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
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
                //The user has been authenticated
                //Update local session variables
//                SharedPreferences.Editor editor = prf.edit();
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(RegisterPage.this);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                //editor.putString(key, name);

                editor.putString("user", userN);
                editor.putString("pass", passwd);
                editor.putString("fname", firstname);
                editor.putString("lname", lastname);
                editor.putString("add", add);
                editor.putString("tel", phonenum);
                editor.putString("e_mail", email);
                editor.putString("sessionValue", serverResponse);
                //editor.commit();
                editor.apply();
                msgToast= "User added successfully!";
//                //Define the next activity
//                Intent i = new Intent(MainActivity.this, HomePage.class);
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

//        /**
//         * Transform JSON Object to a String
//         */
//        public String getPostDataString(JSONObject params) throws Exception {
//            StringBuilder result = new StringBuilder();
//            boolean ob = true;
//            Iterator<String> itr = params.keys();
//
//            while (itr.hasNext()) {
//                String k = itr.next();
//                Object value = params.get(k);
//
//                if(ob) {
//                    ob = false;
//                }
//                else {
//                    result.append("&");
//
//                    result.append(URLEncoder.encode(k, "UTF-8"));
//                    result.append("=");
//                    result.append(URLEncoder.encode(value.toString(), "UTF-8"));
//                }
//            }
//            return result.toString();
//        }
    }
//    public void sendUserDataObject() {
//        pref = this.getSharedPreferences("user_inf",MODE_PRIVATE);
//        i = new Intent(RegisterPage.this, RegisterPage.class);
//
//        //Verify for session variables
//        if(pref.contains("first name") && pref.contains("last name") && pref.contains("username") &&
//                pref.contains("telephone") && pref.contains("address") && pref.contains("email") &&
//                pref.contains("password") && pref.contains("sessionValue")) {
//            //The user credentials already exists
//            firstname = pref.getString("first name", null);
//            lastname = pref.getString("last name", null);
//            phonenum = pref.getString("telephone", null);
//            add = pref.getString("address", null);
//            email = pref.getString("email", null);
//            userN = pref.getString("username", null);
//            passwd = pref.getString("password", null);
//            confpasswd = pref.getString("password", null);
//
////            //Authenticate credentials
////            new RegisterPage().GetItems(RegisterPage.this).execute();
//            Toast.makeText(RegisterPage.this, "Already have an account...", Toast.LENGTH_SHORT).show();
//        }
//        else {
//            //The user has not been authenticated
//            btnreg.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    firstname = fname.getText().toString();
//                    lastname = lname.getText().toString();
//                    phonenum = phoneNum.getText().toString();
//                    add = address.getText().toString();
//                    email = e_mail.getText().toString();
//                    userN = uname.getText().toString();
//                    passwd = passwrd.getText().toString();
//
//                    //Authenticate the user via webservices
//                    new RegisterPage().GetItems(RegisterPage.this).execute();
//                }
//            });

    //       }

//        if(TextUtils.isEmpty(firstname)){
//            Toast.makeText(this, "Enter your first name", Toast.LENGTH_SHORT).show();
//        }
//        else if(TextUtils.isEmpty(lastname)){
//            Toast.makeText(this, "Enter your last name", Toast.LENGTH_SHORT).show();
//        }
//        else if(TextUtils.isEmpty(add)){
//            Toast.makeText(this, "Enter your post address", Toast.LENGTH_SHORT).show();
//        }
//        else if(TextUtils.isEmpty(email)){
//            Toast.makeText(this, "Enter your email", Toast.LENGTH_SHORT).show();
//        }
//        else if(TextUtils.isEmpty(userN)){
//            Toast.makeText(this, "Enter your username", Toast.LENGTH_SHORT).show();
//        }
//        else if(TextUtils.isEmpty(passwd)){
//            Toast.makeText(this, "Enter your password", Toast.LENGTH_SHORT).show();
//        }
//        else if(TextUtils.isEmpty(confpasswd)){
//            Toast.makeText(this, "Confirm your password", Toast.LENGTH_SHORT).show();
//        }
//        else {
//            loadingBar.setTitle("Create Account");
//            loadingBar.setMessage("Please wait, we are checking the credentials.");
//            loadingBar.setCanceledOnTouchOutside(false);
//            loadingBar.show();
//
//            validateEmail(firstname, lastname, add, email, userN, passwd, confpasswd);
//            validateUserName(firstname, lastname, add, email, userN, passwd, confpasswd);
//        }

//        Intent i = new Intent(RegisterPage.this, MyebayInfo.class);
//        i.putExtra("username", userN);
//        i.putExtra("first name", firstname);
//        i.putExtra("last name", lastname);
//        i.putExtra("address", add);
//        i.putExtra("email", email);
//        startActivity(i);
//    }

//    /**
//     *  This class define a thread for networks transactions
//     */
//    private class GetItems extends AsyncTask<String, Void, String> {
//        SharedPreferences per;
//        SharedPreferences.Editor edt;
//
//        // Context: every transaction in a Android application must be attached to a context
//        private Activity activity;
//
//        //Server response
//        private String serverResponse;
//
//        private String url;
//
//        /***
//         * Special constructor: assigns the context to the thread
//         *
//         * @param activity: Context
//         */
//        //@Override
//        protected GetItems(Activity activity)
//        {
//            //Define the servlet URL
//            url = "http://" + hostAddress +"/"+ servletName;
//            this.activity = activity;
//        }
//
//        /**
//         *  on PreExecute method: runs after the constructor is called and before the thread runs
//         */
//        protected void onPreExecute() {
//            super.onPreExecute();
//            Toast.makeText(RegisterPage.this, "Authenticating..." + url, Toast.LENGTH_LONG).show();
//        }
//
//        /***
//         *  Main thread
//         * @param arg0
//         * @return
//         */
//        protected String doInBackground(String... arg0) {
//
//            //Read GUI inputs
//            String firstName, lastName, telephone, address, email, userName, passWord;
//            firstName = ((EditText) findViewById(R.id.firstnameText)).getText().toString();
//            lastName = ((EditText) findViewById(R.id.lastnameText)).getText().toString();
//            telephone = ((EditText) findViewById(R.id.telText)).getText().toString();
//            address = ((EditText) findViewById(R.id.addressText)).getText().toString();
//            email = ((EditText) findViewById(R.id.emailTxt)).getText().toString();
//            userName = ((EditText) findViewById(R.id.usernameText)).getText().toString();
//            passWord = ((EditText) findViewById(R.id.userPassText)).getText().toString();
//
//            //Define a HttpHandler
//            HttpHandler handler = new HttpHandler();
//
//            //perform the authentication process and capture the result in serverResponse variable
//            serverResponse = handler.makeServiceCallPost(url, firstName, lastName, telephone, address, email, userName, passWord);
//
//            //Clean response
//            serverResponse=serverResponse.trim();
//
//            return null;
//        }
//
//
//        /***
//         *  This method verify the authentication result
//         *  If authenticated, it creates an jsonPerson Object and open an authenticatedActivity
//         *  otherwise, it shows a error message
//         * @param result
//         */
//        protected void onPostExecute (String result){
//            String msgToast;
//
//            //Verify the authentication result
//            // not: the user could not be authenticated
//            if (!serverResponse.equals("not")) {
//                //The user has been authenticated
//                //Update local session variables
//                SharedPreferences.Editor editor = pref.edit();
//                editor.putString("first name", firstname);
//                editor.putString("last name", lastname);
//                editor.putString("telephone", phonenum);
//                editor.putString("address", add);
//                editor.putString("email", email);
//                editor.putString("username", userN);
//                editor.putString("password", passwd);
//                editor.putString("sessionValue", serverResponse);
//                editor.commit();
//
//                //Define the next activity
//                Intent i = new Intent(RegisterPage.this, RegisterPage.class);
//                //call the DetailsActivity
//                startActivity(i);
//        }
//            else {
//                ///The user could not been authenticated, destroy session variables
//                SharedPreferences.Editor editor = pref.edit();
//                editor.clear();
//                editor.commit();
//
//                //Toast message
//                msgToast= "Create your account!";
//                Toast.makeText(getApplicationContext(),
//                        msgToast,
//                        Toast.LENGTH_LONG).show();
//            }
//        }
//
//        protected void onProgressUpdate(Void result) {
//            super.onProgressUpdate(result);
//        }
//    }

//    private void validateUserName(String firstname, String lastname, String add, String email, String userN, String passwd, String confpasswd) {
//    }
//
//    private void validateEmail(String firstname, String lastname, String add, String email, String userN, String passwd, String confpasswd) {
//
//    }

//    /**
//     * This method is to validate the email address of the user
//     * @return
//     */
//    public boolean validatingEmail(){
//        String Email = e_mail.getText().toString().trim();
//
//        if(Email.isEmpty()){
//            e_mail.setError("Can't be empty!");
//            return false;
//        }
//        else if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
//            e_mail.setError("Enter a valid email address");
//            return false;
//        }
//        else {
//            e_mail.setError(null);
//            return true;
//        }
//    }
//
//    /**
//     * This method is to validate the password of the user
//     * @return
//     */
//    public boolean validatingPassword(){
//        String Passwd = passwrd.getText().toString().trim();
//
//        if(Passwd.isEmpty()){
//            passwrd.setError("Can't be empty!");
//            return false;
//        }
//        else if(!passwd_pattern.matcher(Passwd).matches()) {
//            passwrd.setError("Weak password");
//            return false;
//        }
//        else {
//            passwrd.setError(null);
//            return true;
//        }
//    }

//    /**
//     * This class defines a ArrayAdapter for the ListView manipulation
//     */
//    public class DBAdapter extends ArrayAdapter<dbUser> {
//
//        /**
//         *  Constructor:
//         * @param context: Activity
//         * @param user: ArrayList for storing Items list
//         */
//        public DBAdapter(Context context, ArrayAdapter<dbUser> user) {
//            super(context, 0, (List<dbUser>) user);
//        }
//
//        @Override
//        public void getUser(String userN, String )
//    }
//
//    public class dbUser {
//
//    }
}
