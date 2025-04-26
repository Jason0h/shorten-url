package com.example.shortenurl.controllers;

import com.example.shortenurl.models.Url;
import com.example.shortenurl.models.UrlResponse;
import com.example.shortenurl.models.UrlStatsResponse;
import com.example.shortenurl.services.ShortenUrlService;
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
    public String redirectToLongUrl(@PathVariable String shortUrl) {
        UrlResponse urlResponse = shortenUrlService.retrieveLongUrl(shortUrl);
        return "redirect:" + urlResponse.getUrl();
    }

    @PostMapping("/shorten/{shortUrl}")
    public UrlResponse updateShortUrl(@PathVariable String shortUrl) {
        return shortenUrlService.updateShortUrl(shortUrl);
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
