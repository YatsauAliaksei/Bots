package by.mrj.bots.telegram;

import org.junit.Test;
import org.telegram.telegrambots.api.objects.Message;

import static org.assertj.core.api.Assertions.assertThat;

public class DeafTelegramBotTest {

    @Test
    public void say() {
        TelegramBot telegramBot = TelegramBot.takeMe(DeafTelegramBot.class, "firstOne123bot");
        Message message = telegramBot.say("Hello, man");
        assertThat(message).isNotNull();
        assertThat(message.getMessageId()).isNotNull();
    }

}
