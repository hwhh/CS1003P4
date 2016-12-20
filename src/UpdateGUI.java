package com.company;



import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;


public class UpdateGUI {

    //refresh the gui

    private static GUI currentGUI;


    private static final SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM");

    public static void setCurrentGUI(GUI currentGUI) {
        UpdateGUI.currentGUI = currentGUI;
    }


    public static ImageIcon getIcon(String icon, String type){
        ImageIcon imageIcon = null;
        //Based on icon stored in weather object find correct icon
        switch(icon){
            case("01d")://sunny
                imageIcon= new ImageIcon("Icons/sunny"+type+".jpg");
                break;
            case("02d")://partly sunny
                imageIcon= new ImageIcon("Icons/partlySunny"+type+".jpg");
                break;
            case("03d")://cloudy
                imageIcon= new ImageIcon("Icons/overcast"+type+".jpg");
                break;
            case("50d")://mist
            case("04d")://more cloudy
                imageIcon= new ImageIcon("Icons/cloudy"+type+".jpg");
                break;
            case("09d")://rainy
            case("10d")://rainy
                imageIcon= new ImageIcon("Icons/rain"+type+".jpg");
                break;
            case("11d")://thunder
                imageIcon= new ImageIcon("Icons/thunder"+type+".jpg");
                break;
            case("13d")://snow
                imageIcon= new ImageIcon("Icons/snow"+type+".jpg");
                break;
            case("01n")://night
            case("02n")://night 
            case("03n")://night
            case("04n")://night 
            case("09n")://night
            case("10n")://night
            case("11n")://night
            case("13n")://night
                imageIcon= new ImageIcon("Icons/night"+type+".jpg");
                break;
        }

        return imageIcon;
    }



    public static void convertTemp (int tempUnit, JSONWeatherParser.weather weather ){
        switch (tempUnit){

            case(0):
                //Change the labels temperature labels on all panels to original values from server
                currentGUI.getjLabelTemperature().setText("Temperature: "+String.valueOf(weather.temperature)+"K");
                currentGUI.getjLabelMaximumTemp().setText("Maximum Temp: "+String.valueOf(weather.maxTemp)+"K");
                currentGUI.getjLabelMinimumTemp().setText("Minimum Temp: "+String.valueOf(weather.minTemp)+"K");
                currentGUI.getjLabelTemp1().setText("Temp: "+String.valueOf(weather.temperature)+"K");
                currentGUI.getjLabelTemp2().setText("Temp: "+String.valueOf(weather.temperature)+"K");
                currentGUI.getjLabelTemp3().setText("Temp: "+String.valueOf(weather.temperature)+"K");
                currentGUI.getjLabelTemp4().setText("Temp: "+String.valueOf(weather.temperature)+"K");
                currentGUI.getjLabelTemp5().setText("Temp: "+String.valueOf(weather.temperature)+"K");
                break;
            case(1):
                //Change the labels temperature labels on all panels to to celsius by converting original temperature read in from server
                currentGUI.getjLabelTemperature().setText("Temperature: "+String.valueOf(Math.round((weather.temperature-278)*100.0)/100)+"°C");
                currentGUI.getjLabelMaximumTemp().setText("Maximum Temp: "+String.valueOf(Math.round((weather.maxTemp-278)*100.0)/100)+"°C");
                currentGUI.getjLabelMinimumTemp().setText("Minimum Temp: "+String.valueOf(Math.round((weather.minTemp-278)*100.0)/100)+"°C");
                currentGUI.getjLabelTemp1().setText("Temp: "+String.valueOf(Math.round((weather.temperature-278)*100.0)/100)+"°C");
                currentGUI.getjLabelTemp2().setText("Temp: "+String.valueOf(Math.round((weather.temperature-278)*100.0)/100)+"°C");
                currentGUI.getjLabelTemp3().setText("Temp: "+String.valueOf(Math.round((weather.temperature-278)*100.0)/100)+"°C");
                currentGUI.getjLabelTemp4().setText("Temp: "+String.valueOf(Math.round((weather.temperature-278)*100.0)/100)+"°C");
                currentGUI.getjLabelTemp5().setText("Temp: "+String.valueOf(Math.round((weather.temperature-278)*100.0)/100)+"°C");
                break;
            case(2):
                //Change the labels temperature labels on all panels to to fahrenheit by converting original temperature read in from server
                currentGUI.getjLabelTemperature().setText("Temperature: "+String.valueOf((Math.round(((weather.temperature*9)/5)- 459.67)*100.0)/100)+ "°F");
                currentGUI.getjLabelMaximumTemp().setText("Maximum Temp: "+String.valueOf((Math.round(((weather.maxTemp*9)/5)- 459.67)*100.0)/100)+ "°F");
                currentGUI.getjLabelMinimumTemp().setText("Minimum Temp: "+String.valueOf((Math.round(((weather.minTemp*9)/5)- 459.67)*100.0)/100)+ "°F");
                currentGUI.getjLabelTemp1().setText("Temp: "+String.valueOf((Math.round(((weather.temperature*9)/5)- 459.67)*100.0)/100)+ "°F");
                currentGUI.getjLabelTemp2().setText("Temp: "+String.valueOf((Math.round(((weather.temperature*9)/5)- 459.67)*100.0)/100)+ "°F");
                currentGUI.getjLabelTemp3().setText("Temp: "+String.valueOf((Math.round(((weather.temperature*9)/5)- 459.67)*100.0)/100)+ "°F");
                currentGUI.getjLabelTemp4().setText("Temp: "+String.valueOf((Math.round(((weather.temperature*9)/5)- 459.67)*100.0)/100)+ "°F");
                currentGUI.getjLabelTemp5().setText("Temp: "+String.valueOf((Math.round(((weather.temperature*9)/5)- 459.67)*100.0)/100)+ "°F");
                break;

        }

    }
    public static void update5DayGUI(List<JSONWeatherParser.weather> weather){
        //Set the labels for 5 day weather forecast panel
        currentGUI.getjLabelTemp1().setText("Temp: "+String.valueOf(weather.get(0).temperature)+"K");
        currentGUI.getjLabelTemp2().setText("Temp: "+String.valueOf(weather.get(1).temperature)+"K");
        currentGUI.getjLabelTemp3().setText("Temp: "+String.valueOf(weather.get(2).temperature)+"K");
        currentGUI.getjLabelTemp4().setText("Temp: "+String.valueOf(weather.get(3).temperature)+"K");
        currentGUI.getjLabelTemp5().setText("Temp: "+String.valueOf(weather.get(4).temperature)+"K");
        currentGUI.getjLabelIcon1().setIcon(getIcon(weather.get(0).icon,"Small"));
        currentGUI.getjLabelIcon2().setIcon(getIcon(weather.get(1).icon,"Small"));
        currentGUI.getjLabelIcon3().setIcon(getIcon(weather.get(2).icon,"Small"));
        currentGUI.getjLabelIcon4().setIcon(getIcon(weather.get(3).icon,"Small"));
        currentGUI.getjLabelIcon5().setIcon(getIcon(weather.get(4).icon,"Small"));
        currentGUI.getjLabelDate1().setText(String.valueOf(sdf.format(weather.get(0).currentTime)));
        currentGUI.getjLabelDate2().setText(String.valueOf(sdf.format(weather.get(1).currentTime)));
        currentGUI.getjLabelDate3().setText(String.valueOf(sdf.format(weather.get(2).currentTime)));
        currentGUI.getjLabelDate4().setText(String.valueOf(sdf.format(weather.get(3).currentTime)));
        currentGUI.getjLabelDate5().setText(String.valueOf(sdf.format(weather.get(4).currentTime)));
        currentGUI.getjLabelDescription1().setText(String.valueOf(weather.get(0).description));
        currentGUI.getjLabelDescription2().setText(String.valueOf(weather.get(1).description));
        currentGUI.getjLabelDescription3().setText(String.valueOf(weather.get(2).description));
        currentGUI.getjLabelDescription4().setText(String.valueOf(weather.get(3).description));
        currentGUI.getjLabelDescription5().setText(String.valueOf(weather.get(4).description));
        currentGUI.setweatherForecast(weather);
    }

