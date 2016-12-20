package com.company;


import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.AddressComponent;
import com.google.maps.model.GeocodingResult;

import javax.swing.*;
import java.io.IOException;

import java.net.InetAddress;
import java.net.URLEncoder;

public class WeatherInfo {

    //Constants for constructing URL for APIs
    private static final String CITY_NAME = "http://api.openweathermap.org/data/2.5/weather?q=";
    private static final String LAT_LONG = "http://api.openweathermap.org/data/2.5/weather?lat=";
    private static final String CITY_NAME_5 = "http://api.openweathermap.org/data/2.5/forecast?q=";
    private static final String LAT_LONG_5 = "http://api.openweathermap.org/data/2.5/forecast?q=";
    private static final String LOCATION = "http://api.db-ip.com/addrinfo?addr=";
    public static final String OPEN_weather_API = "&appid=5970327895fbd4b7fa982299669a2806";
    public static final String DB_IP_API = "&api_key=f6351a1b78060efa896bc969d8fbc66162ca5b1c";
    private static final GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyBj8KKpFyTlOer0lemWuKbqEYKHccXEx7A");
    private static JSONLocationParser.Location location;
    private static GUI gui;


    public static void setGui(GUI gui) {
        WeatherInfo.gui = gui;
    }

    public static String getLocationCity() {
        return location.city;
    }

    public static void getWeatherForCityName(String inputCity) {
        try {
            //Create new weather parser object
            JSONWeatherParser weatherParser = new JSONWeatherParser();
            //attempt to get the json data from the server, parse it then apply the correct information to the GUI
            UpdateGUI.update5DayGUI(weatherParser.JSON5DayweatherGenerator(Connection.getConnection(CITY_NAME_5 + URLEncoder.encode(inputCity, "UTF-8") + OPEN_weather_API)));
            UpdateGUI.update1DayGUI(weatherParser.JSONweatherGenerator(Connection.getConnection(CITY_NAME + URLEncoder.encode(inputCity, "UTF-8") + OPEN_weather_API)));

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(gui,"Please enter a new location","LOCATION NOT FOUND.",JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void getWeatherForPostcode(String inputPostCode) {
        try {
            inputPostCode = inputPostCode.replace(" ", "");//Remove any spaces in the input postcode
            //Attempt to get the name location using google maps api from input postcode
            GeocodingResult[] results = GeocodingApi.geocode(context, inputPostCode).await();
            AddressComponent[] addressComponents = results[0].addressComponents;
            //Create new weather parser object
            JSONWeatherParser weatherParser = new JSONWeatherParser();
            //attempt to get the json data from the server, parse it then apply the correct information to the GUI
            UpdateGUI.update5DayGUI(weatherParser.JSON5DayweatherGenerator(Connection.getConnection(CITY_NAME_5 + URLEncoder.encode(addressComponents[2].shortName, "UTF-8") + OPEN_weather_API)));
            UpdateGUI.update1DayGUI(weatherParser.JSONweatherGenerator(Connection.getConnection(CITY_NAME + URLEncoder.encode(addressComponents[2].shortName, "UTF-8") + OPEN_weather_API)));

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(gui,"Please enter a different postcode","POSTCODE NOT FOUND.",JOptionPane.WARNING_MESSAGE);
        }
    }



    public static void getWeatherForLongAndLat(double latitude, double longitude) {
        try {
            //Create new weather parser object
            JSONWeatherParser weatherParser = new JSONWeatherParser();
            //attempt to get the json data from the server, parse it then apply the correct information to the GUI
            UpdateGUI.update5DayGUI(weatherParser.JSON5DayweatherGenerator(Connection.getConnection(LAT_LONG_5 + URLEncoder.encode(String.valueOf(latitude), "UTF-8")+"&lon="+
                    URLEncoder.encode(String.valueOf(longitude), "UTF-8")+OPEN_weather_API)));
            UpdateGUI.update1DayGUI(weatherParser.JSONweatherGenerator(Connection.getConnection(LAT_LONG + URLEncoder.encode(String.valueOf(latitude), "UTF-8")+"&lon="+
                            URLEncoder.encode(String.valueOf(longitude), "UTF-8")+OPEN_weather_API)));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(gui,"Please enter new coordinates","COORDINATES NOT FOUND.",JOptionPane.WARNING_MESSAGE);
        }

    }

    public static void getLocation() {
        try {
            //Create new location parser object
            JSONLocationParser locationParser = new JSONLocationParser();
            //attempt to get the json data from the server and parse it to get the name of users current location
            location = locationParser.JSONLocationGenerator(Connection.makeRESTCall(LOCATION + InetAddress.getLocalHost().getHostAddress() + DB_IP_API));
            getWeatherForCityName(location.city);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(gui,"Error","Error.",JOptionPane.WARNING_MESSAGE);
        }
    }
}



















/*private static final String GEOGRAPHICAL_COORDINATES = "http://api.openweathermap.org/data/2.5/weather?lat=";//{lat}&lon={lon}";
    private static final String ZIP_CODE = "http://api.openweathermap.org/data/2.5/weather?zip=";//{zip code},{country code}";*/