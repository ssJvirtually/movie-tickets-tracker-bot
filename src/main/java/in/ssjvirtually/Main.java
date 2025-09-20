package in.ssjvirtually;

import in.ssjvirtually.bot.MoviesTrackerBot;
import in.ssjvirtually.config.ConfigProps;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Properties;

public class Main {

    public static void main(String[] args) {

        Properties properties = ConfigProps.readPropertiesFromClasspath(".env");

        final String BOT_TOKEN = properties.getProperty("BOT_TOKEN");

        try {
            // Instantiate Telegram API
            TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication();

            botsApplication.registerBot(BOT_TOKEN, new MoviesTrackerBot(BOT_TOKEN));
            // Ensure this prcess wait forever
            Thread.currentThread().join();
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
