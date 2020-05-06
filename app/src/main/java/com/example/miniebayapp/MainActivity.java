package com.example.miniebayapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    EditText username;;
    EditText userpass;
    Button btnlogin;
    TextView register;
    CheckBox remembox;
    TextView forgotPass;

    //Host address
    protected String hostAddress="192.168.0.11:8088";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText) findViewById(R.id.userNameText);
        userpass = (EditText) findViewById(R.id.userPassText);

        btnlogin = (Button) findViewById(R.id.loginButton);

        register = (TextView) findViewById(R.id.registerTxtView);
        forgotPass = (TextView) findViewById(R.id.forgotPassTxtView);

        btnlogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                loginBtnObject();
            }
        });

       register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent ireg = new Intent(MainActivity.this, RegisterPage.class);
                startActivity(ireg);
            }
        });

       forgotPass.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent iFP = new Intent(MainActivity.this, ChangePasswordPage.class);
               startActivity(iFP);
           }
       });
    }

    public void loginBtnObject() {
        String userN = username.getText().toString();
        String passwrd = userpass.getText().toString();

//        if(userN.equals("deyaneiradc21") && passwrd.equals("1234deya")){
//            Intent i = new Intent(MainActivity.this, HomePage.class);
//            i.putExtra("userName", userN);
//            startActivity(i);
//        }
//        else {
//            Toast.makeText(getApplicationContext(),"Invalid usarname or password", Toast.LENGTH_SHORT).show();
//        }
    }

    /**
     * If the user click the check box of "remember me", the user will stay login in the application until
     * he/she decide to logout from the application.
     */
    public void rememberCheckBoxObject(){
        remembox = (CheckBox) findViewById(R.id.rememberCheck);


    }

    /**
     *  This class define a thread for networks transactions
     */
    private class GetItems extends AsyncTask<Void, Void, Void> {

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
        protected GetItems(Activity activity)
        {
            //Define the servlet URL
            url = "http://" + hostAddress;
            this.activity = activity;
        }

        /**
         *  on PreExecute method: runs after the constructor is called and before the thread runs
         */
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity.this, "Authenticating..." + url, Toast.LENGTH_LONG).show();
        }

        /***
         *  Main thread
         * @param arg0
         * @return
         */
        protected Void doInBackground(Void... arg0) {

            //Read GUI inputs
            String userName, passWord;
            userName = ((EditText) findViewById(R.id.usernameText)).getText().toString();
            passWord = ((EditText) findViewById(R.id.userPassText)).getText().toString();

            //Define a HttpHandler
            HttpHandler handler = new HttpHandler();

            //perform the authentication process and capture the result in serverResponse variable
            serverResponse = handler.makeServiceCallPost(url, userName, passWord);

            return null;
        }


        /***
         *  This method verify the authentication result
         *  If authenticated, it creates an jsonPerson Object and open an authenticatedActivity
         *  otherwise, it shows a error message
         * @param result
         */
        protected void onPostExecute (Void result){
            String msgToast;

            //Verify the authentication result
            // not: the user could not be authenticated
            if (serverResponse.trim().compareTo("not")==0) {
                msgToast= "Wrong user or password";
                Toast.makeText(getApplicationContext(),
                        msgToast,
                        Toast.LENGTH_LONG).show();
            } else {
                //Define a jsonPerson object using the http response
                jsonPerson jPerson = new jsonPerson(serverResponse);
                //Create an intent in order to call the other activity
                Intent i = new Intent(MainActivity.this, HomePage.class);

                //Add parameters such as objects
                i.putExtra("jPerson", jPerson);

                //Start the other activity
                startActivity(i);
            }
        }
    }
}
