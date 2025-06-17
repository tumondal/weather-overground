package ui;

import api.WeatherAPI;
import model.WeatherData;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class WeatherFrame extends JFrame {

    private JLabel img;
    private JLabel tempLabel;
    private JLabel weatherLabel;
    private JLabel windLabel;
    private JLabel timeLabel;
    private JLabel errorLabel;
    ArrayList<JLabel> labels = new ArrayList<JLabel>();

    public WeatherFrame() {

        this.setResizable(false);

        setTitle("Weather Overground");
        setSize(400, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)); //padding

        JTextField cityInput = new JTextField(15);
        JButton searchButton = new JButton("Search");
        tempLabel = new JLabel("--°C");
        weatherLabel = new JLabel("");
        windLabel = new JLabel("Wind Speed: -- km/h");
        timeLabel = new JLabel("Time: --");
        errorLabel = new JLabel("");
        errorLabel.setForeground(Color.RED);

        ImageIcon clearImage = new ImageIcon("src/main/java/images/clear.png");
        ImageIcon cloudyImage = new ImageIcon("src/main/java/images/cloudy.png");
        ImageIcon rainImage = new ImageIcon("src/main/java/images/rain.png");
        ImageIcon snowImage = new ImageIcon("src/main/java/images/snow.png");

        img = new JLabel(clearImage);

        JLabel titleLabel = new JLabel("Weather Overground");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 25));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        cityInput.setAlignmentX(Component.CENTER_ALIGNMENT);
        cityInput.setHorizontalAlignment(JTextField.CENTER);
        cityInput.setMaximumSize(new Dimension(350, 45));

        img.setAlignmentX(Component.CENTER_ALIGNMENT);

        tempLabel.setFont(new Font("Arial", Font.BOLD, 40));

        weatherLabel.setFont(new Font("Arial", Font.PLAIN, 20));

        searchButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        tempLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        weatherLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        windLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        timeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);



        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        labels.add(titleLabel);

        panel.add(cityInput);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        panel.add(searchButton);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));

        panel.add(img);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));

        panel.add(tempLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        labels.add(tempLabel);

        panel.add(weatherLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        labels.add(weatherLabel);

        panel.add(windLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        labels.add(windLabel);

        panel.add(timeLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        labels.add(timeLabel);

        panel.add(errorLabel);

        searchButton.addActionListener(e -> {
            System.out.println("Button was clicked!");
            String city = cityInput.getText().trim();
            if (city.isEmpty()) {
                errorLabel.setText("Please enter a city name!");
                img.setIcon(null);
                tempLabel.setText("");
                weatherLabel.setText("");
                windLabel.setText("");
                timeLabel.setText("");
                return;
            }
            errorLabel.setText("");

            System.out.println("City entered: " + city);

            double[] coordinates = WeatherAPI.getCoordinatesFromCity(city);

            if (coordinates == null) {
                errorLabel.setText("City not found!");
                img.setIcon(null);
                tempLabel.setText("");
                weatherLabel.setText("");
                windLabel.setText("");
                timeLabel.setText("");
                return;
            }


            double lat = coordinates[0];
            double lon = coordinates[1];


            WeatherData data = WeatherAPI.getWeather(lat, lon);

            if (data != null) {
                //Update GUI labels instead of just printing
                tempLabel.setText(data.current_weather.temperature + "°C");
                weatherLabel.setText(data.current_weather.convertWeatherCode());
                switch (data.current_weather.convertWeatherCode()) {
                    case "Clear" -> img.setIcon(clearImage);
                    case "Cloudy" -> img.setIcon(cloudyImage);
                    case "Foggy" -> img.setIcon(cloudyImage);
                    case "Rain" -> img.setIcon(rainImage);
                    case "Snow" -> img.setIcon(snowImage);
                }
                windLabel.setText("Wind Speed: " + data.current_weather.windspeed + " km/h");
                timeLabel.setText("Time: " + data.getTime());
                if (data.current_weather.is_day == 0) {
                    panel.setBackground(Color.DARK_GRAY);
                    for (int i=0; i<labels.size(); i++){
                        labels.get(i).setForeground(Color.WHITE);
                    }
                } else {
                    panel.setBackground(new Color(238, 238, 238));
                    for (int i=0; i<labels.size(); i++){
                        labels.get(i).setForeground(Color.BLACK);
                    }
                }

            } else {
                errorLabel.setText("Could not load weather data!");
                img.setIcon(null);
                tempLabel.setText("");
                weatherLabel.setText("");
                windLabel.setText("");
                timeLabel.setText("");
            }

        });

        add(panel);
        setVisible(true);

    }

}
