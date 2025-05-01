package com.example.shortenurl.helpers;

import java.net.MalformedURLException;
import java.net.URL;

public class LongUrlValidator {
    public static boolean isValid(String longUrl) {
        try {
            URL parsedLongUrl = new URL(longUrl);
            String protocol = parsedLongUrl.getProtocol();
            if (!protocol.equals("http") && !protocol.equals("https")) {
                return false;
            }
            String host = parsedLongUrl.getHost();
            if (host == null || host.isEmpty()) {
                return false;
            }
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }
}
