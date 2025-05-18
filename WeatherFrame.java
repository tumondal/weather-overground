package ui;

import api.WeatherAPI;
import model.WeatherData;

import javax.swing.*;
import java.awt.*;

public class WeatherFrame extends JFrame {

    private JLabel tempLabel;
    private JLabel windLabel;
    private JLabel timeLabel;
    private JLabel errorLabel;


    public WeatherFrame() {

        setTitle("Weather Overground");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)); //padding


        JTextField cityInput = new JTextField(15);
        JButton searchButton = new JButton("Search");
        tempLabel = new JLabel("Temperature: --°C");
        windLabel = new JLabel("Wind Speed: -- km/h");
        timeLabel = new JLabel("Time: --");
        errorLabel = new JLabel("");
        errorLabel.setForeground(Color.RED);

        JLabel titleLabel = new JLabel("Weather Overground");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);


        cityInput.setAlignmentX(Component.CENTER_ALIGNMENT);
        searchButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        tempLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        windLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        timeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);


        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));

        panel.add(cityInput);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        panel.add(searchButton);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));

        panel.add(tempLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));

        panel.add(windLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));

        panel.add(timeLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));

        panel.add(errorLabel);





        searchButton.addActionListener(e -> {
            System.out.println("Button was clicked!");
            String city = cityInput.getText().trim();
            if (city.isEmpty()) {
                errorLabel.setText("Please enter a city name!");
                tempLabel.setText("");
                windLabel.setText("");
                timeLabel.setText("");
                return;
            }
            errorLabel.setText("");

            System.out.println("City entered: " + city);

            double[] coordinates = WeatherAPI.getCoordinatesFromCity(city);

            if (coordinates == null) {
                errorLabel.setText("City not found!");
                tempLabel.setText("");
                windLabel.setText("");
                timeLabel.setText("");
                return;
            }


            double lat = coordinates[0];
            double lon = coordinates[1];


            WeatherData data = WeatherAPI.getWeather(lat, lon);

            if (data != null) {
                //Update GUI labels instead of just printing
                tempLabel.setText("Temperature: " + data.current_weather.temperature + "°C");
                windLabel.setText("Wind Speed: " + data.current_weather.windspeed + " km/h");
                timeLabel.setText("Time: " + data.current_weather.time);
            } else {
                errorLabel.setText("Could not load weather data!");
                tempLabel.setText("");
                windLabel.setText("");
                timeLabel.setText("");
            }

        });

        add(panel);
        setVisible(true);

    }

}
