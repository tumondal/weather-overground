package api;

import java.io.*;
import java.net.URL;
import java.net.HttpURLConnection;

import com.google.gson.Gson;
import model.WeatherData;

public class WeatherAPI {
    public static WeatherData getWeather(double lat, double lon){
        try {
            String urlString = "https://api.open-meteo.com/v1/forecast?latitude=" + lat + "&longitude=" + lon + "&current_weather=true";
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();

            while((line = reader.readLine()) != null){
                response.append(line);
            }
            reader.close();

            Gson gson = new Gson();
            WeatherData data = gson.fromJson(response.toString(), WeatherData.class);

            System.out.println("Temperature: " + data.current_weather.temperature + "°C");
            System.out.println("Wind Speed: " + data.current_weather.windspeed + " km/h");
            System.out.println("Time: " + data.current_weather.time);


            return data;





        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static double[] getCoordinatesFromCity(String city) {
        try {
            // Encode city name into URL-safe format
            String encodedCity = java.net.URLEncoder.encode(city, "UTF-8");

            // Create the geocoding URL
            String geoUrl = "https://nominatim.openstreetmap.org/search?q=" + encodedCity + "&format=json&limit=1";

            // Open connection
            URL url = new URL(geoUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");  // Required by Nominatim

            // Read the response
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Parse JSON response (it's an array)
            Gson gson = new Gson();
            GeocodeResult[] results = gson.fromJson(response.toString(), GeocodeResult[].class);

            if (results.length > 0) {
                double lat = Double.parseDouble(results[0].lat);
                double lon = Double.parseDouble(results[0].lon);
                return new double[]{lat, lon};
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static class GeocodeResult {
        String lat;
        String lon;
    }


}




