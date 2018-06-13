package by.mrj.bots.telegram;

import org.junit.Test;

public class DeafTelegramBotTest {

    @Test
    public void say() {
        TelegramBot telegramBot = TelegramBot.takeMe(DeafTelegramBot.class, "firstOne123bot");
        telegramBot.say("Hello, man");
    }

}
