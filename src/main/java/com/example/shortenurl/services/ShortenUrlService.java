package com.example.shortenurl.services;

import com.example.shortenurl.exceptions.LongUrlExistsException;
import com.example.shortenurl.exceptions.ShortUrlDoesNotExistException;
import com.example.shortenurl.helpers.SecureRandomStringGenerator;
import com.example.shortenurl.models.UrlInfo;
import com.example.shortenurl.models.UrlResponse;
import com.example.shortenurl.models.UrlStatsResponse;
import com.example.shortenurl.repositories.ShortenUrlRepository;
import org.springframework.stereotype.Service;

@Service
public class ShortenUrlService {
    private final ShortenUrlRepository shortenUrlRepository;

    public ShortenUrlService(ShortenUrlRepository shortenUrlRepository) {
        this.shortenUrlRepository = shortenUrlRepository;
    }

    public UrlResponse createShortUrl(String longUrl) {
        if (shortenUrlRepository.existsByUrl(longUrl)) throw new LongUrlExistsException();
        String randomStr = SecureRandomStringGenerator.generateSecureAlphanumeric();
        while (shortenUrlRepository.existsByShortcode(randomStr)) {
            randomStr = SecureRandomStringGenerator.generateSecureAlphanumeric();
        }
        UrlInfo urlInfo = new UrlInfo();
        urlInfo.setUrl(longUrl);
        urlInfo.setShortcode(randomStr);
        shortenUrlRepository.save(urlInfo);
        return urlInfo;
    }

    public UrlResponse retrieveLongUrl(String shortUrl) {
        return shortenUrlRepository.findByShortcode(shortUrl)
                .orElseThrow(ShortUrlDoesNotExistException::new);
    }

    public UrlResponse updateShortUrl(String shortUrl) {
        return new UrlResponse();
    }

    public void deleteShortUrl(String shortUrl) {

    }

    public UrlStatsResponse getShortUrlStats(String shortUrl) {
        return new UrlStatsResponse();
    }
}
