package com.example.shortenurl.models;

public class UrlStatsResponse extends UrlResponse {
    private long accessCount;

    public long getAccessCount() {
        return accessCount;
    }

    public void setAccessCount(long accessCount) {
        this.accessCount = accessCount;
    }
}
