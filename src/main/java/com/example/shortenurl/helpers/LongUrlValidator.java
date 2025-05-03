package com.example.shortenurl.helpers;

import java.net.MalformedURLException;
import java.net.URL;

public class LongUrlValidator {
    public static boolean isNotValid(String longUrl) {
        try {
            URL parsedLongUrl = new URL(longUrl);
            String protocol = parsedLongUrl.getProtocol();
            if (!protocol.equals("http") && !protocol.equals("https")) {
                return true;
            }
            String host = parsedLongUrl.getHost();
            if (host == null || host.isEmpty()) {
                return true;
            }
            return false;
        } catch (MalformedURLException e) {
            return true;
        }
    }
}
