package com.company;


public class Main {

    //main method

    public static void main(String[] args) {
        //Create new GUI and
        java.awt.EventQueue.invokeLater(() -> {
            GUI weatherGUI = new GUI();
            weatherGUI.setVisible(true);
            UpdateGUI.setCurrentGUI(weatherGUI);
            WeatherInfo.setGui(weatherGUI);
            WeatherInfo.getLocation();

        });



    }

}
