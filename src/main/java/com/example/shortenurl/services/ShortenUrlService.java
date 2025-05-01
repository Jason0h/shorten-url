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
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ShortenUrlService {
    private final ShortenUrlRepository shortenUrlRepository;

    public ShortenUrlService(ShortenUrlRepository shortenUrlRepository) {
        this.shortenUrlRepository = shortenUrlRepository;
    }

    public UrlResponse createShortCode(String longUrl) {
        if (shortenUrlRepository.existsByUrl(longUrl)) throw new LongUrlAlreadyExistsException();
        if (!LongUrlValidator.isValid(longUrl)) throw new LongUrlIsNotValidException();
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

    public UrlResponse retrieveLongUrl(String shortCode) {
        UrlInfo urlInfo = shortenUrlRepository.findByShortcode(shortCode)
                .orElseThrow(ShortCodeDoesNotExistException::new);
        urlInfo.setAccessCount(urlInfo.getAccessCount() + 1);
        shortenUrlRepository.save(urlInfo);
        return shortenUrlRepository.findById(urlInfo.getId())
                .orElseThrow(UrlIdDoesNotExistException::new);
    }

    public UrlResponse updateShortCode(String shortCode, String longUrl) {
        if (!LongUrlValidator.isValid(longUrl)) throw new LongUrlIsNotValidException();
        UrlInfo urlInfo = shortenUrlRepository.findByShortcode(shortCode)
                .orElseThrow(ShortCodeDoesNotExistException::new);
        urlInfo.setUrl(longUrl);
        urlInfo.setUpdatedAt(LocalDateTime.now());
        shortenUrlRepository.save(urlInfo);
        return shortenUrlRepository.findById(urlInfo.getId())
                .orElseThrow(UrlIdDoesNotExistException::new);
    }

    public void deleteShortCode(String shortCode) {
        try {
            shortenUrlRepository.deleteByShortcode(shortCode);
        } catch (EmptyResultDataAccessException e) {
            throw new ShortCodeDoesNotExistException();
        }
    }

    public UrlStatsResponse getShortCodeStats(String shortCode) {
        UrlInfo urlInfo = shortenUrlRepository.findByShortcode(shortCode)
                .orElseThrow(ShortCodeDoesNotExistException::new);
        urlInfo.setAccessCount(urlInfo.getAccessCount() + 1);
        shortenUrlRepository.save(urlInfo);
        return shortenUrlRepository.findById(urlInfo.getId())
                .orElseThrow(UrlIdDoesNotExistException::new);
    }
}
