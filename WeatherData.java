package model;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class WeatherData {
    public CurrentWeather current_weather;
    private double lon;
    private double lat;

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLatLon(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public String getTime() {
        String timeZoneID = TimezoneMapper.latLngToTimezoneString(lat, lon);
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of(timeZoneID));
        System.out.println(zonedDateTime.format(DateTimeFormatter.ofPattern("HH:mm")));

        return zonedDateTime.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public static class CurrentWeather{
        public String time;
        public int interval;
        public double temperature;
        public double windspeed;
        public double winddirection;
        public int is_day;
        public int weathercode;

        public String convertWeatherCode() {
            String weather = "";

            if (weathercode == 0) {
                weather = "Clear";
            } else if (weathercode <= 3) {
                weather = "Cloudy";
            } else if (weathercode <= 48) {
                weather = "Foggy";
            } else if ((weathercode >= 51 && weathercode <= 67) || weathercode >= 80 && weathercode <= 99) {
                weather = "Rain";
            } else if (weathercode >= 71 && weathercode <= 77) {
                weather = "Snow";
            }

            return weather;
        }

    }


}
