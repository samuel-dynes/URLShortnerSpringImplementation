package com.shortUrl.URLShortner.dtos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShortenedUrlResponseDtoTest {

    private static final String SHORTENED_URL = "https://short.url/abc123";

    @Test
    void constructionAndGettersWorkAsExpected() {
        ShortenedUrlResponseDto responseDto = new ShortenedUrlResponseDto(SHORTENED_URL);

        assertNotNull(responseDto);
        assertEquals(SHORTENED_URL, responseDto.getShortenedUrl());
    }

    @Test
    void constructionAndSettersWorkAsExpected() {
        ShortenedUrlResponseDto responseDto = new ShortenedUrlResponseDto("");

        responseDto.setShortenedUrl(SHORTENED_URL);

        assertEquals(SHORTENED_URL, responseDto.getShortenedUrl());
    }
}
