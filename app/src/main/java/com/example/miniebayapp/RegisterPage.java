package com.example.miniebayapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;
import java.util.regex.Pattern;

//Se referencian las Clases necesarias para la conexión con el Servidor MySQL //CHANGE TO ENGLISH
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class RegisterPage extends AppCompatActivity {
//    private static final Pattern passwd_pattern =
//            Pattern.compile("^" +
//                    "(?=.*[0-9])" +         //At least one digit
//                    "(?=.*[a-z])" +         //At least one lower case letter
//                    "(?=.*[A-Z])" +         //At least one upper case letter
//                    "(?=.*[@#$%^&+=])" +    //At least one special character
//                    "(?=\\S+$)" +           //No white spaces
//                    ".{8,}" +             //At least eight characters
//                    "$");

    private ProgressDialog loadingBar;

    Button btnconfirm;
    Button btnlogin;

    EditText fname;
    EditText lname;
    EditText uname;
    EditText address;
    EditText e_mail;
    EditText passwrd;
    EditText passwrdconf;

      //This is for debugging
//    private String TAG = HomePage.class.getSimpleName();
//    //This is for managing the listview in the activity
//    private ListView listv;
//    //Web server's IP address
//    private String hostAddress;
//    //Users adapter
//    private DBAdapter databaseAdapter;
//    // Item list for storing data from the web server
//    private ArrayList<userItem> itemUserList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        btnconfirm = (Button) findViewById(R.id.registerButton);
        btnlogin = (Button) findViewById(R.id.loginBtn);

        loadingBar = new ProgressDialog(this);

        uname = (EditText) findViewById(R.id.usernameText);
        passwrd = (EditText) findViewById(R.id.userPassText);
        passwrdconf = (EditText) findViewById(R.id.userconfpasswdText);
        lname = (EditText) findViewById(R.id.lastnameText);
        fname = (EditText) findViewById(R.id.firstnameText);
        address = (EditText) findViewById(R.id.addressText);

        Intent i = getIntent();

        btnconfirm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
//                validatingEmail();
//                validatingPassword();
                sendUserDataObject();
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegisterPage.this, MainActivity.class);
                startActivity(i);
            }
        });

//        // Define the web server's IP address
//        hostAddress="192.168.0.11:8088";
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
    }

    public void sendUserDataObject() {
        String firstname = fname.getText().toString();
        String lastname = lname.getText().toString();
        String add = address.getText().toString();
        String email = e_mail.getText().toString();
        String userN = uname.getText().toString();
        String passwd = passwrd.getText().toString();
        String confpasswd = passwrdconf.getText().toString();

        if(TextUtils.isEmpty(firstname)){
            Toast.makeText(this, "Enter your first name", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(lastname)){
            Toast.makeText(this, "Enter your last name", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(add)){
            Toast.makeText(this, "Enter your post address", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Enter your email", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(userN)){
            Toast.makeText(this, "Enter your username", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(passwd)){
            Toast.makeText(this, "Enter your password", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(confpasswd)){
            Toast.makeText(this, "Confirm your password", Toast.LENGTH_SHORT).show();
        }
        else {
            loadingBar.setTitle("Create Account");
            loadingBar.setMessage("Please wait, we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            validateEmail(firstname, lastname, add, email, userN, passwd, confpasswd);
            validateUserName(firstname, lastname, add, email, userN, passwd, confpasswd);
        }

        Intent i = new Intent(RegisterPage.this, MyebayInfo.class);
        i.putExtra("username", userN);
        i.putExtra("first name", firstname);
        i.putExtra("last name", lastname);
        i.putExtra("address", add);
        i.putExtra("email", email);
        startActivity(i);
    }

    private void validateUserName(String firstname, String lastname, String add, String email, String userN, String passwd, String confpasswd) {
    }

    private void validateEmail(String firstname, String lastname, String add, String email, String userN, String passwd, String confpasswd) {

    }

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
