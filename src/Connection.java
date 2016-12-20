package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Connection {

    public static String makeRESTCall(String strURL) throws IOException {
        URL url = new URL(strURL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET"); //Specify that we are expecting JSON data to be returned
        conn.setRequestProperty("Accept", "application/json");
        //200 is the 'OK' response code. This method may also return 401 for an unauthorised request, or -1 if the response is not valid HTTP
        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
        }
        BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream()))); //Create reader to read response from the server
        //Using a StringBuilder is more time and memory efficient, when the size of the concatenated String could be very large
        StringBuilder buffer = new StringBuilder();
        String output;
        while ((output = br.readLine()) != null) {
            buffer.append(output);
        }
        conn.disconnect();
        return buffer.toString();
    }

    public static String  getConnection(String URL){
        try {
            //Return the JSON data as string
            return (Connection.makeRESTCall(URL));
        }
        //Catch Exceptions thrown by the APIs when trying to connect
        catch(MalformedURLException e) {
            System.out.println("Problem encountered with the URL");
            e.printStackTrace();
        }
        catch(IOException e) {
            System.out.println("Problem encountered during communication");
            e.printStackTrace();
        }
        return null;

    }
}
