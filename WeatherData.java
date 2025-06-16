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

        public String convertWeatherCode() {
            String weather = "";

            if (weathercode == 0) {
                weather = "Clear";
            } else if (weathercode < 3) {
                weather = "Cloudy";
            } else if ((weathercode >= 51 && weathercode <= 67) || weathercode >= 80 && weathercode <= 99) {
                weather = "Rain";
            } else if (weathercode >= 71 && weathercode <= 77) {
                weather = "Snow";
            }

            return weather;
        }

    }


}
