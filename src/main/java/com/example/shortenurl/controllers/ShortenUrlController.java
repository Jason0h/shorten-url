package com.example.shortenurl.controllers;

import com.example.shortenurl.models.Url;
import com.example.shortenurl.models.UrlResponse;
import com.example.shortenurl.models.UrlStatsResponse;
import com.example.shortenurl.services.ShortenUrlService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ShortenUrlController {
    private final ShortenUrlService shortenUrlService;

    public ShortenUrlController(ShortenUrlService shortenUrlService) {
        this.shortenUrlService = shortenUrlService;
    }

    @PostMapping("/shorten")
    public UrlResponse createShortUrl(@RequestBody Url longUrl) {
        return shortenUrlService.createShortUrl(longUrl.getUrl());
    }

    @GetMapping("/shorten/{shortUrl}")
    public UrlResponse retrieveLongUrl(@PathVariable String shortUrl) {
        return shortenUrlService.retrieveLongUrl(shortUrl);
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> redirectToLongUrl(@PathVariable String shortUrl) {
        UrlResponse urlResponse = shortenUrlService.retrieveLongUrl(shortUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, urlResponse.getUrl());  // Add redirect URL to headers
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    @PostMapping("/shorten/{shortUrl}")
    public UrlResponse updateShortUrl(@PathVariable String shortUrl, @RequestBody Url longUrl) {
        return shortenUrlService.updateShortUrl(shortUrl, longUrl.getUrl());
    }

    @DeleteMapping("/shorten/{shortUrl}")
    public void deleteShortUrl(@PathVariable String shortUrl) {
        shortenUrlService.deleteShortUrl(shortUrl);
    }

    @GetMapping("/shorten/{shortUrl}/stats")
    public UrlStatsResponse getShortUrlStats(@PathVariable String shortUrl) {
        return shortenUrlService.getShortUrlStats(shortUrl);
    }
}
