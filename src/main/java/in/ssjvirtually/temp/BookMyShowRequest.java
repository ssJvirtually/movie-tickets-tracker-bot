package in.ssjvirtually.temp;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class BookMyShowRequest {
    public static void main(String[] args) {
        String url = "https://in.bookmyshow.com/movies/hyderabad/mirai/buytickets/ET00395402/20250923";

        try {
            // Create HttpClient
            HttpClient client = HttpClient.newHttpClient();

            // Build GET request with headers (mimic browser, because BookMyShow may block bots)
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) "
                            + "AppleWebKit/537.36 (KHTML, like Gecko) "
                            + "Chrome/135.0.0.0 Safari/537.36")
                    .GET()
                    .build();

            // Send request
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Print status and body
            System.out.println("Status code: " + response.statusCode());
            System.out.println("Response body:\n" + response.body());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
