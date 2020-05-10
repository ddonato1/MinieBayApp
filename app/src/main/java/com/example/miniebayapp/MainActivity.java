package com.example.miniebayapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import static android.content.Context.MODE_PRIVATE;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    //Intent for calling another activity
    Intent i;
    //Shared object though the application
    SharedPreferences pref;

    //Host address
    protected String hostAddress="192.168.0.11:8088";
//    //Shared object though the application
//    SharedPreferences pref2;
    //Authentication Servlet name
    protected String servletName = "sessionServlet";
    //Server default response
    String serverResponse="not";


    //String variables
    String userName;
    String passwd;
    //Layouts variables
    EditText username;;
    EditText userpass;
    Button btnlogin;
    TextView register;
    TextView forgotPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText) findViewById(R.id.userNameText);
        userpass = (EditText) findViewById(R.id.userPassText);

        btnlogin = (Button) findViewById(R.id.loginButton);

        register = (TextView) findViewById(R.id.registerTxtView);
        forgotPass = (TextView) findViewById(R.id.forgotPassTxtView);

//        btnlogin.setOnClickListener(new View.OnClickListener(){
//                @Override
//                public void onClick(View v){
//                   Intent i = new Intent(MainActivity.this, HomePage.class);
//                   startActivity(i);
//                }
//            });

        /*Create local session variables*/
        pref = getSharedPreferences("user_details", MODE_PRIVATE);
        i = new Intent(MainActivity.this, HomePage.class);

        //Verify for session variables
        if(pref.contains("username") && pref.contains("password") && pref.contains("sessionValue")) {
            //The user has log to the application
            userName = pref.getString("user", null);
            passwd = pref.getString("sessionValue", null);

            //Authenticate credentials
            new GetItems(MainActivity.this).execute();
        }
        else {
            //The user has not been authenticated
            btnlogin.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    userName = username.getText().toString();
                    passwd = userpass.getText().toString();

                    //Authenticate the user via webservices
                    new GetItems(MainActivity.this).execute();
                }
            });
        }



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


    /**
     *  This class define a thread for networks transactions
     */
    private class GetItems extends AsyncTask<Void, Void, Void> {

        // Context: every transaction in a Android application must be attached to a context
        private Activity activity;

        //Server response
        //private String serverResponse;

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
            url = "http://" + hostAddress +"/"+ servletName;
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
            String username, passWord;
            username = ((EditText) findViewById(R.id.userNameText)).getText().toString();
            passWord = ((EditText) findViewById(R.id.userPassText)).getText().toString();

            //Define a HttpHandler
            HttpHandler handler = new HttpHandler();

            //perform the authentication process and capture the result in serverResponse variable
            serverResponse = handler.makeServiceCallPost(url, username, passWord);

            //Clean response
            serverResponse=serverResponse.trim();

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
            if (!serverResponse.equals("not")) {
                //The user has been authenticated
                //Update local session variables
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("username", userName);
                editor.putString("password", passwd);
                editor.putString("sessionValue", serverResponse);
                editor.commit();

                //Define the next activity
                Intent i = new Intent(MainActivity.this, HomePage.class);
                //call the DetailsActivity
                startActivity(i);
            }
            else {
                ///The user could not been authenticated, destroy session variables
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.commit();

                //Toast message
                msgToast= "Wrong user or password";
                Toast.makeText(getApplicationContext(),
                        msgToast,
                        Toast.LENGTH_LONG).show();
            }
        }
    }
}
