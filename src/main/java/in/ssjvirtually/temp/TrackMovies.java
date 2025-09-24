package in.ssjvirtually.temp;

import in.ssjvirtually.bot.MoviesTrackerBot;
import in.ssjvirtually.utils.WebUtils;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TrackMovies {

    public static void main(String[] args) {

        MoviesTrackerBot bot = new MoviesTrackerBot("token");
        while (true) {

            List<String> urls = new ArrayList<>();
            urls.add("https://www.district.in/movies/they-call-him-og-movie-tickets-in-hyderabad-MV171668?&fromdate=2025-09-25");
            //urls.add("https://in.bookmyshow.com/movies/hyderabad/they-call-him-og/buytickets/ET00369074/20250925");
            //urls.add("https://in.bookmyshow.com/movies/hyderabad/mirai/buytickets/ET00395402/20250923");
            //urls.add("https://in.bookmyshow.com/cinemas/hyderabad/inox-gsm-mall-hyderabad/buytickets/IGMH/20250923");

            List<String> theaters = new ArrayList<>();

            theaters.add("Geeta");
            theaters.add("GSM Mall");
            for (String url : urls) {
                WebUtils webUtils = new WebUtils();
                Document doc;
                doc = webUtils.makeHttpRequest(url);
                //add if block to check if doc is null
                if (doc != null) {
                    //search for theaters in doc
                    for (String theater : theaters) {
                        boolean isAvailable = doc.text().contains(theater);
                        System.out.println(url + " : " + theater + " is " + (isAvailable ? "available" : "not available"));
                        if (isAvailable) {

                            bot.sendMessage(1046931502, url + "\n" + theater + " is " + "available");

                        }
                    }
                }
            }
            try {
                long chatId = 123456789;
                bot.sendMessage(chatId, "bot is tracking " + new Date());
                Thread.sleep(5 * 60_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
