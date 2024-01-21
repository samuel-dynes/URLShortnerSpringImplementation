package com.shortUrl.URLShortner.controllers;

import com.shortUrl.URLShortner.dtos.ShortenedUrlRequestDto;
import com.shortUrl.URLShortner.dtos.ShortenedUrlResponseDto;
import com.shortUrl.URLShortner.service.UrlShortenerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ShortenedUrlControllerTest {

    private final static String SHORTENED_URL = "shortened-url";
    private final static String EXAMPLE_URL = "https://www.example.com";

    private final UrlShortenerService urlShortenerService = mock(UrlShortenerService.class);

    private ShortenedUrlController controller;

    @BeforeEach
    void setUp() {
        controller = new ShortenedUrlController(urlShortenerService);
    }

    @Test
    void testShortenUrlSuccessful() {
        ShortenedUrlResponseDto responseDto = new ShortenedUrlResponseDto(SHORTENED_URL);

        when(urlShortenerService.createShortUrl(ArgumentMatchers.any(ShortenedUrlRequestDto.class))).thenReturn(responseDto);
        ResponseEntity<String> response = controller.shortenUrl(EXAMPLE_URL);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(SHORTENED_URL, response.getBody());
    }

    @Test
    void testShortenUrlFailing() {

        when(urlShortenerService.createShortUrl(new ShortenedUrlRequestDto(EXAMPLE_URL))).thenReturn(null);

        ResponseEntity<String> response = controller.shortenUrl(EXAMPLE_URL);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Failed to shorten the URL.", response.getBody());
    }

    @Test
    void testExpandUrlSuccessful() {

        when(urlShortenerService.getFullUrl(SHORTENED_URL)).thenReturn(EXAMPLE_URL);

        ResponseEntity<String> response = controller.expandUrl(SHORTENED_URL);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(EXAMPLE_URL, response.getBody());
    }

    @Test
    void testExpandUrlFailing() {
        String shortUrl = "non-existent-shortened-url";

        when(urlShortenerService.getFullUrl(shortUrl)).thenReturn(null);

        ResponseEntity<String> response = controller.expandUrl(shortUrl);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("The entered shortened URL does not exist, ensure it has been entered correctly.", response.getBody());
    }
}
