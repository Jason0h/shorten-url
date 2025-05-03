package com.example.shortenurl.services;

import com.example.shortenurl.exceptions.LongUrlAlreadyExistsException;
import com.example.shortenurl.exceptions.LongUrlIsNotValidException;
import com.example.shortenurl.exceptions.ShortCodeDoesNotExistException;
import com.example.shortenurl.models.UrlResponse;
import com.example.shortenurl.models.UrlStatsResponse;
import com.example.shortenurl.repositories.ShortenUrlRepository;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ShortenUrlServiceTest {
    private final ShortenUrlService shortenUrlService;
    private final ShortenUrlRepository shortenUrlRepository;

    private final static String longUrl = "https://google.com/search?q=dogs";
    private static String tempShortCode;

    @Autowired
    public ShortenUrlServiceTest(ShortenUrlService shortenUrlService, ShortenUrlRepository shortenUrlRepository) {
        this.shortenUrlService = shortenUrlService;
        this.shortenUrlRepository = shortenUrlRepository;
    }

    @Test
    @Order(1)
    void createShortCodeHappyFlow() {
        UrlResponse urlResponse = shortenUrlService.createShortCode(longUrl);
        assertEquals(longUrl, urlResponse.getUrl());
        assertEquals(1, urlResponse.getId());
        tempShortCode = urlResponse.getShortcode();
    }

    @Test
    @Order(2)
    void createShortCodeBadFlow() {
        assertThrows(LongUrlAlreadyExistsException.class, () -> shortenUrlService.createShortCode(longUrl));
        String badLongUrl = "https:/google.com/search?q=dogs";
        assertThrows(LongUrlIsNotValidException.class, () -> shortenUrlService.createShortCode(badLongUrl));
    }

    @Test
    @Order(3)
    void retrieveLongUrlHappyFlow() {
        UrlResponse urlResponse = shortenUrlService.retrieveLongUrl(tempShortCode);
        assertEquals(longUrl, urlResponse.getUrl());
    }

    @Test
    @Order(4)
    void retrieveLongUrlBadFlow() {
        String badShortCode = "badShortCode";
        assertThrows(ShortCodeDoesNotExistException.class, () -> shortenUrlService.retrieveLongUrl(badShortCode));
    }

    @Test
    @Order(5)
    void updateShortCodeHappyFlow() {
        String newLongUrl = "https://google.com/search?q=cats";
        UrlResponse urlResponse = shortenUrlService.updateShortCode(tempShortCode, newLongUrl);
        assertEquals(newLongUrl, urlResponse.getUrl());
        assertEquals(1, urlResponse.getId());
        assertEquals(tempShortCode, urlResponse.getShortcode());
        urlResponse = shortenUrlService.updateShortCode(tempShortCode, longUrl);
        assertEquals(longUrl, urlResponse.getUrl());
        assertEquals(1, urlResponse.getId());
        assertEquals(tempShortCode, urlResponse.getShortcode());
        assertEquals(1, shortenUrlRepository.count());
    }

    @Test
    @Order(6)
    void updateShortCodeBadFlow() {
        assertThrows(LongUrlAlreadyExistsException.class,
                () -> shortenUrlService.updateShortCode(tempShortCode, longUrl));
        String badLongUrl = "https:/google.com/search?q=dogs";
        assertThrows(LongUrlIsNotValidException.class,
                () -> shortenUrlService.updateShortCode(tempShortCode, badLongUrl));
    }

    @Test
    @Order(7)
    void deleteShortCodeHappyFlow() {
        shortenUrlService.deleteShortCode(tempShortCode);
        assertThrows(ShortCodeDoesNotExistException.class, () -> shortenUrlService.retrieveLongUrl(tempShortCode));
        assertEquals(0, shortenUrlRepository.count());
        UrlResponse urlResponse = shortenUrlService.createShortCode(longUrl);
        tempShortCode = urlResponse.getShortcode();
    }

    @Test
    @Order(8)
    void deleteShortCodeBadFlow() {
        String badShortCode = "badShortCode";
        assertThrows(ShortCodeDoesNotExistException.class, () -> shortenUrlService.deleteShortCode(badShortCode));
        assertEquals(1, shortenUrlRepository.count());
    }

    @Test
    @Order(9)
    void getShortCodeStatsHappyFlow() {
        shortenUrlService.retrieveLongUrl(tempShortCode);
        UrlStatsResponse urlStatsResponse = shortenUrlService.getShortCodeStats(tempShortCode);
        assertEquals(2, urlStatsResponse.getAccessCount());
    }

    @Test
    @Order(10)
    void getShortCodeStatsBadFlow() {
        String badShortCode = "badShortCode";
        assertThrows(ShortCodeDoesNotExistException.class, () -> shortenUrlService.getShortCodeStats(badShortCode));
    }
}
