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
    public UrlResponse createShortCode(@RequestBody Url longUrl) {
        return shortenUrlService.createShortCode(longUrl.getUrl());
    }

    @GetMapping("/shorten/{shortCode}")
    public UrlResponse retrieveLongUrl(@PathVariable String shortCode) {
        return shortenUrlService.retrieveLongUrl(shortCode);
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirectToLongUrl(@PathVariable String shortCode) {
        UrlResponse urlResponse = shortenUrlService.retrieveLongUrl(shortCode);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, urlResponse.getUrl());
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    @PostMapping("/shorten/{shortCode}")
    public UrlResponse updateShortCode(@PathVariable String shortCode, @RequestBody Url longUrl) {
        return shortenUrlService.updateShortCode(shortCode, longUrl.getUrl());
    }

    @DeleteMapping("/shorten/{shortCode}")
    public void deleteShortCode(@PathVariable String shortCode) {
        shortenUrlService.deleteShortCode(shortCode);
    }

    @GetMapping("/shorten/{shortCode}/stats")
    public UrlStatsResponse getShortCodeStats(@PathVariable String shortCode) {
        return shortenUrlService.getShortCodeStats(shortCode);
    }
}
