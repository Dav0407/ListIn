package com.igriss.ListIn.telegram_bot;

import com.igriss.ListIn.user.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Collections;

@Component
public class ListInBot extends TelegramLongPollingBot {

    @Value("${application.bot.token}")
    private String botToken;

    @Value("${application.bot.username}")
    private String botUsername;

    @Value("${application.deeplink.base_url}") // Add this to application.properties
    private String deepLinkBaseUrl;

    private final UserService userService;

    public ListInBot(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.hasContact()) {
                String phoneNumber = message.getContact().getPhoneNumber();
                String userId = message.getFrom().getId().toString();

                // Store user data in backend and generate token
                String token = userService.storePhoneNumber(userId, phoneNumber);

                // Send deep link button
                sendDeepLinkButton(message.getChatId(), token);
            } else if (message.hasText() && message.getText().equals("/start")) {
                sendPhoneRequestKeyboard(message.getChatId());
            }
        }
    }

    private void sendPhoneRequestKeyboard(Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("📲 Please share your phone number.");

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        KeyboardButton button = new KeyboardButton("📞 Share Phone Number");
        button.setRequestContact(true);
        KeyboardRow row = new KeyboardRow();
        row.add(button);

        keyboardMarkup.setKeyboard(Collections.singletonList(row));
        keyboardMarkup.setResizeKeyboard(true);

        message.setReplyMarkup(keyboardMarkup);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendDeepLinkButton(Long chatId, String token) {
        String deepLink = deepLinkBaseUrl + "/deeplink?token=" + token;

        InlineKeyboardButton button = new InlineKeyboardButton("🚀 Open App");
        button.setUrl(deepLink);

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(Collections.singletonList(Collections.singletonList(button)));

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("✅ Your phone number has been received! Click below to continue:");
        message.setReplyMarkup(markup);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
