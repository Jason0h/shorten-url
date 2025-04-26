package com.example.shortenurl.helpers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SecureRandomStringGeneratorTests {
    @Test
    void generateSecureAlphanumericTest() {
        for (int i = 0; i < 1000; i++) {
            String randomString = SecureRandomStringGenerator.generateSecureAlphanumeric();
            assertEquals(6, randomString.length(), randomString);
        }
    }
}
