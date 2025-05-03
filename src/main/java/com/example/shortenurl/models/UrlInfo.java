package com.example.shortenurl.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UrlInfo extends UrlStatsResponse {
    public UrlResponse toUrlResponse() {
        UrlResponse urlResponse = new UrlResponse();
        urlResponse.setId(getId());
        urlResponse.setUrl(getUrl());
        urlResponse.setShortcode(getShortcode());
        urlResponse.setUpdatedAt(getUpdatedAt());
        urlResponse.setCreatedAt(getCreatedAt());
        return urlResponse;
    }
}