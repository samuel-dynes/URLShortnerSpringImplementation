package com.shortUrl.URLShortner.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO to represent a response after creating a shortened URL
 */
@Getter
@Setter
@AllArgsConstructor
public class ShortenedUrlResponseDto {
    private String shortenedUrl;
}
