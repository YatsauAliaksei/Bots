package by.mrj.bots;

import com.google.common.base.Preconditions;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@Slf4j
public abstract class TelegramBot extends TelegramLongPollingBot {

    static {
        ApiContextInitializer.init();
    }

    private static final long DEFAULT_CHAT_ID = -286755964L;
    private static Map<String, TelegramBot> nameToBot = new HashMap<>();
    private static Properties props = initProps();

    @NonNull
    private String botUsername;
    @NonNull
    private String botToken;

    protected TelegramBot(DefaultBotOptions options) {
        super(options);
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @SneakyThrows
    public Message say(long chatId, String message) {
        return execute(new SendMessage(chatId, message));
    }

    @SneakyThrows
    public Message say(String message) {
        return execute(new SendMessage(DEFAULT_CHAT_ID, message));
    }

    public abstract void onUpdateReceived(Update update);

    @Override
    public void clearWebhook() throws TelegramApiRequestException {}

    private void registerBot() throws TelegramApiRequestException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        telegramBotsApi.registerBot(this);
        log.info("Bot {} has been registered successfully", botUsername);
    }

    /**
     * One bot with name {@param botUsername} could be created.
     * @param clazz - bot type
     */
    @SneakyThrows
    public static TelegramBot takeMe(Class<? extends TelegramBot> clazz, String botUsername) {
        Preconditions.checkArgument(clazz != null, "Bot class mandatory field.");

        // no synchronization. simplest solution.
        TelegramBot telegramBot = nameToBot.getOrDefault(botUsername, createTelegramBot(clazz, botUsername));
        nameToBot.putIfAbsent(botUsername, telegramBot);

        return telegramBot;
    }

    @SneakyThrows
    private static TelegramBot createTelegramBot(Class<? extends TelegramBot> clazz, String botUsername) {
        String botToken = props.getProperty(botUsername + ".token");

        TelegramBot telegramBot = clazz.getDeclaredConstructor(String.class, String.class).newInstance(botUsername, botToken);
        telegramBot.registerBot();
        return telegramBot;
    }

    @SneakyThrows
    private static Properties initProps() {
        Properties props = new Properties();
        props.load(TelegramBot.class.getResourceAsStream("/bots.exclude.properties")); // tip: add "*.exclude.*" to .gitignore
        return props;
    }
}
