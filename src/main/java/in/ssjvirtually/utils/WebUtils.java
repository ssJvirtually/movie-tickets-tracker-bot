package in.ssjvirtually.utils;

import in.ssjvirtually.config.ConfigProps;
import okhttp3.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class WebUtils {

    List<String> userAgents = new ArrayList<>();
    public WebUtils(){
        userAgents = ConfigProps.readLines("user-agents.txt");
    }
    public Document makeRequest(String url)  {
        Document doc = null;
        try {
            doc = Jsoup.connect(url)
                    .userAgent(userAgents.get((int) (Math.random() * userAgents.size()))) // spoof browser agent
                    .timeout(10000)
                    .get();
            Thread.sleep(5000);
        } catch (IOException e) {
           e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return doc;
    }


    public Document makeHttpRequest(String url) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("Cookie", "__cf_bm=evTfXh8PfhHOVec9eAdWNfGXHURA5833e7a1WarFgBA-1758606298-1.0.1.1-X2fzouJr48n.iEDvz5qcnrmFCkGSMIhPoz6SahTbE2sL1JPlvDgqVueqvEmC2qdBO2ET0Z5M6V42oeELNtXHfc7CuHFU.F326LiK9r2hg6A; _cfuvid=UYnQA8A6R8EvECfiHK1mvrMQqItTT33RM4oNYaXvjsk-1758606298377-0.0.1.1-604800000")
                .build();
        Response response;
        Document doc = null;
        try {
            response = client.newCall(request).execute();

            doc = Jsoup.parse(response.body() != null ? response.body().string() : null);
        } catch (IOException e) {
            // Log the exception and continue
            System.err.println("Error making HTTP request: " + e.getMessage());
        }
        return doc;
    }
}
