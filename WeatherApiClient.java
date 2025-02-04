import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONObject;





public class WeatherApiClient {


    /**
     * A Java application that fetches weather data from a public REST API and displays it in a structured format.
     */


        // Replace with your OpenWeatherMap API key
        private static final String API_KEY = "your_api_key_here";
        private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather";

        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);

            try {
                // Ask user for the city name
                System.out.print("Enter the city name: ");
                String city = scanner.nextLine();

                // Build the API URL
                String urlString = BASE_URL + "?q=" + city + "&appid=" + API_KEY + "&units=metric";

                // Send the HTTP GET request
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                // Get the response
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();

                    // Parse the JSON response
                    JSONObject jsonResponse = new JSONObject(response.toString());
                    displayWeatherData(jsonResponse);
                } else {
                    System.out.println("Error: Unable to fetch weather data. HTTP response code: " + responseCode);
                }

                connection.disconnect();
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
                e.printStackTrace();
            } finally {
                scanner.close();
            }
        }

        /**
         * Displays weather data in a structured format.
         * @param jsonResponse The JSON object containing weather data.
         */
        private static void displayWeatherData(JSONObject jsonResponse) {
            // Extract main data
            String cityName = jsonResponse.getString("name");
            JSONObject main = jsonResponse.getJSONObject("main");
            double temperature = main.getDouble("temp");
            int humidity = main.getInt("humidity");

            // Extract weather description
            JSONObject weather = jsonResponse.getJSONArray("weather").getJSONObject(0);
            String description = weather.getString("description");

            // Print the weather data
            System.out.println("Weather Data for " + cityName + ":");
            System.out.println("Temperature: " + temperature + " °C");
            System.out.println("Humidity: " + humidity + "%");
            System.out.println("Description: " + description);
        }


}
