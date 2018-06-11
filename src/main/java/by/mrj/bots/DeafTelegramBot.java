package by.mrj.bots;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.DefaultBotOptions;

@Slf4j
public class DeafTelegramBot extends TelegramBot {

    public DeafTelegramBot(String botUsername, String botToken) {
        super(botUsername, botToken);
    }

    @Override
    public void onUpdateReceived(Update update) {

    }
}
