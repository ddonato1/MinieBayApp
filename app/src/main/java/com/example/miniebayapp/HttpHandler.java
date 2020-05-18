/**
 * Angel J. Vargas Lopez - S01274152
 * Deyaneira Donato Carrasquillo - S01183053
 * **
 * Project HTTPHandler caller, this class helps to interact with the database
 * by using the GET and POST communication.
 **/
package com.example.miniebayapp;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class HttpHandler {
    // This for debugging
    private static final String TAG = HttpHandler.class.getSimpleName();

    /***
     *  Default constructor
     */
    public HttpHandler() {
    }


    /***
     *  This method downloads the JSON data from a Request URL
     *For the USERNAME and PASSWORD in login
     * @param reqUrl : Target URL
     * @return
     */
    public String makeServiceCallPost(String reqUrl, String userName, String pass) {
        // HTTP Response
        String response = null;
        try {
            //Generate a URL object from the requested URL
            URL url = new URL(reqUrl);
            // Create a Http Connection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Define Request POST
            conn.setRequestMethod("POST");

            //Define the parameters list
            String parameters="user="+userName+"&pass="+pass;

            //Establish the option for sending parameters using the POST method
            conn.setDoOutput(true);
            //Add the parameters list to the http request
            conn.getOutputStream().write(parameters.getBytes("UTF-8"));

            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            // Convert the InputStream in a Spring
            response = convertStreamToString(in);
        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
        return response;
    }

    /***
     *  This method downloads the JSON data from a Request URL
     *
     * @param reqUrl: Target URL
     * @return
     */
    public String makeServiceCall(String reqUrl) {
        // HTTP Response
        String response = null;
        try {
            //Generate a URL object from the requested URL
            URL url = new URL(reqUrl);
            // Create a Http Connection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Define Request GET
            conn.setRequestMethod("GET");
            System.out.println(conn.getRequestProperties().toString());

            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());

            // Convert the InputStream in a Spring
            response = convertStreamToString(in);

        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
        return response;
    }

    /**
     *  This method generates a String  from a InputStream
     * @param is: InputStream
     * @return
     */
    private String convertStreamToString(InputStream is) {
        // Generate a BufferedReader from a InputStream
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        // Create a StringBuilder
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            // Traverse the inputStream and generate a String
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Return the String
        return sb.toString();
    }

    /***
     *  This method downloads the JSON data from a Request URL
     *For the User registration
     * @param reqUrl : Target URL
     * @return
     */
    public String makeServiceCallPost2(String reqUrl, String userN, String passwd, String firstname, String lastname, String phonenum, String email, String add) {
        // HTTP Response
        String response = null;
        try {
            //Generate a URL object from the requested URL
            URL url = new URL(reqUrl);
            // Create a Http Connection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Define Request POST
            conn.setRequestMethod("POST");

            //Define the parameters list
            String parameters="user="+userN+"&pass="+passwd+"&fname="+firstname+"&lname="+lastname+"&tel="+add+"&e_mail="+phonenum+"&add="+email;

            //Establish the option for sending parameters using the POST method
            conn.setDoOutput(true);
            //Add the parameters list to the http request
            conn.getOutputStream().write(parameters.getBytes("UTF-8"));

            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            // Convert the InputStream in a Spring
            response = convertStreamToString(in);
        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
        return response;
    }

    /***
     *  This method downloads the JSON data from a Request URL
     *For the Product adding
     * @param reqUrl : Target URL
     * @return
     */
    public String makeServiceCallPost3(String reqUrl, String Name, String Description, String Price, String photo, String deptname, String cateid, String owner) {
        // HTTP Response
        String response = null;
        try {
            //Generate a URL object from the requested URL
            URL url = new URL(reqUrl);
            // Create a Http Connection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Define Request POST
            conn.setRequestMethod("POST");

            //Define the parameters list
            String parameters="namep="+Name+"&decp="+ Description +"&pricep="+Price+"&photoURL="+photo+"&dept="+deptname+"&cateid="+cateid+"&owner="+owner;

            //Establish the option for sending parameters using the POST method
            conn.setDoOutput(true);
            //Add the parameters list to the http request
            conn.getOutputStream().write(parameters.getBytes("UTF-8"));

            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            // Convert the InputStream in a Spring
            response = convertStreamToString(in);
        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
        return response;
    }

    /***
     *  This method downloads the JSON data from a Request URL
     *For the Bid on a Product
     * @param reqUrl : Target URL
     * @return
     */
    public String makeServiceCallPost4(String reqUrl, String PriceBid, String username, String prodid, String owner) {
        // HTTP Response
        String response = null;
        try {
            //Generate a URL object from the requested URL
            URL url = new URL(reqUrl);
            // Create a Http Connection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Define Request POST
            conn.setRequestMethod("POST");

            //Define the parameters list
            String parameters="pricebid="+PriceBid+"&Owner="+owner+"&prodid="+prodid+"&username="+username;

            //Establish the option for sending parameters using the POST method
            conn.setDoOutput(true);
            //Add the parameters list to the http request
            conn.getOutputStream().write(parameters.getBytes("UTF-8"));

            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            // Convert the InputStream in a Spring
            response = convertStreamToString(in);
        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
        return response;
    }
}