    public static void update1DayGUI (JSONWeatherParser.weather weather){
        //Set the labels for 5 day weather forecast panel
        currentGUI.getjLabelCloudiness().setText("Cloudiness: "+String.valueOf(weather.cloudiness));
        currentGUI.getjLabelHumidity().setText("Humidity: "+String.valueOf(weather.humidity));
        currentGUI.getjLabelIcon().setIcon(getIcon(weather.icon,""));
        //Using the google maps API try to get image of google maps at the input location
        try {
            currentGUI.getjLabelMap().setIcon(new ImageIcon(new URL("https://maps.googleapis.com/maps/api/staticmap?center="+ URLEncoder.encode(weather.location,"UTF-8")+"&zoom=9.5&size=300x150&key=AIzaSyDquA5Ah6Iv6awlnwp-NuIUOktTAweP87A")));
        } catch (Exception ex) {
        }
        currentGUI.getjLabelMaximumTemp().setText("Maximum Temp: "+String.valueOf(weather.maxTemp)+"K");
        currentGUI.getjLabelMinimumTemp().setText("Maximum Temp: "+String.valueOf(weather.minTemp)+"K");
        currentGUI.getjLabelPressure().setText("Pressure: "+String.valueOf(weather.pressure));
        currentGUI.getjLabelRain().setText("Rain: "+String.valueOf(weather.rain));
        currentGUI.getjLabelStatus().setText("Status: "+String.valueOf(weather.overView));
        currentGUI.getjLabelSunriseTime().setText("Sunrise Time: "+String.valueOf(weather.sunRise));
        currentGUI.getjLabelSunset().setText("Sunset Time: "+String.valueOf(weather.sunSet));
        currentGUI.getjLabelTemperature().setText("Temperature: "+String.valueOf(weather.temperature)+"K");
        currentGUI.getjLabelWindDirection().setText("Wind Direction: "+String.valueOf(weather.windDirection));
        currentGUI.getjLabelWindSpeed().setText(String.valueOf("Wind Speed: "+weather.windSpeed+ "m/s"));
        currentGUI.getjLabelCurrentLocation().setText(String.valueOf("Current Location: "+ WeatherInfo.getLocationCity()));
        currentGUI.getjLabelLocation().setText(String.valueOf("Displaying results for: "+weather.location));
        currentGUI.setweather(weather);

    }

}