package com.example.shortenurl.services;

import com.example.shortenurl.exceptions.LongUrlAlreadyExistsException;
import com.example.shortenurl.exceptions.ShortUrlDoesNotExistException;
import com.example.shortenurl.exceptions.UrlIdDoesNotExistException;
import com.example.shortenurl.helpers.SecureRandomStringGenerator;
import com.example.shortenurl.models.UrlInfo;
import com.example.shortenurl.models.UrlResponse;
import com.example.shortenurl.models.UrlStatsResponse;
import com.example.shortenurl.repositories.ShortenUrlRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ShortenUrlService {
    private final ShortenUrlRepository shortenUrlRepository;

    public ShortenUrlService(ShortenUrlRepository shortenUrlRepository) {
        this.shortenUrlRepository = shortenUrlRepository;
    }

    public UrlResponse createShortUrl(String longUrl) {
        if (shortenUrlRepository.existsByUrl(longUrl)) throw new LongUrlAlreadyExistsException();
        String randomStr = SecureRandomStringGenerator.generateSecureAlphanumeric();
        while (shortenUrlRepository.existsByShortcode(randomStr)) {
            randomStr = SecureRandomStringGenerator.generateSecureAlphanumeric();
        }
        UrlInfo urlInfo = new UrlInfo();
        urlInfo.setUrl(longUrl);
        urlInfo.setShortcode(randomStr);
        urlInfo.setCreatedAt(LocalDateTime.now());
        urlInfo.setUpdatedAt(LocalDateTime.now());
        shortenUrlRepository.save(urlInfo);
        return shortenUrlRepository.findById(urlInfo.getId())
                .orElseThrow(UrlIdDoesNotExistException::new);
    }

    public UrlResponse retrieveLongUrl(String shortUrl) {
        UrlInfo urlInfo = shortenUrlRepository.findByShortcode(shortUrl)
                .orElseThrow(ShortUrlDoesNotExistException::new);
        urlInfo.setAccessCount(urlInfo.getAccessCount() + 1);
        urlInfo.setUpdatedAt(LocalDateTime.now());
        shortenUrlRepository.save(urlInfo);
        return shortenUrlRepository.findById(urlInfo.getId())
                .orElseThrow(UrlIdDoesNotExistException::new);
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
