package com.example.shortenurl.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UrlResponse {
    @Id
    private long id;
    private String url;
    private String shortcode;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
