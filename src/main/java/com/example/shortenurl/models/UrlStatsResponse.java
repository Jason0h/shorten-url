package com.example.shortenurl.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UrlStatsResponse extends UrlResponse {
    private long accessCount;
}
