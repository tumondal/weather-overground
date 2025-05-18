package model;

public class WeatherData {
    public CurrentWeather current_weather;

    public static class CurrentWeather{
        public String time;
        public int interval;
        public double temperature;
        public double windspeed;
        public double winddirection;
        public int is_day;
        public int weathercode;

    }
}
