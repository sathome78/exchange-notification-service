package me.exrates.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor(access = AccessLevel.NONE)
public class MessageFormatterUtil {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String format(String name, String email, String telegram, String text) {
        return "<b>Date:</b> " + LocalDateTime.now().format(FORMATTER) + "<br>" +
                "<b>Name:</b> " + name + "<br>" +
                "<b>Email:</b> " + email + "<br>" +
                "<b>Telegram:</b> " + telegram + "<br>" +
                "<b>Text:</b> " + text;
    }
}