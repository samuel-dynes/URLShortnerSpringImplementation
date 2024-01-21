package com.shortUrl.URLShortner.dtos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShortenedUrlRequestDtoTest {

    private final static String EXAMPLE_URL = "https://www.example.com";

    @Test
    void constructionAndGettersWorkAsExpected() {
        ShortenedUrlRequestDto requestDto = new ShortenedUrlRequestDto(EXAMPLE_URL);

        assertNotNull(requestDto);
        assertEquals(EXAMPLE_URL, requestDto.getFullUrl());
    }

    @Test
    void constructionAndSettersWorkAsExpected() {
        ShortenedUrlRequestDto requestDto = new ShortenedUrlRequestDto("");

        requestDto.setFullUrl(EXAMPLE_URL);

        assertEquals(EXAMPLE_URL, requestDto.getFullUrl());
    }
}
