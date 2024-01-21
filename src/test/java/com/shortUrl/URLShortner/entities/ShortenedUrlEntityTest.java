package com.shortUrl.URLShortner.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShortenedUrlEntityTest {

    @Test
    void testConstructorAndGetters() {
        String shortenedUrl = "https://short.url/abc123";
        String fullUrl = "https://www.example.com";
        Long usageCount = 0L;

        ShortenedUrlEntity entity = new ShortenedUrlEntity(0L, shortenedUrl, fullUrl, usageCount);

        assertNotNull(entity);
        assertEquals(shortenedUrl, entity.getShortenedUrl());
        assertEquals(fullUrl, entity.getFullUrl());
        assertEquals(usageCount, entity.getUsageCount());
    }

    @Test
    void testIncrementUsageCount() {
        ShortenedUrlEntity entity = new ShortenedUrlEntity();
        entity.setUsageCount(0L);

        entity.incrementUsageCount();

        assertEquals(1L, entity.getUsageCount());
    }
}
