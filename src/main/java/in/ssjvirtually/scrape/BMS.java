package in.ssjvirtually.scrape;

import in.ssjvirtually.utils.WebUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class BMS {

    public boolean isAvailable(String movieUrl,String theater) throws IOException {

        WebUtils webUtils = new WebUtils();
        Document doc = webUtils.makeRequest(movieUrl);
        //check if doc has txt theater name
        boolean isAvailable = doc.text(theater).hasText();
        return isAvailable;
    }
}
