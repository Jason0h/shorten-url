package com.example.shortenurl.helpers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LongUrlValidatorTest {
    @Test
    void longUrlValidatorTest() {
        String validLongUrl = "https://www.google.com/search?q=dogs";
        assertFalse(LongUrlValidator.isNotValid(validLongUrl));
        String invalidLongUrl = "https:/www.google.com/search?q=dogs";
        assertTrue(LongUrlValidator.isNotValid(invalidLongUrl));
    }
}
