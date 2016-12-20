package com.company;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;

public class JSONLocationParser {

    public Location nextLocation;


    /*Nested class used to hold name of the location found
     could use more attributes if necessary hence why have
     mde it class*/

    public class Location{
        public String city;

    }


    public Location JSONLocationGenerator(String JSONLocation){
        JsonReader reader = Json.createReader(new StringReader(JSONLocation));//Create JSON reader from input string
        JsonObject jsonObject = reader.readObject();
        nextLocation = new Location();
        nextLocation.city=jsonObject.getJsonString("city").getString();//Find the value for the json objects
        return nextLocation;
    }
}

