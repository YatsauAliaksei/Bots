package by.mrj.bots.telegram;

import org.telegram.telegrambots.api.objects.Update;

public class DeafTelegramBot extends TelegramBot {

    DeafTelegramBot(String botUsername, String botToken, Long chatId) {
        super(botUsername, botToken, chatId);
    }

    @Override
    public void onUpdateReceived(Update update) {

    }
}
