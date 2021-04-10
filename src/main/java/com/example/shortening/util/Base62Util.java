package com.example.shortening.util;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Random;

@Component
public class Base62Util {

    private static char[] base62Characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

    public static String getShortUrl(List<String> shortUrls) {

        String shortUrl = "";
        while (true) {
            shortUrl = makeShortUrl();
            if(!shortUrls.contains(shortUrl)) {
                break;
            }
        }
        return shortUrl;
    }

    private static String makeShortUrl() {
        int len = (int) ((Math.random() * 8) + 1);
        int base62Len = base62Characters.length;
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < len; i++) {
            sb.append(base62Characters[new Random().nextInt(base62Len)]);
        }

        return sb.toString();
    }
}
