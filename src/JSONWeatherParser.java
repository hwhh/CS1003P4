package com.company;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonString;



public class JSONWeatherParser {

    private final static long MILLIS_PER_DAY = 24 * 60 * 60 * 1000L;
    private List<weather> weatherForecasts;
    private weather nextWeather;
    private Date oldDate, currentDate;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");


     /*Nested class used to hold name necessary
      weather information*/

    public class weather {
        public String overView, description, icon, location, country;
        public double temperature, pressure, humidity, windSpeed, windDirection, cloudiness, maxTemp, minTemp, rain;
        public Date sunRise, sunSet, currentTime ;
    }



    public weather assighnValues (JsonObject jsonObject, JsonObject mainWeather, JsonObject mainInfo, JsonObject wind, JsonObject clouds, JsonObject rain){
        //Find the value for the json objects
        nextWeather.overView = mainWeather.getJsonString("main").getString();
        nextWeather.description = mainWeather.getJsonString("description").getString();
        nextWeather.icon = mainWeather.getJsonString("icon").getString();
        nextWeather.temperature=mainInfo.getJsonNumber("temp").doubleValue();
        nextWeather.maxTemp=mainInfo.getJsonNumber("temp_max").doubleValue();
        nextWeather.minTemp=mainInfo.getJsonNumber("temp_min").doubleValue();
        nextWeather.pressure =mainInfo.getJsonNumber("pressure").doubleValue();
        nextWeather.humidity =mainInfo.getJsonNumber("humidity").doubleValue();
        //Some values may not be present in the returned JSON data
        try{
            nextWeather.windSpeed =wind.getJsonNumber("speed").doubleValue();
        }catch (Exception e){
            nextWeather.windSpeed=0;}
        try{
            nextWeather.rain =rain.getJsonNumber("3h").doubleValue();
        }catch (Exception e){
            nextWeather.rain=0;}
        try{
            nextWeather.windDirection =wind.getJsonNumber("deg").doubleValue();
        }catch (Exception e){
            nextWeather.windDirection=0;}
        try{
            nextWeather.cloudiness =clouds.getJsonNumber("all").doubleValue();
        }catch (Exception e){
            nextWeather.cloudiness=0;}

        return nextWeather;
    }




    //Returns a list containing 5 different forecast for next 5 days
    public List<weather> JSON5DayweatherGenerator(String responce){
        List<weather> weatherFor5Days = new ArrayList();//List that contains weather objects for 5 different days
        JsonReader reader = Json.createReader(new StringReader(responce));//Create JSON reader from input string
        JsonObject jsonObject = reader.readObject();
        //Generate the JSON objects from the JSON data returned from the server
        JsonArray jsonArray = jsonObject.getJsonArray("list");
        for (int i=0; i < jsonArray.size(); i++) {
            try {
                //Create new objects for each forecast in the 5 day forecast
                nextWeather =new weather();
                JsonObject nextJsonObject = jsonArray.getJsonObject(i);
                JsonArray weather = nextJsonObject.getJsonArray("weather");
                JsonObject mainweather = weather.getJsonObject(0);
                JsonObject mainInfo = nextJsonObject.getJsonObject("main");
                JsonObject wind =nextJsonObject.getJsonObject("wind");
                JsonObject clouds =nextJsonObject.getJsonObject("clouds");
                JsonObject rain =nextJsonObject.getJsonObject("rain");
                JsonString dateTime =nextJsonObject.getJsonString("dt_txt");
                JsonObject location = jsonObject.getJsonObject("city");
                nextWeather.location= location.getJsonString("name").getString();
                nextWeather.country=location.getJsonString("country").getString();
                currentDate = sdf.parse(dateTime.getString());
                nextWeather.currentTime=currentDate;

                if (i==0){//If first item add the forecast then set the oldDate attribute to the date in the forecast
                    oldDate=currentDate;
                    nextWeather = assighnValues(jsonObject, mainweather, mainInfo, wind, clouds, rain);
                    weatherFor5Days.add(nextWeather);
                }
                //Check to see if the next forecast is at least 24hrs later
                else if(Math.abs(currentDate.getTime() - oldDate.getTime()) >= MILLIS_PER_DAY){
                    oldDate=currentDate;
                    nextWeather = assighnValues(jsonObject, mainweather, mainInfo, wind, clouds, rain);
                    weatherFor5Days.add(nextWeather);
                }

            } catch (ParseException ex) {
                Logger.getLogger(JSONWeatherParser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return weatherFor5Days;
    }

    //Returns current weather forcast
    public weather JSONweatherGenerator (String responce) throws IOException {
        weatherForecasts = new ArrayList<>();

        JsonReader reader = Json.createReader(new StringReader(responce));//Create JSON reader from input string
        JsonObject jsonObject = reader.readObject();
        //Create new objects for forecast
        JsonArray weather = jsonObject.getJsonArray("weather");
        JsonObject mainweather = weather.getJsonObject(0);
        JsonObject mainInfo = jsonObject.getJsonObject("main");
        JsonObject wind =jsonObject.getJsonObject("wind");
        JsonObject clouds =jsonObject.getJsonObject("clouds");
        JsonObject rain =jsonObject.getJsonObject("rain");
        JsonObject sunInfo =jsonObject.getJsonObject("sys");
        nextWeather = assighnValues(jsonObject, mainweather, mainInfo, wind, clouds, rain);
        nextWeather.location= jsonObject.getJsonString("name").getString();
        nextWeather.country=sunInfo.getJsonString("country").getString();
        nextWeather.sunRise = new Date(sunInfo.getJsonNumber("sunrise").longValue()*1000);
        nextWeather.sunSet = new Date(sunInfo.getJsonNumber("sunset").longValue()*1000);
        nextWeather.currentTime =  new Date();
        weatherForecasts.add(nextWeather);
        return nextWeather;
    }
}