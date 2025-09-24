package in.ssjvirtually.bot;

import in.ssjvirtually.scrape.BMS;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.io.IOException;

public class MoviesTrackerBot implements LongPollingSingleThreadUpdateConsumer {

    private final TelegramClient telegramClient;

    public MoviesTrackerBot(String botToken) {
        telegramClient = new OkHttpTelegramClient(botToken);
    }


    @Override
    public void consume(Update update) {
        // We check if the update has a message and the message has text
        if(update.hasMessage() &&  update.getMessage().hasEntities()) {

            // Set variables
            handleUpdate(update);

        }
        else if (update.hasMessage() && update.getMessage().hasText()) {
            // Set variables
            String message_text = update.getMessage().getText();
            System.out.println(message_text);
            long chat_id = update.getMessage().getChatId();

            SendMessage message = SendMessage // Create a message object
                    .builder()
                    .chatId(chat_id)
                    .text(message_text)
                    .build();
            try {
                telegramClient.execute(message); // Sending our message object to user
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    public String trackMovie(Message message)  {

        String text = message.getText();
        String movieUrl = text.split("\n")[1];
        String theater = text.split("\n")[2];

        BMS bms = new BMS();
        boolean isAvailable = false;
        try {
            isAvailable = bms.isAvailable(movieUrl,theater);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return isAvailable ? "YES" : "NO";
    }


    public void handleUpdate(Update update) {

        Message message = update.getMessage();

        // Handle the update
        switch (message.getText()) {
            case "/start":
                // Send a welcome message
                break;
            case "/help":
                // Send some help message
                break;
            case "/track":
                trackMovie(message);
                break;
        }
    }


    public void sendMessage(long chatId, String text) {
        SendMessage message = SendMessage // Create a message object
                .builder()
                .chatId(chatId)
                .text(text)
                .build();
        try {
            telegramClient.execute(message); // Sending our message object to user
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}