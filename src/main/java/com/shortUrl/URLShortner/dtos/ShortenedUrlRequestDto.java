package com.shortUrl.URLShortner.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO to represent a request to create a shortened URL
 */
@Getter
@Setter
@AllArgsConstructor
public class ShortenedUrlRequestDto {
    private String fullUrl;
}
