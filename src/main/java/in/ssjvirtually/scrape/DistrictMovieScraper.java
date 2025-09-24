package in.ssjvirtually.scrape;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DistrictMovieScraper {

    public static void main(String[] args) throws IOException {
        String url = "https://www.district.in/movies/miraj-cinemas-geeta-chandanagar-hyderabad-in-hyderabad-CD10249?fromdate=2025-09-25";

        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0") // spoof browser agent
                .timeout(10000)
                .get();

        // Select all movie <li> elements
        Elements movieLis = doc.select("li.MovieSessionsListing_movieSessions__c4gaO");

        List<Movie> movies = new ArrayList<>();

        for (Element li : movieLis) {
            Movie movie = new Movie();

            // Title
            Element titleEl = li.selectFirst("div.MovieSessionsListing_movieDetailsDivHeading__0OtOG");
            if (titleEl != null) movie.title = titleEl.text();

            // Language/Rating line (first subheading)
            Element langEl = li.select("div.MovieSessionsListing_movieDetailsDivSubHeading__Y9rYN").first();
            if (langEl != null) {
                movie.language = langEl.text(); // contains rating + language
            }

            // Genre (second subheading)
            Element genreEl = li.select("div.MovieSessionsListing_movieDetailsDivSubHeading__Y9rYN").last();
            if (genreEl != null && !genreEl.equals(langEl)) {
                movie.genre = genreEl.text();
            }

            // Showtimes
            Elements timeDivs = li.select("div.MovieSessionsListing_time___f5tm");
            for (Element t : timeDivs) {
                movie.showtimes.add(t.text());
            }

            movies.add(movie);
        }

        // Print results
        for (Movie m : movies) {
            System.out.println(m);
        }
    }

    static class Movie {
        String title;
        String language;
        String genre;
        List<String> showtimes = new ArrayList<>();

        @Override
        public String toString() {
            return "Movie{" +
                    "title='" + title + '\'' +
                    ", language='" + language + '\'' +
                    ", genre='" + genre + '\'' +
                    ", showtimes=" + showtimes +
                    '}';
        }
    }
}
