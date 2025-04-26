package com.example.shortenurl.models;

import org.springframework.data.annotation.Id;

public class UrlInfo extends UrlStatsResponse {
    @Id
    private long id;
}