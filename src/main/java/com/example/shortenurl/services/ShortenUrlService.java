package com.example.shortenurl.services;

import com.example.shortenurl.exceptions.LongUrlAlreadyExistsException;
import com.example.shortenurl.exceptions.LongUrlIsNotValidException;
import com.example.shortenurl.exceptions.ShortCodeDoesNotExistException;
import com.example.shortenurl.exceptions.UrlIdDoesNotExistException;
import com.example.shortenurl.helpers.LongUrlValidator;
import com.example.shortenurl.helpers.SecureRandomStringGenerator;
import com.example.shortenurl.models.UrlInfo;
import com.example.shortenurl.models.UrlResponse;
import com.example.shortenurl.models.UrlStatsResponse;
import com.example.shortenurl.repositories.ShortenUrlRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class ShortenUrlService {
    private final ShortenUrlRepository shortenUrlRepository;

    public ShortenUrlService(ShortenUrlRepository shortenUrlRepository) {
        this.shortenUrlRepository = shortenUrlRepository;
    }

    public UrlResponse createShortCode(String longUrl) {
        if (shortenUrlRepository.existsByUrl(longUrl)) throw new LongUrlAlreadyExistsException(longUrl);
        if (LongUrlValidator.isNotValid(longUrl)) throw new LongUrlIsNotValidException(longUrl);
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
        urlInfo = shortenUrlRepository.findByShortcode(randomStr)
                .orElseThrow(RuntimeException::new);
        return urlInfo.toUrlResponse();
    }

    public UrlResponse retrieveLongUrl(String shortCode) {
        UrlInfo urlInfo = shortenUrlRepository.findByShortcode(shortCode)
                .orElseThrow(() -> new ShortCodeDoesNotExistException(shortCode));
        urlInfo.setAccessCount(urlInfo.getAccessCount() + 1);
        shortenUrlRepository.save(urlInfo);
        long id = urlInfo.getId();
        urlInfo = shortenUrlRepository.findById(id)
                .orElseThrow(() -> new UrlIdDoesNotExistException(id));
        return urlInfo.toUrlResponse();
    }

    public UrlResponse updateShortCode(String shortCode, String longUrl) {
        if (shortenUrlRepository.existsByUrl(longUrl)) throw new LongUrlAlreadyExistsException(longUrl);
        if (LongUrlValidator.isNotValid(longUrl)) throw new LongUrlIsNotValidException(longUrl);
        UrlInfo urlInfo = shortenUrlRepository.findByShortcode(shortCode)
                .orElseThrow(() -> new ShortCodeDoesNotExistException(shortCode));
        urlInfo.setUrl(longUrl);
        urlInfo.setUpdatedAt(LocalDateTime.now());
        shortenUrlRepository.save(urlInfo);
        long id = urlInfo.getId();
        urlInfo = shortenUrlRepository.findById(id)
                .orElseThrow(() -> new UrlIdDoesNotExistException(id));
        return urlInfo.toUrlResponse();
    }

    public void deleteShortCode(String shortCode) {
        shortenUrlRepository.findByShortcode(shortCode)
                .orElseThrow(() -> new ShortCodeDoesNotExistException(shortCode));
        shortenUrlRepository.deleteByShortcode(shortCode);
    }

    public UrlStatsResponse getShortCodeStats(String shortCode) {
        UrlInfo urlInfo = shortenUrlRepository.findByShortcode(shortCode)
                .orElseThrow(() -> new ShortCodeDoesNotExistException(shortCode));
        urlInfo.setAccessCount(urlInfo.getAccessCount() + 1);
        shortenUrlRepository.save(urlInfo);
        long id = urlInfo.getId();
        return shortenUrlRepository.findById(id)
                .orElseThrow(() -> new UrlIdDoesNotExistException(id));
    }
}
