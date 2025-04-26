package com.example.shortenurl.repositories;

import com.example.shortenurl.models.UrlInfo;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ShortenUrlRepository extends CrudRepository<UrlInfo, Long> {
    boolean existsByShortcode(String shortcode);
    boolean existsByUrl(String url);
    Optional<UrlInfo> findByShortcode(String shortcode);
}
