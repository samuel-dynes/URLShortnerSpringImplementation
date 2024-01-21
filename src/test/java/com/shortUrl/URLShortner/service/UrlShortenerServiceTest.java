package com.shortUrl.URLShortner.service;

import com.shortUrl.URLShortner.components.ShortenedUrlComponent;
import com.shortUrl.URLShortner.configuration.ShortenedUrlConfiguration;
import com.shortUrl.URLShortner.dtos.ShortenedUrlRequestDto;
import com.shortUrl.URLShortner.dtos.ShortenedUrlResponseDto;
import com.shortUrl.URLShortner.entities.ShortenedUrlEntity;
import com.shortUrl.URLShortner.repositories.ShortenedUrlRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UrlShortenerServiceTest {

    private final static String FULL_URL = "https://www.example.com";
    private final static String SHORT_URL = "abc123";
    private final static String URL_START = "https://www.shortUrl.com/";

    private final static String PERMITTED_CHARS = "abcdefghijklmnopqrstuvwxyz123";

    private final static int DEFAULT_LENGTH = 6;
    @Mock
    private ShortenedUrlRepository repository;

    @Mock
    private ShortenedUrlComponent component;

    @Mock
    private ShortenedUrlConfiguration configuration;

    @InjectMocks
    private UrlShortenerService service;

    @Test
    void findByFullUrlReturnsAccurateUrlWhenItsNew() {
        ShortenedUrlRequestDto requestDto = new ShortenedUrlRequestDto(FULL_URL);
        when(repository.findByFullUrl(FULL_URL)).thenReturn(null);
        when(configuration.getLength()).thenReturn(DEFAULT_LENGTH);
        when(configuration.getPermissableChars()).thenReturn(PERMITTED_CHARS);
        when(configuration.getUrlStart()).thenReturn(URL_START);
        when(component.generateShortenedUrl(DEFAULT_LENGTH, PERMITTED_CHARS)).thenReturn(SHORT_URL);

        ShortenedUrlResponseDto responseDto = service.createShortUrl(requestDto);

        assertNotNull(responseDto);
        assertEquals(URL_START + SHORT_URL, responseDto.getShortenedUrl());
    }

    @Test
    void findByFullUrlReturnsAccurateUrlWhenItAlreadyExists() {
        ShortenedUrlRequestDto requestDto = new ShortenedUrlRequestDto(FULL_URL);
        ShortenedUrlEntity existingEntity = new ShortenedUrlEntity();
        existingEntity.setShortenedUrl(SHORT_URL);
        existingEntity.setFullUrl(FULL_URL);
        existingEntity.setUsageCount(0L);

        when(repository.findByFullUrl(FULL_URL)).thenReturn(existingEntity);
        ShortenedUrlResponseDto responseDto = service.createShortUrl(requestDto);

        assertNotNull(responseDto);
        assertEquals(SHORT_URL, responseDto.getShortenedUrl());
    }

    @Test
    void findByShortenedUrlReturnsAccurateUrlWhenItAlreadyExists() {
        ShortenedUrlEntity existingShortenedUrl = new ShortenedUrlEntity();
        existingShortenedUrl.setUsageCount(0L);
        existingShortenedUrl.setFullUrl(FULL_URL);

        when(repository.findByShortenedUrl(SHORT_URL)).thenReturn(existingShortenedUrl);
        String returnedUrl = service.getFullUrl(SHORT_URL);

        assertNotNull(returnedUrl);
        assertEquals(FULL_URL, returnedUrl);
    }

    @Test
    void getFullUrlReturnsNullWhenUrlDoesntExist() {
        when(repository.findByShortenedUrl(SHORT_URL)).thenReturn(null);

        String fullUrl = service.getFullUrl(SHORT_URL);

        assertNull(fullUrl);
    }
}
