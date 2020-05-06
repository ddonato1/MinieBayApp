package com.example.miniebayapp;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.Serializable;

public class jsonPerson implements Serializable {
    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private String userName;


    /**
     *  Special constuctor using the actual values
     * @param firstName
     * @param lastName
     * @param address
     * @param email
     * @param userName
     */
    public jsonPerson(String userName, String firstName, String lastName, String address, String email)
    {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
    }

    /**
     *  Special constructor using a Json file with the following syntax:
     *      {   "userName"        ="value0",
     *          "firstName"       ="value1",
     *          "lastName"        ="value2",
     *          "address"         ="value3",
     *          "email"           ="value4"
     *      }
     * @param jsonFile
     */


    public jsonPerson(String jsonFile)
    {
        try{
            //Define a JSON object from the received data
            JSONObject jsonObj = new JSONObject(jsonFile);
            userName = jsonObj.getString("username");
            firstName = jsonObj.getString("firstname");
            lastName = jsonObj.getString("lastname");
            address = jsonObj.getString("address");
            email = jsonObj.getString("email");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /******************************************************************8
     *  Observer methods
     *
     * @return
     */


    public String getFirstName()
    {
        return  firstName;
    }

    public String getLastName()
    {
        return  lastName;
    }


    public  String getEmail( )
    {
        return email;
    }

    public  String getUserName()
    {
        return userName;
    }

    public  String getAddress()
    {
        return address;
    }
}

