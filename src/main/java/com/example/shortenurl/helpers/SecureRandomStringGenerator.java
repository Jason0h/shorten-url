package com.example.shortenurl.helpers;

import java.math.BigInteger;
import java.security.SecureRandom;

public class SecureRandomStringGenerator {
    public static String generateSecureAlphanumeric() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[4];
        random.nextBytes(bytes);
        String string = String.format("%.6s", new BigInteger(1, bytes).toString(36));
        return String.format("%6s", string);
    }
}
